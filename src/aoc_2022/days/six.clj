(ns aoc-2022.days.six
  (:require [aoc-2022.helper :as h]
            [aoc-2022.utils :as u]))

(def packet-marker-length 4)

(def message-marker-length 14)

(defn enqueue-marker [marker marker-length val]
  (let [marker (conj marker val)]
    (if (<= (count marker) marker-length)
      marker
      (pop marker))))

(defn find-marker-index [stream marker-length]
  (loop [index 0
         marker (u/queue)]
    (if (= (count (into #{} marker)) marker-length)
      index
      (recur (inc index) (enqueue-marker marker marker-length (nth stream index))))))

(defn part-one [[input]]
  (find-marker-index input packet-marker-length))

(defn part-two [[input]]
  (find-marker-index input message-marker-length))

;; (h/aoc [2022 6] part-one part-two)
