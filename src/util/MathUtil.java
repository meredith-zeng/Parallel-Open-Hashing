package util;

public class MathUtil {
    public static String reverseBits(String hexString){
        int n = Integer.parseInt(hexString, 16);
        int ans = ((n >> 24) & 0xff);
        ans = ans | ((n << 8) & 0xff0000);
        ans = ans | ((n >> 8) & 0xff00);
        ans = ans | ((n << 24) & 0xff000000);
        return Integer.toHexString(ans);
    }


    // 2^3 = 8, 2^4's = 16, 2^5 = 32, 2^6 = 64
    // 2^7 = 128, 2^8 = 256, 2^9 = 512, 2^10 = 1024
    // 2^11 = 2048, 2^12 = 4096, 2^13 = 8192, 2^14 = 16384

    // 7, 17, 31, 61, 127..
    public static int floorPrime(int num){
        // increase
        int n = (num << 1) + 1;
        return getClosestPrime(n);
    }

    public static int ceilingPrime(int num){
        // decrease
        int n = (num >> 1) + 1;
        return getClosestPrime(n);
    }

    public static int getClosestPrime(int n){
        if (n % 2 != 0){
            n -= 2;
        }else{
            n--;
        }

        for (int i = n; i >= 2; i -= 2) {
            int cur = 3;
            for (; cur <= Math.sqrt(i); cur += 2) {
                if (i % cur == 0){
                    break;
                }
            }
            if (cur > Math.sqrt(i)){
                return i;
            }
        }
        return 7;
    }
}
