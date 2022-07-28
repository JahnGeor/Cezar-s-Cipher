package io.Cezar;
import io.Text.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Cezar {
    private final static char[] alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя".toCharArray(); //русский алфавит
    private final static int alphaSize = alphabet.length;
    private final static int firstLetter = alphabet[0];
    private static char[] CharAlphabetCrypt = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя".toCharArray();
    private static HashMap<Character, Character> NewAlphabet = new HashMap<>();
    private static HashMap<Character, Character> NewAlphabetReverse = new HashMap<>();

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static List<char[]> crypt(String text, boolean Crypt_Decrypt) throws IOException { //1 - Crypt, 2 - Decrypt
        List<char[]> textList = Text.getTextList(text);
        if(Crypt_Decrypt) {
            for (char[] string : textList) {
                for (int i = 0; i < string.length; i++) {

                    if(CharContains(alphabet, string[i]))
                    string[i]=NewAlphabet.get(string[i]);
                }
            }
        } else {
            for (char[] string : textList) {
                for (int i = 0; i < string.length; i++) {
                    if(CharContains(alphabet, string[i]))
                    string[i]=NewAlphabetReverse.get(string[i]);
                }
            }
        }

        return textList;
    }



    public static void Init (int key, String code) {


        for(int i = 0, b = key; i < code.length(); i++, b++) {
            CharAlphabetCrypt[b] = code.charAt(i);
        }

        for(int i = 0, k = key+code.length(); i < alphaSize; i++) {
            if(k >= alphaSize) k = k - alphaSize;
            if(!CharContains(code.toCharArray(), alphabet[i])) {
                CharAlphabetCrypt[k] = alphabet[i];
                k = k + 1;
            }
        }
        System.out.println("Полученная таблица шифра Цезаря: ");
        for(char c: alphabet) {
            System.out.print(" " + c);
        }

        System.out.println();

        for(char c: CharAlphabetCrypt) {
            System.out.print(" " + c);
        }

        System.out.println();

        for(int i = 0; i < alphaSize; i++) {
            NewAlphabet.put(alphabet[i], CharAlphabetCrypt[i]);
        }

        for(int i = 0; i < alphaSize; i++) {
            NewAlphabetReverse.put(CharAlphabetCrypt[i], alphabet[i]);
        }


    }

    public static boolean CharContains(char[] code_char, char symbol) {
        for(char c: code_char) {
            if(c == symbol) return true;
        }
        return false;
    }
}

