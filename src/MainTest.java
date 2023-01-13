import hashtable.ThreadSafeHashTable;
import multiThreadSong.MusicStore;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class MainTest {

    public static void main(String[] args) {
        ThreadSafeHashTable<String, String> map = new ThreadSafeHashTable<>();
        map.put("Pop StarPop StarPop StarPop StarPop Star", "http://dhdbsbd//123//22/22");
        map.put("Pink Venom", "http://youtube.com/1212");


        for(int i = 0; i < 4; i++){
            MusicStore musicStore = new MusicStore(i, 3, 100);
            Thread curThread = new Thread(musicStore);
            curThread.start();
        }
    }


}

