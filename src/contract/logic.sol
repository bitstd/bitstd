contract BitSTDData{

    string public name;
    string public symbol;
    uint8 public decimals;
    address public owner;
    // 18 decimals is the strongly suggested default, avoid changing it
    uint256 public totalSupply;

    //An array of all balances
    mapping (address => uint256) public balanceOf;
    mapping (address => mapping (address => uint256)) public allowance;
    uint256 public sellPrice;
    uint256 public buyPrice;
    //The allowed address zhi value wei value is true
    mapping (address => bool) public owners;
    //Freeze address
    mapping (address => bool) public frozenAccount;
    function setBalanceOfr(address add,uint256 value) public {}
    function setAllowance(address add,address _add,uint256 value) public {}
    function setFrozenAccount(address add,bool value) public {}
    function addTotalSupply(uint256 value) public{}
    function setPrices(uint256 newSellPrice, uint256 newBuyPrice) public {}
    //Old contract data
    function getOld_BalanceOfr(address add)constant  public returns(uint256){}

    function getOld_Allowance(address add,address _add)constant  public returns(uint256){}

    function getOld_FrozenAccount(address add)constant  public returns(bool){}
    //Hand over the data layer authority
    function Transfer_of_authority(address newOwner) public{}
}

interface tokenRecipient { function receiveApproval(address _from, uint256 _value, address _token, bytes _extraData) external; }

contract BitSTDLogic{
    address public owner;
    //data layer
	BitSTDData private data;


    function BitSTDLogic(address dataAddress){
        data=BitSTDData(dataAddress);
        owner=msg.sender;
    }
    //Hand over the logical layer authority
    function Transfer_of_authority(address newOwner)onlyOwner public{
        owner=newOwner;
    }
	modifier onlyOwner(){
		require(msg.sender == owner);
        _;
	}
	//Hand over the data layer authority
    function Transfer_of_authority_data(address newOwner)onlyOwner public {
        data.Transfer_of_authority(newOwner);
    }
    function setData(address dataAddress)onlyOwner public {
        data=BitSTDData(dataAddress);
    }

    // Old contract data
    function getOld_BalanceOfr(address add)constant  public returns(uint256){
        return data.getOld_BalanceOfr(add);
    }

	/**
	 * Internal transfers can only be invoked through this contract
	*/
    function _transfer(address _from, address _to, uint _value) internal {
        uint256 f_value=balanceOf(_from);
        uint256 t_value=balanceOf(_to);
        // Prevents transmission to 0x0 address.Call to Burn ()
        require(_to != 0x0);
        // Check that the sender is adequate
        require(f_value >= _value);
        // Check the overflow
        require(t_value + _value > t_value);
        // Save it as a future assertion
        uint previousBalances = f_value + t_value;
        // Minus from the sender
        setBalanceOf(_from,f_value-_value);
        // Add to receiver
        setBalanceOf(_to,t_value+_value);

        // Assertions are used to use static analysis to detect errors in code.They should not fail
        assert(balanceOf(_from) +balanceOf(_to) == previousBalances);

    }
    // data migration
    function migration(address sender,address add)onlyOwner public{
        require(sender!=add);
        //Start data migration
        uint256 t_value=balanceOf(add);
        uint256 _value=data.getOld_BalanceOfr(add);
        //Transfer balance
        if(data.balanceOf(add)==0){
            if(_value>0)
            _transfer(sender,add,_value);
        }
        //Frozen account migration
        if(data.getOld_FrozenAccount(add)==true){
            if(data.frozenAccount(add)!=true)
            data.setFrozenAccount(add,true);
        }
        //End data migration
    }

    function migration(address sender,address add,uint256 _value)onlyOwner public{
            require(sender!=add);
            //Start data migration
            uint256 t_value=balanceOf(add);
            //Transfer balance
            if(data.balanceOf(add)==0){
                _transfer(sender,add,_value);
            }
            //Frozen account migration
            if(data.getOld_FrozenAccount(add)==true){
                if(data.frozenAccount(add)!=true)
                data.setFrozenAccount(add,true);
            }
            //End data migration
        }

    //Check the contract token
    function balanceOf(address add)constant  public returns(uint256) {
        return data.balanceOf(add);
    }

    function name()constant  public returns(string) {
  	   return data.name();
  	}

  	function symbol()constant  public returns(string) {
  	   return data.symbol();
  	}

  	function decimals()constant  public returns(uint8) {
  	   return data.decimals();
  	}

  	function totalSupply()constant  public returns(uint256) {
  	   return data.totalSupply();
  	}

  	function allowance(address add,address _add)constant  public returns(uint256) {
  	   return data.allowance(add,_add);
  	}

  	function sellPrice()constant  public returns(uint256) {
  	   return data.sellPrice();
  	}

  	function buyPrice()constant  public returns(uint256) {
  	   return data.buyPrice();
  	}

  	function frozenAccount(address add)constant  public returns(bool) {
  	   return data.frozenAccount(add);
  	}

    //Modify the contract
    function setBalanceOf(address add,uint256 value)onlyOwner public {
        data.setBalanceOfr(add,value);
    }

    /**
     * Pass the token
     * send a value token to your account
    */
    function transfer(address sender,address _to, uint256 _value)onlyOwner public {
        _transfer(sender, _to, _value);
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
    function transferFrom(address _from,address sender, address _to, uint256 _value)onlyOwner public returns (bool success) {
        uint256 a_value=data.allowance(_from,sender);
        require(_value <=_value );     // Check allowance
        data.setAllowance(_from,sender,a_value-_value);
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
    function approve(address _spender,address sender, uint256 _value)onlyOwner public returns (bool success) {
        data.setAllowance(sender,_spender, _value);
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
    function approveAndCall(address _spender,address sender,address _contract, uint256 _value, bytes _extraData)onlyOwner public returns (bool success) {
        tokenRecipient spender = tokenRecipient(_spender);
        if (approve(_spender,sender, _value)) {
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
    function burn(address sender,uint256 _value)onlyOwner public returns (bool success) {
        uint256 f_value=balanceOf(sender);
        require(f_value >= _value);                 // Check that the sender is adequate
        setBalanceOf(sender,f_value-_value);    // Minus from the sender
        data.addTotalSupply(data.totalSupply()-_value);                      // Renewal aggregate supply
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
    function burnFrom(address _from,address sender, uint256 _value)onlyOwner public returns (bool success) {
        uint256 f_value=balanceOf(sender);
        uint256 a_value=data.allowance(_from,sender);
        require(f_value >= _value);                             // Check that the target balance is adequate
        require(_value <= a_value);                             // Check the allowance
        setBalanceOf(_from,f_value-_value);                // Subtract from the goal balance
        data.setAllowance(_from,sender,a_value-_value);  // Minus the sender's allowance
        data.addTotalSupply(data.totalSupply()-_value);         // update totalSupply

        return true;
    }

    //@ notifies you to create the mintedAmount token and send it to the target
      // @param target address receiving token
      // @param mintedAmount will receive the number of tokens
    function mintToken(address target,address _contract, uint256 mintedAmount) onlyOwner public {
        uint256 f_value=balanceOf(target);
        setBalanceOf(target,f_value+mintedAmount);
        data.addTotalSupply(data.totalSupply()+mintedAmount);

    }

    //Notice freezes the account to prevent "target" from sending and receiving tokens
      // @param target address is frozen
      // @param freezes or does not freeze
    function freezeAccount(address target, bool freeze) onlyOwner public {
        data.setFrozenAccount(target,freeze);

    }

    // Notice of purchase of tokens by sending ether
    function buy(address _contract,address sender,uint256 value) payable public {
        require(false);
        uint amount = value / data.buyPrice();        // Calculate the purchase amount
        _transfer(_contract, sender, amount);              // makes the transfers
    }
    // @notice to sell the amount token
    // @param amount
    function sell(address _contract,address sender,uint256 amount) public {
        require(false);
        require(address(_contract).balance >= amount * data.sellPrice());      // Check if there is enough ether in the contract
        _transfer(sender, _contract, amount);              // makes the transfers
        sender.transfer(amount * data.sellPrice());          // Shipping ether to the seller.This is important to avoid recursive attacks
    }

}