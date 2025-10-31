package view;

import java.util.List;

// Interface de la vue.
// Définit ce que la vue doit pouvoir faire.
public interface CalculatorGUIInterface {
    
    // Méthode appelée par le contrôleur pour mettre à jour l’affichage.
    // @param accu : valeur à afficher dans l’accumulateur
    // @param stackData : contenu de la pile à afficher
    void change(String accu, List<Double> stackData);
    
    // Lance l’interface graphique.
    void affiche();
}
