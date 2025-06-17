import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class AutoTyperTool extends JFrame implements ActionListener {
    private JTextArea messageArea;
    private JTextField intervalField, repeatField;
    private JButton startButton, stopButton;
    private JComboBox<String> appSelector;
    private JCheckBox enterCheckbox;
    private Timer timer;
    private String[] messages;
    private int currentRepeat = 0;
    private int maxRepeat = 1;
    private int messageIndex = 0;
    private int intervalMillis = 1000;

    public AutoTyperTool() {
        setTitle(" WISE.COM [DOXBIN EDITION]");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Use custom panel with background image
        setContentPane(new BackgroundPanel("doxbin.jpg"));
        setLayout(new BorderLayout(20, 20));

        Font monospace = new Font("Consolas", Font.PLAIN, 14);
        Color red = new Color(255, 50, 50);
        Color darkRed = new Color(100, 0, 0);

        // TOP
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        JLabel topLabel = new JLabel("DOX TOOL BY WISEHAX:");
        topLabel.setForeground(red);
        topLabel.setFont(monospace);
        topPanel.add(topLabel, BorderLayout.NORTH);
        messageArea = new JTextArea(7, 40);
        messageArea.setBackground(Color.BLACK);
        messageArea.setForeground(red);
        messageArea.setCaretColor(red);
        messageArea.setFont(monospace);
        JScrollPane scroll = new JScrollPane(messageArea);
        scroll.setBorder(BorderFactory.createLineBorder(darkRed));
        topPanel.add(scroll, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // CENTER
        JPanel centerPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        centerPanel.add(makeLabel("USER ID:", monospace, red));
        intervalField = makeTextField("1000", monospace, red);
        centerPanel.add(intervalField);

        centerPanel.add(makeLabel("IP ADDRESS:", monospace, red));
        repeatField = makeTextField("1", monospace, red);
        centerPanel.add(repeatField);

        centerPanel.add(makeLabel("Select App:", monospace, red));
        appSelector = new JComboBox<>(new String[]{"Discord", "Notepad", "Facebook", "WhatsApp", "Other"});
        appSelector.setBackground(Color.BLACK);
        appSelector.setForeground(red);
        appSelector.setFont(monospace);
        centerPanel.add(appSelector);

        centerPanel.add(makeLabel("Auto Dox:", monospace, red));
        enterCheckbox = new JCheckBox("Enable", true);
        enterCheckbox.setBackground(Color.BLACK);
        enterCheckbox.setForeground(red);
        enterCheckbox.setFont(monospace);
        centerPanel.add(enterCheckbox);

        add(centerPanel, BorderLayout.CENTER);

        // BOTTOM
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        startButton = makeButton("START", monospace, red);
        stopButton = makeButton("STOP", monospace, red);
        stopButton.setEnabled(false);
        startButton.addActionListener(this);
        stopButton.addActionListener(this);
        bottomPanel.add(startButton);
        bottomPanel.add(stopButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JLabel makeLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(font);
        return label;
    }

    private JTextField makeTextField(String text, Font font, Color color) {
        JTextField field = new JTextField(text);
        field.setBackground(Color.BLACK);
        field.setForeground(color);
        field.setCaretColor(color);
        field.setFont(font);
        return field;
    }

    private JButton makeButton(String text, Font font, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(Color.BLACK);
        btn.setForeground(color);
        btn.setFocusPainted(false);
        btn.setFont(font);
        btn.setBorder(BorderFactory.createLineBorder(color));
        return btn;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            String allMessages = messageArea.getText().trim();
            if (allMessages.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Type at least one message.");
                return;
            }

            try {
                intervalMillis = Integer.parseInt(intervalField.getText().trim());
                maxRepeat = Integer.parseInt(repeatField.getText().trim());

                if (intervalMillis < 1 || maxRepeat < 1) throw new NumberFormatException();

                messages = allMessages.split("\\R");
                messageIndex = 0;
                currentRepeat = 0;

                timer = new Timer();
                String selectedApp = appSelector.getSelectedItem().toString();
                JOptionPane.showMessageDialog(this,
                        "Switch to " + selectedApp + " in 5 seconds.\nAuto typing will start.",
                        "Doxbin Tool Alert", JOptionPane.INFORMATION_MESSAGE);

                timer.schedule(new TimerTask() {
                    public void run() {
                        startTyping();
                    }
                }, 5000);

                startButton.setEnabled(false);
                stopButton.setEnabled(true);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter valid numbers.");
            }

        } else if (e.getSource() == stopButton) {
            if (timer != null) timer.cancel();
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        }
    }

    private void startTyping() {
        try {
            Robot robot = new Robot();
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    if (currentRepeat >= maxRepeat) {
                        timer.cancel();
                        SwingUtilities.invokeLater(() -> {
                            startButton.setEnabled(true);
                            stopButton.setEnabled(false);
                        });
                        return;
                    }

                    if (messageIndex >= messages.length) {
                        messageIndex = 0;
                        currentRepeat++;
                        return;
                    }

                    String msg = messages[messageIndex++];
                    for (char c : msg.toCharArray()) {
                        typeChar(robot, c);
                    }

                    if (enterCheckbox.isSelected()) {
                        robot.keyPress(KeyEvent.VK_ENTER);
                        robot.keyRelease(KeyEvent.VK_ENTER);
                    }
                }
            }, 0, intervalMillis);
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
    }

    private void typeChar(Robot robot, char c) {
        try {
            boolean upper = Character.isUpperCase(c);
            int code = KeyEvent.getExtendedKeyCodeForChar(c);
            if (KeyEvent.CHAR_UNDEFINED == code) return;

            if (upper) robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(code);
            robot.keyRelease(code);
            if (upper) robot.keyRelease(KeyEvent.VK_SHIFT);
        } catch (IllegalArgumentException ignored) {
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AutoTyperTool().setVisible(true));
    }
}

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            backgroundImage = new ImageIcon(imagePath).getImage();
        } catch (Exception e) {
            System.err.println("Image not found: " + imagePath);
        }
        setLayout(new BorderLayout(10, 20));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null)
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
