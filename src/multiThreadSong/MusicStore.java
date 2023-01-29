package multiThreadSong;

import hashtable.ThreadSafeHashTable;
import util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import static util.MathUtil.getRandomOperation;

public class MusicStore{
    private int numOfConcurrentThreads;
    private int cntOfOperations;
    private List<String[]> songAndSocketList;
    private int songsListSize;
    private ThreadSafeHashTable<String, String> musicTable;

    public MusicStore(){
        this.musicTable = new ThreadSafeHashTable<>();
    }

    public MusicStore(int numOfConcurrentThreads, int cntOfOperations,
                      List<String> songList) {

        this.numOfConcurrentThreads = numOfConcurrentThreads;
        this.cntOfOperations = cntOfOperations;

        if(numOfConcurrentThreads <= 0 || numOfConcurrentThreads >= Integer.MAX_VALUE){
            System.out.println("Number of threads to run concurrently should larger than 0 and less than Integer.MAX_VALUE.");
            System.out.println("This run has been terminated.");
            return;
        }

        if(cntOfOperations <= 0 || cntOfOperations >= Integer.MAX_VALUE){
            System.out.println("Number of operations a thread needs to handle should larger than 0 and less than Integer.MAX_VALUE.");
            System.out.println("This run has been terminated.");
            return;
        }

        List<String[]> songAndSocketList = new ArrayList<>();
        for(String songAndSocket : songList){
            if(songAndSocket == null || songAndSocket.length() == 0){
                continue;
            }
            String[] curStr = songAndSocket.split(",");
            if(curStr == null || curStr.length >= 3 || curStr.length <= 1){
                continue;
            }
            String url = curStr[1];
            url = url.replace(" ","");
            boolean urlFlag = StringUtil.urlPatternCheck(url);
            if(!urlFlag){
                continue;
            }
            songAndSocketList.add(curStr);
        }
        this.songAndSocketList = songAndSocketList;
        if(songAndSocketList == null || songAndSocketList.size() == 0){
            System.out.println("Lines of \"valid\" song name and socket should larger than 0 and less than Integer.MAX_VALUE.");
            System.out.println("This run has been terminated.");
            return;
        }
        this.songsListSize = songAndSocketList.size();

        this.musicTable = new ThreadSafeHashTable<>();

        randomModelThreadExecute();
    }

    public void randomModelThreadExecute() {
        for(int threadCnt = 1;  threadCnt <= numOfConcurrentThreads; threadCnt++){
            int finalThreadCnt = threadCnt;
            Thread thread = new Thread(){
                public void  run(){
                    Random random = new Random();
                    int curThreadId = finalThreadCnt;

                    for(int i = 0; i < cntOfOperations; i++){
                        String[] curString = songAndSocketList.get(i % songsListSize);
                        String songName = curString[0], socket = curString[1];
                        int curOperation = getRandomOperation(random);
                        if(curOperation == 0){
                            // get
                            getMusic(songName, curThreadId);
                        }else if(curOperation == 1){
                            // put
                            putMusic(songName, socket, curThreadId);
                        }else{
                            // delete
                            deleteMusic(songName, socket, curThreadId);
                        }

                    }

                    System.out.println("Thread " + curThreadId + " finished all tasks. Now this thread exit.");
                }
            };

            thread.start();
        }
    }

    public synchronized void getMusic(String songName, int threadId){
//        (e.g., “<thread id>: get <song name> is not in the hash table”)
        if(!musicTable.containsKey(songName)){
            System.out.println("Thread " + threadId + " : Get \"" + songName + "\" is not in the hash table");
            return;
        }
//        (e.g., “ <thread id>: get <song name> can be download from [<socket>]”)
        String socket = musicTable.get(songName);
        System.out.println("Thread " + threadId + " : Get \"" + songName + "\" can be download from " + "[" + socket + "]");
    }

    public synchronized void putMusic(String songName, String socket, int threadId){

        Object cur = musicTable.put(songName, socket);
        int hash = musicTable.hashCode(songName);
        int idx = (hash & Integer.MAX_VALUE) % musicTable.getCapacity();
        if(cur == null){
            // “<thread id>: put <song name> at <socket> in the hash table with index <index>”)
            System.out.println("Thread " + threadId + " : Put \"" + songName + "\" at " + socket + " in the hash table with index " + idx);
            return;
        }
        // “<thread id>: put <song name> at <socket> already in the hash table with index <index>”

        System.out.println("Thread " + threadId + " : Put \"" + songName + "\" at " + socket + " already in the hash table with index " + idx);
    }

    public synchronized void deleteMusic(String songName, String socket, int threadId){
        if(musicTable == null
                || musicTable.isEmpty()
                || (musicTable.size() == 0)
                || !musicTable.containsKey(songName)
                || !musicTable.get(songName).equals(socket)){
            // (e.g., “<thread id>: delete <song name> at <socket> is not in the hash table”).
            System.out.println("Thread " + threadId + " : Delete \"" + songName + "\" at " + socket + "is not in the hash table");
            return;
        }
        musicTable.remove(songName);
        System.out.println("Thread " + threadId + " : Delete \"" + songName + "\" at " + socket + "from the hash table");
    }
}
