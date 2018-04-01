package com.github.olegik1719.testtask;

import java.io.*;
import java.util.Scanner;

public class ConsoleApp {
    private static Store store;
    private static PrintStream mainOS = System.out;
    private static InputStream mainIS = System.in;

    public static void main(String[] args) throws IOException {
        //mainIS = System.in;
        Scanner sc = new Scanner(mainIS);
        //mainOS = System.out;
        store = new Store();
        while (true){
            help();
            mainOS.print("Get task for application: ");
            String task = sc.nextLine();
            if (task.isEmpty()){
                help();
            }else{
                switch (task.charAt(0)) {
                    case '+': upload(task);
                        break;
                    case '?': search(task);
                        break;
                    case '-': download(task);
                        break;
                    case 'l': list(task);
                        break;
                    case 'h': help();
                        break;
                    case 'e': exit(task); break;
                    default : help();
            }
//            //System.out.println(task);
//            if (task.equals("e")) {
//                mainOS.println(exit(task));
//                System.exit(0);
//            }
//            mainOS.flush();
//            if (task.length() < 3 || task.charAt(1) != ' '){
//                mainOS.println(help());
//            }else {
//
//                   }
            }
        }
    }

    private static void help(){
        mainOS.println("Usage:");
        mainOS.println("{+|-|?*|l*|e|h} <parameter(s)>:");
        mainOS.println("+ -- upload file to base. Parameter is path to file");
        mainOS.println("Example: + fileUpload.txt");
        mainOS.println("- -- download file from base. 2 parameters: ID file and path to new file");
        mainOS.println("Example: - 3 fileDownload.txt");
        mainOS.println("?* -- search in file. ? -- return only first result.");
        mainOS.println("\t  ?<int> return first <int> results");
        mainOS.println("\t  ?? return all (up to MAXINT) results.");
        mainOS.println("1 parameter: substring");
        mainOS.println("Examples:\n\t? substring\n\t\t -- Search first result");
        mainOS.println("\t ?3 substring\n\t\t -- Search 3 first results");
        mainOS.println("\t ?? substring\n\t\t -- Search all results");
        mainOS.println("l -- list loaded files. Without parameters.");
        mainOS.println("e -- exit from program. Without parameters.");
        mainOS.println("h -- show this message. Without parameters.");
    }

    private static void upload(CharSequence full){
        File file = new File(full.subSequence(2,full.length()).toString());
        int index = -1;
        try {
            index = store.addToStore(new FileInputStream(file));
            if (index < 0){
                mainOS.println("File found, but not stored: " + file.getPath());
            }else {
                mainOS.println("Upload: " + index + " " + file.getPath());
            }
        }catch (FileNotFoundException e){
            mainOS.println("File not found: " + file.getPath());
        }
    }

    private static void download(CharSequence full){
        mainOS.println("Download: " + full.toString());
    }

    private static void search(CharSequence full){
        mainOS.println("Search: " + full.toString());
    }

    private static void exit (CharSequence full){
        mainOS.println("Exit... ");
        System.exit(0);
    }

    private static void list(CharSequence full){
        mainOS.println("list: " + full.toString());
    }
}
