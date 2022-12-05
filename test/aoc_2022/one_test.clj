(ns aoc-2022.one-test
  (:require
   [aoc-2022.days.one :as one]
   [aoc-2022.helper :as h]))

(h/defaoc-test [2022 1]
  one/part-one 67633
  one/part-two 199628)
