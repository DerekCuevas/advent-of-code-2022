(ns aoc-2022.days.seven
  (:require [aoc-2022.helper :as h]
            [aoc-2022.utils :as u]
            [clojure.string :as str]
            [clojure.core.match :as m]))

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

(defn create-file [name size]
  {:name name :size size})

(defn dir-path [cwd]
  (vec (interleave (repeat (count cwd) :sub-directories) cwd)))

(defn add-file [fs cwd name size]
  (update-in fs (conj (dir-path cwd) :files) #(conj % (create-file name size))))

(defn add-directory [fs cwd dir-name]
  (update-in fs (conj (dir-path cwd) :sub-directories) #(assoc % dir-name (create-directory dir-name))))

(defn build-fs [instructions]
  (reduce
   (fn [{:keys [fs cwd]} instruction]
     (m/match instruction
       {:type :file :size size :name name} {:fs (add-file fs cwd name size) :cwd cwd}
       {:type :directory :name name} {:fs (add-directory fs cwd name) :cwd cwd}
       {:type :command :command :cd :argument "/"} {:fs fs :cwd []}
       {:type :command :command :cd :argument ".."} {:fs fs :cwd (pop cwd)}
       {:type :command :command :cd :argument dir-name} {:fs fs :cwd (conj cwd dir-name)}
       :else {:fs fs :cwd cwd}))
   {:fs (create-directory "/") :cwd []}
   instructions))

(defn part-one [input]
  (->> input
       (map parse-input-line)
       (build-fs)))

(defn part-two [input] false)

(h/aoc [2022 7] part-one part-two)

;; scratch

(def mock-fs (create-directory "/"))

(-> mock-fs
    (add-file [] "foo.txt" 0)
    (add-directory [] "pine")
    (add-file [] "bar.txt" 10)
    (add-directory [] "xx")
    (add-directory ["pine"] "apple")
    (add-file ["pine" "apple"] "sponge.txt" 34))

(def mock-instructions [{:type :command, :command :cd, :argument "/"}
                        {:type :command, :command :ls, :argument nil}
                        {:type :directory, :name "cvt"}
                        {:type :file, :size 4967, :name "hcqbmwc.gts"}
                        {:type :file, :size 5512, :name "hsbhwb.clj"}
                        {:type :directory, :name "hvfvt"}
                        {:type :directory, :name "phwgv"}
                        {:type :file, :size 277125, :name "pwgswq.fld"}
                        {:type :file, :size 42131, :name "qdzr.btl"}])

(build-fs mock-instructions)
