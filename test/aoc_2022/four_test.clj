(ns aoc-2022.four-test
  (:require
   [aoc-2022.days.four :as four]
   [aoc-2022.helper :as h]))

(h/defaoc-test [2022 4]
  four/part-one 560
  four/part-two 839)
