package view;

import java.util.List;

// Interface de la vue.
// Définit ce que la vue doit pouvoir faire.
// Conforme au cahier des charges : deux méthodes change distinctes
public interface CalculatorGUIInterface {
    
    // Méthode appelée par le contrôleur pour mettre à jour l'affichage de l'accumulateur.
    // @param accu : valeur à afficher dans l'accumulateur
    void change(String accu);
    
    // Méthode appelée par le contrôleur pour mettre à jour l'affichage de la pile.
    // @param stackData : contenu de la pile à afficher
    void change(List<Double> stackData);
    
    void showError(String message);

    // Lance l'interface graphique.
    void affiche();
}