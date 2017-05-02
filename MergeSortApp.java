/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MergeSortApp;

/**
 *
 * @author Holo
 * @MergeSort
 * @/.../IntegerArray.txt
 */

import java.io.BufferedReader;  
import java.io.DataInputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.io.InputStreamReader;  

public class MergeSortApp {

    static int a[] = new int[100000];  
  
    /** 
     * Assumes the 2 parts of the array are sorted, and merges them. While merging, count split inversions 
     *  
     * @param start 
     * @param end 
     * @param leftStart 
     * @param leftEnd 
     * @param rightStart 
     * @param rightEnd 
     * @return 
     */  
    public static long countSplitInversionsAndMerge(int start, int end, int leftStart, int leftEnd, int rightStart, int rightEnd) {  
        int subArray1[] = new int[leftEnd - leftStart + 1];  
        int subArray2[] = new int[rightEnd - rightStart + 1];  
  
        int count = 0;  
        for (int i = leftStart; i <= leftEnd; i++) {  
            subArray1[count++] = a[i];  
        }  
        count = 0;  
        for (int i = rightStart; i <= rightEnd; i++) {  
            subArray2[count++] = a[i];  
        }  
  
        // merge and count inversions  
        int leftPointer = 0, rightPointer = 0;  
        long inversions = 0;  
        for (int i = start; i <= end; i++) {  
            if(leftPointer >= subArray1.length) {  
                a[i] = subArray2[rightPointer++];  
  
            }else if(rightPointer >= subArray2.length) {  
                a[i] = subArray1[leftPointer++];  
  
            }else if (subArray1[leftPointer] <= subArray2[rightPointer]) {  
                a[i] = subArray1[leftPointer++];  
  
            }else if(subArray1[leftPointer] > subArray2[rightPointer]) {  
                a[i] = subArray2[rightPointer++];  
                inversions = inversions + subArray1.length - leftPointer;  
            }  
        }  
        return inversions;  
    }  
  
    /** 
     * Recursive method to count left and right inversions 
     *  
     * @param start 
     * @param end 
     * @return 
     */  
    public static long countInversionsAndSort(int start, int end) {   
        if(end - start == 1) {  
            // two elements in array, just sort them and return if this is an inversion  
            if (a[start] > a[end]) {  
                int temp = a[start];  
                a[start] = a[end];  
                a[end] = temp;  
                return 1;  
            }else {  
                return 0;  
            }  
        }else if(end == start) {  
            // one element in array, no sorting required, can be a left / right inversion  
            return 0;  
        }else {  
            int leftStart = start;  
            int leftEnd = ((end - start) / 2 ) + start;  
            int rightStart = ((end - start) / 2 ) + start +1;  
            int rightEnd = end;  
            long leftInversions = countInversionsAndSort(leftStart, leftEnd);  
            long rightInversions = countInversionsAndSort(rightStart, rightEnd);  
            long splitInversions = countSplitInversionsAndMerge(start, end, leftStart, leftEnd, rightStart, rightEnd);  
  
            return leftInversions + rightInversions + splitInversions;  
        }  
    }  
  
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        File inputFile = new File("/Users/Holo/Downloads/IntegerArray.txt");  
        String inputFilePath = inputFile.getAbsolutePath();  
  
  
        // read and parse input file  
        try { 
            String strLine;
            int count = 0;  
            FileInputStream fstream = new FileInputStream(inputFilePath);  
            DataInputStream in = new DataInputStream(fstream);  
            BufferedReader br = new BufferedReader(new InputStreamReader(in));  
            while ((strLine = br.readLine()) != null) {  
                a[count ++] = Integer.parseInt(strLine);  
            } 
        } catch (FileNotFoundException e) {  
        } catch (IOException e) {  
        }  
  
        System.out.println(countInversionsAndSort(0, a.length-1));  
    }   
}
