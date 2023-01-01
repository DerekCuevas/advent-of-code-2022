(ns aoc-2022.days.seventeen
  (:require [aoc-2022.helper :as h]))

(def tunnel-width 7)
(def drop-count 2022)

(def horizontal-line
  [[0 0] [0 1] [0 2] [0 3]])

(def plus
  [[2 1]
   [1 0] [1 1] [1 2]
   [0 1]])

(def letter-l
  [[2 2]
   [1 2]
   [0 0] [0 1] [0 2]])

(def vertical-line
  [[3 0]
   [2 0]
   [1 0]
   [0 0]])

(def square
  [[1 0] [1 1]
   [0 0] [0 1]])

(def shapes [horizontal-line plus letter-l vertical-line square])

(defn parse-input [input]
  (mapv #(case % \> :right \< :left) (first input)))

(defn move-shape-by [shape [dr dc]]
  (map (fn [[r c]] [(+ r dr) (+ c dc)]) shape))

(def direction->move {:up [1 0] :down [-1 0] :left [0 -1] :right [0 1]})

(defn add-coord-to-lookup
  ([coord]
   (add-coord-to-lookup (sorted-map) coord))
  ([lookup [row col]]
   (update lookup row (fnil #(conj % col) (sorted-set)))))

(defn add-shape-to-lookup [lookup shape]
  (reduce add-coord-to-lookup lookup shape))

(defn top-row [lookup]
  (when-let [reversed-lookup (rseq lookup)]
    (first reversed-lookup)))

(defn wall-collision? [[_ col] min-col max-col]
  (not (< (dec min-col) col max-col)))

(defn collision? [lookup shape]
  (some #(or (wall-collision? % 0 tunnel-width)
             (some? (get-in lookup %))) shape))

(defn move-shape-in-direction [lookup shape direction]
  (let [moved-shape (move-shape-by shape (direction->move direction))]
    (if (collision? lookup moved-shape)
      [true shape]
      [false moved-shape])))

(defn move-shape-to-initial-drop-position [lookup shape]
  (let [top-row-idx (first (top-row lookup))]
    (move-shape-by shape [(+ 4 top-row-idx) 2])))

(defn get-wind-direction [wind-directions move-count]
  (nth wind-directions (mod move-count (count wind-directions))))

(defn drop-shape [wind-directions lookup initial-move-count initial-shape]
  (loop [move-count initial-move-count
         shape (move-shape-to-initial-drop-position lookup initial-shape)]
    (let [wind-direction (get-wind-direction wind-directions move-count)
          [drop-collision? shape]
          (as-> shape $s
            (move-shape-in-direction lookup $s wind-direction)
            (second $s)
            (move-shape-in-direction lookup $s :down))]
      (if drop-collision?
        {:lookup (add-shape-to-lookup lookup shape) :count (inc move-count)}
        (recur (inc move-count) shape)))))

(defn add-floor [lookup]
  (->> (range 0 tunnel-width)
       (map #(vector 0 %))
       (reduce add-coord-to-lookup lookup)))

(defn part-one [input]
  (let [wind-directions (parse-input input)
        lookup (add-floor (sorted-map))]
    (->> (cycle shapes)
         (take drop-count)
         (reduce (fn [{:keys [lookup count]} shape]
                   (drop-shape wind-directions lookup count shape))
                 {:lookup lookup :count 0})
         (:lookup)
         (top-row)
         (first))))

(defn part-two [input] false)

(comment (part-one (h/aoc-example-input 2022 17)))

(comment (h/aoc [2022 17] part-one part-two))
