package controller;

import java.util.List;

public interface CalculatorControllerInterface {
    
    // Appelée par le modèle quand l'accumulateur change
    void change(String accu);
    
    // Appelée par le modèle quand la pile change
    void change(List<Double> stackData);
    
    // Appelée par le modèle en cas d'erreur
    void error(String message);

}