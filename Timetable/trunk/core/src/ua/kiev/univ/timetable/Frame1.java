package ua.kiev.univ.timetable;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileFilter;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import javax.xml.parsers.ParserConfigurationException;

import org.jgap.InvalidConfigurationException;

import org.xml.sax.SAXException;


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
    private JButton buttonSave = new JButton();
    private JButton buttonRun = new JButton();
    private JButton buttonHelp = new JButton();
    private ImageIcon imageOpen =
        new ImageIcon(Frame1.class.getResource("openfile.gif"));
    private ImageIcon imageClose =
        new ImageIcon(Frame1.class.getResource("closefile.gif"));
    private ImageIcon imageRun =
        new ImageIcon(Frame1.class.getResource("run-icon.png"));
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
    private JMenuItem menuFileOpenInputData = new JMenuItem();
    private JMenuItem menuFileOpenTimetable = new JMenuItem();
    private JMenuItem menuHelpAbout = new JMenuItem();
    private JLabel labelFileName = new JLabel();
    
    //----------FileChooser----------------
    JFileChooser fc = new JFileChooser("e:\\");
    private BorderLayout borderLayout1 = new BorderLayout();

    //----------Tables----------------------
    private JScrollPane jScrollPaneListLessons = new JScrollPane();
    
    private String[][] data = {{"a","b"},
                               {"c","d"}
                               };
    private String[] header = {"A", "B"};
    private JTable tableListLessons = new JTable(data, header);
    private JButton buttonAddListLessons = new JButton("Add");
    private JButton buttonDeleteListLessons = new JButton("Delete");
    private JPanel panelListLessonsTop = new JPanel();


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
        this.setSize(new Dimension(790, 503));
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
        buttonOpen.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fileOpenInputData_actionPerformed(e);
                }
            });
        buttonSave.setToolTipText("Close File");
        buttonSave.setIcon(imageClose);
        buttonSave.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonSave_actionPerformed(e);
                }
            });
        buttonRun.setToolTipText("Составить расписание");
        buttonRun.setIcon(imageRun);
        buttonRun.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fileRun_ActionPerformed(e);
                }
            });
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
        panelListsLessons.setLayout(borderLayout1);
        menuFileOpenInputData.setText("Открыть входные данные (XML)");
        menuFileOpenInputData.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fileOpenInputData_actionPerformed(e);
                }
            });
        menuFileOpenTimetable.setText("Открыть расписание (XML)");
        menuHelpAbout.setText("О программе");
        menuHelpAbout.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    helpAbout_ActionPerformed(e);
                }
            });
        menuFile.add(menuFileOpenInputData);
        menuFile.add(menuFileOpenTimetable);
        menuFile.addSeparator();
        menuFile.add(menuFileRun);
        menuFile.addSeparator();
        menuFile.add(menuFileExit);
        menuBar.add(menuFile);
        menuHelp.add(menuHelpAbout);
        menuBar.add(menuHelp);
        this.getContentPane().add(statusBar, BorderLayout.SOUTH);
        toolBar.add(buttonOpen);
        toolBar.add(buttonSave);
        toolBar.add(buttonRun);
        toolBar.add(buttonHelp);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        this.getContentPane().add(panelCenter, BorderLayout.CENTER);
        
        buttonAddListLessons.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
              buttonsAdd_Delete_ListLessons_actionPerformed(e);
            }
        });
      buttonDeleteListLessons.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e){
            buttonsAdd_Delete_ListLessons_actionPerformed(e);
          }
      });
        //----Init tabs
        jTabbedPanelMain.addTab("Списки", tpLists);
        
        initPanelListsGroups();
        tpLists.addTab("Группы", panelListsGroups);

        panelListsLessons.add(labelFileName, BorderLayout.SOUTH);
        jScrollPaneListLessons.getViewport().add(tableListLessons, null);
        panelListsLessons.add(jScrollPaneListLessons, BorderLayout.CENTER);
        panelListLessonsTop.setLayout(new FlowLayout(FlowLayout.LEADING));
        panelListLessonsTop.add(buttonAddListLessons);
        panelListLessonsTop.add(buttonDeleteListLessons);
        panelListsLessons.add(panelListLessonsTop,BorderLayout.NORTH);
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
    
    //---Init all info-panels---------
    private void initPanelListsGroups(){
      panelListsGroups.setLayout(new GridLayout(3,1));
      //panelListsGroups.add(new JButton("OK"));
      //panelListsGroups.add( new JToolBar());
    }
    
    void fileExit_ActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    void helpAbout_ActionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this, new Frame1_AboutBoxPanel1(),
                                      "О программе",
                                      JOptionPane.PLAIN_MESSAGE);
    }

    void fileRun_ActionPerformed(ActionEvent e) {
//        panelListsGroups.removeAll();
//        panelListsGroups.add( drawTable(data, headers) );
        if (Start.XML_TEST_FILENAME != null) {
            String[] startter = new String[5];
            try {
                Start.main(startter);
            } catch (InvalidConfigurationException f) {
                System.out.println(f.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this,
                                          "Не выбран файл с входными данными",
                                          "Ошибка",
                                          JOptionPane.WARNING_MESSAGE, null);
        }
    }

    void fileOpenInputData_actionPerformed(ActionEvent e) {
        //fc.showOpenDialog(this);
        fc.setFileFilter(new XMLFileFilter());
        switch (fc.showOpenDialog(this)) {
        case JFileChooser.APPROVE_OPTION:
            System.out.println("APPROVE_OPTION");
            File newFile = fc.getSelectedFile();
            labelFileName.setText(newFile.getPath());
            Start.setXML_TEST_FILENAME(newFile.getPath());
            //panelListsGroups.getComponent(n)
            showInputData();
            break;
        case JFileChooser.CANCEL_OPTION:
            System.out.println("CANCEL_OPTION");
            break;
        case JFileChooser.ERROR_OPTION:
            System.out.println("ERROR_OPTION");
        default:
            System.out.println("n/a");
        }

    }

    private void showInputData() {
      // Reading data from xml    
      try {
          new InputData().readFromFile(Start.XML_TEST_FILENAME);
      } catch (SAXException e) {
          System.out.println("SAXException"+e.getMessage());
          System.exit(1);
      } catch (IOException e) {
          System.out.println("IOException"+e.getMessage());
          System.exit(1);
      } catch (ParserConfigurationException e) {
          System.exit(1);
          System.out.println("ParserConfigurationException"+e.getMessage());
      }
      
        Object[][] groupData = new Object[Start.MAX_NUMBER_OF_GROUPS][4];
        Integer[] idGroup = GroupGene.getAllIdGroup();
        String[] groupName = GroupGene.getAllNames();
        Integer[] groupSize = GroupGene.getAllGroupSize();
        for (int i = 0; i < Start.MAX_NUMBER_OF_GROUPS; i++) {
            groupData[i][0] = i+1;
            groupData[i][1] = idGroup[i];
            groupData[i][2] = groupName[i];
            groupData[i][3] = groupSize[i];
        }
        String[] groupHeaders = {"№","Id группы", "Название группы", "Размер группы"};

        JTable groupTable = new JTable(groupData, groupHeaders);
        groupTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        groupTable.setCellSelectionEnabled(true);
        JScrollPane jsp = new JScrollPane(groupTable);
        panelListsGroups.add(jsp);
    }
    
    
  JScrollPane drawTable(String[][] data, String[] headers){
    JTable table1 = new JTable(data, headers);
    table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table1.setCellSelectionEnabled(true);
    JScrollPane jsp = new JScrollPane(table1);
    return jsp;
  //         TableModel dataModel = new AbstractTableModel() {
  //          public int getColumnCount() { return 4; }
  //          public int getRowCount() { return 4;}
  //          public Object getValueAt(int row, int col) { return new Integer(row*col); }
  //      };
  //      JTable table = new JTable(dataModel);
  //      JScrollPane scrollpane = new JScrollPane(table);
  //      return scrollpane;
  }

    private void buttonSave_actionPerformed(ActionEvent e) {
      tableListLessons.setValueAt("first", 0, 0);
    }
    
    private void buttonsAdd_Delete_ListLessons_actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton){
          JButton b = (JButton)e.getSource();
          if(b.getText().equals("Add"))
              addRowTableListLessons();
          if(b.getText().equals("Delete"))
              deleteRowTableListLessons();
        }
    }

    private void addRowTableListLessons() {
        System.out.println("Add");
    }

    private void deleteRowTableListLessons() {
        System.out.println("Delete");
    }
}

 class XMLFileFilter extends FileFilter{

    public boolean accept(File f) {
        if(f.getName().endsWith(".xml")) return true;
        if(f.getName().endsWith(".XML")) return true;
        if(f.isDirectory()) return true;
        
        return false;
    }

    public String getDescription() {
        return "XML files";
    }
}
