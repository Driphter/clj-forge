(ns clj-forge.mod)

(defmacro defmod
  [class-name & {:keys [^String mod-id ^String mod-name ^String version pre-init init post-init]}]
  (let [prefix-sym   (gensym 'prefix)
        preinit-sym  (gensym 'preinit)
        init-sym     (gensym 'init)
        postinit-sym (gensym 'postinit)]
  `(do
     (gen-class
       :name ~(with-meta (symbol class-name)
                         `{cpw.mods.fml.common.Mod {:modid   ~(eval mod-id)
                                                    :name    ~(eval mod-name)
                                                    :version ~(eval version)}})
       :prefix ~prefix-sym
       :methods [[~(with-meta preinit-sym
                              `{cpw.mods.fml.common.Mod$EventHandler {}})
                  [cpw.mods.fml.common.event.FMLPreInitializationEvent] Void]
                 [~(with-meta init-sym
                              `{cpw.mods.fml.common.Mod$EventHandler {}})
                  [cpw.mods.fml.common.event.FMLPreInitializationEvent] Void]
                 [~(with-meta postinit-sym
                              `{cpw.mods.fml.common.Mod$EventHandler {}})
                  [cpw.mods.fml.common.event.FMLPreInitializationEvent] Void]])
     ~(when pre-init
       `(defn ~(symbol (str prefix-sym preinit-sym))
          [this# event#]
          (~pre-init event#)))
     ~(when init
       `(defn ~(symbol (str prefix-sym init-sym))
          [this# event#]
          (~init event#)))
     ~(when post-init
       `(defn ~(symbol (str prefix-sym postinit-sym))
          [this# event#]
          (~post-init event#))))))
