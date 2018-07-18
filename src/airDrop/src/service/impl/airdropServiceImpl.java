package service.impl;

import ico.Airdrop;
import ico.ERC20;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import service.airdropService;
import util.PropertyReader;
import util.readFile;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Service("airdropService")
public class airdropServiceImpl implements airdropService {

   // @Override
    public List<String> airDrop_(String tokenContractAddress, String path,BigInteger value, String addressPath,String pwd, BigInteger everyGas, BigInteger GasPrice) {
        List<String> addressList = new ArrayList<String>();
        String str= readFile.read(path,"");
        addressList.addAll(java.util.Arrays.asList(str.split(",")));
        Airdrop airDrop = null;
        Credentials credentials=null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String> hash=new ArrayList<>();
        try {
            File targetFile = new File(addressPath);
            credentials= WalletUtils.loadCredentials(pwd, targetFile);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        System.out.println("开始空投！");
        List<String>  addArray=new ArrayList<String>();
        Date d1=new Date();
        List<Boolean> bos=new ArrayList<Boolean>();
        String result=null;
      /*  int fori=0;
        if (addressList.size()!=0&&addressList.size()<readFile.ko){
            fori=1;
        }else{
            fori= (int) ;
        }*/
        for (int i=0;i<=Math.ceil(addressList.size()/readFile.ko);i++){
            result=null;
            System.out.println("第"+(i+1)+"次！");
            try {addArray.addAll(addressList);
                if (i==(Math.ceil(addressList.size()/readFile.ko))){
                    addArray = (addArray.subList(i*readFile.ko,addArray.size()));
                }else {
                    if (addressList.size()-i*readFile.ko>10){
                        addArray = (addArray.subList(i*readFile.ko,(i*readFile.ko+readFile.ko)));
                    }else{
                        addArray = (addArray.subList(i*readFile.ko,addArray.size()));
                    }
                }
                for (int adi=0;adi<addArray.size();adi++){
                    if(addArray.get(adi).lastIndexOf("0x")!=0 || addArray.get(adi).length()!="0xc387683bd495658b6ba02ca482a7c8614936df6a".length()){
                        addArray.remove(adi);
                    }
                }
                airDrop= Airdrop.load(readFile.contractAddress, readFile.web3j, credentials,GasPrice,
                        readFile.GAS_LIMIT.add(
                                new BigInteger(PropertyReader.get("everyGas","bitstd.properties")).multiply(new BigInteger(String.valueOf(addArray.size())))));
                Date d2=new Date();

                TransactionReceipt balanceOf =airDrop.drop(tokenContractAddress,
                        addArray,value,BigInteger.ZERO).sendAsync().get();
                bos.add(true);
                long diff = d2.getTime() - new Date().getTime();//这样得到的差值是微秒级别
                result="hashId=" + balanceOf.getTransactionHash()+",耗时："+diff/1000+"s";
                hash.add(balanceOf.getTransactionHash());
            }catch (Exception e){
                result+="第"+i+1+"次！"+i*readFile.ko+"——"+(addArray.size())+" 空投失败！err:"+e.getMessage();
                StringBuffer strBf=new StringBuffer();
                for (int j=0;j<addArray.size();j++){
                    strBf.append(addArray.get(j));
                    if (j!=addArray.size()-1){
                        strBf.append(",");
                    }
                    if (j%5==1){
                        strBf.append("\r\n");
                    }
                }
                result+=strBf.toString();
            }finally {
                addArray.clear();
                System.out.println(result);
                readFile.write(result);
            }

        }
        long diff = d1.getTime() - new Date().getTime();//这样得到的差值是微秒级别
        readFile.write("创建时间："+df.format(d1)+",总耗时："+diff/1000+"s ，成功率:"+(Math.round(bos.size()/Math.ceil(addressList.size()/readFile.ko)*1000)/1000)+"%\n");
        return hash;
    }

    @Override
    public List<String> airDropValues(String tokenContractAddress, String path, String value, String addressPath, String pwd, BigInteger everyGas, BigInteger GasPrice) {
        List<String> addressList = new ArrayList<String>();
        List<BigInteger> valueList=new ArrayList<>();
        String str= readFile.read(path,"");
        addressList.addAll(java.util.Arrays.asList(str.split(",")));
        str=readFile.read(value,"");
        String lo=null;
        for (String  val:java.util.Arrays.asList(str.split(","))) {
            lo=(new BigDecimal(val).multiply(BigDecimal.valueOf(readFile.Decimals))).setScale(0).toString();
            valueList.add(new BigInteger(lo));
        }
        Airdrop airDrop = null;
        Credentials credentials=null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String> hash=new ArrayList<>();
        try {
            File targetFile = new File(addressPath);
            credentials= WalletUtils.loadCredentials(pwd, targetFile);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("开始空投！");
        List<String>  addArray=new ArrayList<String>();
        List<BigInteger>  valueArray=new ArrayList<>();
        Date d1=new Date();
        List<Boolean> bos=new ArrayList<Boolean>();
        String result=null;
        if (valueList.size()!=addressList.size()){
            try {
                throw new Exception("value与address不匹配！");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        for (int i=0;i<=Math.ceil(addressList.size()/readFile.ko);i++){
            result=null;
            System.out.println("第"+(i+1)+"次！");
            BigInteger gross=BigInteger.ZERO;
            try {
                addArray.addAll(addressList);
                valueArray.addAll(valueList);
                if (i==(Math.ceil(addressList.size()/readFile.ko)-1)){
                    addArray = (addArray.subList(i*readFile.ko,addArray.size()));
                    valueArray=(valueArray.subList(i*readFile.ko,valueArray.size()));
                }else {
                    if (addressList.size()-i*readFile.ko>10){
                        addArray = (addArray.subList(i*readFile.ko,(i*readFile.ko+readFile.ko)));
                        valueArray=(valueArray.subList(i*readFile.ko,(i*readFile.ko+readFile.ko)));
                    }else{
                        addArray = (addArray.subList(i*readFile.ko,addArray.size()));
                        valueArray=(valueArray.subList(i*readFile.ko,valueArray.size()));
                    }
                }
                for (int adi=0;adi<addArray.size();adi++){
                    if(addArray.get(adi).lastIndexOf("0x")!=0 || addArray.get(adi).length()!="0xc387683bd495658b6ba02ca482a7c8614936df6a".length()){
                        addArray.remove(adi);
                        valueArray.remove(adi);
                    }else {
                        gross = gross.add(valueArray.get(adi));
                    }
                }
                airDrop= Airdrop.load(readFile.contractAddress, readFile.web3j, credentials,
                       GasPrice ,
                        readFile.GAS_LIMIT.add(
                                new BigInteger(PropertyReader.get("everyGas","bitstd.properties")).multiply(new BigInteger(String.valueOf(addArray.size())))));
                Date d2=new Date();

                TransactionReceipt balanceOf =airDrop.dropValues(tokenContractAddress,
                        addArray,valueArray,gross,BigInteger.ZERO).sendAsync().get();
                bos.add(true);
                long diff = d2.getTime() - new Date().getTime();//这样得到的差值是微秒级别
                result="hashId=" + balanceOf.getTransactionHash()+",耗时："+diff/1000+"s";
                hash.add(balanceOf.getTransactionHash());
            }catch (Exception e){
                result+="第"+i+1+"次！"+i*readFile.ko+"——"+(addArray.size())+" 空投失败！err:"+e.getMessage();
                StringBuffer strBf=new StringBuffer();
                for (int j=0;j<addArray.size();j++){
                    strBf.append(addArray.get(j));
                    if (j!=addArray.size()-1){
                        strBf.append(",");
                    }
                    if (j%5==1){
                        strBf.append("\r\n");
                    }
                }
                result+=strBf.toString();
            }finally {
                addArray.clear();
                System.out.println(result);
                readFile.write(result);
            }

        }
        long diff = d1.getTime() - new Date().getTime();//这样得到的差值是微秒级别
        readFile.write("创建时间："+df.format(d1)+",总耗时："+diff/1000+"s ，成功率:"+(Math.round(bos.size()/Math.ceil(addressList.size()/readFile.ko)*1000)/1000)+"%\n");
        return hash;
    }

    @Override
    public List<String> dataMigration(String oldTokenContractAddress, String newTokenContractAddress, String path, String addressPath, String pwd, BigInteger everyGas, BigInteger GasPrice) {
        String str=readFile.read(path,"");
        List<String> addressList = new ArrayList<String>();
        addressList.addAll(java.util.Arrays.asList(str.split(",")));
        Airdrop airDrop = null;

        Credentials  credentials=null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            File targetFile = new File(addressPath);
            credentials= WalletUtils.loadCredentials(pwd, targetFile);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        ERC20 oldErc20=ERC20.load(oldTokenContractAddress,readFile.web3j,credentials,GasPrice,readFile.GAS_LIMIT);
        ERC20 newErc20=ERC20.load(newTokenContractAddress,readFile.web3j,credentials,GasPrice,readFile.GAS_LIMIT);
        System.out.println("开始数据迁移！");
        List<String>  addArray=new ArrayList<String>();
        Date d1=new Date();
        List<Boolean> bos=new ArrayList<Boolean>();
        String result="";
        for (int i=0;i<Math.ceil(addressList.size()/readFile.ko);i++){
            System.out.println("第"+(i+1)+"次！");
            BigInteger gross=BigInteger.ZERO;
            try {
                addArray.addAll(addressList);
                if (i==(Math.ceil(addressList.size()/readFile.ko)-1)){
                    addArray = (addArray.subList(i*readFile.ko,addArray.size()));
                }else {
                    if (addressList.size()-i*readFile.ko>10){
                        addArray = (addArray.subList(i*readFile.ko,(i*readFile.ko+readFile.ko)));
                    }else{
                        addArray = (addArray.subList(i*readFile.ko,addArray.size()));
                    }
                }
                for (int adi=0;adi<addArray.size();adi++){
                    BigInteger oldValue = oldErc20.balanceOf(addArray.get(adi)).send();
                    BigInteger newValue = newErc20.balanceOf(addArray.get(adi)).send();
                    if(addArray.get(adi).lastIndexOf("0x")!=0 || addArray.get(adi).length()!="0xc387683bd495658b6ba02ca482a7c8614936df6a".length()|| oldValue.toString().equals("0") || !newValue.toString().equals("0")){
                        addArray.remove(adi);
                        adi--;
                    }else {
                        gross = gross.add(oldValue);
                    }
                }
                if (gross==BigInteger.ZERO){
                    continue;
                }
                airDrop= Airdrop.load(readFile.contractAddress, readFile.web3j, credentials,
                        GasPrice,
                        readFile.GAS_LIMIT.add(
                                new BigInteger(PropertyReader.get("everyGas","bitstd.properties")).multiply(new BigInteger(String.valueOf(addArray.size())))));
                Date d2=new Date();

                TransactionReceipt balanceOf =airDrop.dataMigration(oldTokenContractAddress,newTokenContractAddress,
                        addArray,gross,BigInteger.ZERO).sendAsync().get();
                bos.add(true);
                long diff = d2.getTime() - new Date().getTime();//这样得到的差值是微秒级别
                result="hashId=" + balanceOf.getTransactionHash()+",耗时："+diff/1000+"s";

            }catch (Exception e){
                if (gross==BigInteger.ZERO){
                    result+="第"+(i+1)+"次！轮空！";
                }else {
                    e.printStackTrace();
                    result+="第"+(i+1)+"次！"+i*readFile.ko+"——"+(addArray.size())+" 迁移失败！err:"+e.getMessage();
                    StringBuffer strBf=new StringBuffer();
                    for (int j=0;j<addArray.size();j++){
                        strBf.append(addArray.get(j));
                        if (j!=addArray.size()-1){
                            strBf.append(",");
                        }
                        if (j%5==1){
                            strBf.append("\r\n");
                        }
                    }
                    result+=strBf.toString();
                    //e.printStackTrace();
                }
            }finally {
                addArray.clear();
                System.out.println(result);
                readFile.write(result);
            }

        }
        long diff = d1.getTime() - new Date().getTime();//这样得到的差值是微秒级别
        readFile.write("创建时间："+df.format(d1)+",总耗时："+diff/1000+"s ，成功率:"+Math.round((bos.size()/(addressList.size()<readFile.ko?1:Math.ceil(addressList.size()/readFile.ko))*readFile.ko*1000)/1000)+"%\n");

        return null;
    }
}
