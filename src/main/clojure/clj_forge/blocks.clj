(ns clj-forge.blocks
  (:use [clojure.string :only [lower-case]])
  (:import (cpw.mods.fml.common.registry GameRegistry)
           (net.minecraft.block Block)))

(defn register-block
  [& blocks]
    (doseq [^Block block blocks]
      (GameRegistry/registerBlock block (-> block
                                            .getUnlocalizedName
                                            (subs 5)))))

(defn create-block
  [& {:keys [name
             mod-id
             material
             creative-tab
             texture-name
             step-sound
             hardness
             resistance
             harvest-tool
             harvest-level
             unbreakable
             light-level]}]
  (when material
    (let [block (proxy [Block] [material])]
      (when name
        (.setBlockName block (str name)))
      (when creative-tab
        (.setCreativeTab block creative-tab))
      (when (and mod-id (or name texture-name))
        (.setBlockTextureName block (lower-case (str mod-id ":"
                                                     (or texture-name name)))))
      (when step-sound
        (.setStepSound block step-sound))
      (when hardness
        (.setHardness block (float hardness)))
      (when resistance
        (.setResistance block (float resistance)))
      (when (and harvest-tool harvest-level)
        (.setHarvestLevel block (str harvest-tool) harvest-level))
      (when unbreakable
        (.setBlockUnbreakable block))
      (when light-level
        (.setLightLevel block (float light-level)))
      block)))
