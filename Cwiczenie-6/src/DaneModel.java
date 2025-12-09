// DaneModel.java

/**
 * Model - symuluje wczytywanie danych po udanym logowaniu.
 */
public class DaneModel {
    /**
     * Symuluje asynchroniczne pobieranie dużej ilości danych użytkownika.
     */
    public String wczytajDaneUzytkownika(String user) {
        try {
            System.out.println("Controller: Rozpoczynam wczytywanie danych dla: " + user);
            Thread.sleep(3000); // 3 sekundy opóźnienia na wczytywanie
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Błąd wczytywania danych.";
        }
        return "Lista 1000 rekordów dla " + user;
    }
}