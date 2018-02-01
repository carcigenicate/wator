(ns wator.sim.species.shark
  (:require [helpers.general-helpers :as g]
            [wator.sim.world :as ww])

  (:import [wator.sim.species.fish Fish]))

(defrecord Shark [repro-timer energy])

(defn make-shark-move [world position rand-gen]
  (let [available (ww/get-inbound-adjacent-positions world position)
        pairs (ww/position-contents-pairs world available)
        fish-cells (filter #(instance? Fish (second %)) pairs)
        choices (if (empty? fish-cells) available (map first fish-cells))
        new-pos? (g/random-from-collection choices rand-gen)]

    new-pos?))

(defn eat [shark food-value]
  (update shark :energy + food-value))

(defn starve [shark]
  (update shark :energy dec))

(defn starved? [shark]
  (<= (:energy shark) 0))