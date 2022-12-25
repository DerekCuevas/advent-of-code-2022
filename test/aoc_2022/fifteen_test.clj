(ns aoc-2022.fifteen-test
  (:require [aoc-2022.days.fifteen :as fifteen]
            [aoc-2022.helper :as h]))

(h/defaoc-test [2022 15]
  fifteen/part-one 4724228
  fifteen/part-two 13622251246513)
