package multiThreadSong;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScannerMode {
    public static void randomMode(Scanner scanner){
        // For random mode
        // 1) number of threads to run concurrently
        // 2) number of operations a thread needs to handle
        // 3) lines of “song name” and “socket”.

        System.out.println("Number of threads to run concurrently should larger than 0 and less than Integer.MAX_VALUE: ");
        int concurrentThreadNum = scanner.nextInt();
//        int concurrentThreadNum = 3;
        System.out.println("Number of threads to run concurrently is: " + concurrentThreadNum);
//
        System.out.println("Number of operations a thread needs to handle should larger than 0 and less than Integer.MAX_VALUE: ");
        int cntOfOperations = scanner.nextInt();
//        int cntOfOperations = 20;
        System.out.println("Number of operations a thread is: " + cntOfOperations);

        List<String> songList = new ArrayList<>();
        System.out.println("Enter Several lines of \"song name\" and \"socket\".");
        System.out.println("Example: Listen to the music, http://foo.com:54321");
        System.out.println("Note: 1) Invalid pairs will be neglect.");
        System.out.println("      2) Enter \"eof\" to finish entering lines action");
        while(!scanner.hasNext("eof")){
            String songStr = scanner.nextLine();
            songList.add(songStr);
        }
        System.out.println("=================Start Execution=================");
        MusicStore musicStore = new MusicStore(concurrentThreadNum, cntOfOperations, songList);

        musicStore.randomModelThreadExecute();
    }

    // For manual mode
    // 1) operation type
    public static void manualMode(Scanner scanner){
        System.out.println("Note: Manual Mode will use main thread, which thread id is 0, to execute all operations.");
        System.out.println("=================Start Manual Entering=================");
        MusicStore musicStore = new MusicStore();
        System.out.println("Please enter operation type(GET/PUT/DELETE): ");
        while(!scanner.hasNext("eof")){

            String operationType = scanner.nextLine();
            if(!operationType.equals("GET") && !operationType.equals("PUT") && !operationType.equals("DELETE")){
                System.out.println("Please enter valid operation type.");
            }else{
                System.out.println("Note: 1) Invalid pairs will be neglect.");
                System.out.println("      2) Enter \"eof\" to finish entering lines action");
                if(operationType.equals("GET")){
                    System.out.println("Please enter song name: ");
                    String songName = scanner.nextLine();
                    manualGet(musicStore, songName);
                }else if(operationType.equals("PUT")){
                    System.out.println("Please enter song name: ");
                    String songName = scanner.nextLine();
                    System.out.println("Please enter socket: ");
                    System.out.println("Example: http://foo.com:54321");
                    String socket = scanner.nextLine();
                    manualPut(musicStore, songName, socket);
                }else if(operationType.equals("DELETE")){
                    System.out.println("Please enter song name: ");
                    String songName = scanner.nextLine();
                    System.out.println("Please enter socket: ");
                    System.out.println("Example: http://foo.com:54321");
                    String socket = scanner.nextLine();
                    manualDelete(musicStore, songName, socket);
                }
            }

        }

    }

    private static void manualGet(MusicStore musicStore, String songName){
        musicStore.getMusic(songName, 0);
    }

    private static void manualPut(MusicStore musicStore, String songName, String socket){
        musicStore.putMusic(songName, socket, 0);
    }

    private static void manualDelete(MusicStore musicStore, String songName, String socket){
        musicStore.putMusic(songName, socket, 0);
    }
}
