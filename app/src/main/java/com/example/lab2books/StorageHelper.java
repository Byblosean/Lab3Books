package com.example.lab2books;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class StorageHelper {

    private static final String FILE_NAME = "books_history.txt";

    public static boolean saveToFile(Context context, String text) {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_APPEND);
            fos.write((text + "\n\n").getBytes());
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String readFromFile(Context context) {
        StringBuilder builder = new StringBuilder();

        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line).append("\n");
            }

            br.close();
            isr.close();
            fis.close();
        } catch (Exception e) {
            return "";
        }

        return builder.toString().trim();
    }
}