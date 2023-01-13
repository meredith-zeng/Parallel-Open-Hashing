import java.math.BigInteger;
import java.util.*;

public class MainTest {
    public static void main(String[] args) {
        Map<String, Song> songToSockets = new HashMap<String, Song>(){
            {
                put("Listen to the music", new Song("Listen to the music", "http://foo.com:54321"));
                put("Time to say goodbye", new Song("Time to say goodbye", "http://bar.com:12345"));
                put("Time to say goodbye", new Song("Time to say goodbye", "http://ijk.com:33333"));
            }
        };
        for(Song song : songToSockets.values()){
            System.out.println(song.getSongName() + ", " + song.getSocket());
        }
    }


}

