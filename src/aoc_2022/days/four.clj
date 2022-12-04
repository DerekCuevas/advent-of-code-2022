(ns aoc-2022.days.four
  (:require [clojure.string :as str])
  (:require [aoc-2022.helper :as h]))

(defn- parse-input-ranges [s]
  (mapv
   #(mapv h/parse-int (str/split % #"-"))
   (str/split s #",")))

(defn- complete-overlap? [[sa ea] [sb eb]]
  (or
   (and (>= sa sb) (<= ea eb))
   (and (>= ea eb) (<= sa sb))))

(defn part-one [input]
  (->> input
       (map parse-input-ranges)
       (filter #(apply complete-overlap? %))
       (count)))

(defn- any-overlap? [[sa ea] [sb eb]]
  (or
   (and (<= sa sb) (>= ea sb))
   (and (<= sb sa) (>= eb sa))))

(defn part-two [input]
  (->> input
       (map parse-input-ranges)
       (filter #(apply any-overlap? %))
       (count)))
