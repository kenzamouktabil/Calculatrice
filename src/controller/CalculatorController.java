package controller;

import java.util.List;
import model.CalculatorModel;
import view.CalculatorGUIInterface;

public class CalculatorController implements CalculatorControllerInterface {
    
    private CalculatorModel model;

    private CalculatorGUIInterface view;
    
    private String currentInput = "";
    
    
    // Crée le modèle et l'associe à ce contrôleur
    public CalculatorController() {
        this.model = new CalculatorModel();
        this.model.setController(this);
    }
    
    // Enregistre la vue
    public void setView(CalculatorGUIInterface view) {
        this.view = view;
        updateView();
    }
    
    // Appelée par le modèle quand l'accumulateur change
    @Override
    public void change(String accu) {
        updateView();
    }
    
    // Appelée par le modèle quand la pile change
    @Override
    public void change(List<Double> stackData) {
        updateView();
    }
    
    // Met à jour l'affichage de la vue
    private void updateView() {
        if (view != null) {
            // Si l'utilisateur saisit un nombre, on l'affiche
            // Sinon, on affiche la valeur de l'accumulateur
            if (!currentInput.isEmpty()) {
                view.change(currentInput);
            } else {
                view.change(String.valueOf(model.getAccu()));
            }
            
            // Mise à jour de la pile
            view.change(model.getMemory());
        }
    }
    
    // Gère l'appui sur un chiffre
    public void onDigitPressed(String digit) {
        currentInput += digit;
        updateView();
    }
    
    // Gère l'appui sur le point décimal
    public void onDotPressed() {
        if (currentInput.isEmpty()) {
            currentInput = "0.";
        } else if (!currentInput.contains(".")) {
            currentInput += ".";
        }
        updateView();
    }
    
    // Gère l'appui sur le bouton +/- (change le signe)
    public void onPlusMinusPressed() {
        if (!currentInput.isEmpty()) {
            // Si une saisie est en cours, on inverse son signe
            if (currentInput.startsWith("-")) {
                currentInput = currentInput.substring(1);
            } else {
                currentInput = "-" + currentInput;
            }
        } else {
            // Sinon, on inverse le signe de l'accumulateur
            model.opposite();
        }
        updateView();
    }
    
    // Gère l'appui sur le bouton PUSH
    // Empile le nombre sur la pile
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
            // PUSH directement l'accumulateur si aucune saisie
            model.push();
        }
    }
    
    // Gère l'appui sur une opération arithmétique
    public void onOperationPressed(String operation) {
        // Si un nombre est en cours de saisie, on le PUSH automatiquement
        if (!currentInput.isEmpty()) {
            try {
                double value = Double.parseDouble(currentInput);
                model.setAccu(value);
                model.push();
                currentInput = "";
            } catch (NumberFormatException e) {
                // On ignore les erreurs de saisie
            }
        }
        
        // Exécute l'opération
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
    
    // Gère l'appui sur SWAP
    public void onSwapPressed() {
        model.swap();
    }
    
    // Gère l'appui sur CLEAR
    public void onClearPressed() {
        currentInput = "";
        model.clear();
    }
    
    // Retourne le modèle (utile pour les tests)
    public CalculatorModel getModel() {
        return model;
    }
    
    // Appelée par le modèle pour afficher une erreur
    public void error(String message) {
        if (view != null) {
            view.showError(message);
        }
    }

}