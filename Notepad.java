import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class Notepad implements ActionListener {
    private JFrame frame;
    private JMenuBar menubar = new JMenuBar();
    private JMenu file = new JMenu("File");
    private JMenu edit = new JMenu("Edit");
    private JMenu help = new JMenu("Help");
    private JMenuItem newFile = new JMenuItem("New");
    private JMenuItem openFile = new JMenuItem("Open");
    private JMenuItem saveFile = new JMenuItem("Save");
    private JMenuItem print = new JMenuItem("Print");
    private JMenuItem exit = new JMenuItem("Exit");
    private JMenuItem cut = new JMenuItem("Cut");
    private JMenuItem copy = new JMenuItem("Copy");
    private JMenuItem paste = new JMenuItem("Paste");
    private JMenuItem selectAll = new JMenuItem("Select All");
    private JMenuItem about = new JMenuItem("About");
    private JTextArea textArea = new JTextArea();

    public Notepad() {
        frame = new JFrame("NotePad Application");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // ImageIcon icon = new ImageIcon(getClass().getResource("my_logo.png"));
        // frame.setIconImage(icon.getImage());

        file.add(newFile);
        file.add(openFile);
        file.add(saveFile);
        file.add(print);
        file.add(exit);

        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);

        help.add(about);

        frame.setJMenuBar(menubar);
        menubar.add(file);
        menubar.add(edit);
        menubar.add(help);

        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        newFile.addActionListener(this);
        openFile.addActionListener(this);
        saveFile.addActionListener(this);
        print.addActionListener(this);
        exit.addActionListener(this);

        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);

        about.addActionListener(this);

        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK));

        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));

        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, KeyEvent.CTRL_DOWN_MASK));
    }

    public void createAndShowGUI() {
        SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        Notepad notepad = new Notepad();
        notepad.createAndShowGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("New")) {
            textArea.setText(null);
        } else if (e.getActionCommand().equalsIgnoreCase("Open")) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter textFilter = new FileNameExtensionFilter("Only text file (.txt)", "txt");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(textFilter);

            int action = fileChooser.showOpenDialog(frame);

            if (action != JFileChooser.APPROVE_OPTION) {
                return;
            } else {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
                    textArea.read(reader, null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getActionCommand().equalsIgnoreCase("Save")) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter textFilter = new FileNameExtensionFilter("Only text file (.txt)", "txt");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(textFilter);

            int action = fileChooser.showSaveDialog(frame);
            if (action != JFileChooser.APPROVE_OPTION) {
                return;
            } else {
                String fileName = fileChooser.getSelectedFile().getAbsolutePath().toString();
                if (!fileName.contains(".txt")) {
                    fileName = fileName + ".txt";
                }
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                    textArea.write(writer);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getActionCommand().equalsIgnoreCase("Print")) {
            try {
                textArea.print();
            } catch (PrinterException e1) {
                e1.printStackTrace();
            }
        } else if (e.getActionCommand().equalsIgnoreCase("Exit")) {
            System.exit(0);
        } else if (e.getActionCommand().equalsIgnoreCase("Cut")) {
            textArea.cut();
        } else if (e.getActionCommand().equalsIgnoreCase("Copy")) {
            textArea.copy();
        } else if (e.getActionCommand().equalsIgnoreCase("Paste")) {
            textArea.paste();
        } else if (e.getActionCommand().equalsIgnoreCase("Select All")) {
            textArea.selectAll();
        } else if (e.getActionCommand().equalsIgnoreCase("About")) {
            JOptionPane.showMessageDialog(frame, "About dialog message");
        }
    }
}