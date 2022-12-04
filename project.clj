(defproject aoc-2022 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [clj-http "3.12.3"]
                 [babashka/fs "0.2.12"]
                 [criterium "0.4.6"]
                 [org.clojure/core.match "1.0.0"]]
  :repl-options {:init-ns aoc-2022.helper}
  :profiles {:dev {:plugins [[com.jakemccrary/lein-test-refresh "0.25.0"]]}})
