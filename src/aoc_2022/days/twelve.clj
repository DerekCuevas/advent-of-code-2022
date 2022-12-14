(ns aoc-2022.days.twelve
  (:require [aoc-2022.helper :as h]
            [aoc-2022.utils :as u]))

(defn find-coord [grid value]
  (first (filter #(= value (get-in grid %)) (u/grid-coords grid))))

(defn parse-input [input]
  (let [grid (mapv #(mapv int %) input)
        start (find-coord grid (int \S))
        end (find-coord grid (int \E))]
    {:grid (-> grid
               (assoc-in start (int \a))
               (assoc-in end (int \z)))
     :start start
     :end end}))

(defn valid-neighbors [grid coord]
  (->> coord
       (u/grid-neighbors false)
       (map #(vector % (get-in grid %)))
       (filter (fn [[_ height]]
                 (and (some? height) (<= height (inc (get-in grid coord))))))
       (map first)))

(defn part-one [input]
  (let [{:keys [grid start end]} (parse-input input)]
    (loop [seen #{start}
           queue (u/queue [[start 0]])]
      (let [[current distance] (or (first queue) [nil nil])]
        (cond
          (nil? current) nil
          (= current end) distance
          :else (let [neighbors
                      (->> current
                           (valid-neighbors grid)
                           (filter #(not (contains? seen %))))]
                  (recur
                   (apply conj seen neighbors)
                   (apply conj
                          (pop queue)
                          (map #(vector % (inc distance)) neighbors)))))))))

(defn part-two [input] false)

;; (part-one (h/aoc-example-input 2022 12))

;; (part-one (h/aoc-input 2022 12))

;; (h/aoc [2022 12] part-one part-two)
