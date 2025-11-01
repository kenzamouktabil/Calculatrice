package controller;

import java.util.List;

// Interface du contrôleur.
// Définit le lien entre le modèle, le contrôleur et la vue.
public interface CalculatorControllerInterface {
    
    // Méthode appelée par le modèle quand l'accumulateur change.
    // @param accu : nouvelle valeur de l'accumulateur
    void change(String accu);
    
    // Méthode appelée par le modèle quand la pile change.
    // @param stackData : nouveau contenu de la pile
    void change(List<Double> stackData);
    void error(String message);

}