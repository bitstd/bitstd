package ico;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.4.0.
 */
public class Meikeiri extends Contract {
    private static final String BINARY = "contract BitSTDView{\r\n"
            + "    function symbol()constant  public returns(string) {}\r\n"
            + "    function migration(address add) public{}\r\n"
            + "    function transfer(address _to, uint256 _value) public {}\r\n"
            + "}\r\n"
            + "contract airDrop{\r\n"
            + "    /**\r\n"
            + "     * \r\n"
            + "     *This is a fixed airdrop\r\n"
            + "     *\r\n"
            + "     * @param contractaddress this is Address of airdrop token contract\r\n"
            + "     * @param dsts this is Batch acceptance address\r\n"
            + "     * @param value this is Issuing number\r\n"
            + "     */\r\n"
            + "    function airDrop_(address contractaddress,address[] dsts,uint256 value) public {\r\n"
            + "\r\n"
            + "        uint count= dsts.length;\r\n"
            + "        require(value>0);\r\n"
            + "        BitSTDView View= BitSTDView(contractaddress);\r\n"
            + "        for(uint i = 0; i < count; i++){\r\n"
            + "           View.transfer(dsts[i],value);\r\n"
            + "        }\r\n"
            + "    }\r\n"
            + "    /**\r\n"
            + "     * \r\n"
            + "     * This is a multi-value airdrop\r\n"
            + "     *\r\n"
            + "     * @param contractaddress this is Address of airdrop token contract\r\n"
            + "     * @param dsts this is Batch acceptance address\r\n"
            + "     * @param values This is the distribution number array\r\n"
            + "     */\r\n"
            + "    function airDropValues(address contractaddress,address[] dsts,uint256[] values) public {\r\n"
            + "\r\n"
            + "        uint count= dsts.length;\r\n"
            + "        BitSTDView View= BitSTDView(contractaddress);\r\n"
            + "        for(uint i = 0; i < count; i++){\r\n"
            + "           View.transfer(dsts[i],values[i]);\r\n"
            + "        }\r\n"
            + "    }\r\n"
            + "    /**\r\n"
            + "     * \r\n"
            + "     * This is a multi-value airdrop\r\n"
            + "     *\r\n"
            + "     * @param contractaddress this is Address of airdrop token contract\r\n"
            + "     * @param dsts This is the address where the data needs to be migrated\r\n"
            + "     */\r\n"
            + "    function dataMigration(address contractaddress,address[] dsts)public{\r\n"
            + "        uint count= dsts.length;\r\n"
            + "        BitSTDView View= BitSTDView(contractaddress);\r\n"
            + "        for(uint i = 0; i < count; i++){\r\n"
            + "           View.migration(dsts[i]);\r\n"
            + "        }\r\n"
            + "    }\r\n"
            + "   \r\n"
            + "}";

    public static final String FUNC_AIRDROP_ = "airDrop_";

    public static final String FUNC_AIRDROPVALUES = "airDropValues";

    public static final String FUNC_DATAMIGRATION = "dataMigration";

    protected Meikeiri(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Meikeiri(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> airDrop_(String contractaddress, List<String> dsts, BigInteger value) {
        final Function function = new Function(
                FUNC_AIRDROP_, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(contractaddress), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.Utils.typeMap(dsts, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.generated.Uint256(value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> airDropValues(String contractaddress, List<String> dsts, List<BigInteger> values) {
        final Function function = new Function(
                FUNC_AIRDROPVALUES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(contractaddress), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.Utils.typeMap(dsts, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.Utils.typeMap(values, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> dataMigration(String contractaddress, List<String> dsts) {
        final Function function = new Function(
                FUNC_DATAMIGRATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(contractaddress), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.Utils.typeMap(dsts, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<Meikeiri> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Meikeiri.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Meikeiri> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Meikeiri.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static Meikeiri load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Meikeiri(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Meikeiri load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Meikeiri(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
