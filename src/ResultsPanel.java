import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ResultsPanel extends JPanel {
    private static Map<String, Integer> regOccurrences;
    private static Map<String, Integer> commOccurrences;
    private static Map<String, Integer> misSpelledWords;
    
    private Image logo;
    
    private static String sregOcc = "";
    private static String scommOcc = "";
    
    private static ArrayList<String> regListocc = new ArrayList<String>();
    private static ArrayList<String> commListocc = new ArrayList<String>();
    private static ArrayList<String> regWordsList = new ArrayList<String>();
    private static ArrayList<String> commWordsList = new ArrayList<String>();
    private static ArrayList<String> regThesaurusList = new ArrayList<String>();
    private static ArrayList<String> mostCommonWords = new ArrayList<String>();
    
    private static JScrollPane commPane;
    private static JScrollPane regPane;
    
    private JScrollPane maxPane;
    private JScrollPane misspelledPane;
    
    private static JTextArea textAreacomm;
    private static JTextArea textAreareg;
    private static JTextArea textAreaMax;
    private static JTextArea textAreaMisspelled;
    
    private static String commonWordsString = "";
    private static String regularWordsString = "";
    private static String misspelledWord = "";
    
    private static int maxValue;

    public ResultsPanel() {
        logo = getImage("Drawing (2).png");
        //background and text for panel
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        //
        for (String word : regOccurrences.keySet()) {
            if (Amplify.wordCount <= 500) {
                if (regOccurrences.get(word) >= 3) {
                    sregOcc = "\"" + word + "\"" + " occurs "
                            + regOccurrences.get(word) + " times. ";
                    regListocc.add(sregOcc);
                    regWordsList.add(word);

                }
            }
            else if (Amplify.wordCount > 500 && Amplify.wordCount < 1000) {
                if (regOccurrences.get(word) >= 4) {
                    sregOcc = "\"" + word + "\"" + " occurs "
                            + regOccurrences.get(word) + " times. ";
                    regListocc.add(sregOcc);
                    regWordsList.add(word);
                }
            }
            else if (Amplify.wordCount >= 1000) {
                if (regOccurrences.get(word) >= 5) {
                    sregOcc = "\"" + word + "\"" + " occurs "
                            + regOccurrences.get(word) + " times. ";
                    regListocc.add(sregOcc);
                    regWordsList.add(word);
                }
            }
        }
        for (String word : commOccurrences.keySet()) {
            if (Amplify.wordCount <= 500) {
                if (commOccurrences.get(word) >= 3) {
                    scommOcc = "\"" + word + "\"" + " occurs "
                            + commOccurrences.get(word) + " times. ";
                    commListocc.add(scommOcc);
                    commWordsList.add(word);
                }
            }
            else if (Amplify.wordCount > 500 && Amplify.wordCount < 1000) {
                if (commOccurrences.get(word) >= 4) {
                    scommOcc = "\"" + word + "\"" + " occurs "
                            + commOccurrences.get(word) + " times. ";
                    commListocc.add(scommOcc);
                    commWordsList.add(word);
                }
            }
            else if (Amplify.wordCount >= 1000) {
                if (commOccurrences.get(word) >= 5) {
                    scommOcc = "\"" + word + "\"" + " occurs "
                            + commOccurrences.get(word) + " times. ";
                    commListocc.add(scommOcc);
                    commWordsList.add(word);
                }
            }
        }
        for (int i = 0; i < regWordsList.size(); i++) {
            new SendRequest(regWordsList.get(i), "en_US",
                    "9ikAtJfNV17JxAriGpGA", "json");

        }
        regThesaurusList = SendRequest.getList();

        for (int i = 0; i < commListocc.size(); i++) {
            commonWordsString += (i + 1) + ". " + commListocc.get(i) + "\n"
                    + "\n";
        }
        for (int i = 0; i < regListocc.size(); i++) {
            regularWordsString += (i + 1) + ". " + regListocc.get(i) + "\n"
                    + regThesaurusList.get(i) + "\n" + "\n";

        }
        if (commWordsList.isEmpty()) {
            commonWordsString = "You have not repeated a word more than the usual"
                    + "\n" + "amount for common words";
        }
        if (regWordsList.isEmpty()) {
            regularWordsString = "You have not repeated a word more than the usual"
                    + "\n" + "amount for unique words" + "\n";
            JOptionPane
                    .showMessageDialog(
                            null,
                            "Your paper is already AMPLIFIED!"
                                    + "\n"
                                    + "Click OK to see other cool things about your paper!");
        }

        if (!regOccurrences.isEmpty()) {
            maxValue = (Collections.max(regOccurrences.values()));
        }
        for (Entry<String, Integer> entry : regOccurrences.entrySet()) {  
                                                                         
                                                                         
            if (entry.getValue() == maxValue) {
                String mostCommonWord = entry.getKey();
                mostCommonWords.add(mostCommonWord);
            }
        }
        if (!misSpelledWords.isEmpty()) {
            for (String word : misSpelledWords.keySet()) {
                misspelledWord += word + " (" + misSpelledWords.get(word)
                        + ")" + "\n";
            }
        }
        else {
            misspelledWord = "No misspelled words!";
        }

        textAreacomm = new JTextArea(23, 37);
        textAreareg = new JTextArea(23, 38);
        textAreaMax = new JTextArea(9, 10);
        textAreaMisspelled = new JTextArea(10, 20);

        textAreacomm.setText(commonWordsString);
        textAreacomm.setEditable(false);
        textAreareg.setText(regularWordsString);
        textAreareg.setEditable(false);
        textAreaMisspelled.setText(misspelledWord);
        textAreaMisspelled.setEditable(false);

        String maxArea = "";
        for (int i = 0; i < mostCommonWords.size(); i++) {
            maxArea += mostCommonWords.get(i) + "\n";
        }
        if (maxValue == 1) {
            maxArea += "(" + maxValue + " time)";
        }
        else {
            maxArea += "(" + maxValue + " times)";
        }
        textAreaMax.setText(maxArea);
        textAreaMax.setEditable(false);

        maxPane = new JScrollPane(textAreaMax);
        commPane = new JScrollPane(textAreacomm);
        regPane = new JScrollPane(textAreareg);
        misspelledPane = new JScrollPane(textAreaMisspelled);
        textAreareg.setCaretPosition(0);
        textAreacomm.setCaretPosition(0);
        textAreaMax.setCaretPosition(0);
        textAreaMisspelled.setCaretPosition(0);

        add(maxPane);
        add(commPane);
        add(regPane);
        add(misspelledPane);
        add(Results.getReUpload());
        add(Results.getNote());
        add(Results.getSave());

    }

    public static Map<String, Integer> getRegOccurrences(
            Map<String, Integer> map) {
        return map;
    }

    public static Map<String, Integer> getCommOccurrences(
            Map<String, Integer> map) {
        return map;
    }

    public static void setRegOccurrences(Map<String, Integer> map) {
        regOccurrences = map;
    }

    public static void setCommOccurrences(Map<String, Integer> map) {
        commOccurrences = map;
    }

    public static void setMisSpelledWords(Map<String, Integer> map) {
        misSpelledWords = map;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        textAreareg.setBackground(Color.BLACK);
        textAreacomm.setBackground(Color.BLACK);
        textAreacomm.setForeground(Color.WHITE);
        textAreareg.setForeground(Color.WHITE);
        textAreaMax.setBackground(Color.BLACK);
        textAreaMax.setForeground(Color.WHITE);
        textAreaMisspelled.setBackground(Color.BLACK);
        textAreaMisspelled.setForeground(Color.WHITE);

        Font f = new Font("Arial", Font.PLAIN, 18);
        textAreareg.setFont(f);
        textAreacomm.setFont(f);

        regPane.setBackground(Color.BLACK);
        commPane.setBackground(Color.BLACK);
        maxPane.setBackground(Color.BLACK);
        misspelledPane.setBackground(Color.BLACK);
        regPane.setLocation(10, 60);
        commPane.setLocation(525, 60);
        maxPane.setLocation(10, 528);
        misspelledPane.setLocation(238, 508);
        Results.getReUpload().setLocation(785, 510);
        Results.getNote().setLocation(470, 485);
        Results.getSave().setLocation(785, 537);

        Font p = new Font("Times New Roman", Font.BOLD, 30);
        Font t = new Font("Times New Roman", Font.PLAIN, 15);
        g.setFont(p);
        g.setColor(Color.RED);
        g.drawString("Repeated Unique Words", 10, 35);
        g.drawString("Repeated Common Words", 535, 35);
        g.setColor(Color.WHITE);
        g.drawLine(500, 0, 500, 445);

        Font q = new Font("Times New Roman", Font.ITALIC, 20);
        textAreaMax.setFont(q);
        textAreaMisspelled.setFont(q);
        g.setFont(q);
        if (mostCommonWords.size() == 1) {
            g.drawString("Word Used Most:", 10, 525);
        }
        else {
            g.drawString("Words Used Most:", 10, 525);
        }
        g.drawString("Misspelled Words (Word #)", 240, 505);
        g.drawString("Total Word Count: " + Amplify.wordCount, 10, 505);
        g.setFont(t);
        g.drawString("Alec K. Keller. All Rights Reserved " + "\u00a9", 750,
                670);
        g.drawImage(logo, 790, 452, 200, 200, null);

    }

    private Image getImage(String filename) {
        URL url = getClass().getResource(filename);
        ImageIcon icon = new ImageIcon(url);
        return icon.getImage();
    }

    public static void reset() {
        regOccurrences.clear();
        commOccurrences.clear();
        misSpelledWords.clear();
        sregOcc = "";
        scommOcc = "";
        regListocc.clear();
        commListocc.clear();
        regWordsList.clear();
        commWordsList.clear();
        regThesaurusList.clear();
        textAreacomm.setText("");
        textAreareg.setText("");
        textAreaMax.setText("");
        textAreaMisspelled.setText("");
        commonWordsString = "";
        regularWordsString = "";
        misspelledWord = "";
        mostCommonWords.clear();
        maxValue = 0;

    }

}
