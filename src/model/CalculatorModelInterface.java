package model;

public interface CalculatorModelInterface {
    
    // Opérations arithmétiques de base
    void add();
    void substract();
    void multiply();
    void divide();
    void opposite();
    
    // Opérations sur la pile
    void push();      // Empile l'accumulateur
    void pop();       // Dépile vers l'accumulateur
    void drop();      // Supprime le sommet
    void swap();      // Échange les 2 derniers éléments
    void clear();     // Vide l'accumulateur
}