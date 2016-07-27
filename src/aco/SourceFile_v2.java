/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aco;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author bishu
 */
public class SourceFile_v2 {

    /**
     * Creates new form SourceFile
     */
    public static JFrame f;
    private JLabel st;
    private JLabel h;
    private JPanel p;
    public File file;
    public int jj = 0;
    public String text;

    public SourceFile_v2() {
        inittComponents();
        ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\icons\\folder.jpg");
        f.setIconImage(icon.getImage());
        Toolkit toolkit = f.getToolkit();
        Dimension size = toolkit.getScreenSize();
        f.setLocation(size.width / 2 - f.getWidth() / 2, size.height / 2 - f.getHeight() / 2);

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void main(String args[]) {
        try {
            SourceFile_v2 s = new SourceFile_v2();
            s.showFile();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void inittComponents() {
        f = new JFrame("Choose a source file");
        f.setSize(500, 500);
        f.setLayout(new GridLayout(3, 1));
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        }
        );
        h = new JLabel("", JLabel.CENTER);
        st = new JLabel("", JLabel.CENTER);
        st.setSize(350, 100);
        p = new JPanel();
        p.setLayout(new FlowLayout());

        f.add(h);
        f.add(p);
        f.add(st);
        f.setVisible(false);
    }// </editor-fold>                        

    public void showFile() {
        h.setFont(new java.awt.Font("Comic Sans MS", 0, 18));
        h.setText("Choose the source file:");

        final JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String ext = null, s = f.getName();
                int i = s.lastIndexOf(".");
                if (i > 0 && i < s.length() - 1) {
                    ext = s.substring(i + 1).toLowerCase();
                }
                if (ext != null) {
                    if (ext.equals("c") || ext.equals("cpp")) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public String getDescription() {
                return "C/C++ Source Files";
            }
        });

        JButton sf = new JButton("Open File");
        JButton lgt = new JButton("Next");
        sf.setToolTipText("Open a file");
        sf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sf.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\src\\icons\\docs.jpg"));

        lgt.setToolTipText("Next to testing");
        lgt.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\src\\icons\\back.png"));
        lgt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lgt.setVisible(false);
        lgt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jj = 1;
                f.dispose();
                if (text.equals("rfd")) {
                    new RFD_v5().run(file);
                } else if (text.equals("aco")) {
                    new ACO_v4().run(file);
                } else if (text.equals("coo")) {
                    new cuckoo_v3().run(file);
                }
                //System.out.println("Got the file");
            }
        });
        lgt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                if (evt.getKeyCode() == 10) {
                    lgt.doClick();
                }
            }
        });

        sf.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            int r = fc.showOpenDialog(f);
                            if (r == JFileChooser.APPROVE_OPTION) {
                                file = fc.getSelectedFile();
                                st.setText("File selected: " + file.getName());
                                //Thread.sleep(500);
                                lgt.setVisible(true);
                            } else {
                                st.setText("Cancelled");
                            }
                        } catch (Exception E) {
                            System.out.println(E);
                        }
                    }
                }
        );      //sf.setVisible(true);
        fc.setCurrentDirectory(new File(System.getProperty("user.dir") + "\\Inputs"));
        p.add(sf);
        p.add(lgt);
        f.setVisible(true);
    }

    public boolean getSignal() {
        if (jj == 1) {
            return true;
        }
        return false;
    }

    /**
     * @param args the command line arguments
     */
}
