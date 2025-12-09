// AplikacjaLogowania.java

import javax.swing.SwingUtilities;

public class AplikacjaLogowania {

    public static void main(String[] args) {
        // ZASADA NR 1: Uruchomienie aplikacji Swing w wątku EDT
        SwingUtilities.invokeLater(() -> {
            // 1. Inicjalizacja Modelu i Widoku
            UzytkownikModel model = new UzytkownikModel();
            GlownyView view = new GlownyView();

            // 2. Inicjalizacja Controllera (połączenie Modelu i Widoku)
            GlownyController controller = new GlownyController(model, view);

            // 3. Wyświetlenie okna
            view.setVisible(true);
        });
    }
}