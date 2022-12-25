(ns aoc-2022.days.fifteen
  (:require [aoc-2022.helper :as h]
            [aoc-2022.utils :as u]))

(defn parse-line [line]
  (let [line-format #"Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)"
        [sc sr bc br] (map u/parse-int (rest (re-matches line-format line)))]
    {:sensor [sc sr] :beacon [bc br]}))

(defn parse-input [input]
  (map parse-line input))

(defn manhattan-distance [[ac ar] [bc br]]
  (+ (abs (- bc ac)) (abs (- br ar))))

(defn signal-out-of-range-on-row [row {:keys [sensor beacon]}]
  (let [[sensor-col sensor-row] sensor
        distance (manhattan-distance sensor beacon)
        offset (- distance (abs (- row sensor-row)))]
    (when (>= offset 0)
      {:start (- sensor-col offset) :end (+ sensor-col offset)})))

(defn sorted-overlap? [{end-a :end} {start-b :start}]
  (>= end-a (dec start-b)))

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

(defn ranges-out-of-bounds [row pairs]
  (->> pairs
       (map #(signal-out-of-range-on-row row %))
       (filter some?)
       (combine-ranges)))

(def target-row 2000000)

(defn part-one [input]
  (->> input
       (parse-input)
       (ranges-out-of-bounds target-row)
       (map range-length)
       (reduce +)))

(defn clamp [min-value max-value x]
  (max min-value (min max-value x)))

(defn clamp-range [min-value max-value {:keys [start end]}]
  {:start (clamp min-value max-value start) :end (clamp min-value max-value end)})

(def bound 4000000)

(defn tuning-frequency [[col row]]
  (+ (* col 4000000) row))

(defn into-missing-coord [{:keys [row range]}]
  [(inc (:end (first range))) row])

(defn part-two [input]
  (let [pairs (parse-input input)
        row-range (range 0 (inc bound))]
    (->> row-range
         (map (fn [row]
                {:row row
                 :range (->> pairs
                             (ranges-out-of-bounds row)
                             (map #(clamp-range 0 bound %)))}))
         (filter #(> (count (:range %)) 1))
         (first)
         (into-missing-coord)
         (tuning-frequency))))

(comment (h/aoc [2022 15] part-one part-two))
