package cn.kai.simple_project.common.utils;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Author: chenKai
 * Date: 2022/12/30
 */
public class StringUtils {
    private static String[] chars = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
            "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
            "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };

    public static synchronized String random(int len) {
        StringBuffer sb = new StringBuffer();
        Random ran = new Random();
        for (int i = 0; i < len; i++) {
            int r = ran.nextInt(chars.length);
            sb.append(chars[r]);
        }
        return sb.toString();
    }


    public static String ridUrl(String url) {
        return url == null ? null : url.replace(",", "");
    }

    public static String getUrl(String qyUrl, String url) {
        if (url != null && url.contains(",")) {
            url = url.replace(",", "");
        }
        qyUrl = StringUtils.nvl(qyUrl);
        if ("".equals(qyUrl)) {
            qyUrl = url;
        } else {
            String[] arr = qyUrl.split(",");
            boolean flag = true;
            for (int i = 0; i < arr.length; i++) {
                if (url.equals(arr[i])) {
                    flag = false;
                    break;
                } else {
                    continue;
                }
            }
            if (flag) {
                qyUrl = qyUrl.concat(",").concat(url);
            }
        }
        return qyUrl;
    }

    public static int toInt(String s, int def) {
        int value = def;

        try {
            value = Integer.parseInt(s);
        } catch (Exception e) {
            value = def;
        }

        return value;
    }

    /**
     * getLength ?????????????????????
     *
     * @param o
     *            ????????????
     * @return string
     *
     */
    public static String nvl(Object o) {
        return (null == o) ? "" : o.toString().trim();
    }

    /**
     * ??????str???null???????????????,????????????str
     *
     * @param str
     * @return
     */
    public static String isNull(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }

    public static String isNull(Object o) {
        if (o == null) {
            return "";
        }
        String str = "";
        if (o instanceof String) {
            str = (String) o;
        } else {
            str = o.toString();
        }
        return str;
    }

    /**
     * ??????email??????????????????????????????true????????????????????????
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        email = isNull(email);
        Pattern regex = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher matcher = regex.matcher(email);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    /**
     * ????????????????????????
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobile(String mobiles) {
        Pattern p = Pattern.compile("^1[3|4|5|7|8][0-9]{9}$");
        // Pattern p =
        // Pattern.compile("^13\\d{9}|14[5,7,9]\\d{8}|15[0,1,2,3,5,6,7,8,9]\\d{8}|17[0,1,3,5,6,7,8]\\d{8}|18\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * ?????????????????????????????????true????????????????????????
     *
     * @return
     */
    public static boolean isCard(String cardId) {
        cardId = isNull(cardId);
        // ????????????????????????(15???)
        Pattern isIDCard1 = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
        // ????????????????????????(18???)
        Pattern isIDCard2 = Pattern
                .compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
        Matcher matcher1 = isIDCard1.matcher(cardId);
        Matcher matcher2 = isIDCard2.matcher(cardId);
        boolean isMatched = matcher1.matches() || matcher2.matches();
        return isMatched;
    }

    /**
     * ??????????????????????????????
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        if (isEmpty(str)) {
            return false;
        }
        Pattern regex = Pattern.compile("\\d*");
        Matcher matcher = regex.matcher(str);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    /**
     * ??????????????????????????????
     *
     * @param str
     * @return
     */
    public static boolean isInt(String str) {
        if (isEmpty(str)) {
            return false;
        }
        Pattern regex = Pattern.compile("\\d*(.[0]*)?");
        Matcher matcher = regex.matcher(str);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    /**
     * ??????????????????????????????
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if (isEmpty(str)) {
            return false;
        }

        Pattern regex = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        Matcher matcher = regex.matcher(str);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    /**
     * ???????????????????????????
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * ???????????????????????????
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * ???????????????????????????
     *
     * @param str
     *            2018/07/18
     * @return
     */
    public static boolean isEmptyAll(String str) {
        if (str == null || "".equals(str.trim()) || "null".equalsIgnoreCase(str)) {
            return true;
        }
        return false;
    }

    /**
     * ???????????????????????????
     *
     * @param str
     *            2018/07/18
     * @return
     */
    public static boolean isNotEmptyAll(String str) {
        return !isEmptyAll(str);
    }

    /**
     * ???????????????
     *
     * @param s
     * @return
     */
    public static String firstCharUpperCase(String s) {
        StringBuffer sb = new StringBuffer(s.substring(0, 1).toUpperCase());
        sb.append(s.substring(1, s.length()));
        return sb.toString();
    }

    public static String hideChar(String str, int len) {
        if (str == null){
            return null;
        }

        char[] chars = str.toCharArray();
        for (int i = 1; i < chars.length - 1; i++) {
            if (i < len) {
                chars[i] = '*';
            }
        }
        str = new String(chars);
        return str;
    }

    public static String hideCenterChar(String str, int len) {
        if (str == null){
            return null;
        }

        if (str.length() < 1) {
            return null;
        }
        char[] chars = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        sb.append(chars[0]);
        for (int i = 1; i < len - 1; i++) {
            sb.append('*');
        }
        sb.append(chars[chars.length - 1]);
        return sb.toString();
    }

    public static String hideLastChar(String str, int len) {
        if (str == null){
            return null;
        }

        char[] chars = str.toCharArray();
        if (str.length() <= len) {
            for (int i = 0; i < chars.length; i++) {
                chars[i] = '*';
            }
        } else {
            for (int i = chars.length - 1; i > chars.length - len - 1; i--) {
                chars[i] = '*';
            }
        }
        str = new String(chars);
        return str;
    }

    /**
     *
     * @return
     */
    public static String format(String str, int len) {
        if (str == null){
            return "-";
        }

        if (str.length() <= len) {
            int pushlen = len - str.length();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < pushlen; i++) {
                sb.append("0");
            }
            sb.append(str);
            str = sb.toString();
        } else {
            String newStr = str.substring(0, len);
            str = newStr;
        }
        return str;
    }

    /**
     * ???????????????
     * @param str
     * @param len
     * @return
     */
    public static String formatDouble(String str, int len) {
        if (str == null){
            return "-";
        }

        if (str.contains(".")) {
            String[] split = str.split("\\.");
            StringBuffer sb = new StringBuffer();
            sb.append(split[0]).append(".");
            if (split[1].length() < len) {
                sb.append(split[1]);
            } else {
                String s = split[1].substring(0, len);
                sb.append(s);
            }
            str = sb.toString();
        }
        return str;
    }

    public static String contact(Object[] args) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]);
            if (i < args.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param s
     * @param type
     * @return
     */
    public static boolean isInSplit(String s, String type) {
        if ("".equals(isNull(s))) {
            return false;
        }
        List<String> list = Arrays.asList(s.split(","));
        if (list.contains(type)) {
            return true;
        }
        return false;
    }

    /**
     * ??????????????????????????????????????????
     * @return
     */
    public static boolean isInSplitList(List<String> list, String str) {
        if (list!=null && list.size()>0) {
            for (String s : list) {
                if (str.contains(s)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isBlank(String str) {
        return "".equals(StringUtils.isNull(str));
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }


    public static String array2Str(Object[] arr) {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            s.append(arr[i]);
            if (i < arr.length - 1) {
                s.append(",");
            }
        }
        return s.toString();
    }

    public static String array2Str(int[] arr) {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            s.append(arr[i]);
            if (i < arr.length - 1) {
                s.append(",");
            }
        }
        return s.toString();
    }

    /**
     * ??????????????? ???????????????????????????????????????????????????4-16???(?![a-zA-Z]+$)
     *
     * @param username
     * @return
     */
    public static boolean checkUsername(String username) {
        // Pattern p = Pattern.compile("^[0-9A-Za-z\u0391-\uFFE5]{4,15}$");
        Pattern p = Pattern.compile("^(?!^\\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]{4,15}$");
        Matcher m = p.matcher(username);
        return m.matches();
    }

    /**
     * ?????????????????????
     *
     * @return
     */
    public static String generateUsername() {
        return getUuid().substring(0, 16).toLowerCase();
    }

    /**
     * ?????????????????????+??????8?????????
     *
     * @param pwd
     * @return
     */
    public static boolean checkwd(String pwd) {
        String regEx = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z~!@#$%^&*()]{8,16}$";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(pwd);
        boolean rs = mat.find();
        return rs;
    }

    /**
     * ????????????????????????
     *
     * @param pwd
     * @return
     */
    public static boolean pwdContainStr(String pwd) {
        Pattern p = Pattern.compile("[a-z|A-Z]");
        Matcher m = p.matcher(pwd);
        return m.find();
    }

    /**
     * ????????????????????????
     *
     * @param pwd
     * @return
     */
    public static boolean pwdContainNum(String pwd) {
        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(pwd);
        return m.find();
    }

    /**
     * ?????????????????????/??????+???????????????
     *
     * @param pwd
     * @return
     */
    public static boolean isLoginPssword(String pwd) {
        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)(?![~!@#$%^&*()]+$)[0-9A-Za-z~!@#$%^&*()]{8,16}$");
        Matcher m = p.matcher(pwd);
        return m.find();
    }

    /**
     * ?????????????????????????????????????????????+??????+????????????10?????????
     *
     * @param pwd
     * @return
     */
    public static boolean checaAdminPwd(String pwd) {
        String regEx = "^(?![0-9A-Za-z]+$)[0-9A-Za-z~!@#$%^&*()]{10,}$";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(pwd);
        boolean rs = mat.find();
        return rs;

    }

    public static String gbk2Utf(String gbk) throws UnsupportedEncodingException {
        char[] c = gbk.toCharArray();
        byte[] fullByte = new byte[3 * c.length];
        for (int i = 0; i < c.length; i++) {
            String binary = Integer.toBinaryString(c[i]);
            StringBuffer sb = new StringBuffer();
            int len = 16 - binary.length();
            // ????????????
            for (int j = 0; j < len; j++) {
                sb.append("0");
            }
            sb.append(binary);
            // ?????????????????????24???3?????????
            sb.insert(0, "1110");
            sb.insert(8, "10");
            sb.insert(16, "10");
            fullByte[i * 3] = Integer.valueOf(sb.substring(0, 8), 2).byteValue();// ??????????????????????????????
            fullByte[i * 3 + 1] = Integer.valueOf(sb.substring(8, 16), 2).byteValue();
            fullByte[i * 3 + 2] = Integer.valueOf(sb.substring(16, 24), 2).byteValue();
        }
        // ??????UTF-8?????????????????????
        return new String(fullByte, "UTF-8");
    }

    public static boolean checkDateString(String dateStr) {
        String eL = "[1-9]{1}[0-9]{3}-[0-9]{2}-[0-9]{2}";
        Pattern p = Pattern.compile(eL);
        Matcher m = p.matcher(dateStr);
        return m.matches();
    }

    public static String getGbk(String str) throws UnsupportedEncodingException {
        return new String(str.getBytes("UTF-8"), "GB2312");
    }

    public static String getEmailLoginUrl(String email) {
        String url = email.split("@")[1];
        return "http://mail." + url;
    }

    public static String[] StringSort(String[] str) {
        MyString mySs[] = new MyString[str.length];// ??????????????????????????????
        for (int i = 0; i < str.length; i++) {
            mySs[i] = new MyString(str[i]);
        }
        Arrays.sort(mySs);// ??????
        String[] str2 = new String[mySs.length];
        for (int i = 0; i < mySs.length; i++) {
            str2[i] = mySs[i].s;
        }
        return str2;
    }

    /**
     * ???????????????????????????????????????????????????
     *
     * @param type
     *            ???????????????
     * @param numb
     *            ????????????
     * @param endNum
     *            :??????????????? ?????????????????????HT+20130927????????????+0013(?????????????????????)+09???????????????
     *            ????????????:HT20130927001391,???????????????TZ130927010919,???????????????JK130927001391
     * @return
     */
    public static String madeAgreementNo(String type, String date, int numb, int endNum) {
        // int endNum=(int)(Math.random()*90)+10;
        if (numb < 10) {
            return type + date + "000" + numb + endNum;
        } else if (numb < 100) {
            return type + date + "00" + numb + endNum;
        } else if (numb < 1000) {
            return type + date + "0" + numb + endNum;
        } else {
            return type + date + numb + endNum;
        }
    }


    /**
     * ???????????????
     *
     * @param sStr
     * @return String
     */
    public static String UrlDecoder(String sStr) {
        String sReturnCode = sStr;
        try {
            sReturnCode = URLDecoder.decode(sStr, "utf-8");
        } catch (Exception e) {
        }
        return sReturnCode;
    }

    /**
     * ???????????????
     *
     * @param sStr
     * @return String
     */
    public static String UrlEncode(String sStr) {
        String sReturnCode = "";
        try {
            sReturnCode = URLEncoder.encode(sStr, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sReturnCode;
    }

    /**
     * ????????????????????????ip
     *
     * @return
     */
    public static String getServerIp() {
        String serverIp = "";
        try {
            serverIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return serverIp;
    }

    /**
     * ??????UUID
     *
     * @Title: getUuid
     * @Description: TODO
     * @Param @return
     * @Return String
     * @Throws
     */
    public static String getUuid() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }

    public static String getExceptionInfo(Throwable t) {
        if (t == null){
            return "";
        }

        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            String info = sw.toString() + "\r\n";
            pw.close();
            sw.close();
            return info;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    /**
     * ????????????,?????????????????????????????????,???????????????
     * ???????????????0.0?????????11
     * @param cost
     * @return
     */
    public static  String getNumber(String cost) {
        Pattern compile = Pattern.compile("(\\d+\\.\\d+)|(\\d+)");
        Matcher matcher = compile.matcher(cost);
        matcher.find();
        return matcher.group();
    }

    /**
     * ???????????????????????????????????????
     * @param content
     * @return
     */
    public static boolean isContainNumber(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }

    /**
     * ????????????????????????????????????????????????????????????
     * @param str ?????????????????????
     * @return ??????????????????
     * true: ???????????? ;false ???????????????
     */
    public static boolean judgeContainsStr(String str) {
        String regex=".*[a-zA-Z]+.*";
        Matcher m=Pattern.compile(regex).matcher(str);
        return m.matches();
    }

    /**
     * ???????????????()????????????
     */
    public static String convertStr(String str) {
        if (StringUtils.isNotEmpty(str)) {
            if (StringUtils.isContainChinese(str)) {
                //???????????????
                return str.replaceAll(" ","");
            }else {
                //?????????????????????????????????
                return str.replaceAll("???.*????|\\(.*?\\)|???.*?\\)|\\(.*????", "").replaceAll(" ","").toUpperCase();
            }
        }
        return str;
    }

    /**
     * ????????????????????????????????????
     * @param str
     * ??????????????????
     * @return ???????????????
     * @warn ???????????????????????????????????????
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
    /**
     * ????????????????????????????????????????????????
     *
     * @param str
     * @return
     */
    public static String removeBracketAndContent(String str) {
        if (str.contains("(")) {
            final int i1 = str.indexOf("(");
            final int i2 = str.indexOf(")");
            final String temp = str.substring(i1, i2 + 1);
            return str.replace(temp, "");
        }
        if (str.contains("???")) {
            final int i1 = str.indexOf("???");
            final int i2 = str.indexOf("???");
            final String temp = str.substring(i1, i2 + 1);
            return str.replace(temp, "");
        }
        return str.trim();
    }
}

class MyString implements Comparable<MyString> {
    public String s;// ??????String

    public MyString(String s) {
        this.s = s;
    }

    @Override
    public int compareTo(MyString o) {
        if (o == null || o.s == null){
            return 1;
        }
        return s.compareTo(o.s);
    }

}

