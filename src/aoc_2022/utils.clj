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

(defn grid [width height init]
  (vec (repeat height (vec (repeat width init)))))

(defn grid-coords [width height]
  (for [row (range height)
        col (range width)]
    [row col]))

(defn grid-dims [grid]
  [(count (nth grid 0)) (count grid)])

(defn row-coords [width row]
  (for [col (range width)]
    [row col]))

(defn col-coords [height col]
  (for [row (range height)]
    [row col]))

(defn row-coords-left [[row col]]
  (for [col (range col)]
    [row col]))

(defn row-coords-right [width [row col]]
  (for [col (range (inc col) width)]
    [row col]))

(defn col-coords-above [[row col]]
  (for [row (range row)]
    [row col]))

(defn col-coords-below [height [row col]]
  (for [row (range (inc row) height)]
    [row col]))

(defn edge-coord? [width height [row col]]
  (or
   (or (zero? row) (= row (dec width)))
   (or (zero? col) (= col (dec height)))))

;; TODO: rename vars row,col 
(defn two-dim-neighbors [[x y]]
  (let [deltas [[0 1] [0 -1] [-1 0] [1 0]]]
    (mapv (fn [[dx dy]] [(+ x dx) (+ y dy)]) deltas)))

(defn all-two-dim-neighbors [[x y]]
  (let [deltas [-1 0 1]]
    (for [dx deltas dy deltas]
      [(+ x dx) (+ y dy)])))
