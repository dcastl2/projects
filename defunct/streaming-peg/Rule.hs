module Rule where

as_string :: Char -> String
as_string    x    =  [x]

-- Rule is a Literal, an Ordered Choice, Sequence,
-- Kleene Star, Kleene Plus, or Nothing.
data Rule = Lit  Char 
          | Or   Rule Rule
          | Star Rule
          | Plus Rule
          | Look Rule
          | Patt String
          | Nil  
          | Fork Rule String
          | Kill String
            deriving (Eq)


-- Show rule as a regex.
instance Show Rule where

   show (Or r1 r2) = 
    concat[
           "(",
           (show r1), 
           "|",
           (show r2),
           ")"
          ]
   show (Nil   )   =  ""
   show (Kill s)   =  concat["_"]
   show (Fork r x) =  concat[(show r)]
   show (Lit  r)   =  concat[(as_string r),  ""]
   show (Star r)   =  concat[(show r),      "*"]
   show (Plus r)   =  concat[(show r),      "+"]
   show (Patt s)   =  s

showName (Or   _ _) = "Or"
showName (Nil     ) = "Nil"
showName (Star _  ) = "Star"
showName (Plus _  ) = "Plus"
showName (Lit  _  ) = "Lit"
showName (Fork _ _) = "Fork"
showName (Patt n  ) = n
showName (Kill k)   = "Kill"++k


data Pat = Pat { 
              nn :: String,
              rn :: [Rule]
            }
            deriving (Eq)

instance Show Pat where

 show (Pat n [])  = (show n)
 show (Pat n r)  =
  concat[
         (show n), "\t",
         (show r)
        ]


look :: String -> [Pat] -> [Rule]
look s []                    = [Nil]
look s (p:xp) | s==(nn p)    = rn p
              | otherwise    = look s xp

