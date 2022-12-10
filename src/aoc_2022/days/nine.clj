(ns aoc-2022.days.nine
  (:require [aoc-2022.helper :as h]
            [clojure.string :as str]
            [aoc-2022.utils :as u]
            [clojure.set :as set]))

(defn parse-move-instruction [s]
  (let [[direction amount] (str/split s #" ")]
    {:direction (keyword direction) :amount (u/parse-int amount)}))

(defn parse-input [input]
  (map parse-move-instruction input))

(defn expand-move-instructions [instructions]
  (mapcat (fn [{:keys [direction amount]}] (repeat amount direction)) instructions))

(def direction->move {:U [0 1] :D [0 -1] :L [-1 0] :R [1 0]})

(defn move-pos [direction pos]
  (mapv + pos (get direction->move direction)))

(defn adjacent? [head tail]
  (contains? (into #{} (u/all-two-dim-neighbors head)) tail))

(defn move-tail [head tail]
  (first
   (set/intersection
    (set (u/all-two-dim-neighbors tail))
    (set (u/two-dim-neighbors head)))))

(defn move [[head tail] direction]
  (let [next-head (move-pos direction head)]
    (if (or (= next-head tail) (adjacent? next-head tail))
      [next-head tail]
      [next-head (move-tail next-head tail)])))

(defn part-one [input]
  (->> input
       (parse-input)
       (expand-move-instructions)
       (reductions move [[0 0] [0 0]])
       (map second)
       (into #{})
       (count)))

(defn part-two [input] false)

(h/aoc [2022 9] part-one part-two)
