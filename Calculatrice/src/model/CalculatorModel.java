package model;
import java.util.Stack;


public class CalculatorModel implements CalculatorModelInterface {
 private double accu;
 private Stack<Double> memory;
 
 public CalculatorModel() {
	 accu  = 0.0;
     memory = new Stack<>(); 
 }
 
@Override
public void add() {
	
	if(memory.size() < 2 ) {
		System.out.println("Pile Insuffisante !");
	}
	
	else {
	double n1 = memory.pop();
	double n2 = memory.pop();
	
	accu=n1+n2;
	}
}

@Override
public void substract() {

	if(memory.size() < 2 ) {
		System.out.println("Pile Insuffisante !");
	}
	
	else {
	double n1 = memory.pop();
	double n2 = memory.pop();
	
	accu=n1-n2;
	}
}

@Override
public void multiply() {


	if(memory.size() < 2 ) {
		System.out.println("Pile Insuffisante !");
	}
	
	else {
	double n1 = memory.pop();
	double n2 = memory.pop();
	
	accu=n1*n2;
	}
}

@Override
public void divide() {

	if(memory.size() < 2 ) {
		System.out.println("Pile Insuffisante !");
	}
	
	else {
	
	    double n1 = memory.pop();
	    double n2 = memory.pop();
	
	    if (n2 != 0) {
	        accu=n1/n2;
	        }
	    else {
		    System.out.println("Erreur de division par zero!");
	        }
	}
}

@Override
public void opposite() {
	
	if(memory.isEmpty() ) {
		System.out.println("Attention ! Pile Vide");
	}
	else {
		accu =-memory.pop();
	}
	
}

@Override
public void push() {

	memory.push(accu);
}
 
@Override
public void pop() {
	if(memory.isEmpty() ) {
		System.out.println("Attention ! Pile Vide");
	}
	else {
		accu = memory.pop();
	}
}

@Override
public void drop() {
	if(memory.isEmpty() ) {
		System.out.println("Attention ! Pile Vide");
	}
	else {
		memory.pop();
	}
}

@Override
public void swap() {
	if(memory.size() < 2 ) {
		System.out.println("Pile Insuffisante !");
	}
	else {
		double var1 = memory.pop();
		double var2 = memory.pop();
		memory.push(var1);
		memory.push(var2);

	}
}

@Override
public void clear() {

	accu=0.0;
}

//pour tester dans le terminal 

public void display() {
	System.out.println("Accu:"+accu);
	System.out.println("Stack:"+memory);

}
}
