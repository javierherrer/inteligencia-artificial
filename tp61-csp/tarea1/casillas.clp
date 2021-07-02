; -------------------------------------------------------------
; MODULO MAIN
;-------------------------------------------------------------
(defmodule MAIN 
	(export deftemplate nodo casilla)
)

; Definimos tipos de datos. Se pide búsqueda CU
; por lo que necesitamos saber que nodos están abiertos o cerrados y el coste

	
(deftemplate MAIN::nodo
	(slot casilla)
	(slot pasos)
	(slot saltos)
	(multislot camino)
	(slot coste (default 0))
     (slot clase (default abierto)))
   
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;     MODULO MAIN::INICIAL        ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(deffacts MAIN::estado-inicial
   (nodo   (casilla 1)
	   (pasos 0)
	   (saltos 0)
           (camino (implode$ (create$ 1 0 0)))
           (clase abierto)))
   
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; MODULO MAIN::CONTROL            ;;;
;;; CU                              ;;;
;;;;;;;;;;;;;;;;;;;;; ;;;;;;;;;;;;;;;;;;


(defrule MAIN::pasa-el-mejor-a-cerrado-CU
	?nodo <- (nodo (coste ?c1)
			(clase abierto)
		 )
	(not (nodo (clase abierto)
			(coste ?c2&:(< ?c2 ?c1))
	     )
	)
	=>
	(modify ?nodo (clase cerrado))
	(focus OPERADORES)
)

;-------------------------------------------------------------
; MODULO OPERADORES
;-------------------------------------------------------------
; en el modulo operadores englobamos las dos acciones 
 (defmodule OPERADORES
   (import MAIN deftemplate nodo))

(defrule OPERADORES::Andar
   (nodo (casilla ?c&:(< ?c 8))
	  (pasos ?p)
	  (saltos ?s)
          (camino $?movimientos)
	  (coste ?coste)
          (clase cerrado))
=>
   (bind ?nueva-casilla (+ ?c 1))
   (bind ?nuevos-pasos (+ ?p 1))
   (bind ?nuevos-saltos ?s)
   (assert (nodo
		(casilla ?nueva-casilla)
		(pasos ?nuevos-pasos)
		(saltos ?nuevos-saltos)
		(camino $?movimientos (
			implode$ (create$ ?nueva-casilla 
					?nuevos-pasos
					?nuevos-saltos)))
		(coste (+ ?coste 1))
	   )
   )
)

(defrule OPERADORES::Saltar
   (nodo (casilla ?c&:(< ?c 5))
	  (pasos ?p)
	  (saltos ?s&:(< ?s ?p))
          (camino $?movimientos)
	  (coste ?coste)
          (clase cerrado))
=>
   (bind ?nueva-casilla (* ?c 2))
   (bind ?nuevos-pasos ?p)
   (bind ?nuevos-saltos (+ ?s 1))
   (assert (nodo
		(casilla ?nueva-casilla)
		(pasos ?nuevos-pasos)
		(saltos ?nuevos-saltos)
		(camino $?movimientos (
			implode$ (create$ ?nueva-casilla 
					?nuevos-pasos
					?nuevos-saltos)))
		(coste (+ ?coste 2))
	   )
   )
)
				  
;-------------------------------------------------------------
; MODULO RESTRICCIONES
;-------------------------------------------------------------
; Nos quedamos con el nodo de menor coste
; La longitud del camino no es el coste

(defmodule RESTRICCIONES
   (import MAIN deftemplate nodo))
 
; eliminamos nodos repetidos
(defrule RESTRICCIONES::repeticiones-de-nodo
	(declare (auto-focus TRUE))
      ?nodo-i <- (nodo (casilla ?c-i) (pasos ?p-i) (saltos ?s-i) (coste ?coste-i))
      ?nodo-j<- (nodo (casilla ?c-i)
	(pasos ?p-j)
	(saltos ?s-j&:(eq (- ?p-i ?s-i) (- ?p-j ?s-j))) 
	(coste ?coste-j&:(> ?coste-i ?coste-j)))
      (test (neq  ?nodo-i ?nodo-j))
 =>
   (retract ?nodo-i))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;    MODULO MAIN::SOLUCION        ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defmodule SOLUCION
   (import MAIN deftemplate nodo))

(defrule SOLUCION::encuentra-solucion
   (declare (auto-focus TRUE))
   ?nodo1 <- (nodo (casilla 8) (camino $?camino)
		(clase cerrado))
 => 
   (retract ?nodo1)
   (assert (solucion $?camino)))

(defrule SOLUCION::escribe-solucion
   (solucion $?movimientos)
  =>
   (printout t "La solucion tiene " (- (length$ $?movimientos) 1)
		" pasos" crlf)
   (loop-for-count (?i 1 (length$ $?movimientos))
	(printout t "(" (nth ?i $?movimientos) ")" " "))
	(printout t crlf)
   (halt))
