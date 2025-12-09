import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

public class Cwiczenie5 extends JFrame {

    private JButton startButton;
    private JProgressBar progressBar;
    private JLabel statusLabel;

    public Cwiczenie5() {
        super("Ćw. 5: Analiza Pliku (SwingWorker)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 150);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        startButton = new JButton("Rozpocznij Analizę");
        progressBar = new JProgressBar(0, 100);
        progressBar.setPreferredSize(new Dimension(400, 25));
        statusLabel = new JLabel("Status: Oczekiwanie...", SwingConstants.CENTER);
        statusLabel.setPreferredSize(new Dimension(400, 25));

        startButton.addActionListener(new AnalizaListener());

        add(startButton);
        add(progressBar);
        add(statusLabel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // 2. Wewnętrzny Listener, który inicjuje SwingWorker
    private class AnalizaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            startButton.setEnabled(false);
            statusLabel.setText("Status: Analiza w toku...");
            progressBar.setValue(0);

            // Uruchomienie SwingWorker (Wynik: String, Postęp: Integer)
            new FileAnalyzerWorker().execute();
        }
    }

    // 3. Implementacja SwingWorker
    private class FileAnalyzerWorker extends SwingWorker<String, Integer> {
        private final Random random = new Random();

        @Override
        protected String doInBackground() throws Exception {
            // Symulacja długiej operacji w wątku tła
            for (int i = 0; i <= 100; i++) {
                Thread.sleep(50); // Opóźnienie 50ms na krok

                // Generowanie błędu losowego
                if (random.nextInt(100) > 95) {
                    throw new RuntimeException("⚠️ Plik uszkodzony lub brak dostępu!");
                }

                // Wysyłanie postępu
                publish(i);
            }
            return "Analiza zakończona pomyślnie. Znaleziono 120 słów i 5 błędów formatowania.";
        }

        @Override
        protected void process(List<Integer> chunks) {
            // Odbieranie postępu w wątku EDT i aktualizacja paska
            // Bierzemy ostatnią wartość z listy
            int latestProgress = chunks.get(chunks.size() - 1);
            progressBar.setValue(latestProgress);
        }

        @Override
        protected void done() {
            // Logika wykonana w wątku EDT po zakończeniu pracy w tle
            try {
                // Próba pobrania wyniku z doInBackground()
                String wynik = get();
                statusLabel.setText("✅ " + wynik);
            } catch (InterruptedException | java.util.concurrent.ExecutionException ex) {
                // Obsługa błędu (gdy doInBackground rzuciło wyjątek)
                String errorMsg = ex.getCause() != null ? ex.getCause().getMessage() : "Nieznany błąd wykonania.";
                statusLabel.setText("❌ BŁĄD: " + errorMsg);
            } finally {
                startButton.setEnabled(true);
                progressBar.setValue(100);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Cwiczenie5());
    }
}