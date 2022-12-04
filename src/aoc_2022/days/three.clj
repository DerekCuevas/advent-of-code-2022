(ns aoc-2022.days.three
  (:require [aoc-2022.helper :as h])
  (:require [clojure.set :as set]))

(defn split [s]
  (split-at (h/midpoint s) s))

(defn common-char [ss]
  (first (apply set/intersection (map set ss))))

(defn char-priority [c]
  (let [code (int c)]
    (if (<= code 90)
      (- code 38)
      (- code 96))))

(defn part-one [input]
  (->> input
       (map (comp char-priority common-char split))
       (reduce +)))

(defn part-two [input]
  (->> input
       (partition 3)
       (map (comp char-priority common-char))
       (reduce +)))
