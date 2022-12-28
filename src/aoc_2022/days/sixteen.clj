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

(def max-flow
  (memoize
   (fn [network valve open-valves budget]
     (let [{:keys [rate connections]} (get network valve)]
       (if (<= budget 0)
         {:max-flow 0 :open-valves open-valves}
         (let [[time-cost valve-flow next-open-valves]
               (if (or (contains? open-valves valve) (zero? rate))
                 [travel-time 0 open-valves]
                 [(+ open-time travel-time) (* rate (- budget open-time)) (conj open-valves valve)])
               connections-max-flow (map #(max-flow network % next-open-valves (- budget time-cost)) connections)]
           (-> (apply (partial max-key :max-flow) connections-max-flow)
               (update :max-flow #(+ valve-flow %)))))))))

(defn part-one [input] false)

(defn part-two [input] false)

(def example-network (parse-input (h/aoc-example-input 2022 16)))

(comment (max-flow example-network "AA" #{} initial-budget))

(comment (h/aoc [2022 16] part-one part-two))
