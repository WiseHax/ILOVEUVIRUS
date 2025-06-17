import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LovePrank {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                showMainDialog();
            }
        });
    }

    static void showMainDialog() {
        JFrame frame = new JFrame("⚠ Windows Warning");
        frame.setSize(400, 180);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(255, 255, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel label = new JLabel("<html><b>OOPS! WALANG EXIT BUTTON, CONTINUE KA NA LANG.</b></html>");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(label, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton continueBtn = new JButton("Continue");
        JButton exitBtn = new JButton("Exit");

        ActionListener nextStep = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                showAreYouSure();
            }
        };

        continueBtn.addActionListener(nextStep);
        exitBtn.addActionListener(nextStep); // Still continues

        buttonPanel.add(exitBtn);
        buttonPanel.add(continueBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    static void showAreYouSure() {
        JFrame frame = new JFrame("Are you sure?");
        frame.setSize(400, 160);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel label = new JLabel("<html><b>SURE KANA BA? MAGCOCONTINUE KA TALAGA?</b></html>");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(label, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton yesBtn = new JButton("Oo");
        JButton noBtn = new JButton("Hindi");

        ActionListener showFinal = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                showFinalMessage();
            }
        };

        yesBtn.addActionListener(showFinal);
        noBtn.addActionListener(showFinal); // Both lead to final screen

        buttonPanel.add(noBtn);
        buttonPanel.add(yesBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    static void showFinalMessage() {
        JFrame frame = new JFrame("❤️ Final Message");
        frame.setSize(480, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel label = new JLabel("<html><center><b>I LOVE YOU SO MUCH, CRUSH MO BA AKO?<br>CONTINUE KA KASI NANG CONTINUE EH 😏</b></center></html>");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);

        JButton okBtn = new JButton("Awww 🥹");
        okBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        okBtn.setBackground(new Color(200, 255, 200));
        okBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(okBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
