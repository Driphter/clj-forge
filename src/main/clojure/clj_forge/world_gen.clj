(ns clj-forge.world-gen
  (:import (cpw.mods.fml.common.registry GameRegistry)
           (net.minecraft.world.gen.feature WorldGenMinable)
           (java.util Random)
           (cpw.mods.fml.common IWorldGenerator)))

(defn register-world-gen
  ([gen-fn]
   (register-world-gen gen-fn 0))
  ([gen-fn weight]
   (let [world-gen (reify IWorldGenerator
                     (generate
                       [_ random x z world _ _]
                       (gen-fn random x z world)))]
     (GameRegistry/registerWorldGenerator world-gen (int weight)))))

(defn gen-minable
  ([block world random x y z]
   (gen-minable block 0 1 world random x y z))
  ([block, min-size, max-size, world ^Random random x y z]
   (let [vein-size (+ min-size (.nextInt random (- max-size min-size)))]
     (.generate (WorldGenMinable. block vein-size)
                world random x y z))))
