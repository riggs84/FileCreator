import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;

/**
 * Created by MartinRiggs on 4/14/2017.
 */
public class FileCreator {
    private JFrame mainFrame;
    private JPanel controlPanel;
    private JPanel footer;
    private int multiplyValue;
    //private JPanel footer;
    public FileCreator()
    {
        mainFrame = new JFrame("File creator tool");
        mainFrame.setSize(600,150);
        mainFrame.setLayout(new GridLayout(3,1));
        mainFrame.getRootPane().setBorder(BorderFactory.createMatteBorder(4,4,4,4, Color.LIGHT_GRAY));
        mainFrame.setLocationRelativeTo(null);
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
        controlPanel.setLayout(new BoxLayout(controlPanel,BoxLayout.X_AXIS));
        controlPanel.setBorder(new EmptyBorder(0,10,0,10));
        JLabel header = new JLabel("Welcome to file creation tool.",SwingConstants.CENTER);
        //JLabel footer = new JLabel("",SwingConstants.CENTER);
        footer = new JPanel(new FlowLayout());
        mainFrame.add(header);
        mainFrame.add(controlPanel);
        mainFrame.add(footer);

    }
    private void start()
    {
        JLabel file = new JLabel("Full path to file:");
        file.setBorder(new EmptyBorder(0,0,0,5));
        JLabel size = new JLabel("file size:");
        size.setBorder(new EmptyBorder(0,5,0,5));
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
                        //raf.setLength(Long.valueOf(size).longValue());

                        for (long i = 1; i <= ((Long.valueOf(size).longValue())*multiplyValue); i++)
                        {
                            raf.write(1);
                        }

                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                JOptionPane.showMessageDialog(null,"File created at " + path);

            }
            }
        );
        /*final DefaultComboBoxModel fDimension = new DefaultComboBoxModel();
        fDimension.addElement("MBytes");
        fDimension.addElement("KBytes");
        fDimension.addElement("Bytes");*/
        String[] fDimension = {"MBytes", "KBytes", "Bytes"};
        final JComboBox fileSizeDim = new JComboBox(fDimension);
        fileSizeDim.setBorder(new EmptyBorder(0,5,0,5));
        fileSizeDim.setSelectedIndex(2);
        fileSizeDim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox combo = (JComboBox) e.getSource();
                String value = (String) combo.getSelectedItem();
                if (value.equals("Bytes"))
                {
                    multiplyValue = 1;
                }
                else if (value.equals("MBytes"))
                {
                    multiplyValue = 1024*1024;
                }
                else
                {
                    multiplyValue = 1024;
                }

            }
        });


        controlPanel.add(file);
        controlPanel.add(fPath);
        controlPanel.add(size);
        controlPanel.add(fSize);
        controlPanel.add(fileSizeDim);
        footer.add(createBtn);
        mainFrame.setVisible(true);
    }



    public static void main (String[] args)
    {
        FileCreator fileCreator = new FileCreator();
        fileCreator.start();

    }
}
