package aima.gui.sudoku.csp;

import aima.core.search.csp.Variable;

/**
 * Añade el valor de la celda, y las coordenadas x e y, 
 * 	además del nombre de la variable.
 * 
 * Una variable tiene un nombre de tipo String. Si mis
 * 	variables tienen más atributos, tendré que heredar de
 * 	esta clase y añadir los campos necesarios en la nueva
 * 	clase.
 * 
 * Las variables tienen dominios que definen los valores
 * 	que pueden tomar. La definición de dominios se realiza
 * 	implementando la clase Domain, que es una subclase de
 * 	la clase Iterable.
 * 
 */
public class SudokuVariable extends Variable{
	private int value;
	private int x;
	private int y;
	
	public SudokuVariable(int value, int x, int y, String name) {
		super(name);
		this.value = value;
		this.x = x;
		this.y = y;
	}

	public SudokuVariable(String name, int x, int y) {
		super(name);
		this.x = x;
		this.y = y;	
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
