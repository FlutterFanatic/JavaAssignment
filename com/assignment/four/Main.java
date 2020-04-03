package com.assignment.four;

import java.util.Scanner;

class Main{

    public static void main(String[] args) {
        System.out.println("Enter number if threads to spawn: ");
        try{
            Scanner Scanner = new Scanner(System.in);
            String input = Scanner.nextLine();
            int n = Integer.parseInt(input);
            Scanner.close();
            for(int i=1; i<=n; i++){
                RandomNumberThread randomNumberThread = new RandomNumberThread();
                randomNumberThread.start();
            }

            while(true){
                System.out.println("min: " + InfiniteStream.getMin());
                System.out.println("max: " + InfiniteStream.getMax());
                System.out.println("avg: " + InfiniteStream.getAvg());
                System.out.println("most freq val: " + InfiniteStream.getMostFreqVal());
            }

        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
}

