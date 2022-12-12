(ns hooks.loopr
  (:require [clj-kondo.hooks-api :as api]))

(defn loopr
  "Transforms `loopr` forms into the following, so that clj-kondo can
  understand it:
      (do
        (loop <acc-bindings>
          (let <elem-bindings>
            <body>))
        <final>)
  "
  [{:keys [node]}]
  (let [[acc-bindings elem-bindings body & [final]] (rest (:children node))
        acc-bindings (:children acc-bindings)
        elem-bindings (:children elem-bindings)]
    (when (or (nil? elem-bindings) (empty? elem-bindings))
      (throw (ex-info "No element bindings for loopr to iterate over" {})))
    (let [loop-node (api/list-node
                     [(api/token-node 'loop)
                      (api/vector-node acc-bindings)
                      (api/list-node
                       [(api/token-node 'let)
                        (api/vector-node elem-bindings)
                        body])])]
      {:node (if final
               (api/list-node
                [(api/token-node 'do)
                 loop-node
                 final])
               loop-node)})))