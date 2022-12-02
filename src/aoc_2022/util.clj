(ns aoc-2022.util)

(defn parse-int [s]
  (try (Integer/parseInt s) (catch Exception _ nil)))

;; https://erikw.me/blog/tech/advent-of-code-tricks/

(defn two-dim-neighbors [[x y]]
  (let [deltas [-1 0 1]]
    (for [dx deltas dy deltas]
      [(+ x dx) (+ y dy)])))
