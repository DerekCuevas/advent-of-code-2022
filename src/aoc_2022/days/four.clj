(ns aoc-2022.days.four
  (:require [clojure.string :as str])
  (:require [aoc-2022.utils :as u]))

(defn- parse-input-ranges [s]
  (mapv
   #(mapv u/parse-int (str/split % #"-"))
   (str/split s #",")))

(defn- complete-overlap? [[sa ea] [sb eb]]
  (or
   (and (>= sa sb) (<= ea eb))
   (and (>= sb sa) (<= eb ea))))

(defn part-one [input]
  (->> input
       (map parse-input-ranges)
       (filter #(apply complete-overlap? %))
       (count)))

(defn- any-overlap? [[sa ea] [sb eb]]
  (and (<= sa eb) (>= ea sb)))

(defn part-two [input]
  (->> input
       (map parse-input-ranges)
       (filter #(apply any-overlap? %))
       (count)))

;; (h/aoc [2022 4] part-one part-two)
