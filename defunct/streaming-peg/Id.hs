module Id where

import Stream
import Stream

-- So 11212 would be size 5
idsizen :: Int -> Int -> Int
idsizen s i = case i^3 > s of
                  False   -> idsizen s i+1
                  True    -> i-1

idsize :: Int -> Int
idsize s  =  idsizen s 1

-- 
leftward :: Int -> Int
leftward x = x + 1*3^(idsize x)

rightward :: Int -> Int
rightward x =  x + 2*3^(idsize x)


