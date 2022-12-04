(ns aoc-2022.days.one
  (:require [aoc-2022.helper :as h]))

(defn- sum-calories [input]
  (->> input
       (map h/parse-int)
       (partition-by nil?)
       (filter (comp some? first))
       (map (partial reduce +))))

(defn part-one [input]
  (apply max (sum-calories input)))

(defn part-two [input]
  (->> input
       (sum-calories)
       (sort >)
       (take 3)
       (reduce +)))
