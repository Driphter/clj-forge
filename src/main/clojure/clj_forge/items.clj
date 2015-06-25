;@formatter:o
(ns clj-forge.items
  (:use [clojure.string :only [lower-case]])
  (:import (net.minecraft.item Item ItemStack)
           (cpw.mods.fml.common.registry GameRegistry)
           (net.minecraft.block Block)))

(defn- -item-stack
  [^Item item quantity metadata]
  (ItemStack. item
              (int (or quantity 1))
              (int (or metadata 0))))

(defn ^ItemStack item-stack
  ([item]
   (item-stack item nil nil))
  ([item quantity]
   (item-stack item quantity nil))
  ([item quantity metadata]
   (condp instance? item
     Item      (-item-stack item quantity metadata)
     Block     (-item-stack (Item/getItemFromBlock item)
                            quantity
                            metadata)
     ItemStack (let [^ItemStack stack item]
                 (-item-stack (.getItem stack)
                              (or quantity (.stackSize stack))
                              (or metadata (.getItemDamage stack))))
     nil)))

(defn register-item
  [& items]
  (doseq [^Item item items]
    (GameRegistry/registerItem item (-> item
                                        .getUnlocalizedName
                                        (subs 5)))))

(defn create-item
  [& {:keys [name
             mod-id
             creative-tab
             texture-name]}]
  (let [item (Item.)]
    (when name
      (.setUnlocalizedName item name))
    (when creative-tab
      (.setCreativeTab item creative-tab))
    (when (and mod-id (or name texture-name))
      (.setTextureName item (lower-case (str mod-id ":"
                                             (or texture-name name)))))
    item))
