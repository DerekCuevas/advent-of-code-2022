(ns aoc-2022.three
  (:require [aoc-2022.helper :as h]))

(def input (h/aoc-input 2022 3))

(defn split [s]
  (let [mid (/ (count s) 2)]
    [(subs s 0 mid) (subs s mid)]))

(defn common-char [ss]
  (first (apply clojure.set/intersection (map set ss))))

(defn char-priority [c]
  (let [code (int c)]
    (cond
      (<= code 90) (- code 38)
      :else (- code 96))))

(defn part-one [input]
  (->> input
       (map #(common-char (split %)))
       (map char-priority)
       (reduce +)))

;; (part-one input)

(defn part-two [input]
  (->> input
       (partition 3)
       (map common-char)
       (map char-priority)
       (reduce +)))

;; (part-two input)