package util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
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
}
