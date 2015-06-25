(ns clj-forge.crafting
  (:use [clj-forge.items :only [item-stack]])
  (:import (cpw.mods.fml.common.registry GameRegistry)))

(defn- -shapeless-recipe
  [result material-map ingredients]
  (GameRegistry/addShapelessRecipe (item-stack result)
                                   (into-array Object (map material-map ingredients))))

(defn- -shaped-recipe
  [result material-map ingredients]
  (GameRegistry/addShapedRecipe (item-stack result)
                                (->> (concat ingredients material-map)
                                     flatten
                                     (into-array Object))))

(defn register-recipe
  [result material-map ingredients]
  (if (coll? ingredients)
    (-shaped-recipe result material-map ingredients)
    (-shapeless-recipe result material-map ingredients)))

(defn register-smelting
  [ingredients result exp]
  (GameRegistry/addSmelting (item-stack ingredients)
                            (item-stack result)
                            (float exp)))
