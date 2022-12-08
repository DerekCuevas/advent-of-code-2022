(ns aoc-2022.days.seven
  (:require [aoc-2022.helper :as h]
            [aoc-2022.utils :as u]
            [clojure.string :as str]))

(def root-directory "/")

(def parent-directory "..")

(defn parse-command [s]
  (let [[_ command arg] (str/split s #" ")]
    {:type :command :command (keyword command) :argument arg}))

(defn parse-directory [s]
  {:type :directory :name (second (str/split s #" "))})

(defn parse-file [s]
  (let [[size name] (str/split s #" ")]
    {:type :file :size (u/parse-int size) :name name}))

(defn parse-input-line [s]
  (cond
    (str/starts-with? s "$") (parse-command s)
    (str/starts-with? s "dir") (parse-directory s)
    :else (parse-file s)))

(defn create-directory [name]
  {:name name :files '() :sub-directories {}})

(defn dir-path [cwd]
  (vec (interleave (repeat (count cwd) :sub-directories) cwd)))

(defn add-file [fs cwd file]
  (update-in fs (conj (dir-path cwd) :files) #(conj % file)))

(defn add-directory [fs cwd dir-name]
  (update-in fs (conj (dir-path cwd) :sub-directories) #(assoc % dir-name (create-directory dir-name))))

(def mock-fs (create-directory "/"))

(-> mock-fs
    (add-file [] {:name "foo.txt"})
    (add-directory [] "pine")
    (add-file [] {:name "bar.txt"})
    (add-directory [] "xx")
    (add-directory ["pine"] "apple")
    (add-file ["pine" "apple"] {:name "sponge.txt"}))

(defn build-fs [instructions]
  (let [fs (create-directory root-directory)
        cwd []]))

(defn part-one [input]
  (map parse-input-line input))

(defn part-two [input] false)

;; (h/aoc-input 2022 7)

(h/aoc [2022 7] part-one part-two)
