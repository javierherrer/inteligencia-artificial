package aima.gui.nqueens.csp;

import java.util.ArrayList;
import java.util.List;
import aima.core.search.csp.CSP;
import aima.core.search.csp.Domain;
import aima.core.search.csp.Variable;  


/**
 * Define el problema.
 * 
 * Para definir un problema CSP debemos heredar de la 
 * 	clase CSP, en la que se definen las variables, sus 
 * 	dominios y las restricciones.
 * 
 */
public class NQueensProblem extends CSP {
     private static final int _queens = 8;
     private static List<Variable> variables = null;

     /**
      *
      * @return Devuelve la lista de variables del NQueens.
      *         Nombre Reina en columna [i], y columna i
      */
     private static List<Variable> collectVariables() {
          variables = new ArrayList<Variable>();
          for (int i = 1; i <= _queens; i++) {
        	  variables.add(new NQueensVariable("Q" + i, i));
          }
          return variables;
     }
     /**
      *
      * @param var variable del NQueens
      * @return Dominio de la variable,
      *         si tiene valor el dominio es el valor. Sino el domino 1-N
      */
     private static List<Integer> getNQueensDomain(NQueensVariable var) {
          List<Integer> list = new ArrayList<Integer>();

           for (int i = 1; i <= _queens; i++)
                list.add(new Integer(i));
           
          return list;
     }

     /**
      * Define como un CSP. Define variables, sus dominios y restricciones.
      * @param pack
      */
     public NQueensProblem() {
    	     //variables
          super(collectVariables());
          for (int i=0;i<_queens;i++) {
               NQueensVariable x = (NQueensVariable) variables.get(i);
          }
          //Define dominios de variables
          Domain domain;
          for (Variable var : getVariables()) {
               domain = new Domain(getNQueensDomain((NQueensVariable) var));
               setDomain(var, domain);
          }
          //restricciones
          doConstraint();
     }
     
     private void doConstraint() {
    	 for (int i = 0; i < _queens; i++) {
    		 for (int j = i+1; j < _queens; j++) {
    				 addConstraint(new NQueensConstraint(
    						 (NQueensVariable)variables.get(i), 
    						 (NQueensVariable) variables.get(j)));
    			 
    		 }
    	 }
     }
}

