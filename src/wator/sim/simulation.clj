(ns wator.sim.simulation
  (:require [wator.sim.world :as ww]

            [wator.sim.species.fish :as sf]
            [wator.sim.species.shark :as ss]
            [wator.sim.species.common :as s-comm])

  (:import [wator.sim.species.fish Fish]))

; FIXME: See Species!

(defrecord Sim-Settings [fish-repro-rate shark-repro-rate, fish-food-value, shark-starting-energy])

(defrecord Simulation [world settings])

(defn advance-shark [shark repro-rate food-value eating?]
  (as-> shark s
    (ss/starve s)

    (if (s-comm/ready-to-reproduce? shark)
     (s-comm/reset-reproduction-time s repro-rate)
     s)

    (if eating?
      (ss/eat s food-value)
      s)))

; FIXME: That duplication!
; TODO: Just pass in settings?

(defn handle-shark-turn [world shark position settings rand-gen]
  (let [{rr :shark-repro-rate, fv :fish-food-value, se :shark-starting-energy} settings
        new-pos? (sf/make-fish-move world position rand-gen)]
    (if (not (or (ss/starved? shark) (nil? new-pos?)))
      (let [repro? (s-comm/ready-to-reproduce? shark)
            eating? (instance? Fish (ww/get-cell world new-pos?))

            adv-shark (advance-shark shark rr fv eating?)

            adv-world (ww/set-cell world position adv-shark)
            offspring? (when repro? (ss/->Shark rr se))]

        (ww/unsafe-move-cell adv-world position new-pos? offspring?))

      world)))

(defn handle-fish-turn [world fish position repro-rate rand-gen]
  (if-let [new-pos? (sf/make-fish-move world position rand-gen)]
    (let [repro? (s-comm/ready-to-reproduce? fish)
          adv-fish (if repro? (s-comm/reset-reproduction-time fish repro-rate) fish)
          adv-world (ww/set-cell world position adv-fish)
          offspring? (when repro? (sf/->Fish repro-rate))]

      (ww/unsafe-move-cell adv-world position new-pos? offspring?))

    world))

(defn advance-simulation [sim rand-gen]
  (let [{starting-world :world, settings :settings} sim
        {frr :fish-repro-rate} settings
        pos-map (-> starting-world :grid :pos-map)]

    (loop [acc-world starting-world
           [[cur-pos cur-spec] & rest-pos] pos-map]

      (if cur-pos
        (if (instance? Fish cur-spec)
          (handle-fish-turn acc-world cur-spec cur-pos frr rand-gen)
          (handle-shark-turn acc-world cur-spec cur-pos settings rand-gen))

        (assoc sim :world acc-world)))))