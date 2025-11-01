package model;

import java.util.Stack;
import controller.CalculatorControllerInterface;

// Modèle de la calculatrice RPN.
// Gère toute la logique métier : l'accumulateur (valeur courante) et la pile (opérandes utilisées pour les calculs).
 
public class CalculatorModel implements CalculatorModelInterface {
    
    // Accumulateur : stocke la valeur courante
    private double accu;
    
    // Pile utilisée pour mémoriser les opérandes
    private Stack<Double> memory;
    
    // Référence vers le contrôleur pour notifier les changements
    private CalculatorControllerInterface controller;
    
    // Constructeur : initialise l'accumulateur à 0 et crée une pile vide. 
    public CalculatorModel() {
        this.accu = 0.0;
        this.memory = new Stack<>();
    }
    
    // Associe le contrôleur au modèle pour pouvoir l'avertir des mises à jour. 
    public void setController(CalculatorControllerInterface controller) {
        this.controller = controller;
    }
    
    // Informe le contrôleur qu'un changement a eu lieu pour mettre à jour la vue. 
    private void notifyController() {
        if (controller != null) {
            controller.change(String.valueOf(accu));
            controller.change(memory);
        }
    }
    
    // PUSH : empile l'accumulateur sur la pile, puis le remet à zéro. 
    @Override
    public void push() {
        memory.push(accu);
        accu = 0.0;
        notifyController();
    }
    
    // POP : dépile la dernière valeur et la met dans l'accumulateur. 
    @Override
    public void pop() {
        if (!memory.isEmpty()) {
            accu = memory.pop();
            notifyController();
        }
    }
    
    // DROP : supprime simplement le dernier élément de la pile. 
    @Override
    public void drop() {
        if (!memory.isEmpty()) {
            memory.pop();
            notifyController();
        }
    }
    
    // SWAP : échange les deux derniers éléments de la pile (ex : [1,2,3] → [1,3,2]). 
    @Override
    public void swap() {
        if (memory.size() >= 2) {
            double top = memory.pop();
            double second = memory.pop();
            memory.push(top);
            memory.push(second);
            notifyController();
        }
    }
    
    // CLEAR : remet l'accumulateur à zéro. 
    @Override
    public void clear() {
        accu = 0.0;
        notifyController();
    }
    
    // ADD : additionne les deux dernières valeurs de la pile (ex : [3,4] → accu=7). 
    @Override
    public void add() {
        if (memory.size() < 2) {
        	notifyError("Pile insuffisante : il faut au moins 2 éléments.");
        } else {
            double n2 = memory.pop();
            double n1 = memory.pop();
            accu = n1 + n2;
            notifyController();
        }
    }
    
    // SUBSTRACT : soustrait les deux dernières valeurs (ex : [3,4] → accu=-1). 
    @Override
    public void substract() {
        if (memory.size() < 2) {
        	notifyError("Pile insuffisante : il faut au moins 2 éléments.");
        } else {
            double n2 = memory.pop();
            double n1 = memory.pop();
            accu = n1 - n2;
            notifyController();
        }
    }
    
    // MULTIPLY : multiplie les deux dernières valeurs (ex : [3,4] → accu=12). 
    @Override
    public void multiply() {
        if (memory.size() < 2) {
        	notifyError("Pile insuffisante : il faut au moins 2 éléments.");
        } else {
            double n2 = memory.pop();
            double n1 = memory.pop();
            accu = n1 * n2;
            notifyController();
        }
    }
    
    // DIVIDE : divise les deux dernières valeurs (ex : [8,2] → accu=4). 
    @Override
    public void divide() {
        if (memory.size() < 2) {
        	notifyError("Pile insuffisante : il faut au moins 2 éléments.");
        } else {
            double n2 = memory.pop(); // Diviseur
            double n1 = memory.pop(); // Dividende
            
            if (n2 != 0) {
                accu = n1 / n2;
                notifyController();
            } else {
                notifyError("Erreur : division par zéro !");
                memory.push(n1);
                memory.push(n2);
            }
        }
    }
    
    // OPPOSITE : inverse le signe de la valeur courante (ex : 5 → -5). 
    @Override
    public void opposite() {
        accu = -accu;
        notifyController();
    }
    
    // Met à jour la valeur de l'accumulateur (utilisée lors de la saisie). 
    public void setAccu(double value) {
        this.accu = value;
        notifyController();
    }
    
    // Accesseurs (utiles pour les tests). 
    public double getAccu() {
        return accu;
    }
    
    public Stack<Double> getMemory() {
        return memory;
    }
    
    private void notifyError(String msg) {
        if (controller != null) controller.error(msg);
    }

}