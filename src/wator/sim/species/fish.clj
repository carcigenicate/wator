(ns wator.sim.species.fish
  (:require [helpers.general-helpers :as g]
            [wator.sim.world :as ww]))

(defrecord Fish [repro-timer])

(defn make-fish-move [world position rand-gen]
  (let [available (ww/get-inbound-adjacent-positions world position)
        new-pos? (g/random-from-collection available rand-gen)]

    new-pos?))