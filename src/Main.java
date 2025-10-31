import view.CalculatorGUI;

// Classe principale.
// Point d’entrée de l’application.

/* 
Déroulement au lancement :
1. Main lance CalculatorGUI
2. CalculatorGUI.start() crée les composants graphiques
3. CalculatorGUI.start() crée le contrôleur
4. Le contrôleur crée le modèle
5. Les liens MVC sont établis :
   - Vue → Contrôleur (setView)
   - Modèle → Contrôleur (setController)
*/
public class Main {
    
    public static void main(String[] args) {
        // Crée et lance la vue JavaFX.
        // La vue crée le contrôleur, qui crée le modèle.
        // Le pattern MVC se met alors en place automatiquement.
        CalculatorGUI gui = new CalculatorGUI();
        gui.affiche();
    }
}
