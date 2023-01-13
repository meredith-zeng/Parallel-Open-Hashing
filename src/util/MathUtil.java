package util;

public class MathUtil {
    public static String reverseBits(String hexString){
        int n = Integer.parseInt(hexString, 16);
        int ans = ((n >> 24) & 0xff)
                | ((n << 8) & 0xff0000)
                | ((n >> 8) & 0xff00)
                | ((n << 24) & 0xff000000);
        return Integer.toHexString(ans);
    }

    public static int getClosestPrime(int num){
        // 2^3 - 1, 2^4's -> 17
        // 7, 17, 31, 61, 127..
        return 7;
    }
}
