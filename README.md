# edit-distance
implemenation of edit distance algorithm using clojure with memoization and output trace methods.
returns the minimum number of changes -- insertions, deletions, or substitutions -- needed to change a given string into a second given string. For example, for "sin" to "bit" it's two, while from "alligator" to "navigator" it's three.

The recursive algorithm is
If 
  both strings empty then 0
  one string empty then the size of the other
  first characters equal then the minimum number of changes to match the 
    rest of each 
  otherwise one plus the minimum of the number of changes from
    a. rest of first to second            (deletion)
    b. first to rest of second            (insertion)
    c. rest of first to rest of second    (substitution)
