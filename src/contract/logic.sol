pragma solidity ^0.4.24;

import "browser/data.sol";

interface tokenRecipient { function receiveApproval(address _from, uint256 _value, address _token, bytes _extraData) external; }

contract BitSTDLogic {
    address public owner;
    // data layer
    BitSTDData private data;

    constructor(address dataAddress) {
    
        data = BitSTDData(dataAddress);
        owner = msg.sender;
    }
    
    // Transfer logical layer authority
    function transferAuthority(address newOwner) onlyOwner public {
    
        owner = newOwner;
    }
    modifier onlyOwner(){
    
	require(msg.sender == owner);
        _;
    }

    // Transfer data layer authority
    function transferDataAuthority(address newOwner) onlyOwner public {
        data.transferAuthority(newOwner);
    }
    function setData(address dataAddress)onlyOwner public {
        data = BitSTDData(dataAddress);
    }

    // Old contract data
    function getOldBalanceOf(address addr) constant public returns (uint256) {
    
        return data.getOldBalanceOf(addr);
    }

    /**
     * Internal transfers can only be invoked through this contract
    */
    function _transfer(address _from, address _to, uint _value) internal {
    
        uint256 f_value = balanceOf(_from);
        uint256 t_value = balanceOf(_to);
        // Prevents transmission to 0x0 address.Call to Burn ()
        require(_to != 0x0);
        // Check that the sender is adequate
        require(f_value >= _value);
        // Check the overflow
        require(t_value + _value > t_value);
        // Save it as a future assertion
        uint previousBalances = f_value + t_value;
        // Minus from the sender
        setBalanceOf(_from, f_value - _value);
        // Add to receiver
        setBalanceOf(_to, t_value + _value);
        // Assertions are used to use static analysis to detect errors in code.They should not fail
        assert(balanceOf(_from) + balanceOf(_to) == previousBalances);
    }
    
    // data migration
    function migration(address sender, address receiver) onlyOwner public returns (bool) {
    
        require(sender != receiver);
        bool result = false;
        // Start data migration
        // uint256 t_value = balanceOf(receiver);
        uint256 _value = data.getOldBalanceOf(receiver);
        //Transfer balance
        if (data.balanceOf(receiver) == 0) {
            if (_value > 0) {
                _transfer(sender, receiver, _value);
                result = true;
            }
        }
        //Frozen account migration
        if (data.getOldFrozenAccount(receiver) == true) {
            if (data.frozenAccount(receiver) != true) {
                data.setFrozenAccount(receiver, true);
            }
        }
        //End data migration
        return result;
    }

    // Check the contract token
    function balanceOf(address addr) constant public returns (uint256) {
    
        return data.balanceOf(addr);
    }

    function name() constant public returns (string) {
    
  	   return data.name();
    }

   function symbol() constant public returns(string) {
   
  	 return data.symbol();
   }

   function decimals() constant public returns(uint8) {
   
      return data.decimals();
   }

   function totalSupply() constant public returns(uint256) {
   
      return data.totalSupply();
   }

   function allowance(address authorizer, address sender) constant public returns(uint256) {
   
      return data.allowance(authorizer, sender);
   }

   function sellPrice() constant public returns (uint256) {
   
      return data.sellPrice();
   }

   function buyPrice() constant public returns (uint256) {
   
      return data.buyPrice();
   }

   function frozenAccount(address addr) constant public returns(bool) {
   
      return data.frozenAccount(addr);
   }

   //Modify the contract
   function setBalanceOf(address addr, uint256 value) onlyOwner public {
   
       data.setBalanceOfAddr(addr, value);
   }

   /**
    * Pass the token
    * send a value token to your account
   */
   function transfer(address sender, address _to, uint256 _value) onlyOwner public returns (bool) {
    
       _transfer(sender, _to, _value);
       return true;
   }

   /**
    *Passing tokens from other addresses
    *
    * sends the value token to "to", representing "from"
    *
    * @param _from sender's address
    * @param _to recipient's address
    * @param _value number sent
   */
   function transferFrom(address _from, address sender, address _to, uint256 _value) onlyOwner public returns (bool success) {
   
       uint256 a_value = data.allowance(_from, sender);
       require(_value <= a_value ); // Check allowance
       data.setAllowance(_from, sender, a_value - _value);
       _transfer(_from, _to, _value);
       return true;
   }

   /**
    * set allowances for other addresses
    *
    * allow the "spender" to spend only the "value" card in your name
    *
    * @param _spender authorized address
    * @param _value they can spend the most money
   */
   function approve(address _spender, address sender, uint256 _value) onlyOwner public returns (bool success) {
   
       data.setAllowance(sender, _spender, _value);
       return true;
   }

   /**
    * Grant and notify other addresses
    *
    * allow "spender" to only mark "value" in your name and then write the contract on it.
    *
    * @param _spender authorized address
    * @param _value they can spend the most money
    * @param _extraData sends some additional information to the approved contract
   */
   function approveAndCall(address _spender, address sender, address _contract, uint256 _value, bytes _extraData) onlyOwner public returns (bool success) {
   
       tokenRecipient spender = tokenRecipient(_spender);
       if (approve(_spender, sender, _value)) {
           spender.receiveApproval(sender, _value, _contract, _extraData);
           return true;
       }
   }

   /**
    * Destroy the tokens,
    *
    * delete "value" tokens from the system
    *
    * param _value the amount of money to burn
   */
   function burn(address sender, uint256 _value) onlyOwner public returns (bool success) {
   
       uint256 f_value = balanceOf(sender);
       require(f_value >= _value);                 // Check that the sender is adequate
       setBalanceOf(sender, f_value - _value);    // Minus from the sender
       data.addTotalSupply(totalSupply() - _value);                      // Renewal aggregate supply
       return true;
   }

   /**
    * Destroy tokens from other accounts
    *
    * delete "value" tokens from "from" in the system.
    *
    * @param _from the address of the sender
    * param _value the amount of money to burn
   */
   function burnFrom(address _from, address sender, uint256 _value) onlyOwner public returns (bool success) {
   
       uint256 f_value = balanceOf(sender);
       uint256 a_value = data.allowance(_from, sender);
       require(f_value >= _value);                             // Check that the target balance is adequate
       require(_value <= a_value);                             // Check the allowance
       setBalanceOf(_from, f_value - _value);                // Subtract from the goal balance
       data.setAllowance(_from, sender, f_value - _value);  // Minus the sender's allowance
       data.addTotalSupply(totalSupply() - _value);         // update totalSupply
       return true;
   }

   //@ notifies you to create the mintedAmount token and send it to the target
   // @param target address receiving token
   // @param mintedAmount will receive the number of tokens
   function mintToken(address target, address _contract, uint256 mintedAmount) onlyOwner public {
   
       require(false);
       uint256 f_value = balanceOf(target);
       setBalanceOf(target, f_value + mintedAmount);
       data.addTotalSupply(totalSupply() + mintedAmount);
   }

   //Notice freezes the account to prevent "target" from sending and receiving tokens
   // @param target address is frozen
   // @param freezes or does not freeze
   function freezeAccount(address target, bool freeze) onlyOwner public returns (bool) {
   
       data.setFrozenAccount(target, freeze);
       return true;

   }

   // Notice of purchase of tokens by sending ether
   function buy(address _contract, address sender, uint256 value) payable public {
    
       require(false);
       uint amount = value / data.buyPrice();        // Calculate the purchase amount
       _transfer(_contract, sender, amount);              // makes the transfers
   }
   // @notice to sell the amount token
   // @param amount
   function sell(address _contract, address sender, uint256 amount) public {
   
       require(false);
       require(address(_contract).balance >= amount * data.sellPrice());      // Check if there is enough ether in the contract
       _transfer(sender, _contract, amount);              // makes the transfers
       sender.transfer(amount * data.sellPrice());          // Shipping ether to the seller.This is important to avoid recursive attacks
   }

}
