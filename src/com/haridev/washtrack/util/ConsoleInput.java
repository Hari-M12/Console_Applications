package com.haridev.washtrack.util;

import java.util.Scanner;

public class ConsoleInput {

    private static Scanner scanner;
    private ConsoleInput(){
        
    }

    public static final Scanner getScanner(){
        if (scanner==null){
            scanner = new Scanner(System.in);
        }
        return scanner;
    }
}
