//package com.alphabet.atozfun.utils;
//
//import java.util.Random;
//
//public class RandomizedArrayExample
//{
//    public static int[] myNumbers = null;
//    public static void main(String[] args)
//    {
//      try
//      {
//          myNumbers = new int[6];
//          System.out.println(" my initial values ");
//          for (int i = 0; i < myNumbers.length; i++) {
//               System.out.println(myNumbers[i]);
//          }
//          Random r = new Random();
//          int total_elements_cnt = 0;
//          boolean loop_status = true;
//          while(loop_status)
//          {
//              int next_num = r.nextInt(42)+1;
//              if(!isCompleted()){
//                  if(!isDuplicate(next_num)){
//                      myNumbers[total_elements_cnt] = next_num;
//                      total_elements_cnt++;
//                  }else{
//                      continue;
//                  }
//              }else{
//                  loop_status = false;
//              }
//          }
//          System.out.println("-------- my final values -------------");
//          for (int i = 0; i < myNumbers.length; i++) {
//               System.out.println(myNumbers[i]);
//          }
//      } catch (Exception e) {
//          e.printStackTrace();
//      }
//  }
//  public static boolean isCompleted(){
//      boolean status = true;
//      for (int i = 0; i < myNumbers.length; i++){
//          if(myNumbers[i]==0){
//              status = false;
//              break;
//          }
//      }
//      return  status;
//  }
//  public static boolean isDuplicate(int num){
//      boolean status = false;
//      for (int i = 0; i < myNumbers.length; i++){
//          if(myNumbers[i]== num){
//              status = true;
//              break;
//          }
//      }
//      return  status;
//  }
//}