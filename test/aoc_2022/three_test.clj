(ns aoc-2022.three-test
  (:require
   [aoc-2022.days.three :as three]
   [aoc-2022.helper :as h]))

(h/defaoc-test [2022 3]
  three/part-one 7821
  three/part-two 2752)
