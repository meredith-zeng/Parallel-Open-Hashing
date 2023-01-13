package multiThreadSong;

public class MusicStore implements Runnable{
    private int threadId;
    private int numOfThreads;
    private int cntHandleOperations;

    public MusicStore(int threadId, int numOfThreads, int cntHandleOperations) {
        this.threadId = threadId;
        this.numOfThreads = numOfThreads;
        this.cntHandleOperations = cntHandleOperations;
    }

    @Override
    public void run() {
        try {
            for(int i = numOfThreads; i > 0 && cntHandleOperations > 0; i--){
                System.out.println("Thread: " + threadId + " ->" + i);
                cntHandleOperations--;
                Thread.sleep(50);
            }
        }catch (InterruptedException e){
            System.out.println("Thread" + threadId + "interrupted.");
        }
        System.out.println();
    }
}
