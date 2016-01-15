import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PaperAmplifierGooey extends JFrame implements ActionListener {
    private JMenuItem about, theHelp;
    private JButton chooseFile, upload;
    private JTextField uploadFileName;
    private File theFile;
    private JLabel logo;

    public PaperAmplifierGooey() {
        //sets up menubar
        super("Paper Amplifier");
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);

        JMenu file = new JMenu("File");
        menubar.add(file);

        JMenu help = new JMenu("Help");
        menubar.add(help);

        JMenuItem exit = file.add("Exit");
        theHelp = help.add("Help with Paper Amplifier");
        about = help.add("About...");

        chooseFile = new JButton("Choose File");
        chooseFile.addActionListener(this);

        upload = new JButton("Upload");
        upload.addActionListener(this);
        upload.setEnabled(false);

        JLabel label = new JLabel("Upload file here");
        JLabel para1 = new JLabel("<html>" + "<b>"
                + "Upload the word document you want Amplified!" + "</b>"
                + "</html>");
        JLabel rightsReserved = new JLabel(
                "                                                    "
                        + "                                     Alec K. Keller. All Rights Reserved "
                        + "\u00a9");

        rightsReserved.setForeground(Color.WHITE);

        para1.setForeground(Color.WHITE);

        label.setForeground(Color.WHITE);

        uploadFileName = new JTextField(20);
        uploadFileName.setEnabled(false);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        try {
            BufferedImage wPic;
            wPic = ImageIO
                    .read(this.getClass().getResource("Drawing (2).png"));
            logo = new JLabel(new ImageIcon(wPic));
        }
        catch (IOException e1) {

        }

        panel.add(label);
        panel.add(chooseFile);
        panel.add(uploadFileName);
        panel.add(upload);
        panel.add(logo);
        panel.add(para1);
        panel.add(rightsReserved);

        panel.setBackground(Color.BLACK);
        add(panel);
        about.addActionListener(this);
        theHelp.addActionListener(this);

        final JFileChooser fc = new JFileChooser();

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        PaperAmplifierGooey.this,
                        "Are you really really quitting?");
                if (result == JOptionPane.YES_OPTION) {
                    dispose();
                }

            }
        });

        chooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(panel);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    theFile = fc.getSelectedFile();
                    uploadFileName.setText(theFile.getName());
                    upload.setEnabled(true);
                }

            }
        });

        upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        PaperAmplifierGooey.this,
                        "Are you sure you want to upload this file?");
                if (result == JOptionPane.YES_OPTION) {
                    Amplify.process(theFile);
                    dispose();
                }

            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                doExit();
            }
        });

        pack();
        setSize(600, 350);
        this.setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void doExit() {
        int result = JOptionPane.showConfirmDialog(PaperAmplifierGooey.this,
                "Are you really really quitting?");
        if (result == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    private void aboutMessage() {
        JLabel abtLbl = new JLabel("<html>" + "<hr>"
                + "<p>Paper Amplifier<br/>by Alec Keller</p>" + "<hr>"
                + "</html>");
        JOptionPane.showMessageDialog(this, abtLbl, "Message",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void helpMessage() {
        JLabel abtLbl = new JLabel(
                "<html>"
                        + "Upload a file and check out if you've used a word too many times!<br/>Longer papers might take a little longer than others!"
                        + "</html>");
        JOptionPane.showMessageDialog(this, abtLbl, "Message",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        JFrame f = new PaperAmplifierGooey();
        f.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.about) {
            aboutMessage();
        }
        if (e.getSource() == this.theHelp) {
            helpMessage();
        }
    }

}
