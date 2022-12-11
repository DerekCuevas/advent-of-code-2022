(ns aoc-2022.ten-test
  (:require [aoc-2022.days.ten :as ten]
            [aoc-2022.helper :as h]))

(def part-two-answer
  [["#"
    "#"
    "#"
    "#"
    "."
    "#"
    "#"
    "#"
    "#"
    "."
    "."
    "#"
    "#"
    "."
    "."
    "#"
    "#"
    "#"
    "#"
    "."
    "#"
    "#"
    "#"
    "#"
    "."
    "#"
    "."
    "."
    "."
    "."
    "#"
    "."
    "."
    "#"
    "."
    "#"
    "#"
    "#"
    "#"
    "."]
   ["#"
    "."
    "."
    "."
    "."
    "#"
    "."
    "."
    "."
    "."
    "#"
    "."
    "."
    "#"
    "."
    "."
    "."
    "."
    "#"
    "."
    "#"
    "."
    "."
    "."
    "."
    "#"
    "."
    "."
    "."
    "."
    "#"
    "."
    "."
    "#"
    "."
    "#"
    "."
    "."
    "."
    "."]
   ["#"
    "#"
    "#"
    "."
    "."
    "#"
    "#"
    "#"
    "."
    "."
    "#"
    "."
    "."
    "."
    "."
    "."
    "."
    "#"
    "."
    "."
    "#"
    "#"
    "#"
    "."
    "."
    "#"
    "."
    "."
    "."
    "."
    "#"
    "#"
    "#"
    "#"
    "."
    "#"
    "#"
    "#"
    "."
    "."]
   ["#"
    "."
    "."
    "."
    "."
    "#"
    "."
    "."
    "."
    "."
    "#"
    "."
    "."
    "."
    "."
    "."
    "#"
    "."
    "."
    "."
    "#"
    "."
    "."
    "."
    "."
    "#"
    "."
    "."
    "."
    "."
    "#"
    "."
    "."
    "#"
    "."
    "#"
    "."
    "."
    "."
    "."]
   ["#"
    "."
    "."
    "."
    "."
    "#"
    "."
    "."
    "."
    "."
    "#"
    "."
    "."
    "#"
    "."
    "#"
    "."
    "."
    "."
    "."
    "#"
    "."
    "."
    "."
    "."
    "#"
    "."
    "."
    "."
    "."
    "#"
    "."
    "."
    "#"
    "."
    "#"
    "."
    "."
    "."
    "."]
   ["#"
    "."
    "."
    "."
    "."
    "#"
    "#"
    "#"
    "#"
    "."
    "."
    "#"
    "#"
    "."
    "."
    "#"
    "#"
    "#"
    "#"
    "."
    "#"
    "#"
    "#"
    "#"
    "."
    "#"
    "#"
    "#"
    "#"
    "."
    "#"
    "."
    "."
    "#"
    "."
    "#"
    "#"
    "#"
    "#"
    "."]])

(h/defaoc-test [2022 10]
  ten/part-one 12540
  ;; FECZELHE
  ten/part-two part-two-answer)
