/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caesarcipher;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Ameer Ayman
 */
public class CaesarCipher {

    /**
     * @param args the command line arguments
     */
    static ArrayList<String> texts = new ArrayList<String>();
    
    static void decryptBruteForce(String cipherText) {

        //int shiftKey = 10;
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String PlainText = "";
        for (int shiftKey = 1; shiftKey < 26; shiftKey++) {

            for (int i = 0; i < cipherText.length(); i++) {
                int charValue = alphabet.indexOf(cipherText.charAt(i));
                int keyValue = (charValue - shiftKey) % 26;
                if (keyValue < 0) {
                    keyValue = alphabet.length() + keyValue;
                }
                char replaceValue = alphabet.charAt(keyValue);
                PlainText += replaceValue;
            }
            texts.add(PlainText);
            PlainText = "";
        }

    }

    static int[] LetterFrequency(String s) {

        char ch = '#';
        String alpha = "abcdefghijklmnopqrstuvwxyz";
//        String s = "aoljhlzhyjpwolypzvulvmaollhysplzaruvduhukzptwslzajpwoly"
//                + "zpapzhafwlvmzbizapabapvujpwolypudopjolhjoslaalypuaolwsh"
//                + "pualeapzzopmalkhjlyahpuubtilyvmwshjlzkvduaolhswohila";
        int count = 0;
        int[] array = new int[alpha.length()];
        for (int i = 0; i < alpha.length(); i++) {
            count = 0;
            for (int j = 0; j < s.length(); j++) {
                ch = s.charAt(j);
                if (ch == alpha.charAt(i)) {
                    count++;
                }
            }
            array[i] = count;
            //m1.put(i, count);
        }
        return array;
    }

    static double chiSquare(int arr[], String text) {

        double[] EngExpected = {0.08167, 0.01492, 0.02782, 0.04253, 0.12702, 0.02228, 0.02015, 0.06094, 0.06966, 0.00153, 0.00772,
            0.04025, 0.02406, 0.06749, 0.07507, 0.01929, 0.00095, 0.05987, 0.06327, 0.09056, 0.02758, 0.00978,
            0.02360, 0.00150, 0.01974, 0.00074};
        double sum=0.0;
        int l=text.length();
        for (int i = 0; i < 26; i++) {
            sum+=Math.pow((arr[i]-(EngExpected[i])*l),2)/(EngExpected[i]*l);
        }
        return sum;
    }
    
    static int key(double arr[]){
    
        double min=arr[0];
        int indexKey = 0;
        
        for (int i = 1; i < arr.length; i++) {
            if(arr[i]<min){
                min=arr[i];
                indexKey=(i+1);
            }
        }
        return indexKey;
    }

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        //char ch[] = null;
        double[] chi_squared=new double[25];
        int[] outputArray = new int[26];
        String cipherText, text;
        //Scanner scanner =new Scanner(System.in);
        //System.out.println("please enter the file to be read");
        try {
            br = new BufferedReader(new FileReader("E:\\files\\test.txt"));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage() + "Errooooor:file not found");
            System.exit(0);
        }

        try {
            while ((cipherText = br.readLine()) != null) {
                cipherText=cipherText.toLowerCase();
                cipherText=cipherText.replaceAll("\\W", "");
                cipherText=cipherText.replaceAll("\\s+", "");
                sb.append(cipherText);
            }
            text = sb.toString();
            //System.out.println(text);
            decryptBruteForce(text);
            for (int i = 0; i < texts.size(); i++) {
                String send = texts.get(i);
                outputArray = LetterFrequency(send);
                chi_squared[i]=chiSquare(outputArray, send);
            }
            
            int k=key(chi_squared);
            System.out.println("key : "+k);
            System.out.println ("the plaintext : ");
            System.out.println(texts.get((k-1)));
            
        } catch (IOException ex) {
            System.out.println("Errooor");
        }

    }

}

//print values of array
/*
for (int i = 0; i < outputArray.length; i++) {
                System.out.println(outputArray[i]);
            }
*/

 //print the all plaintexts with keys stored in the arraylist
/*
            for (int i = 0; i < texts.size(); i++) {
                System.out.println("Decrypted text for key " + (i + 1) + ": " + texts.get(i));
            }

//print the chi-squared values for each key
           for (int i = 0; i < 25; i++) {
               System.out.println(chi_squared[i]);
            }
*/

