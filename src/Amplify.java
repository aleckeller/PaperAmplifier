import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class Amplify extends Results {
    public static int wordCount = 0;

    private static Map<String, Integer> regOccurrences = new TreeMap<String, Integer>();
    private static Map<String, Integer> commOccurrences = new TreeMap<String, Integer>();
    private static Map<String, Integer> misSpelledWords = new TreeMap<String, Integer>();

    private static ArrayList<String> commWords = new ArrayList<String>();
    private static ArrayList<String> dictWords = new ArrayList<String>();

    public static void process(File file) {

        commWords.add("the");
        commWords.add("be");
        commWords.add("i");
        commWords.add("to");
        commWords.add("of");
        commWords.add("and");
        commWords.add("a");
        commWords.add("in");
        commWords.add("that");
        commWords.add("have");
        commWords.add("it");
        commWords.add("for");
        commWords.add("not");
        commWords.add("on");
        commWords.add("with");
        commWords.add("he");
        commWords.add("as");
        commWords.add("you");
        commWords.add("do");
        commWords.add("at");
        commWords.add("but");
        commWords.add("is");

        // check if we can actually process file
        try {
            String ext = FilenameUtils.getExtension(file.getAbsolutePath());
            Scanner scanWordFile = new Scanner(new File("wordsEn.txt"));
            while (scanWordFile.hasNext()) {
                String dictWord = scanWordFile.next();
                dictWords.add(dictWord);
            }
            // word document
            if (ext.equals("docx")) {
                FileInputStream stream = new FileInputStream(
                        file.getAbsolutePath());
                XWPFDocument doc = new XWPFDocument(stream);
                java.util.List<XWPFParagraph> paragraphs = doc.getParagraphs();
                String theFile = "";
                for (XWPFParagraph para : paragraphs) {
                    theFile += para.getText() + " ";
                }

                Scanner scanFile = new Scanner(theFile);
                if (!scanFile.hasNext()) {
                    JOptionPane.showMessageDialog(null, "The File is empty!");
                }

                while (scanFile.hasNext()) {
                    String word = scanFile.next();
                    String cleanWord = word.replaceAll("\\W", "");
                    String theWord = cleanWord.toLowerCase();
                    if (!dictWords.contains(theWord)
                            && !misSpelledWords.containsKey(theWord)
                            && !theWord.matches(".*\\d.*")) {
                        misSpelledWords.put(theWord, wordCount + 1);
                    }
                    if (commWords.contains(theWord)) {
                        Integer count = commOccurrences.get(theWord);
                        if (count == null) {
                            count = 0;
                        }
                        commOccurrences.put(theWord, count + 1);
                        wordCount++;
                    }
                    else {
                        Integer count = regOccurrences.get(theWord);
                        if (count == null) {
                            count = 0;
                        }
                        regOccurrences.put(theWord, count + 1);
                        wordCount++;
                    }
                }

                ResultsPanel.setCommOccurrences(commOccurrences);
                ResultsPanel.setRegOccurrences(regOccurrences);
                ResultsPanel.setMisSpelledWords(misSpelledWords);
                Results.main(null);

                doc.close();

            }
            else {
                JOptionPane.showMessageDialog(null, "Must be a .docx file!");
                PaperAmplifierGooey.main(null);
            }

        }
        catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static void reset() {
        misSpelledWords.clear();
        regOccurrences.clear();
        commOccurrences.clear();
        wordCount = 0;
        commWords.clear();
        dictWords.clear();
    }

}
