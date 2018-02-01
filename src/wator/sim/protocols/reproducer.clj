(ns wator.sim.protocols.reproducer)

(defprotocol Reproducer
  (decrement-timer [this] "Reduce the amount of time left until reproduction.")
  (reset-timer [this reset-value] "Reset the timer after reproduction.")
  (timer-up? [this]))

(defn default-decrement-timer [timer-key this]
  (update this timer-key dec))

(defn default-reset-timer [timer-key this reset-value]
  (assoc this timer-key reset-value))

(defn default-timer-up? [timer-key this]
  (zero? (get this timer-key)))