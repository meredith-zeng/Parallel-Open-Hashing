# Parallel-Open-Hashing
## Overview
Implement parallel open Hashing using Java thread. 

## Hash
The input to the hash function is a text string (i.e., the song's name), and the output
of the hash function is an unsigned integer ranged from 0 to table size n. The input
string is divided into 4-byte chucks (fill with zeros if the last chunk is not full),
reverse the bits for all odd numbered chunks, exclusive OR of all those chunks
Together, the result value is then mod by the table size to get hash index, and
return the index. 

code part:
```java
    public static List<String> chunkSplit(String str){
        List<String> chunks = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        int idx = 0, len = str.length();
        while(idx < len){
            for(int i = 0; i < 4 && idx < len; i++){
                sb.append(Integer.toHexString(str.charAt(idx++)));
            }
            if(sb.length() < 8){
                while(sb.length() < 8){
                    sb.append(Integer.toHexString('0'));
                }
            }
            chunks.add(sb.toString().toUpperCase());
            sb = new StringBuilder();
        }
        return chunks;
    }

```

```java

    public synchronized int StringHashCode(String keyStr){
        if(keyStr == null){
            return 0;
        }

        List<String> chunks = StringUtil.chunkSplit(keyStr);
        for(int i = 0; i < chunks.size(); i += 2){
            String beforeReverseStr = chunks.get(i);
            String afterReverseStr = MathUtil.reverseBits(beforeReverseStr);

            Long curVal = Long.valueOf(afterReverseStr, 16);
            if(curVal >= Integer.MAX_VALUE){
                curVal = curVal % Integer.MAX_VALUE;
            }
            afterReverseStr = Long.toHexString(curVal);

            chunks.set(i, afterReverseStr.toUpperCase());
        }
        
        Long hashCode = Long.valueOf(chunks.get(0), 16);
        for(int i = 1; i < chunks.size(); i++){
            hashCode ^= Long.valueOf(chunks.get(i), 16);
        }
        if(hashCode >= Integer.MAX_VALUE){
            hashCode = hashCode % Integer.MAX_VALUE;
        }
        
        int hash = new Long(hashCode).intValue();
        return (hash & Integer.MAX_VALUE) % capacity;
    }
```
## Multithreading
The author create MusicStore to simulate multi-threaded put, get, delete behavior.

Please see MusicStore for detailed code.

> Use "eof" to quit.

## Test case
### Random
Input:

```java
random
3
20 
Listen to the music, http://foo.com:54321
Time to say goodbye, http://bar.com:12345
Sound of music, http://xyz.com:40000
By the river of Babylon, http://bla.com:65535
Time to say goodbye, http://ijk.com:33333
```

Output:(Due to the multi-threaded nature, it may be different if you run the same input)
```java
/Users/yulinzeng/Library/Java/JavaVirtualMachines/liberica-1.8.0_322/bin/java -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=51599:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Users/yulinzeng/Library/Java/JavaVirtualMachines/liberica-1.8.0_322/jre/lib/charsets.jar:/Users/yulinzeng/Library/Java/JavaVirtualMachines/liberica-1.8.0_322/jre/lib/ext/cldrdata.jar:/Users/yulinzeng/Library/Java/JavaVirtualMachines/liberica-1.8.0_322/jre/lib/ext/dnsns.jar:/Users/yulinzeng/Library/Java/JavaVirtualMachines/liberica-1.8.0_322/jre/lib/ext/jaccess.jar:/Users/yulinzeng/Library/Java/JavaVirtualMachines/liberica-1.8.0_322/jre/lib/ext/jfxrt.jar:/Users/yulinzeng/Library/Java/JavaVirtualMachines/liberica-1.8.0_322/jre/lib/ext/localedata.jar:/Users/yulinzeng/Library/Java/JavaVirtualMachines/liberica-1.8.0_322/jre/lib/ext/nashorn.jar:/Users/yulinzeng/Library/Java/JavaVirtualMachines/liberica-1.8.0_322/jre/lib/ext/sunec.jar:/Users/yulinzeng/Library/Java/JavaVirtualMachines/liberica-1.8.0_322/jre/lib/ext/sunjce_provider.jar:/Users/yulinzeng/Library/Java/JavaVirtualMachines/liberica-1.8.0_322/jre/lib/ext/sunpkcs11.jar:/Users/yulinzeng/Library/Java/JavaVirtualMachines/liberica-1.8.0_322/jre/lib/ext/zipfs.jar:/Users/yulinzeng/Library/Java/JavaVirtualMachines/liberica-1.8.0_322/jre/lib/jce.jar:/Users/yulinzeng/Library/Java/JavaVirtualMachines/liberica-1.8.0_322/jre/lib/jfr.jar:/Users/yulinzeng/Library/Java/JavaVirtualMachines/liberica-1.8.0_322/jre/lib/jfxswt.jar:/Users/yulinzeng/Library/Java/JavaVirtualMachines/liberica-1.8.0_322/jre/lib/jsse.jar:/Users/yulinzeng/Library/Java/JavaVirtualMachines/liberica-1.8.0_322/jre/lib/management-agent.jar:/Users/yulinzeng/Library/Java/JavaVirtualMachines/liberica-1.8.0_322/jre/lib/resources.jar:/Users/yulinzeng/Library/Java/JavaVirtualMachines/liberica-1.8.0_322/jre/lib/rt.jar:/Users/yulinzeng/IdeaProjects/Thread-Safe Hashing/out/production/Thread-Safe Hashing MainTest
Operation mode should be random/manual: 
random
Current mode is: random
Number of threads to run concurrently should larger than 0 and less than Integer.MAX_VALUE: 
3
Number of threads to run concurrently is: 3
Number of operations a thread needs to handle should larger than 0 and less than Integer.MAX_VALUE: 
20
Number of operations a thread is: 20
Enter Several lines of "song name" and "socket".
Example: Listen to the music, http://foo.com:54321
Note: 1) Invalid pairs will be neglect.
      2) Enter "eof" to finish entering lines action
Listen to the music, http://foo.com:54321
Time to say goodbye, http://bar.com:12345
Sound of music, http://xyz.com:40000
By the river of Babylon, http://bla.com:65535
Time to say goodbye, http://ijk.com:33333
eof
=================Start Execution=================
Thread 1 : Get "Listen to the music" is not in the hash table
Thread 2 : Get "Listen to the music" is not in the hash table
Thread 2 : Get "Time to say goodbye" is not in the hash table
Thread 2 : Get "Sound of music" is not in the hash table
Thread 2 : Get "By the river of Babylon" is not in the hash table
Thread 2 : Get "Time to say goodbye" is not in the hash table
Thread 2 : Get "Listen to the music" is not in the hash table
Thread 2 : Get "Time to say goodbye" is not in the hash table
Thread 2 : Get "Sound of music" is not in the hash table
Thread 2 : Delete "By the river of Babylon" at  http://bla.com:65535is not in the hash table
Thread 2 : Get "Time to say goodbye" is not in the hash table
Thread 3 : Delete "Listen to the music" at  http://foo.com:54321is not in the hash table
Thread 2 : Put "Listen to the music" at  http://foo.com:54321 in the hash table with index 1
Thread 1 : Delete "Time to say goodbye" at  http://bar.com:12345is not in the hash table
Thread 1 : Get "Sound of music" is not in the hash table
Thread 1 : Get "By the river of Babylon" is not in the hash table
Thread 1 : Get "Time to say goodbye" is not in the hash table
Thread 1 : Put "Listen to the music" at  http://foo.com:54321 already in the hash table with index 1
Thread 1 : Put "Time to say goodbye" at  http://bar.com:12345 in the hash table with index 1
Thread 1 : Get "Sound of music" is not in the hash table
Thread 1 : Delete "By the river of Babylon" at  http://bla.com:65535is not in the hash table
Thread 1 : Get "Time to say goodbye" can be download from [ http://bar.com:12345]
Thread 1 : Delete "Listen to the music" at  http://foo.com:54321from the hash table
Thread 1 : Get "Time to say goodbye" can be download from [ http://bar.com:12345]
Thread 1 : Get "Sound of music" is not in the hash table
Thread 1 : Get "By the river of Babylon" is not in the hash table
Thread 1 : Get "Time to say goodbye" can be download from [ http://bar.com:12345]
Thread 1 : Get "Listen to the music" is not in the hash table
Thread 1 : Get "Time to say goodbye" can be download from [ http://bar.com:12345]
Thread 1 : Get "Sound of music" is not in the hash table
Thread 1 : Get "By the river of Babylon" is not in the hash table
Thread 1 : Delete "Time to say goodbye" at  http://ijk.com:33333is not in the hash table
Thread 1 finished all tasks. Now this thread exit.
Thread 2 : Get "Time to say goodbye" can be download from [ http://bar.com:12345]
Thread 2 : Get "Sound of music" is not in the hash table
Thread 2 : Get "By the river of Babylon" is not in the hash table
Thread 2 : Get "Time to say goodbye" can be download from [ http://bar.com:12345]
Thread 3 : Put "Time to say goodbye" at  http://bar.com:12345 already in the hash table with index 1
Thread 3 : Get "Sound of music" is not in the hash table
Thread 3 : Put "By the river of Babylon" at  http://bla.com:65535 in the hash table with index 3
Thread 3 : Get "Time to say goodbye" can be download from [ http://bar.com:12345]
Thread 3 : Get "Listen to the music" is not in the hash table
Thread 3 : Get "Time to say goodbye" can be download from [ http://bar.com:12345]
Thread 3 : Put "Sound of music" at  http://xyz.com:40000 in the hash table with index 6
Thread 3 : Get "By the river of Babylon" can be download from [ http://bla.com:65535]
Thread 3 : Get "Time to say goodbye" can be download from [ http://bar.com:12345]
Thread 3 : Put "Listen to the music" at  http://foo.com:54321 in the hash table with index 1
Thread 3 : Put "Time to say goodbye" at  http://bar.com:12345 already in the hash table with index 1
Thread 3 : Get "Sound of music" can be download from [ http://xyz.com:40000]
Thread 3 : Put "By the river of Babylon" at  http://bla.com:65535 already in the hash table with index 3
Thread 3 : Get "Time to say goodbye" can be download from [ http://bar.com:12345]
Thread 3 : Get "Listen to the music" can be download from [ http://foo.com:54321]
Thread 3 : Put "Time to say goodbye" at  http://bar.com:12345 already in the hash table with index 1
Thread 3 : Delete "Sound of music" at  http://xyz.com:40000from the hash table
Thread 3 : Put "By the river of Babylon" at  http://bla.com:65535 already in the hash table with index 3
Thread 3 : Put "Time to say goodbye" at  http://ijk.com:33333 already in the hash table with index 1
Thread 3 finished all tasks. Now this thread exit.
Thread 2 : Get "Listen to the music" can be download from [ http://foo.com:54321]
Thread 2 : Get "Time to say goodbye" can be download from [ http://ijk.com:33333]
Thread 2 : Get "Sound of music" is not in the hash table
Thread 2 : Delete "By the river of Babylon" at  http://bla.com:65535from the hash table
Thread 2 : Put "Time to say goodbye" at  http://ijk.com:33333 already in the hash table with index 1
Thread 2 finished all tasks. Now this thread exit.

```