package ico;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.4.0.
 */
public class Airdrop extends Contract {
    private static final String BINARY = "contract ERC20{\r\n"
            + "    \r\n"
            + "    mapping  (address => uint256) public balanceOf;\r\n"
            + "    function symbol() constant  public returns (string) {}\r\n"
            + "    function transfer(address _to, uint256 _value) public {}\r\n"
            + "    function transferFrom(address _from, address _to, uint256 _value) public returns (bool success) {}\r\n"
            + "}\r\n"
            + "contract Airdrop{\r\n"
            + "    \r\n"
            + "    address public owner;\r\n"
            + "    function Airdrop() public {\r\n"
            + "        \r\n"
            + "        owner=msg.sender;\r\n"
            + "    }\r\n"
            + "    \r\n"
            + "    modifier qualification {\r\n"
            + "        \r\n"
            + "        require(msg.sender == owner);\r\n"
            + "        _;\r\n"
            + "    }\r\n"
            + "    /**\r\n"
            + "     *\r\n"
            + "     *This is a fixed airdrop\r\n"
            + "     *\r\n"
            + "     * @param _token this is Address of airdrop token contract\r\n"
            + "     * @param dsts this is Batch acceptance address\r\n"
            + "     * @param value this is Issuing number\r\n"
            + "     */\r\n"
            + "    function drop(address _token, address[] dsts, uint256 value) payable public {\r\n"
            + "\r\n"
            + "        uint count = dsts.length;\r\n"
            + "        require(value > 0);\r\n"
            + "        ERC20 View = ERC20(_token);\r\n"
            + "        View.transferFrom(msg.sender,this,value*count);\r\n"
            + "        for(uint i = 0; i < count; i++){\r\n"
            + "            View.transfer(dsts[i],value);\r\n"
            + "        }\r\n"
            + "    }\r\n"
            + "    /**\r\n"
            + "     *\r\n"
            + "     * This is a multi-value airdrop\r\n"
            + "     *\r\n"
            + "     * @param contractaddress this is Address of airdrop token contract\r\n"
            + "     * @param dsts this is Batch acceptance address\r\n"
            + "     * @param values This is the distribution number array\r\n"
            + "     * @param gross This is Total number of air drops\r\n"
            + "     */\r\n"
            + "    function dropValues(address contractaddress, address[] dsts, uint256[] values, uint256 gross) payable public {\r\n"
            + "\r\n"
            + "        uint count = dsts.length;\r\n"
            + "        ERC20 View = ERC20(contractaddress);\r\n"
            + "        View.transferFrom(msg.sender,this,gross);\r\n"
            + "        for(uint i = 0; i < count; i++){\r\n"
            + "            View.transfer(dsts[i],values[i]);\r\n"
            + "        }\r\n"
            + "    }\r\n"
            + "    /**\r\n"
            + "     *\r\n"
            + "     * This is a Methods for contract data migration\r\n"
            + "     *\r\n"
            + "     * @param contractaddress this is Address of airdrop token contract\r\n"
            + "     * @param dsts This is the address where the data needs to be migrated\r\n"
            + "     * @param gross This is Total number of Migration\r\n"
            + "     */\r\n"
            + "    function dataMigration(address old_contract, address contractaddress, address[] dsts, uint256 gross) payable public {\r\n"
            + "        \r\n"
            + "        uint count = dsts.length;\r\n"
            + "        ERC20 newContract = ERC20(contractaddress);\r\n"
            + "        ERC20 oldContract = ERC20(old_contract);\r\n"
            + "        newContract.transferFrom(msg.sender,this,gross);\r\n"
            + "        for(uint i = 0; i < count; i++){\r\n"
            + "           if(newContract.balanceOf(dsts[i]) == 0){\r\n"
            + "               newContract.transfer(dsts[i],oldContract.balanceOf(dsts[i]));\r\n"
            + "           }\r\n"
            + "        }\r\n"
            + "    }\r\n"
            + "    /**\r\n"
            + "     * This is a Withdrawal user donation method\r\n"
            + "     *\r\n"
            + "     * No parameters, full extraction\r\n"
            + "     */\r\n"
            + "    function withdrawaETH() public qualification {\r\n"
            + "        \r\n"
            + "        require(msg.sender == owner);                       //reconfirm\r\n"
            + "        msg.sender.transfer(address(this).balance);          //Sends the eth to the caller\r\n"
            + "    }\r\n"
            + "\r\n"
            + "}";

    public static final String FUNC_DROP = "drop";

    public static final String FUNC_DATAMIGRATION = "dataMigration";

    public static final String FUNC_DROPVALUES = "dropValues";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_WITHDRAWAETH = "withdrawaETH";

    protected Airdrop(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Airdrop(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> drop(String _token, List<String> dsts, BigInteger value, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_DROP,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_token),
                        new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                                org.web3j.abi.Utils.typeMap(dsts, org.web3j.abi.datatypes.Address.class)),
                        new org.web3j.abi.datatypes.generated.Uint256(value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> dataMigration(String old_contract, String contractaddress, List<String> dsts, BigInteger gross, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_DATAMIGRATION,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(old_contract),
                        new org.web3j.abi.datatypes.Address(contractaddress),
                        new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                                org.web3j.abi.Utils.typeMap(dsts, org.web3j.abi.datatypes.Address.class)),
                        new org.web3j.abi.datatypes.generated.Uint256(gross)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> dropValues(String contractaddress, List<String> dsts, List<BigInteger> values, BigInteger gross, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_DROPVALUES,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(contractaddress),
                        new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                                org.web3j.abi.Utils.typeMap(dsts, org.web3j.abi.datatypes.Address.class)),
                        new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                                org.web3j.abi.Utils.typeMap(values, org.web3j.abi.datatypes.generated.Uint256.class)),
                        new org.web3j.abi.datatypes.generated.Uint256(gross)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> withdrawaETH() {
        final Function function = new Function(
                FUNC_WITHDRAWAETH,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<Airdrop> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Airdrop.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Airdrop> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Airdrop.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static Airdrop load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Airdrop(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Airdrop load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Airdrop(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
