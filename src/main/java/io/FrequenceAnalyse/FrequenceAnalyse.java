package io.FrequenceAnalyse;
import io.Text.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class FrequenceAnalyse {

   /* private int position(char[] string, char symbol) {
            for
            return false;
        }
    }*/

    private static final char[] alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя".toCharArray();
    private static final int alphSize = alphabet.length;
    private static final int firstLetter = alphabet[0];
    private static int countRusLetters = 0;
    private static int countLetter[] = new int[alphSize];
    private static int countRusBigrams = 0;
    private static final int bigramLength = 2;

    private static List<Map.Entry<Character, Integer>> freqAlgorithm(List<char[]> textList) {
        HashMap<Character, Integer> letterMap = new HashMap<>();
        for (char[] string : textList) {
            for (char letter : string) {
                for (int b = 0; b < alphSize; b++) {
                    if (letter == alphabet[b]) {
                        countRusLetters++;
                        letterMap.put(letter, ++countLetter[b]);
                        break;
                    }
                }
            }
        }
        List<Map.Entry<Character, Integer>> letterEntryList = new ArrayList<>(letterMap.entrySet());
        letterEntryList.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        System.out.println("Количество русских букв в тексте: " + countRusLetters);
        for (int i = 0; i < letterEntryList.size(); i++) {
            double freq = 100.0 * letterEntryList.get(i).getValue() / countRusLetters;
            String formattedFreq = new DecimalFormat("#0.00").format(freq);
            System.out.println("[" + i + "] " + letterEntryList.get(i).getKey() + ": " + formattedFreq + " %");
        }
        return letterEntryList;
    }

    private static List<Map.Entry<String, Integer>> freqBigramAlgorithm(List<char[]> textList) {
        HashMap<String, Integer> bigramMap = new HashMap<>();
        for (char[] string : textList) {
            StringBuilder bigram = new StringBuilder();
            for (int i = 0; i < string.length; i++) {
                if (string[i] == ' ') {
                    bigram = new StringBuilder();
                    continue;
                }
                for (int b = 0, e = alphSize - 1; b < alphSize || e > 0; b++, e--) {
                    if (string[i] == alphabet[b] || string[i] == alphabet[e]) {
                        bigram.append(string[i]);
                        if (bigram.length() > (bigramLength - 1)) {
                            countRusBigrams++;
                            String bigramString = bigram.toString();
                            if (!bigramMap.containsKey(bigramString))
                                bigramMap.put(bigramString, 1);
                            else
                                bigramMap.put(bigramString, bigramMap.get(bigramString) + 1);
                            bigram = new StringBuilder();
                            i--;
                        }
                        break;
                    }
                }
            }
        }

        List<Map.Entry<String, Integer>> bigramEntryList = new ArrayList<>(bigramMap.entrySet());
        bigramEntryList.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        final int bigramMax = 10;
        if (bigramEntryList.size() > bigramMax)
            bigramEntryList.subList(bigramMax, bigramEntryList.size()).clear();

        System.out.println("Количество биграмм в тексте: " + countRusBigrams);
        for (int i = 0; i < bigramEntryList.size(); i++) {
            double freq = 100.0 * bigramEntryList.get(i).getValue() / countRusBigrams;
            String formattedFreq = new DecimalFormat("#0.00").format(freq);
            System.out.println("[" + i + "] " + bigramEntryList.get(i).getKey() + ": " + formattedFreq + " %");
        }

        return bigramEntryList;
    }

    public static List<char[]> decryprt(String chapter, String fullText) throws IOException {
        List<char[]> chapterList = Text.getTextList(chapter);
        List<char[]> textList = Text.getTextList(fullText);

        System.out.println("Зашифрованная глава: ");
        List<Map.Entry<Character, Integer>> lettersChapter = freqAlgorithm(chapterList);
        System.out.println();
        System.out.println("Война и мир: ");
        List<Map.Entry<Character, Integer>> lettersFull = freqAlgorithm(textList);
        System.out.println();
        System.out.println("Зашифрованная глава: ");
        List<Map.Entry<String, Integer>> bigramsChapter = freqBigramAlgorithm(chapterList);
        System.out.println();
        System.out.println("Война и мир: ");
        List<Map.Entry<String, Integer>> bigramsFull = freqBigramAlgorithm(textList);
        System.out.println();

        Set<Character> bigramFullSet = new HashSet<>();
        Map<Character, Character> matchedLettersMap = new HashMap<>();

        for (int i = 0; i < bigramsChapter.size(); i++) {
            char[] bigramC = bigramsChapter.get(i).getKey().toCharArray();
            char[] bigramF = bigramsFull.get(i).getKey().toCharArray();
            for (int j = 0; j < bigramLength; j++) {
                if (!(matchedLettersMap.containsKey(bigramC[j]) || bigramFullSet.contains(bigramF[j]))) {
                    bigramFullSet.add(bigramF[j]);
                    matchedLettersMap.put(bigramC[j], bigramF[j]);
                }
            }
        }

        for (int i = 0; i < lettersChapter.size(); i++) {
            if (!(matchedLettersMap.containsKey(lettersChapter.get(i).getKey()))) {
                matchedLettersMap.put(lettersChapter.get(i).getKey(), lettersFull.get(i).getKey());
            }
        }

        List<Map.Entry<Character, Character>> matchedLettersList = new ArrayList<>(matchedLettersMap.entrySet());
        System.out.println("Совпадение:");
        for (Map.Entry<Character, Character> mL : matchedLettersList)
            System.out.println(mL.getKey() + " -> " + mL.getValue());

        for (char[] string : chapterList) {
            for (int i = 0; i < string.length; i++) {
                for (Map.Entry<Character, Character> mL : matchedLettersList) {
                    if (string[i] == mL.getKey()) {
                        string[i] = mL.getValue();
                        break;
                    }
                }
            }
        }
        return chapterList;
    }
}