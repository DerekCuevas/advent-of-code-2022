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

(defn signal-out-of-range-on-row [row {:keys [sensor beacon]}]
  (let [[sensor-col sensor-row] sensor
        distance (manhattan-distance sensor beacon)
        offset (- distance (abs (- row sensor-row)))]
    (when (>= offset 0)
      {:start (- sensor-col offset) :end (+ sensor-col offset)})))

(defn sorted-overlap? [{end-a :end} {start-b :start}]
  (>= end-a start-b))

(defn merge-overlapping-ranges [{start-a :start end-a :end} {start-b :start end-b :end}]
  {:start (min start-a start-b) :end (max end-a end-b)})

(defn combine-ranges [ranges]
  (let [[first & rest] (sort-by :start ranges)]
    (reduce
     (fn [combined range]
       (if (sorted-overlap? (last combined) range)
         (conj (pop combined) (merge-overlapping-ranges (last combined) range))
         (conj combined range)))
     [first] rest)))

(defn range-length [{:keys [start end]}]
  (- end start))

(def target-row 2000000)

;; need to filter out beacons in target row?
(defn part-one [input]
  (->> input
       (parse-input)
       (map #(signal-out-of-range-on-row target-row %))
       (filter some?)
       (combine-ranges)
       (map range-length)
       (reduce +)))

(defn part-two [input] false)

(comment (h/aoc [2022 15] part-one part-two))
