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

(defn grid-dims [grid]
  [(count (nth grid 0)) (count grid)])

(defn grid-coords
  ([grid]
   (apply grid-coords (grid-dims grid)))
  ([width height]
   (for [row (range height)
         col (range width)]
     [row col])))

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

(defn direct-grid-neighbors [[row col]]
  (let [deltas [[0 1] [0 -1] [-1 0] [1 0]]]
    (mapv (fn [[drow dcol]] [(+ row drow) (+ col dcol)]) deltas)))

(defn all-grid-neighbors [[row col]]
  (let [deltas [-1 0 1]]
    (for [drow deltas dcol deltas]
      [(+ row drow) (+ col dcol)])))

(defn grid-neighbors [include-diagonals? coord]
  (if include-diagonals?
    (all-grid-neighbors coord)
    (direct-grid-neighbors coord)))
