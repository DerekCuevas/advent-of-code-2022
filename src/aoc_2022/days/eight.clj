(ns aoc-2022.days.eight
  (:require [aoc-2022.helper :as h]
            [aoc-2022.utils :as u]))

(defn parse-input [input]
  (mapv #(mapv (comp u/parse-int str) %) input))

(defn max-before [xs]
  (reduce (fn [maxes x]
            (let [current-max (last maxes)]
              (conj maxes (if (nil? current-max) x (max x current-max)))))
          [nil]
          (butlast xs)))

(defn max-after [xs]
  (reverse (max-before (reverse xs))))

(defn all-surrounding-maximums [grid]
  (let [max-in-rows
        (fn [g]
          (mapv #(mapv vector (max-before %) (max-after %)) g))]
    (mapv #(mapv concat %1 %2)
          (max-in-rows grid)
          (u/transpose (max-in-rows (u/transpose grid))))))

(defn is-visible [height surrounding-maximums]
  (some? (or (some nil? surrounding-maximums)
             (some #(> height %) surrounding-maximums))))

(defn part-one [input]
  (let [grid (parse-input input)
        maximums (all-surrounding-maximums grid)]
    (->> (u/grid-dims grid)
         (apply u/grid-coords)
         (filter #(is-visible (get-in grid %) (get-in maximums %)))
         (count))))

(defn viewing-distance [grid height coords]
  (inc (count (take-while #(> height (get-in grid %)) (butlast coords)))))

(defn scenic-score [grid coord]
  (let [[width height] (u/grid-dims grid)
        tree-height (get-in grid coord)
        cross-coords [(reverse (u/col-coords-above coord))
                      (u/col-coords-below height coord)
                      (reverse (u/row-coords-left coord))
                      (u/row-coords-right width coord)]]
    (->> cross-coords
         (map #(viewing-distance grid tree-height %))
         (apply *))))

(defn part-two [input]
  (let [grid (parse-input input)]
    (->> (u/grid-dims grid)
         (apply u/grid-coords)
         (map #(scenic-score grid %))
         (apply max))))

(h/aoc [2022 8] part-one part-two)
