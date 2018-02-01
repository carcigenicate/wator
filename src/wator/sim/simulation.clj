(ns wator.sim.simulation
  (:require [wator.sim.protocols.mover :as pm]
            [wator.sim.protocols.hungry :as ph]
            [wator.sim.protocols.reproducer :as pr]
            [wator.sim.world :as ww])

  (:import [wator.sim.protocols.reproducer Reproducer]
           [wator.sim.protocols.hungry Hungry]))

; FIXME: See Species!

(defrecord Sim-Settings [fish-repro-rate shark-repro-rate, fish-food-value])

(defrecord Simulation [world settings])
#_
(defn advance-reproducer [^Reproducer repro]
  (as-> repro r
        (if (pr/timer-up?))))

(defn advance-hungry [^Hungry hungry]
  (ph/starve hungry))

(defn advance-pre-move-species [species]
  (as-> species s
        (if (satisfies? ph/Hungry s)
          (advance-hungry s)
          s)))

(defn advance-post-move-at [world position]
  (let [species (ww/get-cell world position)]))


(defn advance-simulation [sim rand-gen]
  (let [{starting-world :world, settings :settings} sim
        {frr :fish-repro-rate, srr :shark-repro-rate, ffv :fish-food-value} settings
        pos-map (-> starting-world :grid :pos-map)]

    (loop [acc-world starting-world
           [[cur-pos cur-cont] & rest-pos] pos-map]
      (if cur-pos
        (let [
              adv-word (pm/make-move cur-cont acc-world cur-pos rand-gen)])

        (assoc sim :world acc-world)))))