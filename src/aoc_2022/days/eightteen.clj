(ns aoc-2022.days.eightteen
  (:require [aoc-2022.helper :as h]
            [aoc-2022.utils :as u]
            [clojure.string :as str]))

(defn parse-input [input]
  (map #(mapv u/parse-int (str/split % #",")) input))

(defn cube-neighbors [cube]
  (let [deltas
        [[1 0 0] [-1 0 0]
         [0 1 0] [0 -1 0]
         [0 0 1] [0 0 -1]]]
    (map #(mapv + cube %) deltas)))

(defn exposed-sides [shape cube]
  (->> cube
       (cube-neighbors)
       (filter #(not (contains? shape %)))))

(defn part-one [input]
  (let [shape (into #{} (parse-input input))]
    (->> shape
         (map #(exposed-sides shape %))
         (map count)
         (reduce +))))

(defn part-two [input] false)

(comment (part-one (h/aoc-example-input 2022 18)))

(comment (h/aoc [2022 18] part-one part-two))
