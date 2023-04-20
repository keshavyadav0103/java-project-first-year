package com.example.code;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MyClass {
    public static void main(String[] args) throws IOException {
        EncryptionProgram ep=new EncryptionProgram();}
}
class EncryptionProgram
{
    private Scanner sc;
    private ArrayList<Character> list;
    private ArrayList<Character> shuffledList;
    private char ch;
    private char[] letter;




    EncryptionProgram() throws IOException
    {
        sc=new Scanner(System.in);
        list=new ArrayList();
        shuffledList=new ArrayList();
        ch=' ';
        new Random();
        newkey();
        ask();

    }
    private void ask() throws IOException
    {
        while(true)
        {
            System.out.println("*****");
            System.out.println("Choose an option");
            System.out.println("(N).NewKey");
            System.out.println("(G).GetKey");
            System.out.println("(E).Encrypt");
            System.out.println("(D).Decrypt");
            System.out.println("(Q).Quit");
            System.out.println("*****");
            char check=Character.toUpperCase(sc.nextLine().charAt(0));

            switch(check)
            {
                case 'N':
                    newkey();break;
                case 'G':
                    getkey();break;
                case 'E':
                    encrypt();break;
                case 'D':
                    decrypt();break;
                case 'Q':
                    quit();break;
                default:
                    System.out.println("Not a valid choice!!!!");
            }

        }
    }
    private void newkey(){
        ch=' ';
        list.clear();
        shuffledList.clear();
        for(int i=32;i<127;i++){
            list.add(Character.valueOf(ch));
            ch++;
        }

        shuffledList=new ArrayList(list);
        Collections.shuffle(shuffledList);
        System.out.println("New Key has been Created");

    }

    private void getkey()
    {
        System.out.println("Key: ");
        for(Character x: list)
        {
            System.out.print(x);

        }
        System.out.println();
        for(Character x:shuffledList)
        {
            System.out.print(x);
        }
        System.out.println();

    }
    private void encrypt() throws IOException
    {
        System.out.println("Enter the name to save file: ");
        String text=sc.nextLine();
        String fn="/home/keshavbu/Desktop/codeathon/";
        String write="";
        fn=fn.concat(text+".txt");
        File file = new File("/home/keshavbu/Desktop/codeathon/test.txt");
        Scanner SC=new Scanner(file);
        String content="";
        while(SC.hasNextLine()){
            content=content.concat(SC.nextLine()+"\n");}
        letter=content.toCharArray();
        for(int i=0;i<letter.length;i++)
        {
            for(int j=0;j<list.size();j++)
            {
                if(letter[i]==list.get(j))
                {
                    letter[i]=shuffledList.get(j);
                    break;
                }
            }
        }


        for(char x : letter)
        {
            String s=String.valueOf(x);
            write=write.concat(s);

        }

        System.out.println("Encrypted file is created");
        FileWriter writer=new FileWriter(fn);
        writer.write(write);
        writer.close();
    }

    private void decrypt() throws IOException{

        System.out.println("Enter the file name to be Decrypted: ");
        String text1=sc.nextLine();
        String fn="/home/keshavbu/Desktop/codeathon/";
        String fn1="/home/keshavbu/Desktop/codeathon/";
        String write="";
        fn=fn.concat(text1+".txt");
        File file = new File(fn);
        Scanner SC=new Scanner(file);
        String content="";
        while(SC.hasNextLine()){
            content=content.concat(SC.nextLine()+"\n");}

        letter=content.toCharArray();
        for(int i=0;i<letter.length;i++)
        {
            for(int j=0;j<shuffledList.size();j++)
            {
                if(letter[i]==shuffledList.get(j))
                {
                    letter[i]=list.get(j);
                    break;
                }
            }
        }
        System.out.println("Enter the file name to save ");
        String text2=sc.nextLine();

        for(char x : letter)
        {
            String s=String.valueOf(x);
            write=write.concat(s);
        }
        System.out.println();
        fn1=fn1.concat(text2+".txt");
        System.out.println("decrypted file is created");
        FileWriter writer=new FileWriter(fn1);
        writer.write(write);
        writer.close();




    }
    private void quit(){
        System.out.println("Thank you,have a nice day :)");
        System.exit(0);




    }
}