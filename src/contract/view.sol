pragma solidity ^0.4.24;

import "browser/logic.sol";

contract BitSTDView {

    BitSTDLogic private logic;
    address public owner;
    // This creates a public event on the blockchain that notifies the customer
    event Transfer(address indexed from, address indexed to, uint256 value);
    event FrozenFunds(address target, bool frozen);
    // This tells the customer how much money is being burned
    event Burn(address indexed from, uint256 value);
    
    //start Query data interface
	    function balanceOf(address add)constant  public returns (uint256) {

		return logic.balanceOf(add);
	    }

	    function name() constant  public returns (string) {

		return logic.name();
	    }

	    function symbol() constant  public returns (string) {

		return logic.symbol();
	    }

	    function decimals() constant  public returns (uint8) {

		return logic.decimals();
	    }

	    function totalSupply() constant  public returns (uint256) {

		return logic.totalSupply();
	    }

	    function allowance(address authorizer, address sender) constant  public returns (uint256) {

		return logic.allowance(authorizer, sender);
	    }

	    function sellPrice() constant  public returns (uint256) {

		return logic.sellPrice();
	    }

	    function buyPrice() constant  public returns (uint256) {

		return logic.buyPrice();
	    }

	    function frozenAccount(address addr) constant  public returns (bool) {

		return logic.frozenAccount(addr);
	    }
    //End Query data interface

    //initialize
    constructor(address logicAddressr) public {
    
    	logic=BitSTDLogic(logicAddressr);
        owner=msg.sender;
    }

    //start Authority and control
    modifier onlyOwner(){
    
    	require(msg.sender == owner);
        _;
    }

    //Update the address of the data and logic layer
    function setBitSTD(address dataAddress,address logicAddressr) onlyOwner public{
    
        logic=BitSTDLogic(logicAddressr);
        logic.setData(dataAddress);
    }

    //Hand over the logical layer authority
    function transferLogicAuthority(address newOwner) onlyOwner public{
    
        logic.transferAuthority(newOwner);
    }

    //Hand over the data layer authority
    function transferDataAuthority(address newOwner) onlyOwner public{
    
        logic.transferDataAuthority(newOwner);
    }

    //Hand over the view layer authority
    function transferAuthority(address newOwner) onlyOwner public{
    
        owner=newOwner;
    }
    //End Authority and control

    //data migration
    function migration(address addr) public {
    
        if (logic.migration(msg.sender, addr) == true) {
            emit Transfer(msg.sender, addr,logic.getOldBalanceOf(addr));
        }
    }

    /**
     * Transfer tokens
     *
     * Send `_value` tokens to `_to` from your account
     *
     * @param _to The address of the recipient
     * @param _value the amount to send
     */
    function transfer(address _to, uint256 _value) public {
    
    	if (logic.transfer(msg.sender, _to, _value) == true) {
    		emit Transfer(msg.sender, _to, _value);
    	}
    }

    /**
     * Transfer tokens from other address
     *
     * Send `_value` tokens to `_to` in behalf of `_from`
     *
     * @param _from The address of the sender
     * @param _to The address of the recipient
     * @param _value the amount to send
     */
    function transferFrom(address _from, address _to, uint256 _value) public returns (bool success) {
    
    	if (logic.transferFrom(_from, msg.sender, _to, _value) == true) {
    		emit Transfer(_from, _to, _value);
	        return true;
    	}
    }

    /**
     * Set allowance for other address
     *
     * Allows `_spender` to spend no more than `_value` tokens in your behalf
     *
     * @param _spender The address authorized to spend
     * @param _value the max amount they can spend
     */
    function approve(address _spender, uint256 _value) public returns (bool success) {
    
    	return logic.approve( _spender, msg.sender,  _value);
    }

    /**
     * Set allowance for other address and notify
     *
     * Allows `_spender` to spend no more than `_value` tokens in your behalf, and then ping the contract about it
     *
     * @param _spender The address authorized to spend
     * @param _value the max amount they can spend
     * @param _extraData some extra information to send to the approved contract
     */
    function approveAndCall(address _spender, uint256 _value, bytes _extraData) public returns (bool success) {
    
    	return logic.approveAndCall(_spender, msg.sender, this, _value, _extraData);
    }

    /**
     * Destroy tokens
     *
     * Remove `_value` tokens from the system irreversibly
     *
     * @param _value the amount of money to burn
     */
    function burn(uint256 _value) public returns (bool success) {
    
    	if (logic.burn(msg.sender, _value) == true) {
    		emit Burn(msg.sender, _value);
	        return true;
    	}
    }

    /**
     * Destroy tokens from other account
     *
     * Remove `_value` tokens from the system irreversibly on behalf of `_from`.
     *
     * @param _from the address of the sender
     * @param _value the amount of money to burn
     */
    function burnFrom(address _from, uint256 _value) public returns (bool success) {
    
    	if (logic.burnFrom( _from, msg.sender, _value) == true) {
    		emit Burn(_from, _value);
	        return true;
    	}
    }

    /// @notice Create `mintedAmount` tokens and send it to `target`
    /// @param target Address to receive the tokens
    /// @param mintedAmount the amount of tokens it will receive
    function mintToken(address target, uint256 mintedAmount) onlyOwner public {
    
    	logic.mintToken(target, this,  mintedAmount);
    	emit Transfer(0, this, mintedAmount);
        emit Transfer(this, target, mintedAmount);
    }

    /// @notice `freeze? Prevent | Allow` `target` from sending & receiving tokens
    /// @param target Address to be frozen
    /// @param freeze either to freeze it or not
    function freezeAccount(address target, bool freeze) onlyOwner public {
    
    	if (logic.freezeAccount(target,  freeze) == true) {
    		emit FrozenFunds(target, freeze);
    	}
    }

    //The next two are buying and selling tokens
    function buy() payable public {
    
    	logic.buy(this, msg.sender, msg.value);
    }

    function sell(uint256 amount) public {
    
    	logic.sell(this,msg.sender, amount);
    }
}
