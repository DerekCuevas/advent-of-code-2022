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

(defn get-directory [fs cwd]
  (get-in fs (dir-path cwd)))

(defn compute-directory-size
  ([fs]
   (compute-directory-size fs [] {}))
  ([fs cwd dir->size]
   (let [current-dir (get-directory fs cwd)
         file-size (reduce + (map :size (get current-dir :files)))
         {directory-size :size updated-dir->size :dir->size}
         (reduce
          (fn [{acc-size :size acc-dir->size :dir->size} dir-name]
            (let [{cur-size :size cur-dir->size :dir->size}
                  (compute-directory-size fs (conj cwd dir-name) acc-dir->size)]
              {:size (+ acc-size cur-size)
               :dir->size (merge acc-dir->size cur-dir->size)}))
          {:size 0 :dir->size dir->size}
          (keys (get current-dir :sub-directories)))
         total-size (+ file-size directory-size)]
     {:size total-size
      :dir->size (if (empty? cwd) updated-dir->size
                     (assoc updated-dir->size cwd total-size))})))

(defn part-one [input]
  (->> input
       (map parse-input-line)
       (build-fs)
       (:fs)
       (compute-directory-size)
       (:dir->size)
       (vals)
       (filter #(<= % 100000))
       (reduce +)))

(def total-filesystem-size 70000000)

(def space-required 30000000)

(defn part-two [input]
  (let [{:keys [size dir->size]}
        (->> input
             (map parse-input-line)
             (build-fs)
             (:fs)
             (compute-directory-size))
        unused-space (- total-filesystem-size size)
        min-size-to-delete (- space-required unused-space)]
    (->> (vals dir->size)
         (filter #(>= % min-size-to-delete))
         (apply min))))

;; (h/aoc [2022 7] part-one part-two)
