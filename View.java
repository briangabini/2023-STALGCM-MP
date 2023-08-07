

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class View {
    private JFrame mainFrame;
    List<List<String>> twoDList = new ArrayList<>();

    private JTextField textField;
    private JMenuBar menuBar;
    private JMenu menuFile, menuInput;
    private JMenuItem menuItemOpen, menuItemInputStep, menuItemInputFast;
    private JTabbedPane tabbedPane;
    private JScrollPane scrollPane;
    private JLabel stepCurrState, stepCurrInput,Pop1,Pop2;
    private JTextArea textArea ,textArea1;

    private File file;

    private Machine machine;

    private int stepIndex;

    private List<StateAndIndexPair> stepListt;
    private  List list1;


    public View() {
        this.mainFrame = new JFrame("2DFA");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setLayout(null);
        this.mainFrame.setResizable(false);
        this.mainFrame.setSize(500,500);
        this.mainFrame.setLocationRelativeTo(null);

        //Build the menu
        this.menuBar = new JMenuBar();
        this.menuFile = new JMenu("File");
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

                    try{
                        machine = new Machine(selectedFile);
//                        System.out.println(machine.toString());
                    }
                    catch (Exception ex) {
                        System.out.println(ex);
                    }
                    displayMachineDefinition();

                }
            }
        });

        this.menuInput = new JMenu("Input");


        this.menuItemInputStep = new JMenuItem("Step with Closure...");

        this.menuItemInputStep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a text field
                JTextField textField = new JTextField(20);


                // Create a panel to hold the text field and label
                JPanel panel = new JPanel();
                panel.add(new JLabel("Input"));
                panel.add(textField);

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


                    machine.checkStringAcceptance(inputText);
                    for (Object o : machine.list) {

                    }
                    stepListt = new ArrayList<>(machine.list);
                    //System.out.println("acha "+ stepListt.size());


                    twoDList=machine.twoDList;
                    //System.out.println(twoDList);
                    //System.out.println(twoDList.get(0).get(0));
                    ;


                    JPanel newPanel = new JPanel(null);
                    newPanel.setBackground(Color.white);

                    JButton prevBtn = new JButton("Prev");
                    prevBtn.setBounds(105, 10, 90, 25);

                    JButton nextBtn = new JButton("Next");
                    nextBtn.setBounds(205, 10, 90, 25);

                    JButton resetBtn = new JButton("Reset");
                    resetBtn.setBounds(305, 10, 90, 25);

                    JLabel currState = new JLabel("Current State ");
                    currState.setBounds(200, 50, 100, 30);
                    stepCurrState = new JLabel();
                    Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
                    stepCurrState.setBorder(border);
                    stepCurrState.setBounds(200, 80, 200, 30);




                    JLabel currInput = new JLabel("Current Input");
                    currInput.setBounds(200, 110, 100, 30);
                    stepCurrInput = new JLabel();
                    stepCurrInput.setBorder(border);
                    stepCurrInput.setBounds(200, 140, 100, 30);

                    JLabel pop1 = new JLabel("Stack pop1");
                    pop1.setBounds(200, 170, 100, 30);
                    Pop1 = new JLabel();
                    Pop1.setBorder(border);
                    Pop1.setBounds(200, 200, 200, 30);

                    JLabel pop2 = new JLabel("Stack pop2");
                    pop2.setBounds(200, 230, 100, 30);
                    Pop2 = new JLabel();
                    Pop2.setBorder(border);
                    Pop2.setBounds(200, 270, 200, 30);








                    newPanel.add(prevBtn);
                    newPanel.add(nextBtn);
                    newPanel.add(resetBtn);
                    newPanel.add(currState);
                    newPanel.add(currInput);
                    newPanel.add(pop1);
                    newPanel.add(pop2);



//                    stepCurrState.setText(stepList.get(0).getStateName());
//                    stepCurrInput.setText(formatStringAtIndex(sb.toString(), stepList.get(stepIndex).getIndex()));

                    stepCurrState.setText(stepListt.get(0).getStateName());
                    stepCurrInput.setText(formatStringAtIndex(sb.toString(), stepListt.get(stepIndex).getIndex()));
                    Pop1.setText(twoDList.get(0).get(0));
                    Pop2.setText(twoDList.get(0).get(1));


//
//                    stepCurrState.setText(stepList.get(0));
//                    stepCurrInput.setText(formatStringAtIndex(sb.toString(),stepIndex));


                    newPanel.add(stepCurrState);
                    newPanel.add(stepCurrInput);
                    newPanel.add(Pop1);
                    newPanel.add(Pop2);

                    // Add actions listener
                    prevBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(stepIndex == 0) {
                                return;
                            }
                            stepIndex--;

                            stepCurrState.setText(stepListt.get(stepIndex).getStateName());
                            stepCurrInput.setText(formatStringAtIndex(sb.toString(), stepListt.get(stepIndex).getIndex()));

                            Pop1.setText(twoDList.get(stepIndex).get(0));
                            Pop2.setText(twoDList.get(stepIndex).get(1));


//                            stepCurrState.setText(stepList.get(stepIndex));
//
//                            stepCurrInput.setText(formatStringAtIndex(sb.toString(),stepIndex));
                            mainFrame.repaint();
                            mainFrame.revalidate();
                        }
                    });

                    nextBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(stepIndex == stepListt.size() - 1) {
                                return;
                            }

                            stepIndex++;
//                            stepCurrState.setText(stepList.get(stepIndex));
//                            stepCurrInput.setText(formatStringAtIndex(sb.toString(),stepIndex));
                            stepCurrState.setText(stepListt.get(stepIndex).getStateName());
                            stepCurrInput.setText(formatStringAtIndex(sb.toString(), stepListt.get(stepIndex).getIndex()));
//                            System.out.print(stepList.get(stepIndex).getIndex());
                            Pop1.setText(twoDList.get(stepIndex).get(0));
                            Pop2.setText(twoDList.get(stepIndex).get(1));

                            mainFrame.repaint();
                            mainFrame.revalidate();
                        }
                    });

                    resetBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            stepIndex = 0;

                            stepCurrState.setText(stepListt.get(stepIndex).getStateName());
                            stepCurrInput.setText(formatStringAtIndex(sb.toString(), stepListt.get(stepIndex).getIndex()));

                            Pop1.setText(twoDList.get(stepIndex).get(0));
                            Pop2.setText(twoDList.get(stepIndex).get(1));
//                            stepCurrState.setText(stepList.get(stepIndex));
//                            stepCurrInput.setText(formatStringAtIndex(sb.toString(),stepIndex));
                            mainFrame.repaint();
                            mainFrame.revalidate();
                        }
                    });


                    tabbedPane.add("Step Closure: " + inputText, newPanel);
                    tabbedPane.setSelectedIndex(1);

                    mainFrame.repaint();
                    mainFrame.revalidate();
                }
            }
        });


        this.menuItemInputFast = new JMenuItem("Fast Run...");

        this.menuItemInputFast.addActionListener(new ActionListener() {
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
                    if(machine.checkStringAcceptance(inputText)){


//                        this.scrollPane = new JScrollPane(this.textArea1);
//                        this.scrollPane.setBorder(BorderFactory.createEmptyBorder());

                        machine.sb1.append("\n");


                        this.textArea1.setText(String.valueOf(machine.sb1));



//                        tabbedPane.add("Simulate: " + inputText, scrollPane);
//                        tabbedPane.add("Simulate: " + inputText, this.textArea1);
//
//                        textArea1.setText(String.valueOf(machine.sb1));
////                        panel1.add(textArea1);
//                        scrollPane = new JScrollPane(textArea1);
//                        textArea1.setText(String.valueOf(machine.sb1));
//                        tabbedPane.setSelectedIndex(0);
                        JOptionPane.showMessageDialog(mainFrame,
                                "The input was accepted");
                    }
                    else{
                        machine.sb1.append("\n");


                        this.textArea1.setText(String.valueOf(machine.sb1));
                        JOptionPane.showMessageDialog(mainFrame,
                                "The input was rejected");
                    }

                    tabbedPane.add("Fast run: " + inputText, this.textArea1);
//                    tabbedPane.add(this.textArea1);



                    tabbedPane.setSelectedIndex(0);

                    mainFrame.repaint();
                    mainFrame.revalidate();

                    System.out.println("You entered: " + inputText);
                }
            }
        });

        this.menuInput.add(this.menuItemInputStep);
        this.menuInput.add(this.menuItemInputFast);

        // Create a JTextArea
        this.textArea = new JTextArea();
        this.textArea.setEditable(false);
        this.textArea.setLineWrap(true);
        this.textArea.setWrapStyleWord(true);
        this.textArea.setMargin(new Insets(20,20,20,20));

        // Create a JScrollPane and add the JTextArea to it
        this.scrollPane = new JScrollPane(this.textArea);

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
//        this.mainFrame.getContentPane().removeAll();

        this.mainFrame.setTitle("2DFA - " + file.getName());
        this.textArea.setText(machine.toString());

        this.mainFrame.repaint();
        this.mainFrame.revalidate();

    }

    private String formatStringAtIndex(String inputString, int index) {
        if (index < 0 || index >= inputString.length()) {
            throw new IllegalArgumentException("Invalid index");
        }

        String formattedString = "<html>";

        for (int i = 0; i < inputString.length(); i++) {
            if (i == 0 && index == 0) {
                formattedString += "<u>" + "&#60" + "</u>";
            }
            else if (i == 0){
                formattedString +=  "&#60";
            }
            else if (i == index) {
                formattedString += "<u>" + inputString.charAt(i) + "</u>";
            } else {
                formattedString += inputString.charAt(i);
            }
        }

        formattedString += "</html>";
        return formattedString;
    }
}

