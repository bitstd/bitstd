import ico.Meikeiri;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import util.PropertyReader;
import util.readFile;

import java.io.File;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {
    public static void main(String[] ages){
        final String ethHost = PropertyReader.get("eth.api.host", "bitstd.properties");
        String contractAddress = PropertyReader.get("eth.api.contract", "bitstd.properties");
        final String source = PropertyReader.get("source", "bitstd.properties");
        String pwd = PropertyReader.get("pwd", "bitstd.properties");
        String tokenContractAddress=PropertyReader.get("tokenContractAddress", "bitstd.properties");
        BigInteger GAS_LIMIT=new BigInteger(PropertyReader.get("GAS_LIMIT", "bitstd.properties"));
        BigInteger GasPrice=new BigInteger(PropertyReader.get("GasPrice", "bitstd.properties"));
        int ko=Integer.parseInt(PropertyReader.get("PerformNumber", "bitstd.properties"));
        Web3j web3j = Web3j.build(new HttpService(ethHost));

        String str=readFile.read(PropertyReader.get("path","bitstd.properties"),"");
        List<String> addressList = new ArrayList<String>();
        addressList.addAll(java.util.Arrays.asList(str.split(",")));
        Meikeiri meikei = null;
        Credentials  credentials=null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //if (ETH.ceshi()!=null){
            try {
                File targetFile = new File(source);
                credentials= WalletUtils.loadCredentials(pwd, targetFile);
               /* meikei= Meikeiri.load(contractAddress, web3j, credentials,
                        web3j.ethGasPrice().send().getGasPrice().multiply(GasPrice),
                        GAS_LIMIT.add(new BigInteger("66084").multiply(new BigInteger(String.valueOf(ko)))));*/
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
                System.out.println("开始数据迁移！");
                //while (true){

                   /* EthTransaction eth = web3j.ethGetTransactionByHash("0x63453a66fe86cc4b95d30c03818e57ebeca3b18e6401dbe66d11049f32be87ba").sendAsync().get();
                    Transaction Hash_=eth.getTransaction().get();
                System.out.println(JSON.toJSON(Hash_));*/
                   /* TransactionReceipt balanceOf =meikei.fallback("0xcb03b142546fe2b395db552a821054868d762bb6",
                            "0x1582580D33A30aD52086Ae445d2241C2b4ae477c",
                            addressList,new BigInteger("1")).sendAsync().get();*/
                //TransactionReceipt balanceOf =meikei.dataMigration("0x9288ADB367E12E0a27E967AC000f70aF48d5Fe4F",addressList).sendAsync().get();
                List<String>  addArray=new ArrayList<String>();
                Date d1=new Date();
                List<Boolean> bos=new ArrayList<Boolean>();
                String result=null;
                for (int i=0;i<Math.ceil(addressList.size()/ko);i++){
                    System.out.println("第"+(i+1)+"次！");
                    try {
                        /*if (i>=0){
                            throw new Exception("");
                        }*/
                        /*TransactionReceipt balanceOf =meikei.airDrop_(tokenContractAddress,
                                (i==(Math.ceil(addressList.size()/ko)-1)?
                                        addressList.subList(i*ko,addressList.size()):
                                        addressList.size()-i*ko>20?addressList.subList(i*ko,addressList.size())
                                                :addressList.subList(i*ko,ko))
                                ,new BigInteger("1")).sendAsync().get();*/
                        addArray.addAll(addressList);
                        if (i==(Math.ceil(addressList.size()/ko)-1)){
                            addArray = (addArray.subList(i*ko,addArray.size()));
                        }else {
                            if (addressList.size()-i*ko>10){
                                addArray = (addArray.subList(i*ko,(i*ko+ko)));
                            }else{
                                addArray = (addArray.subList(i*ko,addArray.size()));
                            }
                        }
                        meikei= Meikeiri.load(contractAddress, web3j, credentials,
                                web3j.ethGasPrice().send().getGasPrice().multiply(GasPrice),
                                GAS_LIMIT.add(
                                        new BigInteger(PropertyReader.get("everyGas","bitstd.properties")).multiply(new BigInteger(String.valueOf(addArray.size())))));
                        Date d2=new Date();

                        TransactionReceipt balanceOf =meikei.dataMigration(tokenContractAddress,
                                addArray).sendAsync().get();
                        bos.add(true);
                        long diff = d2.getTime() - new Date().getTime();//这样得到的差值是微秒级别
                        result="hashId=" + balanceOf.getTransactionHash()+",耗时："+diff/1000+"s";

                    }catch (Exception e){
                        result="第"+i+"次！"+i*ko+"——"+(i*ko+ko)+" 迁移失败！err:"+e.getMessage();
                    }finally {
                        addArray.clear();
                        System.out.println(result);
                        readFile.write(result);
                    }

                }
        long diff = d1.getTime() - new Date().getTime();//这样得到的差值是微秒级别
        readFile.write("创建时间："+df.format(d1)+",总耗时："+diff/1000+"s ，成功率:"+Math.round((bos.size()/Math.ceil(addressList.size()/ko))*ko*1000)/1000+"%\n");

    }
}
