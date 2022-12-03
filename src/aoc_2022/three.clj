(ns aoc-2022.three
  (:require [aoc-2022.helper :as h])
  (:require [aoc-2022.util :as u])
  (:require [clojure.set :as set]))

(def input (h/aoc-input 2022 3))

(defn split [s]
  (split-at (u/midpoint s) s))

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

;; (part-one input)

(defn part-two [input]
  (->> input
       (partition 3)
       (map (comp char-priority common-char))
       (reduce +)))

;; (part-two input)