pragma solidity ^0.4.21;

contract owned {
    address public owner;
}

contract TokenERC20 {
    // Public variables of the token
    string public name;
    string public symbol;
    uint8 public decimals = 18;
    // 18 decimals is the strongly suggested default, avoid changing it
    uint256 public totalSupply;

    // This creates an array with all balances
    mapping (address => uint256) public balanceOf;
    mapping (address => mapping (address => uint256)) public allowance;
}

contract BitSTDShares is owned, TokenERC20 {

    uint256 public sellPrice;
    uint256 public buyPrice;

    mapping (address => bool) public frozenAccount;
}

contract BitSTDData {
    // Used to control data migration
    bool public data_migration_control = true;
    address public owner;
    // Public variables of the token
    string public name;
    string public symbol;
    uint8 public decimals;
    uint256 public totalSupply;

    // An array of all balances
    mapping (address => uint256) public balanceOf;
    mapping (address => mapping (address => uint256)) public allowance;
    uint256 public sellPrice;
    uint256 public buyPrice;
    // The allowed address zhi value wei value is true
    mapping (address => bool) public owners;
    // Freeze address
    mapping (address => bool) public frozenAccount;
    BitSTDShares private bit;

    function BitSTDData(address _contractAddress) public {
        bit=BitSTDShares(_contractAddress);
        owner=msg.sender;
        name=bit.name();
        symbol=bit.symbol();
        decimals=bit.decimals();
        //totalSupply=bit.totalSupply();
        sellPrice=bit.sellPrice();
        buyPrice=bit.buyPrice();
        totalSupply=bit.totalSupply();
        balanceOf[msg.sender]=totalSupply;
    }

    modifier qualification {
        require(msg.sender == owner);
        _;
    }

    // Move the super administrator
    function Transfer_of_authority(address newOwner) public{
        require(msg.sender == owner);
        owner=newOwner;
    }

    function setBalanceOfr(address add,uint256 value)qualification public {
        balanceOf[add]=value;
    }

    function setAllowance(address add,address _add,uint256 value)qualification public {
        allowance[add][_add]=value;
    }


    function setFrozenAccount(address add,bool value)qualification public {
        frozenAccount[add]=value;
    }

    function addTotalSupply(uint256 value)qualification public{
        totalSupply=value;
    }

    function setPrices(uint256 newSellPrice, uint256 newBuyPrice) public {
        require(msg.sender == owner);
        sellPrice = newSellPrice;
        buyPrice = newBuyPrice;
    }

    // Old contract data
    function getOld_BalanceOfr(address add)constant  public returns(uint256){
       return bit.balanceOf(add);
    }
   
    
    function getOld_Allowance(address add,address _add)constant  public returns(uint256){
        return bit.allowance(add,_add);
    }

    function getOld_FrozenAccount(address add)constant  public returns(bool){
        return bit.frozenAccount(add);
    }
   
}

