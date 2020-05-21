(ns clojure-examples.core
  (:require [clojure.spec.alpha :as s]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(s/def ::even? (s/and integer? even?))
(s/def ::odd? (s/and integer? odd?))
(s/def ::a integer?)
(s/def ::b integer?)
(s/def ::c integer?)
(def s (s/cat :forty-two #{42}
              :odds (s/+ ::odd?)
              :m (s/keys :req-un [::a ::b ::c])
              :oes (s/* (s/cat :o ::odd? :e ::even?))
              :ex (s/alt :odd ::odd? :even ::even?)))

(s/conform s [42 11 13 15 {:a 1 :b 2 :c 3} 1 2 3 42 43 44 11])
;; => {:forty-two 42,
;;     :odds [11 13 15],
;;     :m {:a 1, :b 2, :c 3},
;;     :oes [{:o 1, :e 2} {:o 3, :e 42} {:o 43, :e 44}],
;;     :ex [:odd 11]}

(s/conform  even? 1000)
(s/valid? nil? nil)
;; => true
(s/valid? string? "abc")
;; => true

(s/valid? #(> % 5) 10)
;; => true
(s/valid? #(> % 5) 0)
;; => false

(import java.util.Date)
(s/valid? inst? (Date.))
;; => true

(s/valid? #{:club :diamond :heart :spade} :club)
;; => true
(s/valid? #{:club :diamond :heart :spade} 42);; => false
(s/valid? #{42} 42) 
;; => true

(s/def ::date inst?)
;; => :clojure-examples.core/date
(s/def ::suit #{:club :diamond :heart :spade})
;; => :clojure-examples.core/suit

(s/valid? ::date (java.util.Date.))
;; => true
(s/conform ::suit :club)
;; => :club

(doc ::date)
(doc ::suit)

(s/def ::big-even (s/and int? even? #(> % 1000)))
;; => :clojure-examples.core/big-even
(s/valid? ::big-even :foo)
;; => false
(s/valid? ::big-even 10)
;; => false
(s/valid? ::big-even 100000)
;; => true

(s/def ::name-or-id (s/or :name string? 
                          :id   int?))
;; => :clojure-examples.core/name-or-id
(s/valid? ::name-or-id "abc")
;; => true
(s/valid? ::name-or-id 100)
;; => true
(s/valid? ::name-or-id :foo)
;; => false
(s/conform ::name-or-id "abc")
;; => [:name "abc"]
(s/conform ::name-or-id 100)
;; => [:id 100]

(s/valid? string? nil)
;; => false
(s/valid? (s/nilable string?) nil)
;; => true

;; s/explain prints to repl
(s/explain ::suit 42)
;; => nil
(s/explain ::big-even 5)
(s/explain ::name-or-id :foo)

;; s/explain-____ for return values:
(s/explain-data ::name-or-id :foo)
;; => #:clojure.spec.alpha{:problems
;;                         ({:path [:name],
;;                           :pred clojure.core/string?,
;;                           :val :foo,
;;                           :via [:clojure-examples.core/name-or-id],
;;                           :in []}
;;                          {:path [:id],
;;                           :pred clojure.core/int?,
;;                           :val :foo,
;;                           :via [:clojure-examples.core/name-or-id],
;;                           :in []}),
;;                         :spec :clojure-examples.core/name-or-id,
;;                         :value :foo}
(s/explain-str ::name-or-id :foo)
;; => ":foo - failed: string? at: [:name] spec: :clojure-examples.core/name-or-id\n:foo - failed: int? at: [:id] spec: :clojure-examples.core/name-or-id\n"

