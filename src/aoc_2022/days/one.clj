(ns aoc-2022.days.one
  (:require [aoc-2022.utils :as u]))

(defn- sum-calories [input]
  (->> input
       (map u/parse-int)
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
