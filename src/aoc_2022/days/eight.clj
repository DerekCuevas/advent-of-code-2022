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
        width (count (nth grid 0))
        height (count grid)
        maximums (all-surrounding-maximums grid)]
    (->> (u/grid-coords width height)
         (filter #(is-visible (get-in grid %) (get-in maximums %)))
         (count))))

(defn part-two [input] false)

(def example (parse-input (h/aoc-example-input 2022 8)))

(part-one example)

(h/aoc [2022 8] part-one part-two)
