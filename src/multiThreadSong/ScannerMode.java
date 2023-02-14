package multiThreadSong;

import util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScannerMode {
    public static int concurrentThreadNum = 0;
    public static int cntOfOperations = 0;
    public static List<String> songList = new ArrayList<>();

    public static void threadSafeScanner(Scanner scanner){
        // 1) manual or random mode
        System.out.println("Operation mode should be Random/Manual: ");

        String operationMode = scanner.nextLine();

        int testCnt = 0;
        while(!operationMode.equals("Random") && !operationMode.equals("Manual") && testCnt < 10){
            testCnt++;
            System.out.println("Operation mode can only be random/manual.");
            if(testCnt == 10){
                System.out.println("You enter wrong mode ten times. Execution end.");
                return;
            }
        }
        System.out.println("Current mode is: " + operationMode);

        if(operationMode.equals("Random")){
            ScannerMode.randomMode(scanner);
        }else if(operationMode.equals("Manual")){
            ScannerMode.manualMode(scanner);
        }

    }
    public static void randomMode(Scanner scanner){
        // For random mode
        // 1) number of threads to run concurrently
        // 2) number of operations a thread needs to handle
        // 3) lines of “song name” and “socket”.

        System.out.println("Number of threads to run concurrently should larger than 0 and less than Integer.MAX_VALUE: ");
        int parameterCnt = 0;
        while(scanner.hasNext()){
            String nextLine = scanner.nextLine();
            if(nextLine.startsWith("#")){
                continue;
            }
            if(parameterCnt == 0){
                if(!StringUtil.stringToIntCheck(nextLine)){
                    System.out.println("Invalid input number, execute end.");
                    return;
                }
                concurrentThreadNum = Integer.parseInt(nextLine);
                System.out.println("Number of threads to run concurrently is: " + concurrentThreadNum);
                parameterCnt++;
                continue;
            }

            System.out.println("Number of operations a thread needs to handle should larger than 0 and less than Integer.MAX_VALUE: ");
            if(parameterCnt == 1){
                if(!StringUtil.stringToIntCheck(nextLine)){
                    System.out.println("Invalid input number, execute end.");
                    return;
                }
                cntOfOperations = Integer.parseInt(nextLine);
                System.out.println("Number of operations a thread is: " + cntOfOperations);
                parameterCnt++;
                continue;
            }

            System.out.println("Enter Several lines of \"song name\" and \"socket\".");
            System.out.println("Example: Listen to the music, http://foo.com:54321");
            System.out.println("Note: 1) Invalid pairs will be neglect.");
            System.out.println("      2) Enter \"eof\" to finish entering lines action");
            if (parameterCnt >= 2){
                parameterCnt++;
                while(scanner.hasNext()){
                    String songStr = scanner.nextLine();
                    songList.add(songStr);
                }
                break;
            }



        }
        System.out.println("=================Start Execution=================");
        MusicStore musicStore = new MusicStore(concurrentThreadNum, cntOfOperations, songList);


    }

    // For manual mode
    // 1) operation type
    public static void manualMode(Scanner scanner){
        System.out.println("Note: Manual Mode will use main thread, which thread id is 0, to execute all operations.");
        System.out.println("=================Start Manual Entering=================");
        MusicStore musicStore = new MusicStore();
        System.out.println("Please enter operation type(GET/PUT/DELETE): ");
        while(!scanner.hasNext("eof")){
            System.out.println("Please enter operation type(GET/PUT/DELETE): ");
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
                System.out.println("Please enter operation type(GET/PUT/DELETE): ");
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
