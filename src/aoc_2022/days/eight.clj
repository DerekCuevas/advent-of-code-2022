(ns aoc-2022.days.eight
  (:require [aoc-2022.helper :as h]
            [aoc-2022.utils :as u]))

(defn parse-input [input]
  (mapv #(mapv (comp u/parse-int str) %) input))

(defn gt? [x xs]
  (every? #(> x %) xs))

(defn search-coords [width height coord]
  [(reverse (u/col-coords-above coord))
   (u/col-coords-below height coord)
   (reverse (u/row-coords-left coord))
   (u/row-coords-right width coord)])

(defn visible? [grid width height coord]
  (->> (search-coords width height coord)
       (map #(map (partial get-in grid) %))
       (filter #(gt? (get-in grid coord) %))
       (seq)))

(defn part-one [input]
  (let [grid (parse-input input)
        [width height] (u/grid-dims grid)]
    (->> (u/grid-coords width height)
         (filter #(visible? grid width height %))
         (count))))

(defn viewing-distance [grid height coords]
  (inc (count (take-while #(> height (get-in grid %)) (butlast coords)))))

(defn scenic-score [grid coord]
  (let [[width height] (u/grid-dims grid)
        tree-height (get-in grid coord)]
    (->> (search-coords width height coord)
         (map #(viewing-distance grid tree-height %))
         (apply *))))

(defn part-two [input]
  (let [grid (parse-input input)]
    (->> (u/grid-dims grid)
         (apply u/grid-coords)
         (map #(scenic-score grid %))
         (apply max))))

;; (h/aoc [2022 8] part-one part-two)
