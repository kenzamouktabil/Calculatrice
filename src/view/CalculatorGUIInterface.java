package view;

import java.util.List;

public interface CalculatorGUIInterface {
    
    void change(String accu);
    
    void change(List<Double> stackData);
    
    void showError(String message);

    void affiche();
}