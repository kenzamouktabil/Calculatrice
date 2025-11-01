package view;

import java.util.List;

public interface CalculatorGUIInterface {
    
    // Appelée par le contrôleur pour mettre à jour l'affichage de l'accumulateur
    void change(String accu);
    
    // Appelée par le contrôleur pour mettre à jour l'affichage de la pile
    void change(List<Double> stackData);
    
    // Appelée par le contrôleur pour afficher un message d'erreur
    void showError(String message);

    // Lance l'interface graphique
    void affiche();
}