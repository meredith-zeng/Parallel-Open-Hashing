package util;

import java.util.Random;

public class MathUtil {

    public static int getRandomOperation(Random random){
        int curRand = random.nextInt(100);
        int randomOperation;
        if(curRand > 0 && curRand < 70){
            // get
            randomOperation = 0;
        }else if(curRand >= 70 && curRand < 90){
            // put
            randomOperation = 1;
        }else{
            // delete
            randomOperation = 2;
        }
        return randomOperation;
    }
    public static String reverseBits(String hexString){
        int n = Integer.parseInt(hexString, 16);
        int ans = ((n >> 24) & 0xff);
        ans = ans | ((n << 24) & 0xff000000);
        ans = ans | ((n << 8) & 0xff0000);
        ans = ans | ((n >> 8) & 0xff00);
        return Integer.toHexString(ans);
    }


    // 2^3 = 8, 2^4's = 16, 2^5 = 32, 2^6 = 64
    // 2^7 = 128, 2^8 = 256, 2^9 = 512, 2^10 = 1024
    // 2^11 = 2048, 2^12 = 4096, 2^13 = 8192, 2^14 = 16384

    // 7, 17, 31, 61, 127..
    public static int floorPrime(int num){
        // increase
        int largerN = (num << 1);
        while(!isPrime(largerN)){
            largerN++;
        }
        int absOfLargerN = Math.abs((num << 1) - largerN);
        int smallerN = (num << 1);
        while(!isPrime(smallerN)){
            smallerN--;
        }
        int absOfSmallerN = Math.abs((num << 1) - smallerN);
        return absOfLargerN >= absOfSmallerN ? smallerN : largerN;
    }

    public static int ceilingPrime(int num){
        // decrease
        int largerN = (num >> 1);
        while(!isPrime(largerN)){
            largerN++;
        }
        int absOfLargerN = Math.abs((num >> 1) - largerN);
        int smallerN = (num >> 1);
        while(!isPrime(smallerN)){
            smallerN--;
        }
        int absOfSmallerN = Math.abs((num >> 1) - smallerN);
        return absOfLargerN >= absOfSmallerN ? smallerN : largerN;
    }

    private static boolean isPrime(int num) {
        int cur = 2, max = (int) Math.sqrt(num);
        while(cur <= max){
            if(num % cur == 0){
                return false;
            }
            cur++;
        }
        return true;
    }

}
