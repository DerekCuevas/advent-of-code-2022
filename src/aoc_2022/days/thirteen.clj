(ns aoc-2022.days.thirteen
  (:require [aoc-2022.helper :as h]))

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

(defn part-two [input] false)

(comment (h/aoc [2022 13] part-one part-two))
