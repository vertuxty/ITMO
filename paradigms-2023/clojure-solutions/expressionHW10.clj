
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


(defn parseFunction [input]
  (let [expr_part (read-string input)]
    (cond
      (symbol? expr_part) (mapOfVariable (name expr_part))
      (number? expr_part) (constant expr_part)
      (list? expr_part) (
                          apply (mapOfOperation (name (first expr_part))) (map (fn [x] (parseFunction (str x))) (rest expr_part))
                         )
      )
    )
  )


;=====================================HW 11===============================

(defn evaluate [x vars] (.evaluate x vars))
(defn toString [x] (.toString x))
(defn diff [x var] (.diff x var))
(defn toStringSuffix [this] (.toStringSuffix this))

(definterface Operation
  (^Number evaluate [expr])
  (^String toStringSuffix [])
  (diff [expr]))

(deftype BinOp [opType op diff_ x y]
  Object
  (toString [this]
    (str "("  opType " " (clojure.string/join " " (vector (.toString x) (.toString y))) ")")
    )
  Operation
  (evaluate [this expr] (op (.evaluate x expr) (.evaluate y expr)))
  (diff [this var] (diff_ x y var))
  (toStringSuffix [this] (str "("(clojure.string/join " " (vector (.toStringSuffix x) (.toStringSuffix y)))  " " opType ")"))
  )

(deftype UnaryOp [opType op diff_f x]
  Object
  (toString [this]
    (str "(" opType " " (.toString x) ")")
    )
  Operation
  (evaluate [this expr] (op (.evaluate x expr)))
  (diff [this expr] (diff_f x expr))
  (toStringSuffix [this] (str "(" (.toStringSuffix x) " " opType ")"))
  )

(deftype ConstConstructor [x]
  Object
  (toString [this] (str x))
  Operation
  (evaluate [this expr] x)
  (diff [this expr] (ConstConstructor. 0))
  (toStringSuffix [this] (str x))
  )

(defn Constant [x] (ConstConstructor. x))

(deftype VariableConstructor [val]
  Object
  (toString [this] (str val))
  Operation
  (evaluate [this expr] (expr val))
  (diff [this expr] (cond
                      (= expr val) (Constant 1)
                      :else (Constant 0))
    )
  (toStringSuffix [this] (str val))
  )

(defn divide_custom [x y]
  (cond
    (zero? y) 0
    :else (/ x y)
        )
  )
(defn exp_custom [x] (Math/exp x))
(defn ln_custom [x] (Math/log x))
(defn pow_custom [x y] (Math/pow x y))
(defn log_custom [x y] (divide_custom (Math/log (Math/abs y)) (Math/log (Math/abs x))))

(defn Variable [x] (VariableConstructor. x))

(declare diff_add)
(declare diff_subtract)
(declare diff_multiply)
(declare diff_divide)
(declare diff_negate)
(declare diff_exp)
(declare diff_ln)
(declare diff_log)
(declare diff_pow)

(defn Add [x y] (BinOp. "+" + diff_add x y))
(defn Subtract [x y] (BinOp. "-" - diff_subtract x y))
(defn Multiply [x y] (BinOp. "*" * diff_multiply x y))
(defn Divide [x y] (BinOp. "/" divide_custom diff_divide x y))
(defn Negate [x] (UnaryOp. "negate" - diff_negate x))
(defn Pow [x y] (BinOp. "myBase.pow" pow_custom diff_pow x y))
(defn Log [x y] (BinOp. "log" log_custom diff_log x y))

(defn Exp [x] (UnaryOp. "exp" exp_custom diff_exp x))
(defn Ln [x] (UnaryOp. "ln" ln_custom diff_ln x))


(defn diff_add [x y var] (Add (.diff x var) (.diff y var)))
(defn diff_subtract [x y var] (Subtract (.diff x var) (.diff y var)))
(defn diff_multiply [x y var] (Add (Multiply (.diff x var) y) (Multiply x (.diff y var))))
(defn diff_divide [x y var] (Divide (Subtract (Multiply (.diff x var) y) (Multiply x (.diff y var))) (Multiply y y)))
(defn diff_negate [x var] (Negate (.diff x var)))
(defn diff_exp [x var] (Multiply (.diff x var) (Exp x)))
(defn diff_ln [x var] (Divide (.diff x var) x))

(defn diff_pow [x y var] (Multiply y (Pow (.diff x var) (Subtract (.diff y var) (Constant 1)))))

(def objectExpr {
                 "+" Add
                 "-" Subtract
                 "*" Multiply
                 "/" Divide
                 "negate" Negate
                 "exp" Exp
                 "ln" Ln
                 "myBase.pow" Pow
                 "log" Log
                 })

(defn parseObject [input]
  (let [expr_part (read-string input)]
    (cond
      (symbol? expr_part) (Variable (str expr_part))
      (number? expr_part) (Constant expr_part)
      (list? expr_part) (
                          apply (objectExpr (name (first expr_part))) (map (fn [x] (parseObject (str x))) (rest expr_part))
                                )
      )
    )
  )


;======================================================HW 12=================================================================

;(load-file "parser.clj")
;
;;(def +all-letters (mapv char (range 0 128)))
;;(def *alpha (+char (apply str (filter #(Character/isLetter %) +all-letters))))
;;(def +digit (+char "0123456789"))
;;(def +number (+seqf cons (+opt (+char "-")) (+plus (+or (+char ".") +digit))))
;;(def +parserVariables (+char "xyz"))
;;(def +parseOp (+or (+char "*-+/") (+seqf (constantly 'negate) (+char "n") (+char "e") (+char "g") (+char "a") (+char "t") (+char "e"))))
;;(def *constant (+map (fn [value] (Constant (Double/parseDouble value))) (+str +number)))
;;(def *variable (+map (fn [x] (Variable (str x))) (+str (+seq +parserVariables))))
;;
;;(def *op (+map (fn [x] (str x)) +parseOp))
;;(def *ws (+ignore (+star (+char " "))))
;;(def *brackets (+map (fn [x] (str x)) (+char "()")))
;
;(defn parseObjectSuffix [input]
;  (let [+all-letters (mapv char (range 0 128))
;        *alpha (+char (apply str (filter #(Character/isLetter %) +all-letters)))
;        +digit (+char "0123456789")
;        +number (+seqf cons (+opt (+char "-")) (+plus (+or (+char ".") +digit)))
;        +parserVariables (+char "xyz")
;        +parseOp (+or (+char "*-+/") (+seqf (constantly 'negate) (+char "n") (+char "e") (+char "g") (+char "a") (+char "t") (+char "e")))
;        *constant (+map (fn [value] (Constant (Double/parseDouble value))) (+str +number))
;        *variable (+map (fn [x] (Variable (str x))) (+str (+seq +parserVariables)))
;        *op (+map (fn [x] (str x)) +parseOp)
;        *ws (+ignore (+star (+char " ")))
;        ]
;    (letfn[
;           (*seqn [] (+seqn 1 *ws (+char "(") (*elements) (+char ")") *ws))
;           (*makeOp [] (+map #(apply (objectExpr (str (last %))) (butlast %)) (*seqn)))
;           (*elements [] (+seqf #(flatten %&) *ws (delay (*value)) *ws (+opt (delay (*value))) *ws *op *ws))
;           (*value [] (+or *constant *variable (*makeOp)))
;           ]
;      (+parser (+seqn 0 *ws (*value) *ws)))
;    ))


