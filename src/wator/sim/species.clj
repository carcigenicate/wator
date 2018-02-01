(ns wator.sim.species
  (:require [wator.sim.protocols.mover :as pm]
            [wator.sim.protocols.reproducer :as pr]
            [wator.sim.protocols.hungry :as ph]

            [wator.sim.common-behaviors :as wb]
            [wator.sim.world :as ww]

            [helpers.general-helpers :as g]))

; TODO: Need to have make-move return a move map instead of the world

; ----- FISH

(defrecord Fish [repro-timer]
  pm/Mover
  (make-move [this world position rand-gen]
    (let [available (ww/get-inbound-adjacent-positions world position)
          new-pos? (g/random-from-collection available rand-gen)]

      (if new-pos?
        (ww/unsafe-move-cell world position new-pos? (pr/timer-up? this))
        world))))

(wb/add-reproduction-behavior Fish)

; ----- Shark

(defrecord Shark [repro-timer energy]
  pm/Mover
  (make-move [this world position rand-gen]
    (let [available (ww/get-inbound-adjacent-positions world position)
          pairs (ww/position-contents-pairs world available)
          fish-cells (filter #(instance? Fish (second %)) pairs)
          choices (if (empty? fish-cells) available (map first fish-cells))
          new-pos? (g/random-from-collection choices rand-gen)]

      (if new-pos?
        (ww/unsafe-move-cell world position new-pos? (pr/timer-up? this))
        world))))

(wb/add-reproduction-behavior Shark)
(extend Shark
  ph/Hungry
  {:eat (partial ph/default-eat :energy)
   :starve (partial ph/default-starve :energy)
   :starved? (partial ph/default-starved? :energy)})


