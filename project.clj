(defproject servicetalk-clojure-examples "0.1.0-SNAPSHOT"
  :description "ServiceTalk network framework examples in Clojure"
  :url "https://github.com/tlehman/servicetalk-clojure-examples"
  :license {:name "EPL-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [
                 [org.clojure/clojure "1.11.1"]
                 [io.servicetalk/servicetalk-http-netty "0.42.30"]
                 ]
  :main ^:skip-aot servicetalk-clojure-examples.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
