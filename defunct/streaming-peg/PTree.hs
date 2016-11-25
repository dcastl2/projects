module PTree where

import Data.Char
import Debug.Trace
import Rule


data PTree = PTree { 
               nt  :: String,     -- name
               st  :: Int,        -- starting pos
               ft  :: Int,        -- ending   pos
               tt  :: [PTree]     -- subtrees
            } deriving (Eq)


-- Tree version of "but with"
t ^$ n  = ( PTree  n     (st t) (ft t) (tt t) )
t ^< s  = ( PTree (nt t)  s     (ft t) (tt t) )
t ^> f  = ( PTree (nt t) (st t)  f     (tt t) )
t ^. tc = ( PTree (nt t) (st t) (ft t)  tc   )


-- Attach down
attachdown   ::  PTree -> PTree -> PTree
attachdown  n t  =  t ^. ((tt t) ++ [n])

-- Attach down
attachup     ::  PTree -> PTree -> PTree
attachup    n t  =  n ^. (tt t)


instance Show PTree where
 show (PTree n s f t) = case (null t) of 
                        False -> concat[ 
                              n, 
                             "(",
                  --          " ",
                  --          ",",
                  --         (show s), "-",
                  --         (show f), ",",
                             (show t), 
                             ")"
                            ]
                        True -> n

