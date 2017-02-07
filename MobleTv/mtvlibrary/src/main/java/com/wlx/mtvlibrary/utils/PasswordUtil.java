package com.wlx.mtvlibrary.utils;


import com.wlx.mtvlibrary.Constants;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class PasswordUtil {

    public final static String DEFAULT_KEY = "buzhidao";
    public final static int KEY_LENGTH = 32;
    /**
     * 算法/模式/填充 *
     */
    private static final String CipherMode = "AES/ECB/PKCS5Padding";
    private static MessageDigest md5 = null;
    private static MessageDigest sha1 = null;


    public static MessageDigest getSha1MessageDigest() {
        if (sha1 == null) {
            try {
                sha1 = MessageDigest.getInstance("SHA-1");
            } catch (NoSuchAlgorithmException e) {
            }
        }
        return sha1;
    }


    public static MessageDigest getMd5MessageDigest() {
        if (md5 == null) {
            try {
                md5 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
            }
        }
        return md5;
    }


    /**
     * 创建密钥 *
     */
    private static SecretKeySpec createKey(String password) {
        byte[] data = null;
        if (password == null) {
            password = "";
        }
        StringBuffer sb = new StringBuffer(32);
        sb.append(password);
        while (sb.length() < KEY_LENGTH) {
            sb.append("0");
        }
        if (sb.length() > KEY_LENGTH) {
            sb.setLength(KEY_LENGTH);
        }

        try {
            data = sb.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        SecretKeySpec keySpec = new SecretKeySpec(data, "AES");
        return new SecretKeySpec(data, "AES");
    }


    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     *
     * @param arrB 需要转换的byte数组
     * @return 转换后的字符串
     */
    public static String byteArr2HexStr(byte[] arrB) {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     *
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     */
    public static byte[] hexStr2ByteArr(String strIn) {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param keyString 加密密码
     * @return
     */
    public static String AESEncrypt(String content, String keyString) {
        byte[] ba;
        try {
            ba = content.getBytes("UTF-8");
            byte[] result = AESEncrypt(ba, keyString);
            return byteArr2HexStr(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     *
     * @param byteContent 需要加密的内容
     * @param keyString 加密密码
     * @return
     */
    public static byte[] AESEncrypt(byte[] byteContent, String keyString) {
        try {
            SecretKeySpec key = createKey(keyString);
            Cipher cipher = Cipher.getInstance(CipherMode);// 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (Exception e) {
            throw new RuntimeException("Can't encrypt:" + e.getMessage(), e);
        }
    }

    /**
     * 解密
     *
     * @param content 待解密内容
     * @param keyString 解密密钥
     * @return
     */
    public static byte[] AESDecrypt(byte[] content, String keyString) {
        try {
            SecretKeySpec key = createKey(keyString);
            Cipher cipher = Cipher.getInstance(CipherMode);// 创建密码器   
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化   
            byte[] result = cipher.doFinal(content);
            return result; // 加密   
        } catch (Exception e) {
            throw new RuntimeException("Can't encrypt:" + e.getMessage(), e);
        }
    }

    /**
     * 解密
     *
     * @param content 待解密内容
     * @param keyString 解密密钥
     * @return
     */
    public static String AESDecrypt(String content, String keyString) {
        byte[] result = AESDecrypt(hexStr2ByteArr(content), keyString);
        try {
            return new String(result, "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * encode password by MD5 digest.
     *
     * @param rawpassword the rawpassword
     * @return the string
     */
    public static String MD5encode(String rawpassword) {
        byte[] input = null;
        try {
            input = rawpassword.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] output = getMd5MessageDigest().digest(input);
        return byteArr2HexStr(output);
    }


    /**
     * SHA-1 摘要，用来替代MD5算法
     *
     * @param rawpassword
     * @return
     */
    public static String SHA1Encode(String rawpassword) {
        byte[] input = rawpassword.getBytes();
        byte[] output = getSha1MessageDigest().digest(input);
        return byteArr2HexStr(output);
    }

	/*
     * Set of characters that is valid. Must be printable, memorable, and "won't
	 * break HTML" (i.e., not ' <', '>', '&', '=', ...). or break shell commands
	 * (i.e., not ' <', '>', '$', '!', ...). I, L and O are good to leave out,
	 * as are numeric zero and one.
	 */
    /**
     * The good char.
     */
    protected static char[] goodChar = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * Minimum length for a decent password.
     */
    public static final int MIN_LENGTH = 8;

    /**
     * The random number generator.
     */
    protected static java.util.Random r = new java.util.Random();

    protected static String[] smsChar = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
    public static final int SMS_MIN_LENGTH = 6;

    public static String generateSmsPassword() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < SMS_MIN_LENGTH; i++) {
            sb.append(smsChar[r.nextInt(smsChar.length)]);
            //try {Thread.sleep();} catch(Exception e) {}
        }
        return sb.toString();
    }

    public static String encryptGetParams(String params) {
        String[] paramsArr = params.split("&");
        Arrays.sort(paramsArr);//排序
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < paramsArr.length; i++) {
            if (i != 0) {
                sb.append("&");
            }
            sb.append(paramsArr[i]);
        }

        return PasswordUtil.MD5encode(sb.toString() + Constants.AES_KEY);

    }

    public static String encrypt(String params) {
        String[] paramsArr = params.split("&");
        StringBuffer sb = new StringBuffer();
        int firstParam = 0;
        for (int i = 0; i < paramsArr.length; i++) {
            if (paramsArr[i].contains("file=File")) {
                firstParam++;
                continue;
            }

            if (i > firstParam)
                sb.append("&");

            sb.append(paramsArr[i]);
        }

        return PasswordUtil.MD5encode(sb.toString() + Constants.AES_KEY);
    }
}
