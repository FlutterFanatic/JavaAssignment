
package com.assignment.four;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentHashMap;
//import java.util.Iterator;

public class InfiniteStream {
    
    private static ConcurrentLinkedDeque<Byte> concurrentLinkedDequeStream = new ConcurrentLinkedDeque<>();
    
    //since numbers range from 1 to 10, all of them will fit in byte. So not declaring ints to save memory
    private static byte max = Byte.MIN_VALUE;
    private static byte min = Byte.MAX_VALUE;
    private static int sum = 0;
    private static double avg = 0d;

    public static byte getMax(){
        return max;
    }

    public static byte getMin(){
        return min;
    }

    public static double getAvg(){
        return avg;
    }

    public static byte getMostFreqVal(){
        byte maxFreq = 0; //0 when stream has no elements
        byte ans = -1; //-1 when stream has no elements
        for(Byte randNum: freqMap.keySet()){
            Byte freq = freqMap.getOrDefault(randNum, (byte)0);
            if(freq>maxFreq){
                ans=randNum;
            }
        }
        return ans;
    }

    //frequency of any random number can't be more than 30 as size of stream at any given time is <=30. 
    //binary representation of 30 will fit in byte. So declaring value in map as byte to save memory
    private static ConcurrentHashMap<Byte, Byte> freqMap = new ConcurrentHashMap<>();

    public static void writeToStream(Byte randomNum){
        //printThisStream("stream before adding");
        boolean isAdded = false;
        if(concurrentLinkedDequeStream.size() < 30){
            isAdded = concurrentLinkedDequeStream.offerLast(randomNum);
        }else {
            Byte polledNum = concurrentLinkedDequeStream.pollFirst();
            if(polledNum!=null){
                //decrease freq or remove from map
                byte currFreq = freqMap.getOrDefault(randomNum, (byte)0);
                if(currFreq-1 <= 0){
                    freqMap.remove(randomNum);
                }
                else{
                    freqMap.put(randomNum, (byte)(currFreq-1));
                }
                //substract polled number from sum
                sum-=polledNum;
            }
            isAdded = concurrentLinkedDequeStream.offerLast(randomNum);
        }
        synchronized(concurrentLinkedDequeStream){
            if(!isAdded){
                return;
            }
            byte currFreq = freqMap.getOrDefault(randomNum, (byte)0);
            freqMap.put(randomNum, (byte)(currFreq+1));
            if(randomNum < min){
                min=randomNum;
            }
            if(randomNum > max){
                max=randomNum;
            }
            sum+=randomNum;
            avg=sum/concurrentLinkedDequeStream.size();
        }
        //printThisStream("stream after adding");
    }



    // private static void printThisStream(String msg){
    //     System.out.println();
    //     System.out.print(msg + "in tid-" + Thread.currentThread().getId() + " is: ");
    //     for(Iterator<Integer> itr = concurrentLinkedDequeStream.iterator(); itr.hasNext();) { 
    //         System.out.print(itr.next()); 
    //         System.out.print(","); 
    //     } 
    // }
}