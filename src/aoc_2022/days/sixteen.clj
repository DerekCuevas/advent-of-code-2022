(ns aoc-2022.days.sixteen
  (:require [aoc-2022.helper :as h]
            [aoc-2022.utils :as u]
            [clojure.string :as str]))

(defn parse-valve [line]
  (let [line-format #"Valve ([A-Z]+) has flow rate=(\d+); tunnel(s?) lead(s?) to valve(s?) (.+)"
        [_ valve rate _ _ _ connections-str] (re-matches line-format line)
        connections (str/split connections-str #", ")]
    {:valve valve :rate (u/parse-int rate) :connections connections}))

(defn parse-input [input]
  (->> input
       (map parse-valve)
       (map #(vector (:valve %) %))
       (into {})))

(def open-time 1)
(def travel-time 1)

(def max-flow
  (memoize
   (fn
     ([network valve budget]
      (max-flow network valve budget #{}))
     ([network valve budget open-valves]
      (let [{:keys [rate connections]} (get network valve)]
        (if (< budget open-time)
          {:flow 0 :open open-valves}
          (let [[time-cost valve-flow next-open-valves]
                ;; FIXME: opening may not produce optimal max even if not open and not zero
                (if (or (contains? open-valves valve) (zero? rate))
                  [travel-time 0 open-valves]
                  [(+ open-time travel-time) (* rate (- budget open-time)) (conj open-valves valve)])]
            (as-> connections $
              (map #(max-flow network % (- budget time-cost) next-open-valves) $)
              (apply (partial max-key :flow) $)
              (update $ :flow #(+ valve-flow %))))))))))

(def initial-budget 30)
(def initial-valve "AA")

(defn part-one [input]
  (-> input
      (parse-input)
      (max-flow initial-valve initial-budget)
      (get :flow)))

(defn part-two [input] false)

;; https://www.reddit.com/r/adventofcode/comments/znklnh/2022_day_16_some_extra_test_cases_for_day_16/

(comment (max-flow (parse-input (h/aoc-example-input 2022 16)) "AA" initial-budget))

(comment (h/aoc [2022 16] part-one part-two))
