package controller;

import java.util.List;

public interface CalculatorControllerInterface {
    
    void change(String accu);
    
    void change(List<Double> stackData);
    
    void error(String message);

}