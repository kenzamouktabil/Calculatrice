package controller;

import java.util.List;
import model.CalculatorModel;
import view.CalculatorGUIInterface;

// Contrôleur de la calculatrice RPN.
// Fait le lien entre le modèle et la vue : reçoit les actions de l'utilisateur, met à jour le modèle, puis notifie la vue.
public class CalculatorController implements CalculatorControllerInterface {
    
    // Référence vers le modèle (logique métier)
    private CalculatorModel model;
    
    // Référence vers la vue (interface graphique)
    private CalculatorGUIInterface view;
    
    // Stocke la saisie numérique en cours
    private String currentInput = "";
    
    // Constructeur : crée le modèle et l'associe à ce contrôleur
    public CalculatorController() {
        this.model = new CalculatorModel();
        // Enregistre ce contrôleur dans le modèle pour recevoir les notifications via change()
        this.model.setController(this);
    }
    
    // Enregistre la vue (appelé après sa création)
    public void setView(CalculatorGUIInterface view) {
        this.view = view;
        // Initialise l'affichage
        updateView();
    }
    
    // Callback : appelée par le modèle quand l'accumulateur change
    @Override
    public void change(String accu) {
        updateView();
    }
    
    // Callback : appelée par le modèle quand la pile change
    @Override
    public void change(List<Double> stackData) {
        updateView();
    }
    
    // Met à jour l'affichage de la vue avec les données du modèle
    private void updateView() {
        if (view != null) {
            // Si l'utilisateur saisit un nombre, on l'affiche directement
            // Sinon, on affiche la valeur de l'accumulateur
            if (!currentInput.isEmpty()) {
                view.change(currentInput, model.getMemory());
            } else {
                view.change(String.valueOf(model.getAccu()), model.getMemory());
            }
        }
    }
    
    // Gère l'appui sur un chiffre (0–9)
    // Appelé par la vue quand l'utilisateur clique sur un bouton numérique
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
    
    // Gère l'appui sur le bouton +/- (change le signe du nombre)
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
    
    // Gère l'appui sur le bouton PUSH (Entrée)
    // Valide la saisie et empile le nombre sur la pile
    // En RPN, on doit toujours pousser avant de faire une opération
    // Si aucune saisie en cours, on PUSH directement l'accumulateur (résultat d'une opération)
    public void onPushPressed() {
        if (!currentInput.isEmpty()) {
            // Si on saisit un nombre, on le met dans l'accu et on PUSH
            try {
                double value = Double.parseDouble(currentInput);
                model.setAccu(value);
                currentInput = ""; // Efface la saisie AVANT de pusher
                model.push(); // Empile la valeur
            } catch (NumberFormatException e) {
                // Si la saisie n'est pas un nombre valide, on la réinitialise
                currentInput = "";
            }
        } else {
            // Si aucune saisie, on PUSH directement l'accumulateur (résultat d'une opération)
            model.push();
        }
    }
    
    // Gère l'appui sur une opération arithmétique (+, -, *, /)
    // En RPN, il faut d'abord PUSHER la saisie avant de calculer
    public void onOperationPressed(String operation) {
        // Si un nombre est en cours de saisie, on le PUSH automatiquement
        if (!currentInput.isEmpty()) {
            try {
                double value = Double.parseDouble(currentInput);
                model.setAccu(value);
                model.push(); // PUSH automatique avant l'opération
                currentInput = "";
            } catch (NumberFormatException e) {
                // On ignore les erreurs de saisie
            }
        }
        
        // Exécute l'opération selon le symbole
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
    
    // Gère l'appui sur SWAP (échange les deux derniers éléments de la pile)
    public void onSwapPressed() {
        model.swap();
    }
    
    // Gère l'appui sur CLEAR (efface la saisie et l'accumulateur)
    public void onClearPressed() {
        currentInput = "";
        model.clear();
    }
    
    // Retourne le modèle (utile pour les tests)
    public CalculatorModel getModel() {
        return model;
    }
}