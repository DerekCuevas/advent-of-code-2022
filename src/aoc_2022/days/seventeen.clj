(ns aoc-2022.days.seventeen
  (:require [aoc-2022.helper :as h]
            [aoc-2022.utils :as u]))

(def tunnel-width 7)
(def drop-count 2022)

(defn parse-input [input]
  (mapv #(case % \> :right \< :left) (first input)))

(defn shape->coords [shape]
  (filter #(pos? (get-in shape %)) (u/grid-coords shape)))

(def horizontal-line
  (shape->coords
   [[1 1 1 1]]))

(def plus
  (shape->coords
   [[0 1 0]
    [1 1 1]
    [0 1 0]]))

(def letter-l
  (shape->coords
   [[0 0 1]
    [0 0 1]
    [1 1 1]]))

(def vertical-line
  (shape->coords
   [[1]
    [1]
    [1]
    [1]]))

(def square
  (shape->coords
   [[1 1]
    [1 1]]))

(def shapes [horizontal-line plus letter-l vertical-line square])

(defn move-shape-by [shape [dr dc]]
  (map (fn [[r c]] [(+ r dr) (+ c dc)]) shape))

(def direction->move {:up [1 0] :down [-1 0] :left [0 -1] :right [0 1]})

(defn move-shape-in-direction [shape direction]
  (move-shape-by shape (direction->move direction)))

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

(defn top-row-collision? [lookup shape]
  (when-let [[row cols] (top-row lookup)]
    (->> shape
         (filter #(= row (first %)))
         (map second)
         (some cols))))

(defn out-of-bounds? [[_ col] min-col max-col]
  (not (< (dec min-col) col max-col)))

(defn push-shape-in-wind-direction [shape direction]
  (let [next-shape (move-shape-in-direction shape direction)]
    (if (some #(out-of-bounds? % 0 tunnel-width) next-shape)
      shape
      next-shape)))

;; todo
;; initial-shape-pos (3 up from bottom row, 2 over from left)
(defn drop [wind-directions lookup count shape]
  {:lookup lookup :count count})

(defn add-bottom-row [lookup]
  (->> (range 0 tunnel-width)
       (map #(vector 0 %))
       (reduce add-coord-to-lookup lookup)))

(defn part-one [input]
  (let [wind-directions (parse-input input)
        lookup (add-bottom-row (sorted-map))]
    (->> (cycle shapes)
         (take drop-count)
         (reduce (fn [{:keys [lookup count]} shape]
                   (drop wind-directions lookup count shape))
                 {:lookup lookup :count 0})
         (:lookup)
         (top-row)
         (first))))

(defn part-two [input] false)

(comment (part-one (h/aoc-example-input 2022 17)))

(comment (h/aoc [2022 17] part-one part-two))
