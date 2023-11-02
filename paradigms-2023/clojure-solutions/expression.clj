
(defn constant [value] (fn [maps] value))

(defn variable [value]
  (fn [maps]
    (maps value)
    )
  )
(defn operation [f]
  (fn [a b]
    (fn [maps] (f ^double (a maps) ^double (b maps)))
    )
  )

(defn unary [f] (fn [a] (fn [maps] f (a maps))))
(defn divide [a, b]
  (fn [maps]
    (cond
      (zero? (b maps)) 0
      :else (/ (a maps) (b maps))
      )
    )
  )

(def add (operation +))
(def subtract (operation -))
(def multiply (operation *))
(defn negate [a] (fn [maps] (- (a maps))))
(defn exp [x] (fn [maps] (Math/exp (x maps))))
(defn ln [x] (fn [maps] (Math/log (x maps))))
(defn arcTan [x] (fn [maps] (Math/atan (x maps))))
(def arcTan2 (operation #(Math/atan2 %1 %2)))
(def atan arcTan)
(def atan2 arcTan2)
(def mapOfOperation
  {"+" add
   "-" subtract
   "*" multiply
   "/" divide
   "negate" negate
   "exp" exp
   "ln" ln
   "atan" arcTan
   "atan2" arcTan2
   }
  )

(def mapOfVariable
  {"x" (variable "x")
   "y" (variable "y")
   "z" (variable "z")
   }
  )

(defn parse [input vars con mapOfOperation]
  (let [expr_part (read-string input)]
    (cond
      (symbol? expr_part) (vars (name expr_part))
      (number? expr_part) (con expr_part)
      (list? expr_part) (
                          apply (mapOfOperation (name (first expr_part))) (map (fn [x] (parse (str x) vars con mapOfOperation)) (rest expr_part))
                                )
      )
    )
  )

(defn parseFunction [input] (parse input mapOfVariable constant mapOfOperation))


;=====================================HW 11==================================
(load-file "proto.clj")


(def evaluate (method :evaluate))
(def toString (method :toString))
(def toStringPostfix (method :toStringPostfix))
(defn allConstructor [this x]
  (assoc this :x x))

(def Constant (constructor
                allConstructor
                {
                 :toString (fn [this] (str (this :x)))
                 :evaluate (fn [this expr] (this :x))
                 :toStringPostfix (fn [this] (str (this :x)))
                 }
                )
  )

(def Variable (constructor
                allConstructor
                {:toString (fn [this] (str (this :x)))
                 :evaluate (fn [this expr] (expr (clojure.string/lower-case (subs (this :x) 0 1))))
                 :toStringPostfix (fn [this] (str (this :x)))
                 }))
(defn BinConstructor [this x y]
  (assoc this :x x :y y))
(defn BinOperation [op opType] (constructor
                                 BinConstructor {
                                            :toString (fn [this] (str "(" opType " " (clojure.string/join " " (vector (toString (this :x)) (toString (this :y)))) ")" ))
                                            :evaluate (fn [this expr] (op (evaluate (this :x) expr) (evaluate (this :y) expr)))
                                            :toStringPostfix (fn [this] (str "("(clojure.string/join " " (vector (toStringPostfix (this :x)) (toStringPostfix (this :y))))  " " opType ")"))
                                            }
                            )
  )
(defn UnaryConstructor [this x]
  (assoc this :x x))

(defn UnaryOperation [op opType] (constructor
                                   UnaryConstructor {
                                                            :toString (fn [this] (str "(" opType " " (toString (this :x)) ")"))
                                                            :evaluate (fn [this expr] (op (evaluate (this :x) expr)))
                                                            :toStringPostfix (fn [this] (str "(" (toStringPostfix (this :x)) " " opType ")"))
                                                            }
                                            )
  )

(defn divide_custom [x y]
  (cond
    (zero? y) 0
    :else (/ x y)
    )
  )


(defn sinh_custom [x] (Math/sinh x))
(defn cosh_custom [x] (Math/cosh x))
(defn inc_custom [x] (+ x 1))
(defn dec_custom [x] (- x 1))
(defn upow_custom [x] (Math/exp x))
(defn ulog_custom [x] (Math/log (Math/abs x)))
(def Add (BinOperation + "+"))
(def Subtract (BinOperation - "-"))
(def Multiply (BinOperation * "*"))
(def Divide (BinOperation divide_custom "/"))
(def Negate (UnaryOperation - "negate"))
(def Sinh (UnaryOperation sinh_custom "sinh"))
(def UPow (UnaryOperation upow_custom "**"))
(def ULog (UnaryOperation ulog_custom "//"))
(def Cosh (UnaryOperation cosh_custom "cosh"))
(def Inc (UnaryOperation inc_custom "++"))
(def Dec (UnaryOperation dec_custom "--"))
(defn diff_add [x y var] (Add (.diff x var) (.diff y var)))
(defn diff_subtract [x y var] (Subtract (.diff x var) (.diff y var)))
(defn diff_multiply [x y var] (Add (Multiply (.diff x var) y) (Multiply x (.diff y var))))
(defn diff_divide [x y var] (Divide (Subtract (Multiply (.diff x var) y) (Multiply x (.diff y var))) (Multiply y y)))
(defn diff_negate [x var] (Negate (.diff x var)))
(defn diff_ln [x var] (Divide (.diff x var) x))

(defn exp_custom [x] (Math/exp x))
(defn ln_custom [x] (Math/log x))
(defn sin_custom [x] (Math/sin x))
(defn cos_custom [x] (Math/cos x))
(defn pow_custom [x y] (Math/pow x y))
(defn log_custom [x y] (divide_custom (Math/log (Math/abs y)) (Math/log (Math/abs x))))

(def objectExpr {
                 "+" Add
                 "-" Subtract
                 "*" Multiply
                 "/" Divide
                 "negate" Negate
                 "sinh" Sinh
                 "cosh" Cosh
                 "++" Dec
                 "--" Inc
                 "upow" UPow
                 "ulog" ULog
                 })

(defn parseObject [input] (parse input Variable Constant objectExpr))

;======================================================HW 12=================================================================
(load-file "parser.clj")
(def +list_char (mapv char (range 0 128)))
(def +digit (+char "0123456789"))
(def +number (+seqf cons (+opt (+char "-")) (+plus (+or (+char ".") +digit))))
(def +parserVariables (+char "xyzXYZ"))
(def +parseOp (+or (+seqf (constantly 'upow) (+char "*") (+char "*"))
                   (+seqf (constantly 'ulog) (+char "/") (+char "/"))
                   (+seqf (constantly 'negate) (+char "n") (+char "e") (+char "g") (+char "a") (+char "t") (+char "e"))
                   (+char "*-+/")))
(def *constant (+map (fn [value] (Constant (Double/parseDouble value))) (+str +number)))
(def *variable (+map (fn [x] (Variable (str x))) (+str (+plus +parserVariables))))
(def *op (+map (fn [x] (str x)) +parseOp))
(def +spaces (+char (apply str (filter #(Character/isWhitespace %) +list_char))))
(def *ws (+ignore (+star +spaces)))
(def parseObjectPr
  (letfn [
          (*argsUnary [] (+seq *ws (delay (*value)) *ws +parseOp *ws))
          (*argsBin [] (+seq *ws (delay (*value)) *ws (delay (*value)) *ws +parseOp *ws))
          (*args [] (+or (*argsBin) (*argsUnary)))
          (*value [] (+or *constant *variable (*makeOp)))
          (*seq [] (+seqn 1 *ws (+char "(") (*args) (+char ")") *ws))
          (*makeOp [] (+map #(apply (objectExpr (str (last %))) (butlast %)) (*seq)))
          ]
    (+parser (+seqn 0 *ws (*value) *ws))))


(defn parseObjectPostfix [input] (parseObjectPr input))

