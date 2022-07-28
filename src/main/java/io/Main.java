package io;

import io.Text.Text;
import io.Cezar.Cezar;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import io.FrequenceAnalyse.*;

public class Main {

    private static void writeToFile(List<char[]> text, String name) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File(name));
        for (char[] string : text)
            pw.println(string);
        pw.close();
    }


    //кодовое слово и ключ
    public final static String code = "апельсин";
    public final static Integer key = 5;




    public static void main(String[] args) throws IOException {

        String fullBookFileName = "src/main/resources/War and Peace.txt";
        String chapterFileName = "src/main/resources/Chapter.txt"; //название файла с полным текстом
        String cryptedFileName = "src/main/resources/Crypted.txt"; //название файла с главой
        String decryptedFileName = "src/main/resources/Decrypted.txt";
        String freqFileName = "src/main/resources/FrequenceDecrypted.txt";

        Cezar x = new Cezar();
        x.Init(key, code);
        writeToFile(x.crypt(chapterFileName, true), cryptedFileName);
        writeToFile(x.crypt(cryptedFileName, false), decryptedFileName);
        List<char[]> freqDecryptedText = FrequenceAnalyse.decryprt(cryptedFileName, fullBookFileName);
        writeToFile(freqDecryptedText, freqFileName);



    }
}
