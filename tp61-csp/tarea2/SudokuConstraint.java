package aima.gui.sudoku.csp;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;

/**
 * Especifica que el valor de dos celdas debe ser distinto
 * 
 */
public class SudokuConstraint implements Constraint{

	private Variable var1;
	private Variable var2;
	private List<Variable> scope;
	
	public SudokuConstraint(Variable var1, Variable var2) {
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
		Object value1 = assignment.getAssignment(var1);
		return value1 == null
				|| !value1.equals(assignment.getAssignment(var2));
	}

}
