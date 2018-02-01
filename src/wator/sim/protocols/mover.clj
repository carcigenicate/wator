(ns wator.sim.protocols.mover)

(defprotocol Mover
  (make-move [this world position rand-gen] "Accepts the current world and its position as a vector. Should return the altered world."))

#_
(defmulti mover
  "Should accept the world and its current location (as a vector), and return an altered world.")
