(ns disintegration-of-loop.twitter
  (:require [twttr.api :as api]
            [twttr.auth :refer [env->UserCredentials]]
            [chime.core :as chime]
            [disintegration-of-loop.core :as core])
  (:import [java.time Instant Duration]))
             
(def creds (env->UserCredentials))

(defn poast! [s] (api/statuses-update creds :params {:status s}))

(def cursor (atom core/text))

(defn scroll! []
  (let [ret (first @cursor)]
    (swap! cursor next)
    ret))

(defn scroll-and-poast! []
  (let [tweet (scroll!)]
    (poast! tweet)))

(def schedule
 (vec
   (rest
    (let [now (Instant/now)
          step 300
          duration 3000]
      (for [x (range 0 duration step)]
        (.plusSeconds now x))))))
 
(chime/chime-at schedule
                (fn [time] (scroll-and-poast!)))
