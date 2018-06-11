contract BitSTDData{

    address public owner;
    // 18 decimals is the strongly suggested default, avoid changing it
    uint256 public totalSupply;

    //包含所有余额的数组
    mapping (address => uint256) public balanceOf;
    mapping (address => mapping (address => uint256)) public allowance;
    uint256 public sellPrice;
    uint256 public buyPrice;
    //允许调用的地址zhi值wei值为true
    mapping (address => bool) private owners;
    //冻结地址
    mapping (address => bool) public frozenAccount;
    function setBalanceOfr(address add,uint256 value) public {}
    function setAllowance(address add,address _add,uint256 value) public {}
    function setFrozenAccount(address add,bool value) public {}
    function addTotalSupply(uint256 value) public{}
    function setPrices(uint256 newSellPrice, uint256 newBuyPrice) public {}
    //老合约数据
    function getOld_BalanceOfr(address add)constant  public returns(uint256){}

    function getOld_Allowance(address add,address _add)constant  public returns(uint256){}

    function getOld_FrozenAccount(address add)constant  public returns(bool){}

}

interface tokenRecipient { function receiveApproval(address _from, uint256 _value, address _token, bytes _extraData) external; }

contract BitSTDLogic{
    //数据层
	BitSTDData private data;
	//允许调用的地址
	mapping( address => bool) public owners;

	// 这会在区块链上产生一个公开事件，它会通知客户
    event Transfer(address indexed from, address indexed to, uint256 value);
    event FrozenFunds(address target, bool frozen);

    // 这就会通知客户烧钱的数量
    event Burn(address indexed from, uint256 value);

    function BitSTDLogic(address dataAddress){
        data=BitSTDData(dataAddress);
		owners[msg.sender]=true;
    }
    //修改允许调用合约的地址
	function setOwners(address addr,bool bo) public {
		require(data.owner()==msg.sender);
		owners[addr]=true;
	}

	modifier onlyOwner(){
		require(owners[msg.sender] == true);
        _;
	}

	/**
	 * 内部转移，只能通过这个合同来调用
	*/
    function _transfer(address _from, address _to, uint _value) internal {
        uint256 f_value=balanceOf(_from);
        uint256 t_value=balanceOf(_to);
        // 防止传输到0x0地址。调用Burn()
        require(_to != 0x0);
        // 检查发送者是否已经足够
        require(f_value >= _value);
        // 检查溢出
        require(t_value + _value > t_value);
        // 将其保存为将来的断言
        uint previousBalances = f_value + t_value;
        // 从发送方减去
        setBalanceOf(_from,f_value-_value);
        // 向接收者添加
        setBalanceOf(_to,t_value+_value);

        emit Transfer(_from, _to, _value);
        // 断言用于使用静态分析来发现代码中的错误。他们不应该失败
        assert(balanceOf(_from) +balanceOf(_to) == previousBalances);

    }
    //数据迁移
    function migration(address sender,address add) public{
        //数据迁移head
        uint256 t_value=balanceOf(add);
        uint256 _value=data.getOld_BalanceOfr(add);
        require(balanceOf(sender)>=_value);
        require(t_value + _value > t_value);
        //将余额迁移
        if(data.balanceOf(add)==0){
            data.setBalanceOfr(sender,balanceOf(sender)-_value);
            data.setBalanceOfr(add,_value);
        }
        //将冻结账户迁移
        if(data.getOld_FrozenAccount(add)==true){
            if(data.frozenAccount(add)!=true)
            data.setFrozenAccount(add,true);
        }
        //数据迁移end
    }

    //查询合约代币
    function balanceOf(address add)constant  public returns(uint256) {
        return data.balanceOf(add);
    }

    //修改合约
    function setBalanceOf(address add,uint256 value) public {
        data.setBalanceOfr(add,value);
    }

    /**
     * 传递令牌
     * 从你的账户发送“价值”令牌到“to”
    */
    function transfer(address sender,address _to, uint256 _value) public {
        _transfer(sender, _to, _value);
    }

    /**
     * 从其他地址传递令牌
     *
     * 将“值”令牌发送到“to”，代表“from”
     *
     * @param _from 发送者的地址
     * @param _to 收件人的地址
     * @param _value 发送数量
     */
    function transferFrom(address _from,address sender, address _to, uint256 _value) public returns (bool success) {
        uint256 a_value=data.allowance(_from,sender);
        require(_value <=_value );     // Check allowance
        data.setAllowance(_from,sender,a_value-_value);
        _transfer(_from, _to, _value);
        return true;
    }

     /**
     * 为其他地址设置津贴
     *
     * 允许“花钱者”在你的名义下只花“价值”牌
     *
     * @param _spender 授权使用的地址
     * @param _value 他们能花的最多的钱
     */
    function approve(address _spender,address sender, uint256 _value) public returns (bool success) {
        data.setAllowance(sender,_spender, _value);
        return true;
    }

    /**
     * 为其他地址设置津贴并通知
     *
     * 允许“spender”在你的名义下只花“价值”的标记，然后将合同写在合同上。
     *
     * @param _spender 授权使用的地址
     * @param _value 他们能花的最多的钱
     * @param _extraData 向已批准的合同发送一些额外信息
     */
    function approveAndCall(address _spender,address sender, uint256 _value, bytes _extraData)public returns (bool success) {
        tokenRecipient spender = tokenRecipient(_spender);
        if (approve(_spender,sender, _value)) {
            spender.receiveApproval(sender, _value, this, _extraData);
            return true;
        }
    }

     /**
     * 摧毁代币
     *
     * 从系统中删除“值”代币
     *
     * @param _value the amount of money to burn
     */
    function burn(address sender,uint256 _value) public returns (bool success) {
        uint256 f_value=balanceOf(sender);
        require(f_value >= _value);                 // 检查发送者是否已经足够
        setBalanceOf(sender,f_value-_value);    // 从发送方减去
        data.addTotalSupply(data.totalSupply()-_value);                      // 更新总供给totalSupply
        emit Burn(sender, _value);
        return true;
    }

    /**
     * 从其他帐户销毁代币
     *
     * 从系统中“from”中删除“值”代币。
     *
     * @param _from the address of the sender
     * @param _value the amount of money to burn
     */
    function burnFrom(address _from,address sender, uint256 _value) public returns (bool success) {
        uint256 f_value=balanceOf(sender);
        uint256 a_value=data.allowance(_from,sender);
        require(f_value >= _value);                             // 检查目标平衡是否足够
        require(_value <= a_value);                             // 检查津贴
        setBalanceOf(_from,f_value-_value);                // 从目标平衡中减去
        data.setAllowance(_from,sender,a_value-_value);  // 减去发送人的津贴
        data.addTotalSupply(data.totalSupply()-_value);         // 更新totalSupply
        emit Burn(_from, _value);
        return true;
    }

    // @通知创建“mintedAmount”令牌，并将其发送给“目标”
    // @param目标地址接收令牌
    // @param mintedAmount将收到的令牌数量
    function mintToken(address target, uint256 mintedAmount) onlyOwner public {
        uint256 f_value=balanceOf(target);
        setBalanceOf(target,f_value+mintedAmount);
        data.addTotalSupply(data.totalSupply()+mintedAmount);
        emit Transfer(0, this, mintedAmount);
        emit Transfer(this, target, mintedAmount);
    }

    // @notice冻结帐户,防止“target”发送和接收令牌
    // @param目标地址被冻结
    // @param冻结或不冻结
    function freezeAccount(address target, bool freeze) onlyOwner public {
        data.setFrozenAccount(target,freeze);
        emit FrozenFunds(target, freeze);
    }

    // @通知通过发送ether从合同中购买代币
    function buy(address sender,uint256 value) payable public {
        uint amount = value / data.buyPrice();        // 计算购买量
        _transfer(this, sender, amount);              // makes the transfers
    }

     // @通知出售“amount”令牌
    // @param amount 代币的数量
    function sell(address sender,uint256 amount) public {
        require(address(this).balance >= amount * data.sellPrice());      // 检查合同是否有足够的ether
        _transfer(sender, this, amount);              // makes the transfers
        sender.transfer(amount * data.sellPrice());          // 向卖方发送ether 。为了避免递归攻击，这是很重要的
    }

}