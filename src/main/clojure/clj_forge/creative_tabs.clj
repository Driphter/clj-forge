(ns clj-forge.creative-tabs
  (:import (net.minecraft.creativetab CreativeTabs)
           (cpw.mods.fml.relauncher SideOnly Side)
           (cpw.mods.fml.common.registry GameRegistry)))

;noinspection ClojureUnusedGlobalDeclaration
(defn create-tab
  [& {:keys [name
             mod-id
             icon-item-name]}]
  (when name
    (proxy [CreativeTabs] [(CreativeTabs/getNextID) name]
      ^{SideOnly [Side/CLIENT]}
      (getTabIconItem []
        (GameRegistry/findItem (str mod-id)
                               (str icon-item-name))))))
