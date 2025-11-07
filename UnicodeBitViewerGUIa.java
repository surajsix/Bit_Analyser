import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class UnicodeBitViewerGUIa extends JFrame {
    private JTextField inputField;
    private JTextArea outputArea;
    private JButton analyzeButton, exitButton;
    public UnicodeBitViewerGUIa() {
        setTitle("Unicode Bit Viewer");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(new JLabel("Enter characters or emoji:"), BorderLayout.WEST);
        inputField = new JTextField();
        inputPanel.add(inputField, BorderLayout.CENTER);
        JPanel buttonsPanel = new JPanel();
        analyzeButton = new JButton("Analyze");
        JButton clearButton = new JButton("Clear");
        exitButton = new JButton("Exit");
        buttonsPanel.add(analyzeButton);
        buttonsPanel.add(clearButton);
        buttonsPanel.add(exitButton);
        outputArea = new JTextArea();
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
        analyzeButton.addActionListener(e -> analyzeInput());
        clearButton.addActionListener(e -> outputArea.setText(""));
        exitButton.addActionListener(e -> System.exit(0));
    }
    private void analyzeInput() {
        String input = inputField.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter some characters!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("--- Bit Code Information ---\n\n");

        int i = 0;
        while (i < input.length()) {
            int codePoint = input.codePointAt(i);
            String binary = Integer.toBinaryString(codePoint);
            String hex = String.format("%04X", codePoint);
            String utf8Bytes = getUTF8Bytes(codePoint);

            sb.append("Character: ").append(new String(Character.toChars(codePoint))).append("\n");
            sb.append("Unicode Code Point: U+").append(hex).append("\n");
            sb.append("Decimal: ").append(codePoint).append("\n");
            sb.append("Binary: ").append(binary).append("\n");
            sb.append("32-bit padded: ").append(String.format("%32s", binary).replace(' ', '0')).append("\n");
            sb.append("UTF-8 bytes: ").append(utf8Bytes).append("\n\n");

            i += Character.charCount(codePoint);
        }

        outputArea.setText(sb.toString());
    }

    private String getUTF8Bytes(int codePoint) {
        try {
            byte[] bytes = new String(Character.toChars(codePoint)).getBytes("UTF-8");
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02X ", b & 0xFF));
            }
            return sb.toString().trim();
        } catch (Exception e) {
            return "Error encoding UTF-8";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new UnicodeBitViewerGUIa().setVisible(true);
        });
    }
}
