(defproject clojure-examples "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :plugins [[lein-codox "0.10.7"]]
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [incanter "1.5.4"]
                 [thi.ng/geom "1.0.0-RC4"]
                 [thi.ng/luxor "0.3.1"]
                 [org.jogamp.gluegen/gluegen-rt "2.3.2" :classifier "natives-linux-amd64"]
                 [org.jogamp.jogl/jogl-all "2.3.2" :classifier "natives-linux-amd64"]]
  :repl-options {:init-ns clojure-examples.core})
