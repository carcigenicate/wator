(ns wator.sim.map-grid)

(defrecord Grid [position-map dimensions])

(defn new-grid [width height]
  (->Grid {} [width height]))

(defn get-at [grid position]
  (get-in grid [:position-map position]))

(defn set-at [grid position value]
  (assoc-in grid position value))

(defn clear-at [grid position]
  (update grid :pos-map dissoc position))

(defn inbounds? [grid position]
  (let [[w h] (:dimensions grid)
        [x y] position]
    (and (< -1 x w)
         (< -1 y h))))
