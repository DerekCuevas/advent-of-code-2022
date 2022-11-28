(ns aoc-2022.one
  (:require [aoc-2022.helper :as h])
  (:require [criterium.core :as c]))

;; poc
(def input (h/aoc-input 2021 1))

(defn part-one [_]
  (map #(* % %) (range 100)))

(c/quick-bench (part-one input))
