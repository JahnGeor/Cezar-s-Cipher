package io.Text;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Text {
    public static List<char[]> getTextList(String fileName) throws IOException {
        List<char[]> textList = new ArrayList<>();
        FileReader file = new FileReader(fileName);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String string = scanner.nextLine().toLowerCase();
            textList.add(string.toCharArray());
        }
        file.close();
        return textList;
    }
}
