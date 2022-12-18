(ns aoc-2022.days.fourteen
  (:require [aoc-2022.helper :as h]
            [clojure.string :as str]
            [aoc-2022.utils :as u]))

(defn parse-line-segment [s]
  (mapv u/parse-int (str/split s #",")))

(defn parse-input [input]
  (mapv #(mapv parse-line-segment (str/split % #" -> ")) input))

;; TODO: rename, mv to utils
(defn ranged [start end]
  (take (inc (abs (- end start)))
        (iterate (if (> start end) dec inc) start)))

(defn interpolate-points [[start-col start-row] [end-col end-row]]
  (cond
    (= start-col end-col)
    (map #(vector start-col %) (ranged start-row end-row))

    (= start-row end-row)
    (map #(vector % start-row) (ranged start-col end-col))))

(defn expand-line [points]
  (->> points
       (partition 2 1)
       (mapcat #(apply interpolate-points %))
       (dedupe)))

(defn add-point-to-lookup
  "Creates mapping of columns to sorted set of rows"
  [lookup [col row]]
  (update lookup col (fnil #(conj % row) (sorted-set))))

;; TODO: test direct row below
(defn row-below
  "Returns row coordinate of closest row below column or nil"
  [lookup [col row]]
  (when-let [rows (get lookup col)]
    (first (subseq rows > row))))

(-> {}
    (add-point-to-lookup [2 3])
    (add-point-to-lookup [4 5])
    (get-in [5 3]))

(defn drop-sand
  "Returns end coordinate of sand or nil if sand falls into the abyss"
  [lookup [from-col from-row]]
  (when-let [floor-row (row-below lookup [from-col from-row])]
    (let [down-left-coord [(dec from-col) floor-row]
          down-right-coord [(inc from-col) floor-row]
          left-blocked? (some? (get-in lookup down-left-coord))
          right-blocked? (some? (get-in lookup down-right-coord))]
      (cond
        (and left-blocked? right-blocked?)
        [from-col (inc floor-row)]

        (not left-blocked?)
        (recur lookup down-left-coord)

        (not right-blocked?)
        (recur lookup down-right-coord)))))

(def drop-start-location [500 0])

(defn part-one [input]
  (->> input
       (parse-input)
       (mapcat expand-line)
       (reduce add-point-to-lookup {})))

(defn part-two [input] false)

(comment (part-one (h/aoc-example-input 2022 14)))

(def example-lookup (part-one (h/aoc-example-input 2022 14)))

(row-below example-lookup [499 8])

(comment (h/aoc [2022 14] part-one part-two))
