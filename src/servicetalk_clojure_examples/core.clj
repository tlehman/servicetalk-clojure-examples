(ns servicetalk-clojure-examples.core
  (:import (io.servicetalk.http.api BlockingHttpService
                                    DefaultHttpExecutionStrategy
                                    HttpSerializers))
  (:import (io.servicetalk.http.netty HttpServers)))


;; https://github.com/apple/servicetalk/blob/main/servicetalk-examples/http/helloworld/src/main/java/io/servicetalk/examples/http/helloworld/blocking/BlockingHelloWorldServer.java
(let [http-server-builder (HttpServers/forPort 8080)
      serializer (HttpSerializers/textSerializerUtf8)
      handler (proxy [BlockingHttpService][]
                (close []
                  (println "closing"))
                (requiredOffloads []
                  (DefaultHttpExecutionStrategy/OFFLOAD_RECEIVE_DATA_STRATEGY))
                (handle​ [ctx req resFactory]
                  (println "handling")))
      ]
  (-> http-server-builder
      (.listenBlockingAndAwait handler)
      (.awaitShutdown)))





(fn [ctx req resFactory]
  (-> resFactory
      (.ok)
      (.payloadBody "Hello, world!!" serializer)))

DefaultHttpExecutionStrategy/OFFLOAD_RECEIVE_DATA_STRATEGY

;io.servicetalk.http.api.DefaultHttpExecutionStrategy



