//Chengyu Chen, 02/25/2017, cs8bwane
//This class called ReverseRecurse reads integer values from the keyboard into an array whose size 
//is specified by the user and reverse the elements in the array
import java.util.Scanner;

public class ReverseRecurse {
 
 /* This method returns an array
  * it creates a array based on the users' inputs
  * */
 public int[] initArray() {
  Scanner in = new Scanner(System.in);//create a scanner object
  System.out.println(PSA7Strings.MAX_NUM);
  if (in.hasNextInt()) {// if the input is an int go inside
   int size = in.nextInt();//set the size to the corresponding input
    while( size < 0){//if size is smaller than 0 ask you to enter positive integer
       System.out.println(PSA7Strings.TOO_SMALL);
       size = in.nextInt();//asign the size to the second int you passed in
     }
   System.out.printf(PSA7Strings.ENTER_INTS, size);
   int[] Array = new int[size];
   for (int i = 0; i < size - 1; i++) {
      if (!in.hasNextInt()) {// if array input is not int
     int[] newarray = new int[i];//set the newarray to the corresponding size
     System.arraycopy(Array, 0, newarray, 0, i);
     return newarray;
    }
    if (in.hasNextInt()) {
     Array[i] = in.nextInt(); //copy the int the array at index i
    }
   
   }
   if (!in.hasNextInt()) { //check for the last index whether it is int or not
    int[] newarray = new int[size - 1];
    System.arraycopy(Array, 0, newarray, 0, size - 1);
    return newarray;
   } else
   Array[size - 1] = in.nextInt();
   in.close();
   return Array;
 }
  else 
    System.exit(1);//exit if the first input is non integer
  return null;
 }
 

 
 /* print the array
  * */
 public void printArray(int[] array) {
  if (array == null) {
   return;
  }
  if (array.length==0) {
   System.out.println(PSA7Strings.EMPTY);// print out the comment if the it is empty array
  }
    
  for (int i = 0; i < array.length; i++) {
   System.out.print(array[i] + " ");
  }
  System.out.println();
 }

 
 /*reverse the original array from a certain range
  * */
 public void reverse(int[] originalArray, int low, int high) {
   if( originalArray == null) {// return if the passed in array is null
     return;
   }
  if (low == high || low > high) {
    return;//base case, just return without change
  } 
  else {
   int lowValue = originalArray[low];//assign the low value to an int
   int highValue = originalArray[high];//assign the high value to an int
   originalArray[high] = lowValue;//swap them
   originalArray[low] = highValue;
   reverse(originalArray, low + 1, high - 1);//recursive call
  }
 }

 
 
 /* return an array with the passed array elements reversed
  * */
 public int[] reverse(int[] originalArray){
   if( originalArray == null || originalArray.length ==0){// return if the passed in array is null
     //or is an empty array
     return null;
   }
  int[] reverseArray = new int[originalArray.length];// create a new array
   if (originalArray.length==1){//if the array length is 1 
       reverseArray[0] = originalArray[0];//copy the original array into the newarray
       return reverseArray;
       }
   else if(originalArray.length==2){//swap when there the length of array is 2
     reverseArray[0] = originalArray[1];
     reverseArray[1] = originalArray[0];
     return reverseArray;
   }
   else {
          reverseArray[0]= originalArray[originalArray.length-1];
          reverseArray[reverseArray.length-1]= originalArray[0];
          int[] newArray = new int[originalArray.length-2];
          System.arraycopy(originalArray,1,newArray,0,newArray.length);//copy the middle array to a newarray
          System.arraycopy(reverse(newArray),0,reverseArray,1,newArray.length);
          return reverseArray;
       }
 }
}
