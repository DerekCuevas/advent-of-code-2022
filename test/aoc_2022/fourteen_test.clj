(ns aoc-2022.fourteen-test
  (:require [aoc-2022.days.fourteen :as fourteen]
            [aoc-2022.helper :as h]))

(h/defaoc-test [2022 14]
  fourteen/part-one 825
  fourteen/part-two 26729)
