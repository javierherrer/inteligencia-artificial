# Trabajo TP6-1: Lenguaje de Reglas y Propagación de restricciones
El trabajo consta de tres tareas. La **primera tarea** tiene por objeto familiarizarse con en el
desarrollo de programas en CLIPS tanto escribiendo código como depurando programas. Para
ello, deberás escribir programas CLIPS que resuelvan los problemas planteados con los
algoritmos que se piden. Utilizaremos el modulo de control para implementar los distintos
algoritmos (A*, CU). Para una adecuada separación del módulo de control (MAIN) del resto de
módulos puedes consultar el ejemplo del 8-puzzle de la lección de control en sistemas de
producción.
La **segunda tarea** consistirá en la resolución de **sudokus** mediante la **propagación de
restricciones**. No se ha explicado en teoría este tema, por lo que este trabajo servirá para
familiarizarte con los problemas de satisfacción de restricciones. En los **problemas de
satisfacción de restricciones** se representa el estado mediante un conjunto de variables que
pueden tener valores en dominios definidos, y se definen un conjunto de restricciones
especificando combinaciones de valores para subconjuntos de variables. El objetivo es
entender de forma intuitiva como la propagación de restricciones puede reducir el espacio de
estados en los que realizar las búsquedas. Los algoritmos CSP (Constraint Satisfaction
Problem, CSP) entrelazan búsqueda (**backtracking**), algoritmos de propagación de
restricciones considerando diferente número de variables, como las restricciones sobre los
valores de dos variables (**AC-3**, _Arc consistency version 3_), y **heurísticas de propósito
general** como elegir primero las variables con menor número de valores posibles. Para un
conocimiento detallado de cómo resolver problemas mediante la satisfacción de restricciones
debes leer el capítulo 5 del libro “Artificial Intelligence” de Stuart Russell y Peter Norvig
(http://aima.cs.berkeley.edu/newchap05.pdf) para tener una idea general. Para implementar
el problema de resolución de sudokus utilizaras el código en el paquete
`aima.core.search.cps`, y estudiarás el ejemplo de coloreado del mapa de Australia que
puedes encontrar en el paquete `aima.gui.applications.search.csp`.
Finalmente, la **tercera tarea** combina la idea de búsqueda local con la propagación de
restricciones en el algoritmo `min-conflicts`. La idea es aplicar los algoritmos de búsqueda
local, en los que se parte de una configuración completa del estado, y aplicar la propagación
de restricciones utilizando una la heurística más obvia, que consiste en seleccionar el valor que
tiene menor número de confictos con otras variables. En esta tarea definiremos el problema de
las 8 –reinas como un problema CSP, y lo resolveremos con el algoritmo `min-conflicts`.
