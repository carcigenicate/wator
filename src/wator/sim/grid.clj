(ns wator.sim.grid)

(defn new-grid [width height default-filler]
  (vec (repeat height
           (vec (repeat width default-filler)))))

(defn dimensions
  "Returns the dimensions of the grid. Assumes each row is the same length."
  [grid]
  (if-not (empty? grid)
    [(count (first grid))
     (count grid)]
    [0 0]))

(defn get-at
  ([grid x y] (get-in grid [y x]))
  ([grid [x y]] (get-at grid x y)))

(defn set-at
  ([grid x y value] (assoc-in grid [y x] value))
  ([grid [x y] value] (set-at grid x y value)))

(defn inbounds? [grid position]
  (let [[w h] (dimensions grid)
        [x y] position]
    (and (< -1 x w)
         (< -1 y h))))
