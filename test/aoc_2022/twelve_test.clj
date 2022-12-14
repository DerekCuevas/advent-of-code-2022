(ns aoc-2022.twelve-test
  (:require [aoc-2022.days.twelve :as twelve]
            [aoc-2022.helper :as h]))

(h/defaoc-test [2022 12]
  twelve/part-one 504
  twelve/part-two false)
