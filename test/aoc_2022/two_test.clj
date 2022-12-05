(ns aoc-2022.two-test
  (:require
   [aoc-2022.days.two :as two]
   [aoc-2022.helper :as h]))

(h/defaoc-test [2022 2]
  two/part-one 12586
  two/part-two 13193)
