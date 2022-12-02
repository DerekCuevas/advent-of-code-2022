(ns aoc-2022.two
  (:require [aoc-2022.helper :as h])
  (:require [clojure.string :as s]))

(def input (h/aoc-input 2022 2))

(defn parse-input-line [s]
  (let [[opponent self] (s/split s #" ")]
    [({"A" :rock "B" :paper "C" :scissors} opponent)
     ({"X" :rock "Y" :paper "Z" :scissors} self)]))

(def choice->points {:rock 1 :paper 2 :scissors 3})

(def choice->beats {:paper :rock, :scissors :paper, :rock :scissors})

(defn rock-paper-scissors [opponent self]
  (cond
    (identical? opponent self) 3
    (identical? opponent (get choice->beats self)) 6
    :else 0))

(defn part-one [input]
  (->> input
       (map parse-input-line)
       (map #(+ (choice->points (second %))
                (apply rock-paper-scissors %)))
       (reduce +)))

;; (part-one input)
