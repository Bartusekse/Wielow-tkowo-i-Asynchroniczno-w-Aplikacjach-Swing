// GlownyView.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * View - Odpowiada za wygląd interfejsu użytkownika (formularz logowania).
 */
public class GlownyView extends JFrame {

    // Komponenty interfejsu
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;

    public GlownyView() {
        super("System Logowania Asynchronicznego");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new BorderLayout(10, 10));

        // --- Panel Formularza ---
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        loginField = new JTextField(15);
        passwordField = new JPasswordField(15);

        formPanel.add(new JLabel("Nazwa użytkownika:"));
        formPanel.add(loginField);
        formPanel.add(new JLabel("Hasło:"));
        formPanel.add(passwordField);

        // --- Komponenty Statusu/Przycisku ---
        JPanel southPanel = new JPanel(new BorderLayout());
        loginButton = new JButton("Zaloguj");
        statusLabel = new JLabel("Gotowy do logowania.", SwingConstants.CENTER);

        southPanel.add(loginButton, BorderLayout.NORTH);
        southPanel.add(statusLabel, BorderLayout.SOUTH);

        // Dodanie paneli do głównego okna
        add(formPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null); // Centrowanie okna
    }

    // --- Metody publiczne do komunikacji z Controllerem ---

    public String getLogin() {
        return loginField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void ustawStatus(String status) {
        statusLabel.setText(status);
    }

    public void ustawPrzyciskZalogujAktywny(boolean aktywne) {
        loginButton.setEnabled(aktywne);
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }
}