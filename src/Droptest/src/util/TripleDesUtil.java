package util;

import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.util.Random;

/**
 * Created by baolong.wang on 2017/6/7.
 */
public class TripleDesUtil {
    private static final Logger logger = Logger.getLogger(TripleDesUtil.class);

    /**
     * 3DES 加密
     * @param content
     * @param key
     * @return
     */
    public static String encryptTripleDesToString(String content, String key) {
        String result = null;

        try {
            DESedeKeySpec dks = new DESedeKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey securekey = keyFactory.generateSecret(dks);

            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, securekey);
            byte[] bytes = cipher.doFinal(content.getBytes("UTF-8"));

            BASE64Encoder encoder = new BASE64Encoder();
            result = encoder.encode(bytes).replaceAll("\r", "").replaceAll("\n", "");

        } catch (Exception e) {
            logger.error(e);
        }

        return result;
    }

    /**
     * 3DES 解密
     *
     * @param content
     * @param key
     * @return
     */
    public static String decryptTripleDesToString(String content, String key) {
        String result = null;

        try {
            // --通过base64,将字符串转成byte数组
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytesrc = decoder.decodeBuffer(content);
            // --解密的key
            DESedeKeySpec dks = new DESedeKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey securekey = keyFactory.generateSecret(dks);

            // --Chipher对象解密
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, securekey);
            byte[] bytes = cipher.doFinal(bytesrc);

            if (bytes == null) {
                result = "";
            } else {
                result = new String(bytes, "UTF-8");
            }
        } catch (Exception e) {
            logger.error(e);
        }

        return result;
    }

    public static String getRandom(int size) {
        StringBuffer sb = new StringBuffer();//定义变长字符串
        Random random = new Random();
        //随机生成数字，并添加到字符串
        for (int i = 0; i < size; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
