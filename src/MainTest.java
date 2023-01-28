import hashtable.ThreadSafeHashTable;
import multiThreadSong.MusicStore;
import util.MathUtil;

import java.util.*;

public class MainTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 1) manual or random mode
        // 2) number of threads to run concurrently
        // 3) number of operations a thread needs to handle
        // 4) lines of “song name” and “socket”.
//        System.out.println("Operation mode should be Random/Manual: ");
//        String operationMode = scanner.nextLine();
        String operationMode = "Random";
//        System.out.println("Current mode is: " + operationMode);
//
//        System.out.println("Number of threads to run concurrently should larger than 0 and less than Integer.MAX_VALUE: ");
//        int concurrentThreadNum = scanner.nextInt();
        int concurrentThreadNum = 3;
//        System.out.println("Number of threads to run concurrently is: " + concurrentThreadNum);
//
//        System.out.println("Number of operations a thread needs to handle should larger than 0 and less than Integer.MAX_VALUE: ");
//        int cntOfOperations = scanner.nextInt();
        int cntOfOperations = 20;
//        System.out.println("Number of operations a thread is: " + cntOfOperations);

        List<String> songList = new ArrayList<>();
        System.out.println("Enter Several lines of \"song name\" and \"socket\".");
        System.out.println("Example: Listen to the music, http://foo.com:54321");
        System.out.println("Note: 1) Invalid pairs will be neglect.");
        System.out.println("      2) Enter \"eof\" to finish entering lines action");
        while(!scanner.hasNext("eof")){
            String songStr = scanner.nextLine();
            songList.add(songStr);
        }
        System.out.println("=================Start execution=================");
        MusicStore musicStore = new MusicStore(operationMode, concurrentThreadNum, cntOfOperations, songList);

    }
}

