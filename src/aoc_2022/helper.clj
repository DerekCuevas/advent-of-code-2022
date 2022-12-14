(ns aoc-2022.helper
  (:require [clojure.string :as str]
            [clojure.test :as t]
            [clj-http.client :as client]
            [babashka.fs :as fs]))

(def ^:private base-dir ".aoc")

(def ^:private token (slurp (format "%s/session.txt" base-dir)))

(def ^:private aoc-base-url "https://adventofcode.com")

(defn- aoc-input-url [year day]
  (format "%s/%d/day/%d/input" aoc-base-url year day))

(defn- filepath [year day name]
  (format "%s/%d/day/%d/%s" base-dir year day name))

(defn- input-file [year day]
  (filepath year day "input.txt"))

(defn- example-input-file [year day]
  (filepath year day "example.txt"))

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

(defn aoc-example-input [year day]
  (let [filepath (example-input-file year day)]
    (when (fs/exists? filepath)
      (fs/read-all-lines filepath))))

(defn aoc [[year day] & parts]
  (let [input (aoc-input year day)]
    (map #(% input) parts)))

(defmacro defaoc-test [[year day] & parts]
  `(t/deftest ~(symbol (format "%d-%d-test" year day))
     ~@(map-indexed
        (fn [index [solve expect]]
          `(t/testing ~(format "part-%d" (inc index))
             (t/is (= (~solve (aoc-input ~year ~day)) ~expect))))
        (vec (partition 2 parts)))))
