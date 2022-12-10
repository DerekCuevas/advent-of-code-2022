(ns aoc-2022.utils)

(defn parse-int [s]
  (try (Integer/parseInt s) (catch Exception _ nil)))

(defn midpoint [coll]
  (quot (count coll) 2))

(defn queue
  ([] (clojure.lang.PersistentQueue/EMPTY))
  ([coll]
   (reduce conj clojure.lang.PersistentQueue/EMPTY coll)))

(defn transpose [m]
  (apply mapv vector m))

(defn grid-coords [width height]
  (for [row (range height)
        col (range width)]
    [row col]))

(defn row-coords [width row]
  (for [col (range width)]
    [row col]))

(defn col-coords [height col]
  (for [row (range height)]
    [row col]))

(defn edge-coord? [width height [row col]]
  (or
   (or (zero? row) (= row (dec width)))
   (or (zero? col) (= col (dec height)))))

(defn two-dim-neighbors [[x y]]
  (let [deltas [-1 0 1]]
    (for [dx deltas dy deltas]
      [(+ x dx) (+ y dy)])))
