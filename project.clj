(defproject disintegration-of-loop "0.1.0"
  :description "DLP, THI"
  :url "http://example.chttps://twitter.com/Disintegr8Loopom/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/tools.trace "0.7.10"]
                 [org.clojure/core.match "1.0.0"]
                 [twttr "3.2.3"]
                 [jarohen/chime "0.3.2"]]
  :main ^:skip-aot disintegration-of-loop.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
