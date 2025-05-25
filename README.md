# Einkaufsplaner

Einkaufsplaner ist eine Desktopanwendung zur Verwaltung von Einkaufslisten und Rezepten.  

## Funktionen

- Artikel zur Einkaufsliste hinzufügen, bearbeiten und löschen
- Rezepte anlegen mit Zutaten, Mengen und Einheiten
- Zutaten eines Rezepts in die Einkaufsliste übernehmen (mit Mengenaddition)
- Mengenumrechnung je nach Portionenzahl
- Speicherung als JSON/CSV

## Technologien

- Java 17 LTS
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
``````
src/
├── controller/         → JavaFX-Controller für Benutzeroberfläche
├── model/              → Datenmodelle
├── service/            → Verarbeitung/Speicherung
├── helper/             → Validierung und Dialoge
├── App.java            → Startpunkt der Anwendung
resources/
├── *.fxml              → GUI-Layouts
├── grocery.png         → Logo/Icon
data/
├── recipes.json        → Rezepte
├── shopping_list.csv   → Einkaufsliste
``````
