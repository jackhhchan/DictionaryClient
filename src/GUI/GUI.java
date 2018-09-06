
import javax.swing.*;
import java.awt.*;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import Client.DictionaryClient;

public class GUI {
    private JButton queryButton;
    private JButton removeButton;
    private JButton addButton;
    private JTextField wordTxtField;
    private JTextArea meaningTxtArea;
    private JTextField msgTxtField;
    private JFrame frame;
    private JPanel guiPanel;

    private DictionaryClient client = new DictionaryClient();


    public static void main(String[] args) {
        // check arguments
        if (args.length != 2) {
            throw new IllegalArgumentException("Please enter server address and port number in arguments.");
        }
        // assign arguments
        final String hostName = args[0];
        final int port;
        try {
            // check if port is an integer.
            port = Integer.parseInt(args[1]);

            // Create thread to run the GUI.
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        GUI gui = new GUI(hostName, port);
                        gui.frame.setVisible(true);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (NumberFormatException e) {
            System.out.println("Please enter an integer for the port number.");
        }

    }

    /*
     GUI Constructor and Initialization method:
     */
    private GUI(String hostName, int port) {
        Initialize(hostName, port);
    }

    private void Initialize(String hostName, int port) {
        // Initialize the GUI.
        frame = new JFrame("GUI");
        frame.setContentPane(guiPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(500, 400);
        meaningTxtArea.setEditable(true);
        meaningTxtArea.setLineWrap(true);
        msgTxtField.setEditable(false);


        wordTxtField.setText("Enter a word.");


        // Set up host name and port for connection.
        client.setHostName(hostName);
        client.setPort(port);


        // Buttons
        queryButtonAction();        // Listen for query button event.
        addButtonAction();          // Listen for add button event.
        removeButtonAction();       // Listen for remove button event.

    }

    /*
    Button action functions:
        * queryButtonAction     -- operations involving query button.
        * addButtonAction       -- operations involving action button.
        * removeButtonAction    -- operations involving remove button.
     */

    private void queryButtonAction() {
        queryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetFields(true);
                // Parse and check inputs from user GUI.
                String clientWord = parseClientWord();
                if (clientWord == null) {
                    return;
                }

                try {
                    // Ask server for the meaning of query.
                    String result = client.query(clientWord);
                    if (result.equals("Word not found in dictionary."))
                    {
                        msgTxtField.setText(result);
                    }
                    else {
                        meaningTxtArea.setText(result);
                    }
                } catch (IOException ioe) {
                    msgTxtField.setText(ioe.toString());
                }
            }
        });
    }

    private void addButtonAction() {
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetFields(false);
                // Parse and check inputs from user GUI.
                String clientWord = parseClientWord();
                if (clientWord == null){
                    return;
                }
                String meaning = parseMeaning();
                if (meaning == null) {
                    return;
                }

                try {
                    // Add word to server dictionary.
                    String message = client.add(clientWord, meaning);
                    msgTxtField.setText(message);
                } catch (UnknownHostException uhe) {
                    msgTxtField.setText("Unknown Host.");
                } catch (IOException ioe) {
                    msgTxtField.setText("Connection failed.");
                }
            }
        });
    }

    private void removeButtonAction() {
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetFields(true);
                // Parse and check inputs from user GUI.
                String clientWord = parseClientWord();
                if (clientWord == null) {
                    return;
                }

                try {
                    String message = client.remove(clientWord);
                    msgTxtField.setText(message);
                } catch (UnknownHostException uhe) {
                    msgTxtField.setText("Unknown Host.");
                } catch (IOException ioe) {
                    msgTxtField.setText("Connection failed.");
                }
            }
        });
    }


    /*
    Helper Functions:
        * parseClientWord() -- check for validity for word in word text box.
        * parseMeaning()    -- check for validity in meaning text box.
        * resetFields(resetMeaning) -- clear message fields and if resetMeaning, clear meaning fields too.
     */

    private String parseClientWord() {
        String[] word = wordTxtField.getText().split("\\s");
        if (word.length != 1 | word[0].isEmpty()) {
            wordTxtField.setText("Please enter a single word.");
            return null;
        }
        return word[0];
    }

    private String parseMeaning() {
        String meaning = meaningTxtArea.getText();
        if (meaning.isEmpty()) {
            meaningTxtArea.setText("Please enter a meaning for the word here.");
            return null;
        }
        return meaning;
    }

    private void resetFields(boolean resetMeaning) {
        if (resetMeaning) {
            meaningTxtArea.setText(null);
        }
        msgTxtField.setText(null);
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        guiPanel = new JPanel();
        guiPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(7, 2, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Meaning");
        guiPanel.add(label1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        wordTxtField = new JTextField();
        guiPanel.add(wordTxtField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setEnabled(true);
        label2.setText("Word");
        guiPanel.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        meaningTxtArea = new JTextArea();
        guiPanel.add(meaningTxtArea, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        removeButton = new JButton();
        removeButton.setText("Remove");
        guiPanel.add(removeButton, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButton = new JButton();
        addButton.setText("Add");
        guiPanel.add(addButton, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        queryButton = new JButton();
        queryButton.setText("Query");
        guiPanel.add(queryButton, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        msgTxtField = new JTextField();
        guiPanel.add(msgTxtField, new com.intellij.uiDesigner.core.GridConstraints(6, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setHorizontalAlignment(0);
        label3.setHorizontalTextPosition(0);
        label3.setText("Message:");
        guiPanel.add(label3, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return guiPanel;
    }
}
