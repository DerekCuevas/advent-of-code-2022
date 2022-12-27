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

(def initial-budget 30)
(def open-time 1)
(def travel-time 1)

(def max-flow-possible
  (memoize
   (fn [lookup budget valve]
     (let [{:keys [rate connections]} (get lookup valve)]
       (cond
         (= budget 0) 0
         (= budget open-time) rate
         :else
         (let [cost (if (zero? rate) travel-time (+ open-time travel-time))
               remaining-budget (- budget cost)
               connections-max-flow-possible (map #(max-flow-possible lookup remaining-budget %) connections)]
           (+ (* rate (- budget open-time)) (apply max connections-max-flow-possible))))))))

(defn part-one [input] false)

(defn part-two [input] false)

(def example-lookup (parse-input (h/aoc-example-input 2022 16)))

(max-flow-possible example-lookup initial-budget "AA")

(comment (h/aoc [2022 16] part-one part-two))
