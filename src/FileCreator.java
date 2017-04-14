import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by MartinRiggs on 4/14/2017.
 */
public class FileCreator {
    private JFrame mainFrame;
    private JPanel controlPanel;
    private JPanel footer;
    public FileCreator()
    {
        mainFrame = new JFrame("File creator tool");
        mainFrame.setSize(800,400);
        mainFrame.setLayout(new GridLayout(3,1));
        mainFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);

            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout());
        JLabel header = new JLabel("Welcome to file creation tool.",SwingConstants.CENTER);
        JLabel footer = new JLabel("",SwingConstants.CENTER);
        mainFrame.add(header);
        mainFrame.add(controlPanel);
        mainFrame.add(footer);

    }
    private void start()
    {
        JLabel file = new JLabel("File path:");
        JLabel size = new JLabel("file size:");
        JTextField fPath = new JTextField();
        JTextField fSize = new JTextField();
        JButton createBtn = new JButton("Create");
        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = fPath.getText();
                String size = fSize.getText();
                File file = new File(path);
                try {
                    file.createNewFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    try (RandomAccessFile raf = new RandomAccessFile(path, "rw")) {
                        raf.setLength(Integer.getInteger(size));
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
            }
        );
        controlPanel.add(file);
        controlPanel.add(fPath);
        controlPanel.add(size);
        controlPanel.add(fSize);
        controlPanel.add(createBtn);
        mainFrame.setVisible(true);
    }



    public static void main (String[] args)
    {
        FileCreator fileCreator = new FileCreator();
        fileCreator.start();

    }
}
