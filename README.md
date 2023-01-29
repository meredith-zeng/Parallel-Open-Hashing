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

![](../../../../var/folders/b3/5bzwhh5s3fqcsndt42hdt4wr0000gn/T/TemporaryItems/NSIRD_screencaptureui_KdPx3n/Screenshot 2023-01-29 at 2.43.16 PM.png)