module Memo where

import Data.Char
import Debug.Trace
import Rule

data Memo = Memo { 
                   memoname  :: Name 
                  ,begpos    :: Int
                  ,endpos    :: Int
                 } deriving (Eq)

type Notebook = [Memo]


instance Show Memo where
 show (Memo n a b) = concat[ (show n), "\t", 
                             (show a), "->",
                             (show b)      ]


-- Add the memo.
memoize :: Notebook -> Memo -> Notebook
memoize xM m = xM ++ [m]


-- Search; if found, returns endpos else -1.
memo :: Notebook -> Name -> Int
memo []     n = -1
memo (m:xM) n | (memoname m) == n  =  (endpos m)
              | otherwise          =  memo xM n


