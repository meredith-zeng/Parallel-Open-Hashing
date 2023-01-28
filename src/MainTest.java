import hashtable.ThreadSafeHashTable;
import multiThreadSong.MusicStore;
import multiThreadSong.ScannerMode;
import util.MathUtil;

import java.util.*;

public class MainTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 1) manual or random mode


        System.out.println("Operation mode should be Random/Manual: ");
//        String operationMode = scanner.nextLine();
//        String operationMode = "Random";
//        if(!operationMode.equals("Random") && !operationMode.equals("Manual")){
//            System.out.println("Operation mode can only be Random/Manual.");
//        }
        String operationMode = "Manual";
        System.out.println("Current mode is: " + operationMode);

//        ScannerMode.randomMode(scanner);

        ScannerMode.manualMode(scanner);

    }
}

