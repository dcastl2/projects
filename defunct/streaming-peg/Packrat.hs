module Packrat where

import Data.Char
import Debug.Trace
import Rule
import PTree

-- Memo is a boolean and a tree.
data Memo = Memo { 
                   np :: String,
                   xp :: Int,
                   bp :: Bool,
                   tp :: PTree
                 } deriving (Eq)

-- Packrat is a memo list
type Packrat = [Memo]


instance Show Memo where
 show (Memo n x b t ) = concat[ (show n), "\t", 
                                (show t), "\t",   
                                (show b), "\t",   
                                (show t), "\t" 
                              ]


-- Add the memo to the table.
memoize :: Packrat -> Memo -> Packrat
memoize xm m = xm ++ [m]


-- Return Pij.
memo :: Packrat -> String -> Int -> Memo
memo [    ] n x = (Memo "" 0 False (PTree "" 0 0 []) )
memo (m:xm) n x = case (np m)==n of
                          True   -> m 
                          False  -> memo xm n x

