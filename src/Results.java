import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Results extends JFrame {
    private static JButton reUpload;
    private static JButton note;
    private static JButton save;

    public Results() {
        super("Paper Amplifier");
        JButton reUpload = new JButton("New File");
        JButton note = new JButton("Note");
        JButton save = new JButton("Save");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                doExit();
            }
        });

        reUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(Results.this,
                        "Are you sure you want to upload a new file?");
                if (result == JOptionPane.YES_OPTION) {
                    Amplify.reset();
                    ResultsPanel.reset();
                    SendRequest.reset();
                    removeAll();
                    dispose();
                    JFrame f = new PaperAmplifierGooey();
                    f.setVisible(true);

                }

            }
        });
        note.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane
                        .showMessageDialog(
                                null,
                                "Please note that this list of words may include"
                                        + "\n"
                                        + "names of people, contractions, or abbreviations "
                                        + "\n"
                                        + "that may not be spelled incorrectly");

            }
        });
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int result = JOptionPane
                        .showConfirmDialog(Results.this,
                                "Are you sure you want to take a screenshot and save it?");
                if (result == JOptionPane.YES_OPTION) {
                    JFileChooser fc = new JFileChooser();
                    int returnVal = fc.showSaveDialog(Results.this);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        if (file.exists()) {
                            int otherResult = JOptionPane
                                    .showConfirmDialog(Results.this,
                                            "This file already exists, are you sure you want to overwrite it?");
                            if (otherResult == JOptionPane.YES_OPTION) {
                                try {
                                    Rectangle screenRect = new Rectangle(
                                            Toolkit.getDefaultToolkit()
                                                    .getScreenSize());
                                    BufferedImage capture = new Robot()
                                            .createScreenCapture(screenRect);
                                    ImageIO.write(capture, "png", file);

                                }
                                catch (IOException | HeadlessException
                                        | AWTException g) {

                                }
                            }
                        }
                        else {
                            try {
                                Rectangle screenRect = new Rectangle(Toolkit
                                        .getDefaultToolkit().getScreenSize());
                                BufferedImage capture = new Robot()
                                        .createScreenCapture(screenRect);
                                ImageIO.write(capture, "png", file);

                            }
                            catch (IOException | HeadlessException
                                    | AWTException g) {

                            }

                        }

                    }

                }

            }
        });
        setReUpload(reUpload);
        setSave(save);
        setNote(note);
        add(new ResultsPanel());
        setSize(1000, 700);
        this.setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

    }

    private void doExit() {
        int result = JOptionPane.showConfirmDialog(Results.this,
                "Are you really really quitting?");
        if (result == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    public static void main(String args) {
        JFrame f = new Results();
        f.setVisible(true);
    }

    public void setReUpload(JButton button) {
        reUpload = button;
    }

    public void setNote(JButton button) {
        note = button;
    }

    public static JButton getReUpload() {
        return reUpload;
    }

    public static JButton getNote() {
        return note;
    }

    public static JButton getSave() {
        return save;
    }

    public void setSave(JButton save) {
        Results.save = save;
    }

}
