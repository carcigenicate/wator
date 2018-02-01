(ns wator.sim.species.common)

; Reproduction
(defn ready-to-reproduce? [species]
  (<= (:repro-timer species) 0))

(defn decrement-reproduction-time [species]
  (update species :repro-timer dec))

(defn reset-reproduction-time [species new-time]
  (assoc species :repro-timer new-time))