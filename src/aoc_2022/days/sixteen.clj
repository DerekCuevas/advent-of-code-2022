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

(comment (parse-input (h/aoc-example-input 2022 16)))

(defn part-one [input] false)

(defn part-two [input] false)

(comment (h/aoc [2022 16] part-one part-two))
