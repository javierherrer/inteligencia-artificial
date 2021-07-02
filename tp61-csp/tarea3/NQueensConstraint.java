package aima.gui.nqueens.csp;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;

public class NQueensConstraint implements Constraint{

	private NQueensVariable var1;
	private NQueensVariable var2;
	private List<Variable> scope;
	
	public NQueensConstraint(NQueensVariable var1, NQueensVariable var2) {
		this.var1 = var1;
		this.var2 = var2;
		scope = new ArrayList<Variable>(2);
		scope.add(var1);
		scope.add(var2);
	}
	
	/**
	 * Devuelve el conjunto de variables el ámbito/scope 
	 * 	de la restricción.
	 * 
	 */
	@Override
	public List<Variable> getScope() {
		return scope;
	}

	/**
	 * Devuelve cierto si la restricción está satisfecha 
	 * 	por la asignación de valor dada.
	 * 
	 * Una asignación se representa mediante la clase 
	 * 	Assignment que asigna valores a algunas o todas 
	 * 	las variables del problema CSP. La clase permite
	 * 	comprobar si los valores asignados a las variables
	 * 	son consistentes con algunas restricciones, si es
	 * 	completa (todas las variables asignadas), o si es 
	 * 	solución del problema.
	 * 
	 */
	@Override
	public boolean isSatisfiedWith(Assignment assignment) {
		int value1 = (int) assignment.getAssignment(var1);
		int value2 = (int) assignment.getAssignment(var2);
		
		boolean constraint1 = !(value1 == value2);
		boolean constraint2 = Math.abs(value1 - value2) !=
				Math.abs(var1.getColumn() - var2.getColumn());
				
		return constraint1 && constraint2;
	}

}
