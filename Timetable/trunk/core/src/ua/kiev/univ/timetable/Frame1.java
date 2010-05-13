package ua.kiev.univ.timetable;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import oracle.jdeveloper.layout.XYLayout;

import org.jgap.InvalidConfigurationException;

public class Frame1 extends JFrame {
    private BorderLayout layoutMain = new BorderLayout();
    private JPanel panelCenter = new JPanel();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu();
    private JMenuItem menuFileExit = new JMenuItem();
    private JMenu menuHelp = new JMenu();
    private JMenuItem menuHelpAbout = new JMenuItem();
    private JLabel statusBar = new JLabel();
    private JToolBar toolBar = new JToolBar();
    private JButton buttonOpen = new JButton();
    private JButton buttonClose = new JButton();
    private JButton buttonHelp = new JButton();
    private ImageIcon imageOpen =
        new ImageIcon(Frame1.class.getResource("openfile.gif"));
    private ImageIcon imageClose =
        new ImageIcon(Frame1.class.getResource("closefile.gif"));
    private ImageIcon imageHelp =
        new ImageIcon(Frame1.class.getResource("help.gif"));
    private JMenuItem menuFileRun = new JMenuItem();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JToolBar jToolBar3 = new JToolBar();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private FlowLayout flowLayout1 = new FlowLayout();

    public Frame1() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setJMenuBar(menuBar);
        this.getContentPane().setLayout(layoutMain);
        panelCenter.setLayout(null);
        this.setSize(new Dimension(400, 300));
        this.setTitle("Timetable");
        menuFile.setText("File");
        menuFileExit.setText("Exit");
        menuFileExit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    fileExit_ActionPerformed(ae);
                }
            });
        menuHelp.setText("Help");
        menuHelpAbout.setText("About");
        menuHelpAbout.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    helpAbout_ActionPerformed(ae);
                }
            });
        statusBar.setText("");
        buttonOpen.setToolTipText("Open File");
        buttonOpen.setIcon(imageOpen);
        buttonClose.setToolTipText("Close File");
        buttonClose.setIcon(imageClose);
        buttonHelp.setToolTipText("About");
        buttonHelp.setIcon(imageHelp);
        menuFileRun.setText("Run");
        menuFileRun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                fileRun_ActionPerformed(ae);
                }
        });
        jPanel1.setBounds(new Rectangle(45, 45, 60, 60));
        jPanel1.setLayout(flowLayout1);
        jPanel2.setBounds(new Rectangle(250, 45, 60, 60));
        menuFile.add(menuFileRun);
        menuFile.add(menuFileExit);
        menuBar.add(menuFile);
        menuHelp.add(menuHelpAbout);
        menuBar.add(menuHelp);
        this.getContentPane().add(statusBar, BorderLayout.SOUTH);
        toolBar.add(buttonOpen);
        toolBar.add(buttonClose);
        toolBar.add(buttonHelp);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        panelCenter.add(jPanel1, null);
        panelCenter.add(jPanel2, null);
        this.getContentPane().add(panelCenter, BorderLayout.CENTER);
    }

    void fileExit_ActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    void helpAbout_ActionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this, new Frame1_AboutBoxPanel1(),
                                      "About", JOptionPane.PLAIN_MESSAGE);
    }
    
    void fileRun_ActionPerformed(ActionEvent e){
        String[] startter = new String[5];
        try {
            Start.main(startter);
        } catch (InvalidConfigurationException f) {
            System.out.println(f.getMessage());
        }
    }
}
