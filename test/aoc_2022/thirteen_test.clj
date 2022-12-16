(ns aoc-2022.thirteen-test
  (:require [aoc-2022.days.thirteen :as thirteen]
            [aoc-2022.helper :as h]))

(h/defaoc-test [2022 13]
  thirteen/part-one 6623
  thirteen/part-two 23049)
