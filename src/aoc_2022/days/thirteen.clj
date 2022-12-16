(ns aoc-2022.days.thirteen
  (:require [aoc-2022.helper :as h]
            [clojure.core.match :as m]))

(defn parse-input [input]
  (->> input
       (partition 2 3)
       (map #(map read-string %))))

(defn ordered? [[a b]]
  (cond
    (and (nil? a) (some? b)) true

    (and (some? a) (nil? b)) false

    (and (int? a) (int? b))
    (cond
      (< a b) true
      (> a b) false
      :else nil)

    (and (coll? a) (int? b))
    (ordered? [a [b]])

    (and (int? a) (coll? b))
    (ordered? [[a] b])

    (and (coll? a) (coll? b))
    (loop [[af & as] a
           [bf & bs] b]
      (when (or (some? af) (some? bf))
        (let [result (ordered? [af bf])]
          (if (some? result)
            result
            (recur as bs)))))))

(defn part-one [input]
  (->> input
       (parse-input)
       (map-indexed #(vector (inc %1) (ordered? %2)))
       (filter second)
       (map first)
       (reduce +)))

(defn compare-packets [a b]
  (m/match (ordered? [a b])
    nil 0
    true -1
    false 1))

(def dividor-packets [[[2]] [[6]]])

(defn part-two [input]
  (->> input
       (filter seq)
       (map read-string)
       (concat dividor-packets)
       (sort compare-packets)
       (map-indexed vector)
       (filter #(contains? (set dividor-packets) (second %)))
       (map (comp inc first))
       (apply *)))

(comment (h/aoc [2022 13] part-one part-two))
