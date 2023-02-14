package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StringUtil {
    public static final String URL_CORRECT_PATTERN = "(http?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z]{2,6})(:\\d{1,5})([\\/\\w\\.-]*)*\\/?";
    public static final String STRING_TO_INT_PATTERN = "^[0-9]*$";

    public static List<String> chunkSplit(String str){
        List<String> chunks = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        int idx = 0, len = str.length();
        while(idx < len){
            for(int i = 0; i < 4 && idx < len; i++){
                sb.append(Integer.toHexString(str.charAt(idx++)));
            }
            if(sb.length() < 8){
                while(sb.length() < 8){
                    sb.append(Integer.toHexString('0'));
                }
            }
            chunks.add(sb.toString().toUpperCase());
            sb = new StringBuilder();
        }
        return chunks;
    }

    public static boolean urlPatternCheck(String url){
        return Pattern.matches(URL_CORRECT_PATTERN,url);
    }

    public static boolean stringToIntCheck(String str){
        return Pattern.matches(STRING_TO_INT_PATTERN, str);
    }

}
