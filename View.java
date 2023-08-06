
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.BreakIterator;
import java.util.List;
import javax.swing.UIManager;

public class View {

    private JFrame mainFrame;
    private JTextField textField;
    private JMenuBar menuBar;
    private JMenu menuFile, menuInput;
    private JMenuItem menuItemOpen, menuItemInputStep, menuItemInputFast;
    private JTabbedPane tabbedPane;
    private JScrollPane scrollPane;
    private JLabel stepCurrState, stepCurrInput;
    private JTextArea textArea, textArea1;

    private File file;

    private Machine machine;

    private int stepIndex;

    public View() {
        this.mainFrame = new JFrame("2DFA");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setLayout(null);
        this.mainFrame.setResizable(false);
        this.mainFrame.setSize(500, 500);
        this.mainFrame.setLocationRelativeTo(null);

        // Build the menu
        this.menuBar = new JMenuBar();
        this.menuFile = new JMenu("File");
        UIManager.put("MenuBar.background", Color.ORANGE);
        this.menuItemOpen = new JMenuItem("Open...");
        this.menuItemOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
                fileChooser.setFileFilter(filter);
                int returnValue = fileChooser.showOpenDialog(mainFrame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    file = selectedFile;
                    // Process the selected file here
                    // For example, you can display its path or load its contents
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());

                    try {
                        machine = new Machine(selectedFile);
                        // System.out.println(machine.toString());
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                    displayMachineDefinition();

                }
            }
        });

        this.menuInput = new JMenu("Input");

        this.menuItemInputStep = new JMenuItem("Step with Closure...");

        this.menuItemInputStep.addActionListener(new ActionListener() {
            private JScrollPane scrollPane;
            private JTextArea textArea1;

            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a text field
                JTextField textField = new JTextField(20);
                this.textArea1 = new JTextArea();
                this.textArea1.setEditable(false);
                this.textArea1.setLineWrap(true);
                this.textArea1.setWrapStyleWord(true);
                this.textArea1.setMargin(new Insets(20, 20, 20, 20));

                // Create a panel to hold the text field and label
                JPanel panel = new JPanel();
                panel.add(new JLabel("Input"));
                panel.add(textField);
                // panel.add(this.textArea1);

                // Show the input dialog with "OK" and "Cancel" buttons, and custom title
                int result = JOptionPane.showOptionDialog(null,
                        panel,
                        "Input",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        null);

                // Check the result to see if "OK" was clicked
                if (result == JOptionPane.OK_OPTION) {
                    String inputText = textField.getText();

                    StringBuilder sb = new StringBuilder();
                    sb.append("<");
                    sb.append(inputText);
                    sb.append(">");

                    System.out.println(sb);

                    stepIndex = 0;
                    var result1 = machine.checkString(inputText);
                    if (result1 == true) {
                        this.scrollPane = new JScrollPane(this.textArea1);
                        machine.sb1.append("\n");
                        machine.sb1.append(result1);

                        this.textArea1.setText(String.valueOf(machine.sb1));

                    } else {

                        this.scrollPane = new JScrollPane(this.textArea1);
                        machine.sb1.append(result1);

                        this.textArea1.setText(String.valueOf(machine.sb1));

                    }
                    System.out.print(result1);

                    tabbedPane.add("Simulate: " + inputText, this.textArea1);
                    // tabbedPane.add(this.textArea1);

                    tabbedPane.setSelectedIndex(0);

                    mainFrame.repaint();
                    mainFrame.revalidate();

                }
            }
        });

        this.menuInput.add(this.menuItemInputStep);
        // this.menuInput.add(this.menuItemInputFast);

        // Create a JTextArea
        this.textArea = new JTextArea();
        this.textArea.setEditable(false);
        this.textArea.setLineWrap(true);
        this.textArea.setWrapStyleWord(true);
        this.textArea.setMargin(new Insets(20, 20, 20, 20));

        // Create a JScrollPane and add the JTextArea to it
        this.scrollPane = new JScrollPane(this.textArea);
        // this.scrollPane = new JScrollPane(this.textArea1);

        this.tabbedPane = new JTabbedPane();
        this.tabbedPane.setBounds(0, 0, 484, 438);
        this.tabbedPane.add("Machine Definition", scrollPane);
        this.mainFrame.add(this.tabbedPane);
        this.menuBar.add(this.menuFile);
        this.menuBar.add(this.menuInput);
        this.menuFile.add(this.menuItemOpen);

        this.mainFrame.setJMenuBar(this.menuBar);
        this.mainFrame.setVisible(true);
    }

    public void displayMachineDefinition() {
        // this.mainFrame.getContentPane().removeAll();

        this.mainFrame.setTitle("2DFA - " + file.getName());
        this.textArea.setText(machine.toString());

        this.mainFrame.repaint();
        this.mainFrame.revalidate();

    }

}
