# servicetalk-clojure-examples

[ServiceTalk](https://github.com/apple/servicetalk) is an open source Java networking framework made by Apple. It supports HTTP/2 and is built on top of Netty, which uses non-heap memory objects like [ByteBuf](https://netty.io/4.1/api/io/netty/buffer/ByteBuf.html).

This project shows how to use ServiceTalk inside a Clojure program.

## Usage

This project uses [lein](https://leiningen.org/)

    $ lein run
    
## Why build a Clojure wrapper around this Java framework?

The ServiceTalk Java framework supports asynchronous, non-blocking network activity. This fits well with the Clojure functional 
programming paradigm, but a naive attempt to import this directly into clojure runs into problems:

### Function type errors

Imagine we made a Clojure project and tried to make an `HttpServer`:

```clojure
(let [http-server-builder (io.servicetalk.http.netty.HttpServers/forPort 8080)
      serializer (io.servicetalk.http.api.HttpSerializers/textSerializerUtf8)]
  (-> http-server-builder
      (.listenBlockingAndAwait (fn [ctx req resFactory]
                                 (-> resFactory
                                     (.ok)
                                     (.payloadBody "Hello, world!!" serializer))))
      (.awaitShutdown)))
```

You would run into this `ClassCastException:`

```
java.lang.ClassCastException: 
  class servicetalk_clojure_examples.core$eval2495$fn__2496 cannot be cast to 
  class io.servicetalk.http.api.BlockingHttpService 

  (
    servicetalk_clojure_examples.core$eval2495$fn__2496 is in unnamed module of loader clojure.lang.DynamicClassLoader @6ebbf4bc; 
    io.servicetalk.http.api.BlockingHttpService is in unnamed module of loader 'app'
  )
```

That `eval2495$fn_2496` is the clojure function: `(fn [ctx req resFactory] (-> resFactory (.ok) (.payloadBody "Hello, world!!" (io.servicetalk.http.api.HttpSerializers/textSerializerUtf8))))` 

Right now, clojure functions don't conform to the `io.servicetalk.http.api.BlockingHttpService` interface.

So in the process of writing these Clojure ServiceTalk Examples, I needed to wrap these clojure functions so they conform to the interface and cast 
properly.

https://apple.github.io/servicetalk/servicetalk/0.41/javadoc/io/servicetalk/http/api/BlockingHttpService.html
(in a nutshell, that means they need a `void close()` and an `HttpResponse handle(...)` method, the function itself _is_ the handle method 
so this is not a huge lift)



## License

Copyright © 2023 Tobi Lehman

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
