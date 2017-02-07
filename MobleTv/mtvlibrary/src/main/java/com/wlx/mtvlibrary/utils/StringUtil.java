package com.wlx.mtvlibrary.utils;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class StringUtil {

    /**
     * 得到 xx.00形式的数值字符串
     */
    public static String float2Str(float num) {
        DecimalFormat format = new DecimalFormat("0.00");
        String string = format.format(num);
        return string;
    }

    /**
     * 产生一个随机的字符串
     *
     * @return
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }

        return sb.toString();
    }

    /**
     * 获取url地址中的参数，用哈希列表返回结果
     *
     * @param url
     * @param isUrlEncode 表示是否需要做url转义
     * @return
     */
    public static Hashtable<String, String> getUrlParam(String url, boolean isUrlEncode) {
        Hashtable result = new Hashtable<String, String>();
        try {
            if (!isNullOrEmpty(url)) {
                if (url.contains("?")) {
                    url = url.substring(url.indexOf("?") + 1);
                }
                String[] params = url.split("&");
                String key = "";
                String value = "";
                for (int i = 0; i < params.length; i++) {
                    if (params[i].contains("=")) {
                        key = params[i].substring(0, params[i].indexOf("="));
                        value = params[i].substring(params[i].indexOf("=") + 1);
                        if (isUrlEncode) {
                            value = java.net.URLDecoder.decode(value, "utf-8");
                        }
                        result.put(key, value);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 得到本地html浏览的路径
     *
     * @return
     */
    public static String getHtmlLocalPath() {
        String htmlLocalPath = "content://com.android.htmlfileprovider";
        int version = android.os.Build.VERSION.SDK_INT;
        if (version >= 9)
            htmlLocalPath = "file://";

        return htmlLocalPath;
    }

    /**
     * 将字符串转成html格式，将\r\n换成<br/>
     * ,在最外层用p标签包围
     *
     * @param content
     * @return
     */
    public static String getHtmlString(String content) {
        return getHtmlString(content, "");
    }

    /**
     * 根据输入字符串，转换成html文本，将\r\n换成<br/>
     * ,在最外层用p标签包围，classname为最外层p的css eg: getHtmlString("test\r\n123","title")
     * 结果为
     * <p class="title">
     * test><br/>
     * 123
     * </p>
     *
     * @param content
     * @param className
     * @return
     */
    public static String getHtmlString(String content, String className) {
        String result = "";
        if (!isNullOrEmpty(content)) {
            content = content.replace("\r", "");
            result = "<p " + className + " >" + content.replace("\n", "<br />") + "</p>";
        }

        return result;
    }

    /**
     * 判断字符串是否有值，如果为null或者是空字符串或者只有空格，则返回true，否则则返回false
     *
     * @param value
     * @return
     */
    public static boolean isNullOrEmpty(String value) {
        boolean result = false;
        if (value != null && !"".equalsIgnoreCase(value.trim())) {
            result = false;
        } else {
            result = true;
        }

        return result;
    }

    /**
     * 严格判断字符串是否有值，如果为null或者为"NULL","null",或空字符串或者只有空格，则返回true，否则则返回false
     *
     * @param value
     * @return
     */
    public static boolean isEmpty(String value) {
        if (value == null || "".equalsIgnoreCase(value.trim()) || "null".equalsIgnoreCase(value.trim())) {
            return true;
        }

        return false;
    }

    /**
     * MD5转码
     *
     * @param input
     * @return
     */
    public static String toMD5(String input) {
        // LogUtil.d("开始对字符串进行MD5:" + input);
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byte2hex(md.digest(input.getBytes("utf-8")));
        } catch (Exception e) {
            throw new RuntimeException("sign error !");
        }

        return result;
    }

    /**
     * 二行制转字符串
     */
    public static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }

        return hs.toString().toUpperCase();
    }

    /**
     * 获取url连接的扩展名
     *
     * @param url
     * @return
     */
    public static String getMiniTypeFromUrl(String url) {
        String end = url.substring(url.lastIndexOf(".") + 1, url.length()).toLowerCase();
        if (end.indexOf("?") > -1) {
            end = end.substring(0, end.indexOf("?"));
        }
        if (end.indexOf("/") > -1) {
            end = end.substring(0, end.length() - 1);
        }

        String type = "";
        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
            type = "audio/*";
        } else if (end.equals("3gp") || end.equals("mp4")) {
            type = "video/*";
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals("bmp")) {
            type = "image/*";
        } else if (end.equals("apk")) {
            /* android.permission.INSTALL_PACKAGES */
            type = "application/vnd.android.package-archive";
        }
        // 加入对ms office及pdf,rar,zip的判断
        else if (end.equals("doc")) {
            type = "application/msword";
        } else if (end.equals("docx")) {
            type = "application/msword";
        } else if (end.equals("xls")) {
            type = "application/msexcel";
        } else if (end.equals("xlsx")) {
            type = "application/msexcel";
        } else if (end.equals("ppt")) {
            // type = "application/msppt";
            type = "application/vnd.ms-powerpoint";
        } else if (end.equals("pptx")) {
            type = "application/vnd.ms-powerpoint";
        } else if (end.equals("pdf")) {
            type = "application/pdf";
        } else if (end.equals("rar")) {
            type = "application/rar";
        } else if (end.equals("zip")) {
            type = "application/zip";
        }
        // 判断是否链接
        else if (end.equals("html") || end.equals("htm") || end.equals("shtml") || end.equals("asp") || end.equals("aspx") || end.equals("jsp") || end.equals("php") || end.equals("perl") || end.equals("cgi") || end.equals("xml") || end.equals("com") || end.equals("cn") || end.equals("mobi") || end.equals("tel") || end.equals("asia") || end.equals("net") || end.equals("org") || end.equals("name") || end.equals("me") || end.equals("info") || end.equals("cc") || end.equals("hk") || end.equals("biz") || end.equals("tv") || end.equals("公司") || end.equals("网络") || end.equals("中国")) {
            type = "link";
        }
        /* 如果无法直接打开，就跳出软件列表给用户选择 */
        else {
            type = "/*";
        }

        return type;
    }

    /**
     * 获取时间戳,格式“MMddHHmmss”
     *
     * @return
     */
    public static String getCurrentTime() {
        return getCurrentTime("MMddHHmmss");
    }

    /**
     * 获取指定格式的时间戳
     *
     * @param format
     * @return
     */
    public static String getCurrentTime(String format) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        return dateFormat.format(date);
    }

    /**
     * 判断给定的字符串是不是数字
     *
     * @param strNum
     * @return
     */
    public static boolean isNumber(String strNum) {
        boolean result = false;
        if (strNum == null || "".equalsIgnoreCase(strNum)) {
            result = false;
        } else {
            Pattern pattern = Pattern.compile("^[\\-]?\\d*$");
            Matcher matcher = pattern.matcher(strNum);
            result = matcher.matches();
        }

        return result;
    }

    /**
     * 转换字符为整数
     *
     * @param strValue
     * @param def      出错的默认值
     * @return
     */
    public static int convertToInt(String strValue, int def) {
        int result = def;
        try {
            if (isNumber(strValue)) {
                result = Integer.parseInt(strValue);
            } else {
                result = def;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            result = def;
        }

        return result;
    }

    /**
     * 过滤xml文件中的非法字符，将xml 用到的特舒符号转义，如"&"转为"&amp;" "<"转为 "&lt;"
     *
     * @param strValue
     * @return
     */
    public static String getSafeXmlString(String strValue) {
        String result = "";
        if (strValue != null) {
            result = strValue.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
            result = result.replace("'", "&apos;").replace("\"", "&quot;");
        }

        return result;
    }

    /**
     * 根据60进制的字符串，解析出10进制的字符串
     *
     * @param str62
     * @return
     */
    public static String getStr10FromStr62(String str62) {
        long result = 0;
        try {
            for (int i = 0; i < str62.length(); i++) {
                int c = str62.charAt(str62.length() - i - 1);
                // 0-9: 48-57 a-z:97-122 A-Z:65-90
                if (c >= '0' && c <= '9') {
                    c = c - '0';
                } else if (c >= 'a' && c <= 'z') {
                    c = c - 'a' + 10;
                } else if (c >= 'A' && c <= 'Z') {
                    c = c - 'A' + 36;
                } else {
                    result = 0;
                    break;
                }

                result += c * Math.pow(62, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result + "";
    }

    /**
     * 大整数相加
     *
     * @param numFirst
     * @param numSecond
     * @return
     */
    public static String bigNumPlus(String numFirst, String numSecond) {

        String rtnResult = "";
        int carryValue = 0;

        int iFirst = numFirst.length() - 1, iSecond = numSecond.length() - 1;
        for (; iFirst >= 0 && iSecond >= 0; iFirst--, iSecond--) {
            int tempFirst = numFirst.charAt(iFirst) - '0';// 此处如果不减去0的ASCII码值，得到的是对应数字的ASCII码值
            int tempSecond = numSecond.charAt(iSecond) - '0';
            int tempSum = tempFirst + tempSecond + carryValue;

            if (tempSum >= 10) {
                rtnResult = (tempSum - 10) + rtnResult;
                carryValue = 1;
            } else {
                rtnResult = tempSum + rtnResult;
                carryValue = 0;
            }

        }// 两数值共有长度部分相加

        // 处理较长数值的数
        while (iFirst >= 0) {
            int tempSum = numFirst.charAt(iFirst--) - '0' + carryValue;
            if (tempSum >= 10) {
                rtnResult = "0" + rtnResult;// 最后有进位，切当前位值为 9
                carryValue = 1;
            } else {
                rtnResult = tempSum + rtnResult;
                carryValue = 0;
            }
        }
        while (iSecond >= 0) {
            int tempSum = numSecond.charAt(iSecond--) - '0' + carryValue;
            if (tempSum >= 10) {
                rtnResult = "0" + rtnResult;
                carryValue = 1;
            } else {
                rtnResult = tempSum + rtnResult;
                carryValue = 0;
            }
        }

        if (carryValue > 0)
            rtnResult = carryValue + rtnResult;

        return rtnResult;
    }

    /**
     * 大整数相乘
     *
     * @param firstValue
     * @param secondValue
     * @return
     */
    public static String bigNumMultiply(String firstValue, String secondValue) {
        firstValue = firstValue.trim();
        secondValue = secondValue.trim();
        if (firstValue.equalsIgnoreCase("0") || secondValue.equalsIgnoreCase("0"))
            return "0";

        ArrayList<String> mutResultItems = new ArrayList<String>();// 存放单个位数字与被乘数的结果

        for (int i = secondValue.length() - 1; i >= 0; i--) {
            String resultItem = "";
            int carryValue = 0;
            int curDigit = secondValue.charAt(i) - '0';
            if (curDigit == 0)
                continue;
            for (int j = firstValue.length() - 1; j >= 0; j--) {
                int mutValue = curDigit * (firstValue.charAt(j) - '0') + carryValue;

                carryValue = mutValue / 10;
                mutValue = mutValue % 10;

                resultItem = mutValue + resultItem;
            }
            if (carryValue > 0)
                resultItem = carryValue + resultItem;

            for (int k = secondValue.length() - 1 - i; k > 0; k--)// 补零，便于相加
            {
                resultItem = resultItem + "0";
            }
            mutResultItems.add(resultItem);
        }

        String rtnResult = "0";
        for (String value : mutResultItems) {
            rtnResult = bigNumPlus(value, rtnResult);
        }

        return rtnResult;
    }

    /**
     * 62进制转为十进制
     *
     * @param sixtyTwoValue
     * @return
     */
    public static String convertSixtyTwoToDecimal(String sixtyTwoValue) {
        sixtyTwoValue = sixtyTwoValue.trim();

        String rtnResult = "0";
        for (int i = 0; i < sixtyTwoValue.length(); i++) {
            String curCharValue = convertCharToUInt64(sixtyTwoValue.charAt(i)) + "";
            int powerLength = sixtyTwoValue.length() - i - 1;

            String powerValue = "0";
            if (powerLength > 10) {
                powerValue = "839299365868340224";// Convert.ToUInt64(Math.pow(62,
                // 10)).ToString();
                for (int j = powerLength; j > 10; j--) {
                    powerValue = bigNumMultiply(powerValue, "62");
                }
            } else {
                powerValue = (long) Math.pow(62, powerLength) + "";
            }
            rtnResult = bigNumPlus(rtnResult, bigNumMultiply(powerValue, curCharValue));
        }

        return rtnResult;
    }

    private static int convertCharToUInt64(char c) {
        switch (c) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;

            case 'a':
                return 10;
            case 'b':
                return 11;
            case 'c':
                return 12;
            case 'd':
                return 13;
            case 'e':
                return 14;
            case 'f':
                return 15;
            case 'g':
                return 16;
            case 'h':
                return 17;
            case 'i':
                return 18;
            case 'j':
                return 19;
            case 'k':
                return 20;
            case 'l':
                return 21;
            case 'm':
                return 22;
            case 'n':
                return 23;
            case 'o':
                return 24;
            case 'p':
                return 25;
            case 'q':
                return 26;
            case 'r':
                return 27;
            case 's':
                return 28;
            case 't':
                return 29;
            case 'u':
                return 30;
            case 'v':
                return 31;
            case 'w':
                return 32;
            case 'x':
                return 33;
            case 'y':
                return 34;
            case 'z':
                return 35;

            case 'A':
                return 36;
            case 'B':
                return 37;
            case 'C':
                return 38;
            case 'D':
                return 39;
            case 'E':
                return 40;
            case 'F':
                return 41;
            case 'G':
                return 42;
            case 'H':
                return 43;
            case 'I':
                return 44;
            case 'J':
                return 45;
            case 'K':
                return 46;
            case 'L':
                return 47;
            case 'M':
                return 48;
            case 'N':
                return 49;
            case 'O':
                return 50;
            case 'P':
                return 51;
            case 'Q':
                return 52;
            case 'R':
                return 53;
            case 'S':
                return 54;
            case 'T':
                return 55;
            case 'U':
                return 56;
            case 'V':
                return 57;
            case 'W':
                return 58;
            case 'X':
                return 59;
            case 'Y':
                return 60;
            case 'Z':
                return 61;
        }

        return 0;
    }

    static HashMap<String, SimpleDateFormat> hasSdf = null;

    public static Date parseDate(String str, String format) {
        if (str == null || "".equals(str)) {
            return null;
        }

        if (hasSdf == null) {
            hasSdf = new HashMap<String, SimpleDateFormat>();
        }
        if (!hasSdf.containsKey(format)) {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            hasSdf.put(format, sdf);
        }
        try {
            synchronized (hasSdf.get(format)) {
                // SimpleDateFormat is not thread safe
                return hasSdf.get(format).parse(str);
            }
        } catch (Exception pe) {
            return null;
        }
    }

}
