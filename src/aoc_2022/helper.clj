(ns aoc-2022.helper
  (:require [clojure.string :as str])
  (:require [clj-http.client :as client])
  (:require [babashka.fs :as fs]))

(def ^:private base-dir ".aoc")

(def ^:private token (slurp (format "%s/session.txt" base-dir)))

(def ^:private aoc-base-url "https://adventofcode.com")

(defn- aoc-input-url [year day]
  (format "%s/%d/day/%d/input" aoc-base-url year day))

(defn- input-file [year day]
  (format "%s/%d/day/%d/input.txt" base-dir year day))

(defn- fetch-aoc-input [year day]
  (-> (aoc-input-url year day)
      (client/get {:cookies {"session" {:value token}}})
      (get :body)))

(defn aoc-input [year day]
  (let [filepath (input-file year day)]
    (if (fs/exists? filepath)
      (fs/read-all-lines filepath)
      (let [input (fetch-aoc-input year day)
            lines (str/split-lines input)]
        (fs/create-dirs (fs/parent filepath))
        (fs/write-lines filepath lines)
        lines))))
