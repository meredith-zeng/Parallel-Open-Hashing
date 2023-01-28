import hashtable.ThreadSafeHashTable;
import multiThreadSong.MusicStore;
import multiThreadSong.ScannerMode;
import util.MathUtil;

import java.util.*;
import java.util.regex.Pattern;

public class MainTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 1) manual or random mode
        System.out.println("Operation mode should be random/manual: ");
        String operationMode = scanner.nextLine();
        int testCnt = 0;
        while(!operationMode.equals("random") && !operationMode.equals("manual") && testCnt < 10){
            testCnt++;
            System.out.println("Operation mode can only be random/manual.");
            if(testCnt == 10){
                System.out.println("You enter wrong mode ten times. Execution end.");
                return;
            }
        }
        System.out.println("Current mode is: " + operationMode);

        if(operationMode.equals("random")){
            ScannerMode.randomMode(scanner);
        }else if(operationMode.equals("manual")){
            ScannerMode.manualMode(scanner);
        }

//        String urlPattern = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
//        String url = "testUrl";
//        boolean isMatch = Pattern.matches(urlPattern,url);
//        if(!isMatch){
//            continue;
//        }

    }
}

