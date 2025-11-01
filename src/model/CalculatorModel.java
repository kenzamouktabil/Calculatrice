package model;

import java.util.Stack;
import controller.CalculatorControllerInterface;
 
public class CalculatorModel implements CalculatorModelInterface {
    
    // Accumulateur : stocke la valeur courante
    private double accu;
    
    // Pile pour mémoriser les opérandes
    private Stack<Double> memory;
    
    // Référence vers le contrôleur pour les notifications
    private CalculatorControllerInterface controller;
    
    // Initialise l'accumulateur à 0 et crée une pile vide
    public CalculatorModel() {
        this.accu = 0.0;
        this.memory = new Stack<>();
    }
    
    // Associe le contrôleur au modèle
    public void setController(CalculatorControllerInterface controller) {
        this.controller = controller;
    }
    
    // Informe le contrôleur qu'un changement a eu lieu
    private void notifyController() {
        if (controller != null) {
            controller.change(String.valueOf(accu));
            controller.change(memory);
        }
    }
    
    // Empile l'accumulateur sur la pile, puis le remet à zéro
    @Override
    public void push() {
        memory.push(accu);
        accu = 0.0;
        notifyController();
    }
    
    // Dépile la dernière valeur et la met dans l'accumulateur
    @Override
    public void pop() {
        if (!memory.isEmpty()) {
            accu = memory.pop();
            notifyController();
        }
    }
    
    // Supprime le dernier élément de la pile
    @Override
    public void drop() {
        if (!memory.isEmpty()) {
            memory.pop();
            notifyController();
        }
    }
    
    // Échange les deux derniers éléments de la pile
    @Override
    public void swap() {
        if (memory.size() >= 2) {
            double top = memory.pop();
            double second = memory.pop();
            memory.push(top);
            memory.push(second);
            notifyController();
        }
        else {
        	notifyError("Pile insuffisante : il faut au moins 2 éléments.");
        }
    }
    
    // Remet l'accumulateur à zéro
    @Override
    public void clear() {
        accu = 0.0;
        notifyController();
    }
    
    // Additionne les deux dernières valeurs de la pile
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
    
    // Soustrait les deux dernières valeurs de la pile
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
    
    // Multiplie les deux dernières valeurs de la pile
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
    
    // Divise les deux dernières valeurs de la pile
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
    
    // Inverse le signe de la valeur courante
    @Override
    public void opposite() {
        accu = -accu;
        notifyController();
    }
    
    // Met à jour la valeur de l'accumulateur
    public void setAccu(double value) {
        this.accu = value;
        notifyController();
    }
    
    // Accesseurs pour les tests
    public double getAccu() {
        return accu;
    }
    
    public Stack<Double> getMemory() {
        return memory;
    }
    
    // Envoie un message d'erreur au contrôleur
    private void notifyError(String msg) {
        if (controller != null) controller.error(msg);
    }

}