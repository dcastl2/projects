import Match
import Rule
import Stream
import Packrat 
import Stack
import PTree

main = do

       let   x = 0
       let   i1 = "0"
       let   i2 = "1"
       let   n = ""
       let   b = True
       let   k = "_"
       let   l = False
       let   z = [(Stack "" 0 0 (PTree "S" 0 0 []))]
       let   t = (PTree "S" 0 0 [])

       let   r1 = [Kill]
       let   r2 = [Patt "A"]
       let   r3 = [Patt "D"]

       let   s1 = "bc"
       let   s2 = "abc"
       let   s3 = "eec"
       let   s4 = "aaaa"

       let   res   = matches [
--                            (Stream x i1 s1 n b k l r1 z t),
--                            (Stream x i2 s2 n b k l r2 z t)
--                            (Stream x i2 s3 n b k l r3 z t)
                              (Stream x i1 s4 n b k l [Patt "X"] z t)
                             ]
       print res

