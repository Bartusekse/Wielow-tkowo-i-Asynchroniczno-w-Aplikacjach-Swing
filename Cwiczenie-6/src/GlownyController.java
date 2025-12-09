// GlownyController.java

import javax.swing.SwingWorker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller - Pośredniczy między View (GlownyView) a Modelem (UzytkownikModel).
 * Wykorzystuje SwingWorker do asynchronicznego wywoływania Modelu.
 */
public class GlownyController {

    private final UzytkownikModel model;
    private final GlownyView view;

    public GlownyController(UzytkownikModel model, GlownyView view) {
        this.model = model;
        this.view = view;

        // Podpięcie logiki do przycisku "Zaloguj"
        this.view.addLoginListener(new LoginListener());
    }

    private class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            final String login = view.getLogin();
            final String haslo = view.getPassword();

            // Uruchomienie asynchronicznego zadania
            rozpocznijWalidacje(login, haslo);
        }
    }

    private void rozpocznijWalidacje(final String login, final String haslo) {

        // 1. Aktualizacja GUI i wyłączenie przycisku (Wątek EDT)
        view.ustawPrzyciskZalogujAktywny(false);
        view.ustawStatus("Trwa weryfikacja danych...");

        // Typy generyczne: Boolean (wynik walidacji) i Void (brak aktualizacji postępu)
        new SwingWorker<Boolean, Void>() {

            @Override
            protected Boolean doInBackground() throws Exception {
                // 2. WYWOŁANIE MODELU w WĄTKU TŁA (NIE-EDT)
                return model.walidujLogowanie(login, haslo);
            }

            @Override
            protected void done() {
                // 3. POWRÓT do WĄTKU EDT (bezpieczna aktualizacja GUI)
                try {
                    boolean czyPoprawne = get(); // Pobranie wyniku z tła

                    if (czyPoprawne) {
                        view.ustawStatus("✅ Logowanie pomyślne!");
                    } else {
                        view.ustawStatus("❌ Błędny login lub hasło!");
                    }

                } catch (Exception ex) {
                    view.ustawStatus("⚠️ Błąd wykonania: " + ex.getCause().getMessage());
                } finally {
                    // 4. Zawsze włącz ponownie przycisk
                    view.ustawPrzyciskZalogujAktywny(true);
                }
            }
        }.execute(); // WAŻNE: Wystartowanie SwingWorker'a
    }
}