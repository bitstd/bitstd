contract BitSTDData{
    string public name;
    string public symbol;
    uint8 public decimals;
    address public owner;
    // 18 decimals is the strongly suggested default, avoid changing it
    uint256 public totalSupply;
    function Transfer_of_authority(address newOwner) public{}
}
contract BitSTDLogic{
    function BitSTDLogic(address dataAddress){}
	function migration(address sender,address add) public{}
	function balanceOf(address add)constant  public returns(uint256) {}
	function transfer(address sender,address _to, uint256 _value) public {}
	function transferFrom(address _from,address sender, address _to, uint256 _value) public returns (bool success) {}
	function approve(address _spender,address sender, uint256 _value) public returns (bool success) {}
	function approveAndCall(address _spender,address sender, uint256 _value, bytes _extraData)public returns (bool success) {}
	function burn(address sender,uint256 _value) public returns (bool success) {}
	function burnFrom(address _from,address sender, uint256 _value) public returns (bool success) {}
	function mintToken(address target, uint256 mintedAmount)  public {}
	function freezeAccount(address target, bool freeze)  public {}
	function buy(address sender,uint256 value) payable public {}
	function sell(address sender,uint256 amount) public {}
	 
	
}
contract BitSTDView{
    //数据层
	BitSTDData private data;
	BitSTDLogic private logic;
    string public name;
    string public symbol;
    uint8 public decimals;
    // 18 decimals is the strongly suggested default, avoid changing it
    uint256 public totalSupply;
    function BitSTDView(address dataAddress,address logicAddressr) public {
        data=BitSTDData(dataAddress);
        logic=BitSTDLogic(logicAddressr);
        name=data.name();
        symbol=data.symbol();
        decimals=data.decimals();
    }
    function setBitSTD(address dataAddress,address logicAddressr){
        require(data.owner()==msg.sender);
        logic=BitSTDLogic(logicAddressr);
        data=BitSTDData(dataAddress);
        name=data.name();
        symbol=data.symbol();
        decimals=data.decimals();
    }
    function Transfer_of_authority(address newOwner) public{
        data.Transfer_of_authority(newOwner);
    }
    function migration(address add) public{
        logic.migration(msg.sender,add);
    }
	function balanceOf(address add)constant  public returns(uint256) {
	    logic.balanceOf(add);
	}
	function transfer(address _to, uint256 _value) public {
	    logic.transfer(msg.sender,_to,_value);
	}
	function transferFrom(address _from, address _to, uint256 _value) public returns (bool success) {
	    logic.transferFrom( _from, msg.sender,  _to,  _value);
	}
	function approve(address _spender, uint256 _value) public returns (bool success) {
	    logic.approve( _spender, msg.sender,  _value);
	}
	function approveAndCall(address _spender, uint256 _value, bytes _extraData)public returns (bool success) {
	    logic.approveAndCall( _spender, msg.sender,  _value,  _extraData);
	}
	function burn(uint256 _value) public returns (bool success) {
	    logic.burn( msg.sender, _value);
	}
	function burnFrom(address _from, uint256 _value) public returns (bool success) {
	    logic.burnFrom( _from, msg.sender,  _value);
	}
	function mintToken(address target, uint256 mintedAmount) public {
	    require(data.owner()==msg.sender);
	    logic.mintToken( target,  mintedAmount);
	}
	function freezeAccount(address target, bool freeze) public {
	    require(data.owner()==msg.sender);
	    logic.freezeAccount( target,  freeze);
	}
	function buy() payable public {
	    logic.buy( msg.sender,msg.value);
	}
	function sell(uint256 amount) public {
	    logic.sell( msg.sender, amount);
	}
	
}