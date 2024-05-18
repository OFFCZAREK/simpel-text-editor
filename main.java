import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {
    public Main() {
        initComponents();
    }

    public void initComponents() {
        // Setting the layout for the search panel to FlowLayout
        searchPanel.setLayout(new FlowLayout());

        // Adding components to the search panel
        searchPanel.add(find);
        searchPanel.add(searchTextField);
        searchPanel.add(and);
        searchPanel.add(change);
        searchPanel.add(to);
        searchPanel.add(changeTextField);

        // Adding action listeners to the buttons
        change.addActionListener(new changeHandler());
        find.addActionListener(new findHandler());

        // Setting the title of the window
        this.setTitle("Text Scanner");

        // Setting the layout for the main content pane to BorderLayout
        this.getContentPane().setLayout(new BorderLayout());
        // Adding the scroll pane containing the text area to the center
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
        // Adding the search panel to the bottom
        this.getContentPane().add(searchPanel, BorderLayout.SOUTH);

        // Setting the default close operation to exit the application
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setting the minimum size of the window
        this.setMinimumSize(new Dimension(400, 150));
        // Adjusting the size of the window to fit the preferred sizes of its components
        this.pack();
        // Centering the window on the screen
        this.setLocationRelativeTo(null);
    }

    // ActionListener for the find button
    private class findHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Finding the next occurrence of the search text
            beginningOfFinder = textArea.getText().indexOf(searchTextField.getText(), beginningOfFinder + searchTextField.getText().length());

            // If not found, start from the beginning
            if (beginningOfFinder == -1)
                beginningOfFinder = textArea.getText().indexOf(find.getText());

            System.out.println(beginningOfFinder);
            // Highlighting the found text
            if (beginningOfFinder >= 0 && searchTextField.getText().length() > 0) {
                textArea.requestFocus();
                textArea.select(beginningOfFinder, beginningOfFinder + searchTextField.getText().length());
            }
        }

        private int beginningOfFinder = 0;
    }

    // ActionListener for the change button
    private class changeHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // If text is selected, change it
            if (textArea.getSelectionStart() != textArea.getSelectionEnd()) {
                changeText();
            } else {
                // If no text is selected, find the text first and then change it
                find.doClick();
                if (textArea.getSelectionStart() != textArea.getSelectionEnd())
                    changeText();
            }
        }

        private void changeText() {
            textArea.requestFocus();
            // Asking for confirmation to change the text
            int option = JOptionPane.showConfirmDialog(null, "Do you want to change \"" + searchTextField.getText() + "\" to \"" + changeTextField.getText() + "\"?", "Attention, change will be done", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION)
                textArea.replaceRange(changeTextField.getText(), textArea.getSelectionStart(), textArea.getSelectionEnd());
        }
    }

    // Declaration of GUI components
    private JTextArea textArea = new JTextArea(7, 10);
    private JScrollPane scrollPane = new JScrollPane(textArea);
    private JTextField searchTextField = new JTextField(10);
    private JTextField changeTextField = new JTextField(10);
    private JLabel to = new JLabel(" to");
    private JLabel and = new JLabel("and");
    private JPanel searchPanel = new JPanel();
    private JButton find = new JButton("Find");
    private JButton change = new JButton("Change");

    public static void main(String[] args) {
        // Creating and displaying the main window
        new Main().setVisible(true);
    }
}
