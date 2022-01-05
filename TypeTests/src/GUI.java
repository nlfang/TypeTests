import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class GUI {
    private static void openWebPage(ArrayList<TypeTest> typeTests, String keyboard) {
        String URL = "";
        TypeTest result = null;
        JFrame frame = new JFrame("TypeTests");
        for (int i = 0; i < typeTests.size(); i++) {
            if (typeTests.get(i).getKeyboard().equals(keyboard)) {
                result = typeTests.get(i);
                break;
            }
        }
        if (result != null) {
            URL = result.getURL();
            try {
                Desktop.getDesktop().browse(new URL(URL).toURI());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Type test does not exist! Please try again.", "TypeTests", JOptionPane.WARNING_MESSAGE);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws Exception{
        TypeTestHash hash = new TypeTestHash(36); //Setting up hash table of TypeTests
        hash.buildHash("typetests.txt");
        LinkedHashSet<String> switches = new LinkedHashSet<>(hash.getAllSwitches()); //Names of unique switches in the hash table
        LinkedHashSet<String> keyboards = new LinkedHashSet<>(hash.getAllKeyboards()); //Names of unique keyboards in the hash table

        JFrame frame = new JFrame("TypeTests");
        frame.setLayout(new BorderLayout());
        JLabel welcomeMessage = new JLabel("    Welcome to TypeTests! Pick a switch and keyboard below to get started.");
        JLabel description = new JLabel("    (Type tests will open in your native browser)");
        JComboBox switchList = new JComboBox(switches.toArray()); //Dropdown menu of switches
        JComboBox keyboardList = new JComboBox(keyboards.toArray()); //Dropdown menu of keyboards
        Button button = new Button("Go!");

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                if (e.getSource() == button) {
                    try {
                        String switchChoice = (String) switchList.getSelectedItem();
                        String keyboardChoice =(String) keyboardList.getSelectedItem();
                        assert switchChoice != null;
                        openWebPage(hash.get(switchChoice), keyboardChoice);
                    } catch (NoSuchAlgorithmException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };

        button.addActionListener(actionListener);

        welcomeMessage.setPreferredSize(new Dimension(100, 50));
        frame.add(welcomeMessage, BorderLayout.PAGE_START);

        switchList.setPreferredSize(new Dimension(200, 20));
        switchList.setMaximumSize(switchList.getPreferredSize());
        frame.add(switchList, BorderLayout.LINE_START);

        keyboardList.setPreferredSize(new Dimension(200, 20));
        keyboardList.setMaximumSize(keyboardList.getPreferredSize());
        frame.add(keyboardList, BorderLayout.CENTER);

        button.setPreferredSize(new Dimension(50, 50));
        button.setMinimumSize(button.getPreferredSize());
        frame.add(button, BorderLayout.LINE_END);

        description.setPreferredSize(new Dimension(100, 50));
        frame.add(description, BorderLayout.PAGE_END);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 170);
        frame.setVisible(true);
    }
}