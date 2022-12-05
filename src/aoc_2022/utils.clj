(ns aoc-2022.utils)

(defn parse-int [s]
  (try (Integer/parseInt s) (catch Exception _ nil)))

(defn midpoint [coll]
  (quot (count coll) 2))

(defn two-dim-neighbors [[x y]]
  (let [deltas [-1 0 1]]
    (for [dx deltas dy deltas]
      [(+ x dx) (+ y dy)])))