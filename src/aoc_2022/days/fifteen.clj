(ns aoc-2022.days.fifteen
  (:require [aoc-2022.helper :as h]
            [aoc-2022.utils :as u]))

(defn parse-line [line]
  (let [line-format #"Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)"
        [sx sy bx by] (map u/parse-int (rest (re-matches line-format line)))]
    {:sensor [sx sy] :beacon [bx by]}))

(defn parse-input [input]
  (map parse-line input))

(defn manhattan-distance [[ax ay] [bx by]]
  (+ (abs (- bx ax)) (abs (- by ay))))

(defn perimeter [distance]
  (for [y (range 0 distance)
        x (range y distance)]
    [x y]))

(defn part-one [input] false)

(defn part-two [input] false)

(comment (parse-input (h/aoc-example-input 2022 15)))

(comment (h/aoc [2022 15] part-one part-two))
