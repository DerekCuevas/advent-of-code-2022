(ns aoc-2022.one
  (:require [aoc-2022.helper :as h])
  (:require [aoc-2022.util :as u]))

(def input (h/aoc-input 2022 1))

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

;; (part-one input)
;; (part-two input)
