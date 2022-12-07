(ns aoc-2022.days.six
  (:require [aoc-2022.helper :as h]
            [aoc-2022.utils :as u]))

(def marker-length 4)

(defn enqueue-marker [marker val]
  (let [marker (conj marker val)]
    (if (<= (count marker) marker-length)
      marker
      (pop marker))))

(defn part-one [[input]]
  (loop [index 0
         marker (u/queue)]
    (if (= (count (into #{} marker)) marker-length)
      index
      (recur (inc index) (enqueue-marker marker (nth input index))))))

(defn part-two [input] false)

(h/aoc [2022 6] part-one part-two)
