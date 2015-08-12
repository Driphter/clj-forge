(defproject clj-forge "1.1.0-SNAPSHOT"
            :description ""
            :dependencies [[org.clojure/clojure "1.6.0"]
                           [com.google.guava/guava "18.0"]
                           [org.apache.logging.log4j/log4j-core "2.3"]
                           [net.minecraftforge/forgeSrc "1.7.10-10.13.4.1448-1.7.10"]]
            :plugins [[lein-cljfmt "0.1.10"]
                      [lein-kibit "0.1.2"]
                      [jonase/eastwood "0.2.1"]]
            :aot :all
            :source-paths ["src/main/clojure"]
            :repositories [["forge" "http://files.minecraftforge.net/maven"]])
