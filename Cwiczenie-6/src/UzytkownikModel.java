// UzytkownikModel.java

/**
 * Model - zawiera logikę biznesową, w tym walidację logowania
 * z symulacją opóźnienia bazodanowego.
 */
public class UzytkownikModel {

    /**
     * Symuluje asynchroniczną walidację danych logowania w bazie danych.
     * UWAGA: Ta metoda jest czasochłonna (2500 ms) i musi być wywołana
     * w wątku tła (np. przez SwingWorker).
     *
     * @param user Nazwa użytkownika.
     * @param pass Hasło.
     * @return true, jeśli dane są poprawne, w przeciwnym razie false.
     */
    public boolean walidujLogowanie(String user, String pass) {
        try {
            // Symulacja opóźnienia połączenia z bazą danych (2.5 sekundy)
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }

        // Logika walidacji: poprawne dane to "admin" / "haslo123"
        return "admin".equals(user) && "haslo123".equals(pass);
    }
}