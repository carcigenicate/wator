(ns wator.sim.protocols.hungry)

(defprotocol Hungry
  (starved? [this])
  (eat [this food-value])
  (starve [this]))

(defn default-starved? [energy-key this]
  (zero? (get this energy-key)))

(defn default-eat [energy-key this food-value]
  (update this energy-key + food-value))

(defn default-starve [energy-key this]
  (update this energy-key dec))