
(defn generate-blank
  [orig-len curr-len]
  ;utility function to generate uniform blank chars between
  ;two columns in a trace output
  (let [len (+ 5 (- orig-len curr-len))]
    (apply str (repeat len " "))
    )
  )

(defn generate-levels [len]
  ;utility func to print twice the level of spaces before arg output
  ;for non-memo trace output
  (apply str (concat "(" (repeat (* 2 len) " ") ")"))
  )

(defn generate-levels [len]
  ;utility func to print twice the level of spaces before arg output
  ;for non-memo trace output
  (apply str (concat "(" (repeat (* 2 len) " ") ")"))
  )

;=============================================================================

(defn min-dist
  ;non-memo function to determine minimum dist between two words
  [fromString toString ]
  (if (and (empty? fromString) (empty? toString))
    0
    (
      if (or (empty? fromString) (empty? toString))
      (+ (count fromString) (count toString))
      ;if first char not-equal then the min no. of changes to match the rest of each + 1
      (let [first-same (if (= (first fromString) (first toString)) 0 1)]
        (min
          (+ 1 (min-dist (rest fromString) toString))                  ;deletion
          (+ 1 (min-dist fromString (rest toString)))                  ;insertion
          (+ first-same (min-dist (rest fromString) (rest toString)))  ;substitution
          )
        )
      )
    )
  )

;============================================================================

(defn min-dist-memoized
  ;function for memoized execution of min-distance
  [fromString toString]
  (if (and (empty? fromString) (empty? toString))  0
    (if (or (empty? fromString) (empty? toString))
      (+ (count fromString) (count toString))
      (let [first-same (if (= (first fromString) (first toString)) 0 1)]
        (min
          (+ 1 (min-dist-memoized (rest fromString) toString))                    ;deletion
          (+ 1 (min-dist-memoized fromString (rest toString)))                    ;insertion
          (+ first-same (min-dist-memoized (rest fromString) (rest toString)))    ;substitution
          )
        )
      )
    )
  )

(def min-dist-memoized (memoize min-dist-memoized))

;==========================================================
(defn min-dist-memoized-trace
  ;memoized trace method, orig-len is for passing the original
  ;length of fromString in each recursion for uniform space in trace output
  [fromString toString & [orig-len]]
  (let [orig-len (if (nil? orig-len) (count fromString) orig-len)]
    ;Call to generate-blank prints a uniform space between to and from Strings in each recursion
    (println (apply str fromString) (generate-blank orig-len (count fromString)) (apply str toString))
    (if (and (empty? fromString) (empty? toString))  0
      (if (or (empty? fromString) (empty? toString))
        (+ (count fromString) (count toString))
        (let [first-same (if (= (first fromString) (first toString)) 0 1)]
          (min
            (+ 1 (min-dist-memoized-trace (rest fromString) toString orig-len))                    ;deletion
            (+ 1 (min-dist-memoized-trace fromString (rest toString) orig-len))                    ;insertion
            (+ first-same (min-dist-memoized-trace (rest fromString) (rest toString) orig-len))    ;substitution
            )
          )
        )
      )
    )
  )

(def min-dist-memoized-trace (memoize min-dist-memoized-trace))

;===========================================================================

(defn min-dist-trace
  ;non-memoized with trace, includes a third argument for the level which is incremented at each recursive call
  [fromString toString lev & [orig-len]]
  ;orig-len is for passing the original length of fromString
  (let [orig-len (if (nil? orig-len) (count fromString) orig-len)]
    ;generate-levels method prints twice the level of spaces before the argument output, for each recursion call
    (println (generate-levels lev) (apply str fromString) (generate-blank orig-len (count fromString)) (apply str toString))
    (if (and (empty? fromString) (empty? toString))  0
      (if (or (empty? fromString) (empty? toString))
        (+ (count fromString) (count toString))
        (let [first-same (if (= (first fromString) (first toString)) 0 1)]
          (min
            (+ 1 (min-dist-trace (rest fromString) toString (inc lev) orig-len))                     ;deletion
            (+ 1 (min-dist-trace fromString (rest toString) (inc lev) orig-len))                    ;insertion
            (+ first-same (min-dist-trace (rest fromString) (rest toString) (inc lev) orig-len))    ;substitution
            )
          )
        )
      )
    )
  )

