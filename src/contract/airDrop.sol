pragma solidity ^0.4.24;
contract ERC20{
    
    function name() constant public returns (string);
    function symbol() constant  public returns (string);
    function decimals() constant public returns (uint8);
    function totalSupply() constant public returns (uint256);
    function transfer(address _to, uint256 _value) public ;
    function balanceOf(address _owner) constant public returns (uint256 balance);
    function approve(address _spender, uint256 _value) public returns (bool success);
    function transferFrom(address _from, address _to, uint256 _value) public returns (bool success) ;
    function allowance(address _owner, address _spender) constant public returns (uint256 remaining);
}
contract Airdrop{
    
    mapping  (address => uint256) public balanceOf;
    address public owner;
    constructor() public {
        
        owner=msg.sender;
    }
    
    modifier qualification {
        
        require(msg.sender == owner);
        _;
    }
    /**
     *
     *This is a fixed airdrop
     *
     * @param _token this is Address of airdrop token contract
     * @param dsts this is Batch acceptance address
     * @param value this is Issuing number
     */
    function drop(address _token, address[] dsts, uint256 value) payable public {

        uint count = dsts.length;
        require(value > 0);
        ERC20 View = ERC20(_token);
        View.transferFrom(msg.sender,this,value*count);
        for(uint i = 0; i < count; i++){
            View.transfer(dsts[i],value);
        }
    }
    /**
     *
     * This is a multi-value airdrop
     *
     * @param contractaddress this is Address of airdrop token contract
     * @param dsts this is Batch acceptance address
     * @param values This is the distribution number array
     * @param gross This is Total number of air drops
     */
    function dropValues(address contractaddress, address[] dsts, uint256[] values, uint256 gross) payable public {

        uint count = dsts.length;
        ERC20 View = ERC20(contractaddress);
        View.transferFrom(msg.sender,this,gross);
        for(uint i = 0; i < count; i++){
            View.transfer(dsts[i],values[i]);
        }
    }
    /**
     *
     * This is a Methods for contract data migration
     *
     * @param contractaddress this is Address of airdrop token contract
     * @param dsts This is the address where the data needs to be migrated
     * @param gross This is Total number of Migration
     */
    function dataMigration(address old_contract, address contractaddress, address[] dsts, uint256 gross) payable public {
        
        uint count = dsts.length;
        ERC20 newContract = ERC20(contractaddress);
        ERC20 oldContract = ERC20(old_contract);
        newContract.transferFrom(msg.sender,this,gross);
        for(uint i = 0; i < count; i++){
           if(newContract.balanceOf(dsts[i]) == 0){
               newContract.transfer(dsts[i],oldContract.balanceOf(dsts[i]));
           }
        }
    }
    /**
     * This is a Withdrawal user donation method
     *
     * No parameters, full extraction
     */
    function withdrawaETH() public qualification {
        
        require(msg.sender == owner);                       //reconfirm
        msg.sender.transfer(address(this).balance);          //Sends the eth to the caller
    }

}
