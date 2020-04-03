
package com.assignment.four;

import java.util.Random;

class RandomNumberThread extends Thread{
    
    public void run(){
        Random randNumGenerator = new Random();
        byte number = (byte)(randNumGenerator.nextInt(9)+1);
        //int i=1;
        while(true /*i<=10*/){
            InfiniteStream.writeToStream(number);
            //i++;
        }
    }    
}