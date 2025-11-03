package controller;

import java.util.List;
import model.CalculatorModel;
import view.CalculatorGUIInterface;

public class CalculatorController implements CalculatorControllerInterface {
    
    private CalculatorModel model;

    private CalculatorGUIInterface view;
    
    private String currentInput = "";
    

    public CalculatorController() {
        this.model = new CalculatorModel();
        this.model.setController(this);
    }
    
    public void setView(CalculatorGUIInterface view) {
        this.view = view;
        updateView();
    }
    

    @Override
    public void change(String accu) {
        updateView();
    }
    

    @Override
    public void change(List<Double> stackData) {
        updateView();
    }
    

    private void updateView() {
        if (view != null) {
            // Si user saisit un nombre on l'affiche sinon on affiche la valeur de l'accu
            if (!currentInput.isEmpty()) {
                view.change(currentInput);
            } else {
                view.change(String.valueOf(model.getAccu()));
            }
            
            // Mise à jour de la pile
            view.change(model.getMemory());
        }
    }
    
    // Appui sur Digit
    public void onDigitPressed(String digit) {
        currentInput += digit;
        updateView();
    }
    
    // Appui sur "."
    public void onDotPressed() {
        if (currentInput.isEmpty()) {
            currentInput = "0.";
        } else if (!currentInput.contains(".")) {
            currentInput += ".";
        }
        updateView();
    }
    
    // Appui sur signe
    public void onPlusMinusPressed() {
        if (!currentInput.isEmpty()) {
            if (currentInput.startsWith("-")) {
                currentInput = currentInput.substring(1);
            } else {
                currentInput = "-" + currentInput;
            }
        } else {
            model.opposite();
        }
        updateView();
    }
    
    // Appui sur PUSH
    public void onPushPressed() {
        if (!currentInput.isEmpty()) {
            try {
                double value = Double.parseDouble(currentInput);
                model.setAccu(value);
                currentInput = "";
                model.push();
            } catch (NumberFormatException e) {
                currentInput = "";
            }
        } else {
            model.push();
        }
    }
    
    // Appui sur Op arith
    public void onOperationPressed(String operation) {
        if (!currentInput.isEmpty()) {
            try {
                double value = Double.parseDouble(currentInput);
                model.setAccu(value);
                model.push();
                currentInput = "";
            } catch (NumberFormatException e) {
            }
        }
        
        switch (operation) {
            case "+":
                model.add();
                break;
            case "-":
                model.substract();
                break;
            case "*":
                model.multiply();
                break;
            case "/":
                model.divide();
                break;
        }
    }
    
    // Appui sur SWAP
    public void onSwapPressed() {
        model.swap();
    }
    
    // Appui sur CLEAR
    public void onClearPressed() {
        currentInput = "";
        model.clear();
    }
    
    // Retourne le modèle (utile pour les tests)
    public CalculatorModel getModel() {
        return model;
    }
    
    // pour affichage des erreurs
    public void error(String message) {
        if (view != null) {
            view.showError(message);
        }
    }

}