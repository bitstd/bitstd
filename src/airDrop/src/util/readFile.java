package util;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;
import util.PropertyReader;

import java.io.*;
import java.math.BigInteger;

public class readFile {
    private static final String ethHost = PropertyReader.get("eth.api.host", "bitstd.properties");
    public static int ko=Integer.parseInt(PropertyReader.get("PerformNumber", "bitstd.properties"));
    public static String contractAddress = PropertyReader.get("eth.api.contract", "bitstd.properties");
    public static BigInteger GAS_LIMIT=new BigInteger(PropertyReader.get("GAS_LIMIT", "bitstd.properties"));
    public static Web3j web3j = Web3j.build(new HttpService(ethHost));
    public static long Decimals=10;
    static{
        Decimals= (long) Math.pow(Decimals,Integer.parseInt(PropertyReader.get("Decimals","bitstd.properties")));
    }

    public static String read(String file,String n){
       // Admin admin=new Admin.build(new HttpService(ethHost));
        StringBuffer str = new StringBuffer("");;
        try {
            Reader reader = new FileReader(file);
            // 这里我们用到了字符操作的BufferedReader类
             BufferedReader bufferedReader = new BufferedReader(reader);
             String string = null;
             // 按行读取，结束的判断是是否为null，按字节或者字符读取时结束的标志是-1
             while ((string = bufferedReader.readLine()) != null) {
                 // 这里我们用到了StringBuffer的append方法，这个比string的“+”要高效
                 str.append(string + n);
             }
             // 注意这两个关闭的顺序
             bufferedReader.close();
             reader.close();
        } catch (Exception  e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    public static boolean write(String string){
        String path=PropertyReader.get("WriteAddress", "bitstd.properties");
        File file = new File(path);
        String str=null;
        Writer writer = null;
        if (!file.exists()) {
             try {
                 // 如果文件找不到，就new一个
                 file.createNewFile();
             } catch (IOException e) {

                 e.printStackTrace();
                 return false;
             }
        }else {
            str=read(path,"\n")+string;
        }
        try {
            writer = new FileWriter(file);
            writer.write(str);
            writer.write("\r\n");
            // 在这一定要记得关闭流

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
    public static String strToHex(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }
}
