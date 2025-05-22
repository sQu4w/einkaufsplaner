# MealMatch – Einkaufsplaner

MealMatch ist eine JavaFX-Desktopanwendung zur Verwaltung von Einkaufslisten und Rezepten.  

## Funktionen

- Artikel zur Einkaufsliste hinzufügen, bearbeiten und löschen
- Rezepte anlegen mit Zutaten, Mengen und Einheiten
- Zutaten eines Rezepts in die Einkaufsliste übernehmen (mit Mengenaddition)
- Mengenumrechnung je nach Portionenzahl
- Speicherung als JSON/CSV

## Technologien

- Java 17  
- JavaFX  
- MVC-Struktur  
- JSON/CSV 
- SceneBuilder

## Projekt starten

1. Projekt in einer IDE (z. B. IntelliJ IDEA) öffnen  
2. JavaFX SDK installieren und im Projekt als Bibliothek einbinden  
   (Anleitung: https://openjfx.io/openjfx-docs/#IDE-Intellij)  
3. Die Klasse `MainApp.java` ausführen, um die Anwendung zu starten
 

## Projektstruktur (Auszug)

src/
├── controller/         → JavaFX-Controller für die Benutzeroberfläche
├── model/              → Datenmodelle wie Recipe, Ingredient, Article
├── service/            → Verarbeitung & Speicherung (z. B. JSON)
├── helper/             → Validierung und Dialoge
├── App.java            → Startpunkt der Anwendung
resources/
├── *.fxml              → GUI-Layouts (SceneBuilder)
├── grocery.png         → Logo/Icon
data/
├── recipes.json        → Gespeicherte Rezepte
├── shopping_list.csv   → Einkaufsliste
