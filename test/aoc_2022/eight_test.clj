(ns aoc-2022.eight-test
  (:require [aoc-2022.days.eight :as eight]
            [aoc-2022.helper :as h]))

(h/defaoc-test [2022 8]
  eight/part-one 1798
  eight/part-two 259308)
