module Stack where

import PTree

data Stack = Stack {
              nz :: String,
              sz :: Int,
              fz :: Int,
              tz :: PTree
             } deriving (Eq)


instance Show Stack where
 show (Stack n s f t) = concat[ 
                             n,
                             "{",
                 --          " ",
                 --           (show s), 
                 --          "-",
                 --           (show f), 
                 --          ",",
                             (show t), 
                             "}"
                              ]

