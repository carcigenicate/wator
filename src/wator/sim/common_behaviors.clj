(ns wator.sim.common-behaviors
  (:require [wator.sim.protocols.reproducer :as pr])
  (:import [wator.sim.protocols.reproducer Reproducer]))

(defn add-reproduction-behavior [type-name]
  (extend type-name
    pr/Reproducer
    {:decrement-timer (partial pr/default-decrement-timer :repro-timer)
     :reset-timer (partial pr/default-reset-timer :repro-timer)
     :timer-up? (partial pr/default-timer-up? :repro-timer)}))