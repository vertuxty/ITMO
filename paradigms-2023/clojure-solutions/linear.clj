(defn operation [f] (fn fun [val1 val2] (mapv f val1 val2)))
(def v+ (operation +))
(def v- (operation -))
(def v* (operation *))
(def vd (operation /))
(defn scalar [a b] (reduce + (v* a b)))
(defn m*s_v*s [f] (fn [a b] (mapv (fn [x] (f x b)) a)))
(def v*s (m*s_v*s *))
(def m*s (m*s_v*s v*s))
(def m+ (operation v+))
(def m- (operation v-))
(def m* (operation v*))
(def md (operation vd))
(defn m*v [m v] (mapv (fn [x] (scalar x v)) m))
(defn transpose [m] (apply mapv vector m))
(defn m*m [m1 m2] (mapv (fn [x] (m*v (transpose m2) x)) m1))
(defn vect [v1 v2]
  (let [[a1, a2, a3] v1
        matrixOfv1 [[0, (- a3), a2], [a3, 0, (- a1)], [(- a2), a1, 0]]]
    (m*v matrixOfv1 v2)
    )
  )

(defn v_dig [f s1 s2] (letfn [(func [p1 p2] (cond
                                       (number? p1) (f p1 p2)
                                       (vector? p1) (mapv func p1 p2)
                                       ))]
                  (func s1 s2)
                  )
  )

(def s+ (partial v_dig +))
(def s- (partial v_dig -))
(def s* (partial v_dig *))
(def sd (partial v_dig /))