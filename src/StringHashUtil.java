import java.util.LinkedList;
import java.util.List;

public class StringHashUtil {
    public static int generateHashCode(String keyStr){
        List<String> chunk = new LinkedList<>();
        StringBuilder sb = new StringBuilder();

        int idx = 0, len = keyStr.length();
        while(idx < len){
            for(int i = 0; i < 4 && idx < len; i++){
                sb.append(Integer.toHexString(keyStr.charAt(idx++)));
            }
            if(sb.length() < 8){
                while(sb.length() < 8){
                    sb.append(Integer.toHexString('0'));
                }
            }
            chunk.add(sb.toString().toUpperCase());
            sb = new StringBuilder();
        }

        for(int i = 0; i < chunk.size(); i += 2){
            String beforeReverseStr = chunk.get(i);
            String afterReverseStr = reverseBits(beforeReverseStr);
            chunk.set(i, afterReverseStr);
        }

        int hashCode = Integer.parseInt(chunk.get(0), 16);
        for(int i = 1; i < chunk.size(); i++){
            hashCode ^= Integer.parseInt(chunk.get(i), 16);
        }
        return hashCode;
    }

    public static String reverseBits(String hexString){
        int n = Integer.parseInt(hexString, 16);
        int ans = 0;
        for(int i = 0; i < 32; i++){
            ans <<= 1;
            ans = ans | (n & 1);
            n >>= 1;
        }
        return Integer.toHexString(ans);
    }
}
