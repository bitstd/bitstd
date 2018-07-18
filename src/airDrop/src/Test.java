
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.Web3Sha3;
import service.airdropService;
import service.impl.airdropServiceImpl;
import util.PropertyReader;
import util.readFile;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Test {
    public static void main(String[] ages) throws ExecutionException, InterruptedException {
        airdropService air = new airdropServiceImpl();
        String tokenContractAddress = PropertyReader.get("tokenContractAddress","bitstd.properties");
        String addressPath = PropertyReader.get("addressPath","bitstd.properties");
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入密码");
        String pwd = scanner.nextLine();
        try {
            System.out.println("当前区块GasPrice："+ util.readFile.web3j.ethGasPrice().sendAsync().get().getGasPrice().divide(new BigInteger("1000000000"))+"Gwei,请输入GasPrice：");
        }catch (Exception e){
            e.printStackTrace();
        }
        BigDecimal GP=scanner.nextBigDecimal().multiply(BigDecimal.valueOf(1000000000)).setScale(0);

        String path = PropertyReader.get("path","bitstd.properties");
        BigInteger everyGas = new BigInteger(PropertyReader.get("everyGas","bitstd.properties"));
        BigInteger GasPrice = (new BigInteger(GP.toString()).multiply(readFile.web3j.ethGasPrice().sendAsync().get().getGasPrice()).divide(new BigInteger("1000000000")));
        System.out.println("GasPrice："+GasPrice);
        if (ages[0].equals("airDrop")){
            long Decimals=10;
            Decimals= (long) Math.pow(Decimals,Integer.parseInt(PropertyReader.get("Decimals","bitstd.properties")));
            System.out.println("请输入空投数量！");
            String va= (scanner.nextBigDecimal().multiply(BigDecimal.valueOf(Decimals))).setScale(0).toString();
            BigInteger value = new BigInteger(va);
            try {
                air.airDrop_(tokenContractAddress,path,value,addressPath,pwd,everyGas,GasPrice);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if (ages[0].equals("airDropValues")){
            String valuePath=PropertyReader.get("valuePath","bitstd.properties");
            try {
                air.airDropValues(tokenContractAddress,path,valuePath,addressPath,pwd,everyGas,GasPrice);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if (ages[0].equals("dataMigration")){
            System.out.println("请输入旧合约地址！/Please enter the old contract address!");
            String oldContract=scanner.next();
            System.out.println("请输入新合约地址！/Please enter the new contract address!");
            String newContract=scanner.next();
            try {
                air.dataMigration(oldContract,newContract,path,addressPath,pwd,everyGas,GasPrice);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
