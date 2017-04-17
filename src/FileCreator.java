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
import java.lang.reflect.InvocationTargetException;

/**
 * Created by MartinRiggs on 4/14/2017.
 */
public class FileCreator {
    private JFrame mainFrame;
    private JPanel controlPanel;
    private JPanel footer;
    private JLabel header;
    private int multiplyValue = 1;
    private JProgressBar bar;
    public FileCreator()
    {
        mainFrame = new JFrame("File creator tool");
        mainFrame.setSize(600,200);
        mainFrame.setLayout(new GridLayout(4,1));
        mainFrame.getRootPane().setBorder(BorderFactory.createMatteBorder(4,4,4,4, Color.LIGHT_GRAY));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
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
        header = new JLabel("Welcome to file creation tool.",SwingConstants.CENTER);
        //JLabel footer = new JLabel("",SwingConstants.CENTER);
        //
        bar = new JProgressBar(0,100);
        bar.setStringPainted(true);
        bar.setVisible(false);
        footer = new JPanel(new FlowLayout());
        mainFrame.add(header);
        mainFrame.add(controlPanel);
        mainFrame.add(footer);
        mainFrame.add(bar);


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
                                            File directory = new File(file.getParent());
                                            boolean dirCreated = directory.mkdirs();
                                            try {
                                                file.createNewFile();
                                            } catch (IOException e1) {
                                                JOptionPane.showMessageDialog(null, "Can not create file");
                                            }

                                            Thread worker = new Thread() {
                                                public void run() {
                                                    try (RandomAccessFile raf = new RandomAccessFile(path, "rw")) {
                                                        header.setText("I'm creating file.....");
                                                        bar.setVisible(true);
                                                        long tmp = Long.valueOf(size)*multiplyValue;
                                                        for (long i = 1; i <= tmp; i++) {
                                                            raf.write(0);
                                                            double result = (((double)i / tmp)*100);
                                                            //long result = Math.abs(tmp - i)/tmp;
                                                            ProgressBarUpdate((int) result);
                                                            /*bar.setValue(((int) result));
                                                            bar.updateUI();*/
                                                        }
                                                    } catch (Exception e1) {
                                                        JOptionPane.showMessageDialog(null, "Thread input/output error");
                                                    }
                                                    SwingUtilities.invokeLater(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            header.setText("File created at " + path);
                                                            JOptionPane.showMessageDialog(null, "File has been created");
                                                            bar.setVisible(false);
                                                        }
                                                    });
                                              }
                                            };
                                            worker.start();
                                        }
                                    });

        String[] fDimension = {"MBytes", "KBytes", "Bytes"};
        final JComboBox fileSizeDim = new JComboBox(fDimension);
        fileSizeDim.setBorder(new EmptyBorder(0,5,0,5));
        fileSizeDim.setSelectedIndex(2);
        fileSizeDim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox combo = (JComboBox) e.getSource();
                String value = (String) combo.getSelectedItem();
                /*if (value.equals("Bytes"))
                {
                    multiplyValue = 1;
                }*/
                if (value.equals("MBytes"))
                {
                    multiplyValue = 1024*1024;
                }
                else if (value.equals("KBytes"))
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

    private void ProgressBarUpdate(int i)
    {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    bar.setValue(i);
                }
            });
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, "Progress error occur");
        } catch (InvocationTargetException e) {
            JOptionPane.showMessageDialog(null, "Progress bar error occur");
        }
    }




    public static void main (String[] args)
    {
        FileCreator fileCreator = new FileCreator();
        fileCreator.start();

    }
}
