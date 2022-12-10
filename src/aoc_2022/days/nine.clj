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
  (if (or (= head tail) (adjacent? head tail))
    tail
    (let [head-neighbors (set (u/two-dim-neighbors head))
          all-tail-neighbors (set (u/all-two-dim-neighbors tail))
          all-head-neighbors (set (u/all-two-dim-neighbors head))]
      (or
       (first (set/intersection all-tail-neighbors head-neighbors))
       (first (set/intersection all-tail-neighbors all-head-neighbors))))))

(defn move [[head tail] direction]
  (let [next-head (move-pos direction head)]
    [next-head (move-tail next-head tail)]))

(defn part-one [input]
  (->> input
       (parse-input)
       (expand-move-instructions)
       (reductions move [[0 0] [0 0]])
       (map second)
       (into #{})
       (count)))

(def chain-size 10)

(defn move-chain [[head & chain] direction]
  (reduce (fn [chain-acc tail]
            (conj chain-acc (move-tail (last chain-acc) tail)))
          [(move-pos direction head)]
          chain))

(defn part-two [input]
  (->> input
       (parse-input)
       (expand-move-instructions)
       (reductions move-chain (vec (repeat chain-size [0 0])))
       (map last)
       (into #{})
       (count)))

;; (h/aoc [2022 9] part-one part-two)
