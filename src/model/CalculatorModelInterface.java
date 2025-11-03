package model;

public interface CalculatorModelInterface {
    
    // Op arith
    void add();
    void substract();
    void multiply();
    void divide();
    void opposite();
    
    // Op sur la pile
    void push();     
    void pop();       
    void drop();    
    void swap();     
    void clear();    
}