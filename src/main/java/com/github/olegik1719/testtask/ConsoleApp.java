package com.github.olegik1719.testtask;

import java.io.*;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ConsoleApp {
    private static Store store;
    private static PrintStream mainOS = System.out;
    private static InputStream mainIS = System.in;
    private static final Logger log = Logger.getLogger(ConsoleApp.class.getName());
    static {
        try {
            FileHandler fh = new FileHandler(ConsoleApp.class.getName() + ".log");
            log.addHandler(fh);
            log.setLevel(Level.ALL);
            fh.setFormatter(new SimpleFormatter());
            log.setUseParentHandlers(false);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        //mainIS = System.in;
        Scanner sc = new Scanner(mainIS);
        //mainOS = System.out;
        store = new Store();
        help();
        while (true){
            mainOS.print("Get task for application: ");
            String task = sc.nextLine();
            if (task.isEmpty()){
                log.log(Level.INFO,"cmd: empty string");
                help();
            }else{
                switch (task.charAt(0)) {
                    case '+': upload(task.substring(1));
                        break;
                    case '?': search(task.substring(1));
                        break;
                    case '-': download(task.substring(1));
                        break;
                    case 'l': list(task.substring(1));
                        break;
                    case 'h': help();
                        break;
                    case 'e': exit(); break;
                    default : help();
                }
            }
        }
    }

    private static void help(){
        mainOS.println("Usage:");
        mainOS.println("{+|-*|?*|l|e|h} <parameter(s)>:");
        mainOS.println("+ -- upload file to base. Parameter is path to file");
        mainOS.println("Example: + fileUpload.txt");
        mainOS.println("-<ID> -- download file from base. 2 parameters: ID file and path to new file");
        mainOS.println("Example: - 3 fileDownload.txt");
        mainOS.println("?* -- search in file. ? -- return all (up to MAXINT) results.");
        mainOS.println("\t  ?<int> return first <int> results");
        //mainOS.println("\t  ?? return all (up to MAXINT) results.");
        mainOS.println("1 parameter: substring");
        mainOS.println("Examples:\n\t?1 substring\n\t\t -- Search first result");
        mainOS.println("\t ?3 substring\n\t\t -- Search 3 first results");
        mainOS.println("\t ? substring\n\t\t -- Search all results");
        mainOS.println("l -- list loaded files. Maybe 1 parameter: substring");
        mainOS.println("Examples:\n\tl\n\t\t -- list all files");
        mainOS.println("\t l substring\n\t\t -- list all files, that contain substring");
        mainOS.println("e -- exit from program. Without parameters.");
        mainOS.println("h -- show this message. Without parameters.");
    }

    private static void upload(String full){
        if (full.length() == 0) {
            log.log(Level.INFO,"cmd: upload; without parameters");
            help();
            return;
        }
        log.log(Level.INFO,"cmd: upload; parameters: " + full);
        if (full.charAt(0) == ' ') {
            File file = new File(full.substring(1));
            int index;
            try {
                index = store.addToStore(new FileInputStream(file));
                if (index < 0) {
                    mainOS.println("File found, but not stored: " + file.getPath());
                    log.log(Level.WARNING,"File found, but not stored: " + file.getPath());
                } else {
                    mainOS.println("Upload: " + index + " " + file.getPath());
                    log.log(Level.FINE,"Upload: " + index + " " + file.getPath());
                }
            } catch (FileNotFoundException e) {
                mainOS.println("File not found: " + file.getPath());
                log.log(Level.WARNING,"File not found: " + file.getPath());
            }
        }else {
            help();
        }
    }

    private static void download(String full){
        if (full.length() == 0) {
            log.log(Level.INFO,"cmd: download; without parameters");
            help();
            return;
        }
        log.log(Level.INFO,"cmd: download; parameters: " + full);
        //if (full.charAt(0) == ' ') {
            //mainOS.println("Download: " + full);
            String digit = getFirstParam(full);
            try {
                int text = Integer.parseInt(digit);
                String path = getOther(full);
                File output = new File(path);
                ByteArrayOutputStream textStream = store.getFromStore(text);
                if (textStream == null){
                    mainOS.println("Text " + text + " does not exist!");
                    log.log(Level.INFO, "Text " + text + " does not exist!");
                    help();
                    return;
                }
                if (output.createNewFile()) {
                    try (OutputStream fileStream = new FileOutputStream(output)){
                        textStream.writeTo(fileStream);
                        fileStream.close();
                    }
                }else {
                    mainOS.println("File " + path + " exists!");
                    log.log(Level.INFO, "File " + path + " exists!");
                    help();
                }
                textStream.close();
            }catch (NumberFormatException e){
                mainOS.println("It isn't right number of text");
                log.log(Level.INFO, "It isn't right number of text");
                help();
            } catch (FileNotFoundException e) {
                mainOS.println("File not found");
                log.log(Level.SEVERE, "FileNotFoundException " + e.getMessage());
                help();
            } catch (IOException e) {
                mainOS.println("I/O exception");
                log.log(Level.SEVERE, "I/O exception " + e.getMessage());
                help();
            }
//        }else {
//            help();
//        }
    }

    private static void search(String full){
        if (full.length() == 0) {
            log.log(Level.INFO,"cmd: search; without parameters");
            help();
            return;
        }
        log.log(Level.INFO,"cmd: search; parameters: " + full);

        if (full.charAt(0) == ' '){
            mainOS.println("Search: " + full);
            //mainOS.println(full.subSequence(1,full.length()));
            String substring = getOther(full);
            log.log(Level.FINE,"cmd: search substring: " + substring);
            Map<CharSequence, Collection<Result>> result = store.searchAll(substring);
            if (result.isEmpty()){
                log.log(Level.WARNING,"Search result is empty for " + substring);
                mainOS.println("Search result is empty for " + substring);
            }else {
                for (CharSequence key: result.keySet()){
                    Collection<Result> keyResult = result.get(key);
                    if (keyResult != null && keyResult.size() > 0){
                        mainOS.println(key + ":\n");
                        for (Result keyres:keyResult){
                            System.out.println(keyres);
                        }
                    }
                }
            }
        }else {
            String digit = getFirstParam(full);
            //NumberFormatException
            try {
                int count = Integer.parseInt(digit);
                log.log(Level.FINE, "count = " + count);
                String substring = getOther(full);
                Map<CharSequence, Collection<Result>> result = store.searchAll(substring);
                if (result.isEmpty()){
                    log.log(Level.WARNING,"Search result is empty for " + substring);
                    mainOS.println("Search result is empty for " + substring);
                }else {
                    for (CharSequence key: result.keySet()){
                        Collection<Result> keyResult = result.get(key);
                        if (keyResult != null && keyResult.size() > 0){
                            mainOS.println(key + ":\n");
                            for (Result keyres:keyResult){
                                System.out.println(keyres);
                            }
                        }
                    }
                }
            }catch (NumberFormatException e){
                mainOS.println("It isn't right number");
                log.log(Level.INFO, "It isn't right number");
                help();
            }

            mainOS.println("Search: " + full);
        }

    }

    private static void list(String full){
        if (full.length() == 0) {
            help();
            log.log(Level.INFO,"cmd: list; without parameters");
            if (!store.isEmpty()) {
                mainOS.println("Texts in store:");
                store.list().forEach(mainOS::println);
            }else {
                mainOS.println("Store is empty now!");
                log.log(Level.WARNING,"Store is empty now!");
            }
            return;
        }
        log.log(Level.INFO,"cmd: list; parameters: " + full);
        if (full.charAt(1) == ' '){
            String substring = getOther(full);
            if (substring.length() == 0) {
                log.log(Level.INFO,"list, substring is empty");
                if (!store.isEmpty()) {
                    mainOS.println("Texts in store:");
                    store.list().forEach(mainOS::println);
                }else {
                    mainOS.println("Store is empty now!");
                    log.log(Level.WARNING,"Store is empty now!");
                }
            }else {
                log.log(Level.INFO,"list, look for: \"" + substring + "\"");
                if (!store.isEmpty()) {
                    Collection<CharSequence> result = store.contains(substring);
                    if (!result.isEmpty()) {
                        mainOS.println("Texts in store:");
                        result.forEach(mainOS::println);
                        log.log(Level.INFO,"Found: " + result.size() + " texts.");
                    }else{
                        mainOS.println("Substring \"" + substring + "\" isn't found in texts!" );
                        log.log(Level.WARNING,"Substring \"" + substring + "\" isn't found in texts!" );
                    }
                }else {
                    mainOS.println("Store is empty now!");
                    log.log(Level.WARNING,"Store is empty now!");
                }
            }
        }else{
            help();
        }
        mainOS.println("list: " + full);
    }

    private static void exit (){
        mainOS.println("Exit... ");
        log.log(Level.INFO, "Exit");
        System.exit(0);
    }

    private static String getFirstParam(String parameters){
        int border = parameters.indexOf(' ');
        if (border == -1) return parameters;
        return parameters.substring(0,border);
    }

    static String getOther(String parameters){
        String first = getFirstParam(parameters);
        return parameters.substring(first.length() + 1);
    }
}
