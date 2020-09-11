(ns disintegration-of-loop.core
  (:require [clojure.zip :as zip])
  (:gen-class))

(defn- disintegrate-
  "Returns a modified s where the next to-blank non-blank characters
    after offset are replaced with whitespace."
  [s offset to-blank]
  (loop [x (zip/seq-zip (seq s))
         o offset
         n to-blank]
    (let [v (zip/node x)
          blank-char \ ]
      (cond
        (or (zip/end? x) (<= n 0)) (clojure.string/join (zip/root x))
        (or (not (char? v)) (= v blank-char)) (recur (zip/next x) o n)
        (not (<= o 0)) (recur (zip/next x) (dec o) n)
        :else (recur (zip/edit x (constantly blank-char)) 0 (dec n))))))

(defn- seeded-disintegrate
  "Takes a string and seed and computes offset and to-blank
  then applies disintegrate-."
  [string seed]
  (let [offset (mod seed (count string))
        to-blank 1]
    (disintegrate- string offset to-blank)))

(defn disintegrate
  "Disintegrates a string."
  [s]
  (let [abs #(if (< % 0) (* -1 %) %)
        hashfn (comp abs hash)
        seed (hashfn s)
        stepfn (fn ([[string seed]]
                    [(seeded-disintegrate string seed) (hashfn seed)]))]
    (map first (iterate stepfn [s seed]))))
  
(def copypasta "I saw William Basinski at a grocery store in Los Angeles yesterday. I told him how cool it was to meet him in person, but I didn’t want to bother him and ask him for photos or anything. He said, “Oh, like you’re doing now?” I was taken aback, and all I could say was “Huh?”")

(def text
 (distinct
   (take-while (complement clojure.string/blank?) (disintegrate copypasta)))) 
