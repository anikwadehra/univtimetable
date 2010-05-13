package ua.kiev.univ.timetable;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.jgap.InvalidConfigurationException;


public class Frame1 extends JFrame {
    private BorderLayout layoutMain = new BorderLayout();
    private JPanel panelCenter = new JPanel();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu();
    private JMenuItem menuFileExit = new JMenuItem();
    private JMenu menuHelp = new JMenu();
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
    
   //---------Tabs -----------------------
    private JTabbedPane jTabbedPanelMain = new JTabbedPane();
    private GridLayout gridLayout1 = new GridLayout();
    
    //------Panels---------------------------------
    //------------Lists----------------------------
    private JTabbedPane tpLists = new JTabbedPane();
    private JPanel panelListsGroups = new JPanel();
    private JPanel panelListsLessons = new JPanel();
    private JPanel panelListsClasses = new JPanel();
    private JPanel panelListsTeachers = new JPanel();
    private JPanel panelListsTimes = new JPanel();
    //------------Loads----------------------------
    private JTabbedPane tbLoads = new JTabbedPane();
    private JPanel panelLoadsOne = new JPanel();
    private JPanel panelLoadsTwo = new JPanel();
    //------------Timetable------------------------
    private JTabbedPane tbTimetable = new JTabbedPane();
    private JPanel panelTimetableOne = new JPanel();
    private JMenuItem jMenuItem1 = new JMenuItem();
    private JMenuItem jMenuItem2 = new JMenuItem();
    private JMenuItem jMenuItem3 = new JMenuItem();


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
        panelCenter.setLayout(gridLayout1);
        this.setSize(new Dimension(400, 300));
        this.setTitle("Timetable");
        menuFile.setText("Файл");
        menuFileExit.setText("Выход");
        menuFileExit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    fileExit_ActionPerformed(ae);
                }
            });
        menuHelp.setText("Помощь");
        statusBar.setText("");
        buttonOpen.setToolTipText("Open File");
        buttonOpen.setIcon(imageOpen);
        buttonClose.setToolTipText("Close File");
        buttonClose.setIcon(imageClose);
        buttonHelp.setToolTipText("About");
        buttonHelp.setIcon(imageHelp);
        menuFileRun.setText("Запуск");
        menuFileRun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                fileRun_ActionPerformed(ae);
                }
        });

        jTabbedPanelMain.setMinimumSize(new Dimension(210, 188));
        jTabbedPanelMain.setSize(new Dimension(210, 288));
        jMenuItem1.setText("Открыть входные данные (XML)");
        jMenuItem2.setText("Открыть расписание (XML)");
        jMenuItem3.setText("О программе");
        menuFile.add(jMenuItem1);
        menuFile.add(jMenuItem2);
        menuFile.addSeparator();
        menuFile.add(menuFileRun);
        menuFile.addSeparator();
        menuFile.add(menuFileExit);
        menuBar.add(menuFile);
        menuHelp.add(jMenuItem3);
        menuBar.add(menuHelp);
        this.getContentPane().add(statusBar, BorderLayout.SOUTH);
        toolBar.add(buttonOpen);
        toolBar.add(buttonClose);
        toolBar.add(buttonHelp);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        this.getContentPane().add(panelCenter, BorderLayout.CENTER);
        //----Init tabs
        jTabbedPanelMain.addTab("Списки", tpLists);
        tpLists.addTab("Группы", panelListsGroups);
        tpLists.addTab("Предметы", panelListsLessons);
        tpLists.addTab("Аудитории", panelListsClasses);
        tpLists.addTab("Преподаватели", panelListsTeachers);
        tpLists.addTab("Время", panelListsTimes);

        jTabbedPanelMain.addTab("Наргузки", tbLoads);
        tbTimetable.addTab("One", panelTimetableOne);
        jTabbedPanelMain.addTab("Расписание", tbTimetable);
        tbLoads.addTab("One", panelLoadsOne);
        tbLoads.addTab("Two", panelLoadsTwo);


        panelCenter.add(jTabbedPanelMain);
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
