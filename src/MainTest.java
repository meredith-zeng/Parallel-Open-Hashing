import hashtable.ThreadSafeHashTable;
import multiThreadSong.MusicStore;
import util.MathUtil;

import java.util.*;

public class MainTest {

    public static void main(String[] args) {
        ThreadSafeHashTable<String, String> map = new ThreadSafeHashTable<>();

        for(int i = 0; i < 100; i++){
            map.put("Yulin is the Best!" + i, "http://yulin.com/" + i);
        }


//        for(int i = 0; i < 4; i++){
//            MusicStore musicStore = new MusicStore(i, 3, 100);
//            Thread curThread = new Thread(musicStore);
//            curThread.start();
//        }

    }
}

