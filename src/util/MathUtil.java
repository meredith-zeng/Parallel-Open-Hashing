package util;

public class MathUtil {
    public static int[] PRIMES_ARRAY = new int[]{ 7, 17, 31, 61, 127 };
    public static String reverseBits(String hexString){
        int n = Integer.parseInt(hexString, 16);
        int ans = ((n >> 24) & 0xff)
                | ((n << 8) & 0xff0000)
                | ((n >> 8) & 0xff00)
                | ((n << 24) & 0xff000000);
        return Integer.toHexString(ans);
    }


    // 2^3 = 8, 2^4's = 16, 2^5 = 32, 2^6 = 64
    // 2^7 = 128, 2^8 = 256, 2^9 = 512, 2^10 = 1024
    // 2^11 = 2048, 2^12 = 4096, 2^13 = 8192, 2^14 = 16384

    // 7, 17, 31, 61, 127..
    public static int getClosestPrimeLess(int num){
        // increase
        int n = (num << 1) + 1;

        // decrese
//        int n = (num >> 1) + 1;

        if (n % 2 != 0){
            n -= 2;
        }else{
            n--;
        }

        int i, j;
        for (i = n; i >= 2; i -= 2) {
            for (j = 3; j <= Math.sqrt(i); j += 2) {
                if (i % j == 0){
                    break;
                }
            }
            if (j > Math.sqrt(i)){
                return i;
            }
        }

        return 7;
    }


}
