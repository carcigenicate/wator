(ns wator.ui.test-ui
  (:require [wator.sim.simulation :as ws]

            [seesaw.core :as sc]
            [seesaw.graphics :as sg]))

; TODO: Test Fish first to make sure it doesn't just explode.

(def window-width 1000)
(def window-height (* window-width 2/3))

(def world-width window-width)
(def world-height window-height)

(defn paint [state-atom cvs g]
  (let [sim @state-atom
        alive (-> sim :world :grid :pos-map)]

    (doseq [[[x y] spec] alive]
      (sg/draw g
         (sg/rect x y 1 1) ; Size?
         (sg/style :background :black))))) ; TODO: Adjust for species

(defn new-canvas [state-atom]
  (sc/canvas :paint (partial paint state-atom)))

(defn new-frame [starting-state]
  (let [state-atom (atom starting-state)
        contents (new-canvas state-atom)
        f (sc/frame :size [window-width :by window-height])]))
