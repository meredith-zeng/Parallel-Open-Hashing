import java.util.LinkedList;
import java.util.List;

public class Song{
    private String songName;
    private String socket;

    // song's name is the key of hash
    // socket is hash's value
    public Song(String songName, String socket){
        this.songName = songName;
        this.socket = socket;
    }

    @Override
    public int hashCode(){
        return StringHashUtil.generateHashCode(songName);
    }

    @Override
    public boolean equals(Object obj){
        return obj instanceof Song &&
                this.songName.equals(((Song) obj).songName) &&
                this.socket.equals(((Song) obj).socket);
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }
}