(ns euler5.core
  (:require [reagent.core :as reagent :refer [atom]]
            [clojure.core :as core]))

(defonce app-state (atom {:text "The largest palindrome made from the product of two n-digit numbers"}))

(defn palindrome? [word]
  (let [strword (str word)]
    (if (= strword (apply str (reverse strword))) true false)))

(defn numarray [n]
  (for [x (vec (to-array (range 0 n)))
        :let [basearray (* 9 (Math/pow 10 x))]]
    basearray))

(defn palindromeArray
  "returns the largest integer that is the product of two intergers of length
  s that is also a Palindrome"
  [number]
  (do
    (let [
          rangevec (rseq (vec (to-array (range (reduce + (numarray (- number 1))) (inc (reduce + (numarray number)))))))]
      (for [x rangevec
            y rangevec
            :let [z (* x y)]
            :when (palindrome? z)
            :while (not= x y)]
        [x y z]
        ))))
(defn on-js-reload []
  (swap! app-state update-in [:__figwheel_counter] inc)
  )

(defn counting-component []
  (let [click-count (reagent/atom 3)]
    (fn []
      [:h1
       [:div [:input {:type "button" :value "Click me!" :on-click (if (>= @click-count 6) #(reset! click-count 1) #(swap! click-count inc))}]
        " The largest palindrome from two "  @click-count " digit numbers: "
        (str (first (first (palindromeArray @click-count))))
        " x "
        (str (second (first (palindromeArray @click-count))))
        " = "
        (str (last (first (palindromeArray @click-count))))]])))

(when-let [element (js/document.getElementById  "app")]
  (reagent/render-component [counting-component] element))

(.log js/console "Greetings. Thank you for reviewing this submission")

(comment
  (.log js/console "firstPalindrome " (str (first (palindromeArray 5))))
  (.log js/console "reduce bignumarray " (reduce + (numarray 5)))
  (.log js/console "reduce bignumarray -1" (reduce + (numarray (- 5 1)))))


