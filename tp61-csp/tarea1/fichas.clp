; -------------------------------------------------------------
; MODULO MAIN
;-------------------------------------------------------------
(defmodule MAIN 
	(export deftemplate nodo casilla)
	(export deffunction heuristica)
)

; Definimos tipos de datos. Se pide búsqueda A*
; por lo que necesitamos saber que nodos están abiertos o cerrados y el coste

	
(deftemplate MAIN::nodo
	(multislot estado)
	(multislot camino)
	(slot coste (default 0))
	(slot heuristica)
     (slot clase (default abierto)))

(defglobal MAIN
   ?*estado-inicial* = (create$ B B B H V V V)
   ?*estado-final* = (create$ V V V H B B B))

(deffunction MAIN::heuristica ($?estado)
   (bind ?res 0)
   (loop-for-count (?i 1 7)
    (if (neq (nth$ ?i $?estado)
             (nth$ ?i ?*estado-final*))
         then (bind ?res (+ ?res 1))
     )
    )
   ?res)
   
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;     MODULO MAIN::INICIAL        ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(deffacts nodo-inicial
   (nodo   (estado ?*estado-inicial*)
           (camino (implode$ ?*estado-inicial*))
           (heuristica (heuristica ?*estado-inicial*))
           (clase abierto)))
 


   
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; MODULO MAIN::CONTROL            ;;;
;;; A*                              ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(defrule MAIN::pasa-el-mejor-a-cerrado-A*
	?nodo <- (nodo (heuristica ?h1)
			(coste ?c1)
			(clase abierto)
		 )
	(not (nodo (clase abierto)
			(heuristica ?h2)
			(coste ?c2&:(< (+ ?c2 ?h2) (+ ?c1 ?h1)))
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
   (import MAIN deftemplate nodo)
   (import MAIN deffunction heuristica))

(defrule OPERADORES::mover-dcha
   (nodo (estado $?a H $?b&:(<= (length$ $?b) 2) ?c $?d)
          (camino $?movimientos)
          (clase cerrado)
          (coste ?coste))

=>
   (bind $?nuevo-estado (create$ $?a ?c $?b H $?d))
   (assert (nodo 
		      (estado $?nuevo-estado)
              (camino $?movimientos (implode$ $?nuevo-estado))
              (coste (+ ?coste 1))
              (heuristica (heuristica $?nuevo-estado)))))

(defrule OPERADORES::mover-izq
   (nodo (estado $?a ?b $?c&:(<= (length$ $?c) 2) H $?d)
          (camino $?movimientos)
          (clase cerrado)
          (coste ?coste))

=>
   (bind $?nuevo-estado (create$ $?a H $?c ?b $?d))
   (assert (nodo 
		      (estado $?nuevo-estado)
              (camino $?movimientos (implode$ $?nuevo-estado))
              (coste (+ ?coste 1))
              (heuristica (heuristica $?nuevo-estado)))))


;-------------------------------------------------------------
; MODULO RESTRICCIONES
;-------------------------------------------------------------
; Nos quedamos con el nodo de menor coste
; Solución válida si el coste de cada operador es 1.

(defmodule RESTRICCIONES
   (import MAIN deftemplate nodo))
 
; eliminamos nodos repetidos
(defrule RESTRICCIONES::repeticiones-de-nodo
	(declare (auto-focus TRUE))
      ?nodo1 <- (nodo (estado $?estado) (camino $?camino1))
      ?nodo2 <- (nodo (estado $?estado) 
        	      (camino $?camino2&:(> (length$ ?camino1) (length$ ?camino2))))
      (test (neq  ?nodo1 ?nodo2))
 =>
   (retract ?nodo1))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;    MODULO MAIN::SOLUCION        ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defmodule SOLUCION
   (import MAIN deftemplate nodo))

(defrule SOLUCION::encuentra-solucion
   (declare (auto-focus TRUE))
   ?nodo <- (nodo (estado V V V H B B B)
               (camino $?movimientos))
 => 
   (retract ?nodo)
   (assert (solucion $?movimientos)))

(defrule SOLUCION::escribe-solucion
   (solucion $?movimientos)
  =>
   (printout t "La solucion tiene " (- (length $?movimientos) 1) " pasos" crlf)

   (loop-for-count (?i 1 (length $?movimientos))
       (bind ?mov (nth ?i $?movimientos))
       (printout t ?mov crlf))

   (halt))


