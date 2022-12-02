(ns aoc-2022.two
  (:require [aoc-2022.helper :as h])
  (:require [clojure.string :as s]))

(def input (h/aoc-input 2022 2))

(def choice->points {:rock 1 :paper 2 :scissors 3})

(def choice->beats {:paper :rock, :scissors :paper, :rock :scissors})

(def choice->looses (clojure.set/map-invert choice->beats))

(defn- rock-paper-scissors [opponent self]
  (cond
    (identical? opponent self) 3
    (identical? opponent (get choice->beats self)) 6
    :else 0))

(defn- play-rounds [parse input]
  (->> input
       (map parse)
       (map #(+ (choice->points (second %))
                (apply rock-paper-scissors %)))
       (reduce +)))

(defn- parse-input-line-part-one [s]
  (let [[opponent self] (s/split s #" ")]
    [({"A" :rock "B" :paper "C" :scissors} opponent)
     ({"X" :rock "Y" :paper "Z" :scissors} self)]))

(defn part-one [input]
  (play-rounds parse-input-line-part-one input))

;; (part-one input)

(defn- parse-input-line-part-two [s]
  (let [[opponent self] (s/split s #" ")
        opponent-choice ({"A" :rock "B" :paper "C" :scissors} opponent)]
    [opponent-choice
     ({"X" (choice->beats opponent-choice)
       "Y" opponent-choice
       "Z" (choice->looses opponent-choice)} self)]))

(defn part-two [input]
  (play-rounds parse-input-line-part-two input))

;; (part-two input)
