(ns wator.sim.world
  (:require [wator.sim.map-grid :as wg]))

; FIXME: Needs to wrap out of bounds cells instead of filtering them out!

(def empty-water ::water)

; Could generate using a for by discarding when the absolute values of the x and y are the same.
; Would need to cache it though, as generating it constantly would be expensive.
(def adjacent-offsets [[0 -1] [0 1], [-1 0] [1 0]])

(defrecord World [grid])

; TODO: Take the settings as a var-arg pair list?
(defn new-world [width height]
  (->World (wg/new-grid width height)))

(defn inbounds? [world position]
  (wg/inbounds? (:grid world) position))

(defn get-cell [world position]
  (or (wg/get-at (:grid world) position)
      empty-water))

(defn set-cell [world position contents]
  (update world :grid wg/set-at position contents))

(defn remove-cell [world position]
  (update world :grid wg/clear-at position))

; TODO: Use transducer?
(defn get-inbound-adjacent-positions [world position]
  (->> adjacent-offsets
       (map #(map + position %))
       (filter (partial inbounds? world))))

(defn position-contents-pairs [world positions]
  (map #(vector % (get-cell world %)) positions))

(defn unsafe-move-cell [world old-position new-position & [offspring?]]
  (let [contents (get-cell world old-position)]

    (as-> world w
          (if offspring?
            (set-cell w old-position offspring?)
            (remove-cell w old-position))

          (set-cell w new-position contents))))

