(ns aoc-2022.days.fifteen
  (:require [aoc-2022.helper :as h]
            [aoc-2022.utils :as u]))

(defn parse-line [line]
  (let [line-format #"Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)"
        [sc sr bc br] (map u/parse-int (rest (re-matches line-format line)))]
    {:sensor [sc sr] :beacon [bc br]}))

(defn parse-input [input]
  (map parse-line input))

(defn manhattan-distance [[ax ay] [bx by]]
  (+ (abs (- bx ax)) (abs (- by ay))))

(defn surrounding-cols [[col _] length]
  (conj (mapcat #(list (- col %) (+ col %)) (range 1 (inc length))) col))

(defn no-signal-cols [target-row {:keys [sensor beacon]}]
  (let [[sensor-col sensor-row] sensor
        [beacon-col beacon-row] beacon
        distance (manhattan-distance sensor beacon)
        offset (- distance (abs (- target-row sensor-row)))]
    (when (>= offset 0)
      (->> (surrounding-cols [sensor-col target-row] offset)
           (filter #(not (and (= beacon-row target-row) (= % beacon-col))))))))

(def target-row 2000000)

(defn part-one [input]
  (->> input
       (parse-input)
       (map #(no-signal-cols target-row %))
       (filter some?)
       (reduce #(apply conj %1 %2) #{})
       (count)))

(defn part-two [input] false)

(comment (h/aoc [2022 15] part-one part-two))
