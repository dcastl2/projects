module Stream where

import Rule
import Stack
import PTree

data Stream = Stream {
               xs :: Int,           -- position in string
               is :: String,        -- id of this stream
               ss :: String,        -- string to be parsed
               ns :: String,        -- pattern name
               bs :: Bool,          -- success flag
               ks :: String,        -- kill target
               ls :: Bool,          -- lookahead flag
               rs :: [Rule],        -- sequence of rules
               zs :: [Stack],       -- stack for this stream
               ts :: PTree          -- parse tree for this stream
             } deriving  (Eq)


-- The < ("but with") operator returns a copy of the given Stream but with
-- one of the parameters changed.
s <@ x = (Stream   x     (is s) (ss s) (ns s) (bs s) (ks s) (ls s) (rs s) (zs s) (ts s))
s <# i = (Stream  (xs s)  i     (ss s) (ns s) (bs s) (ks s) (ls s) (rs s) (zs s) (ts s))
s <$ a = (Stream  (xs s) (is s)  a     (ns s) (bs s) (ks s) (ls s) (rs s) (zs s) (ts s))
s <: n = (Stream  (xs s) (is s) (ss s)  n     (bs s) (ks s) (ls s) (rs s) (zs s) (ts s))
s <? b = (Stream  (xs s) (is s) (ss s) (ns s)  b     (ks s) (ls s) (rs s) (zs s) (ts s))
s </ k = (Stream  (xs s) (is s) (ss s) (ns s) (bs s)  k     (ls s) (rs s) (zs s) (ts s))
s <> l = (Stream  (xs s) (is s) (ss s) (ns s) (bs s) (ks s)  l     (rs s) (zs s) (ts s))
s <| r = (Stream  (xs s) (is s) (ss s) (ns s) (bs s) (ks s) (ls s)  r     (zs s) (ts s))
s << z = (Stream  (xs s) (is s) (ss s) (ns s) (bs s) (ks s) (ls s) (rs s)  z     (ts s))
s <^ t = (Stream  (xs s) (is s) (ss s) (ns s) (bs s) (ks s) (ls s) (rs s) (zs s)  t    )


instance Show Stream where
   show s =
    concat["\t",   lastof (is s),
           "\t",   (show  (xs s)), 
           "\t",   (show  (bs s)), 
           "\t",   (show  (ss s)), 
           "\t\t", (show  (rs s)),
           "\t\t", (show  (zs s))
          ]

   showList []  = showString "[]"
   showList (x) = showString "[\n" . showl x
                     where showl []     = showString "\n]"
                           showl (x:xs) = shows x . (showString "\n") . showl xs


lastof  :: String -> String
lastof s | length s > 6 = concat[ "..", take 4 (reverse s) ]
         | otherwise    = s

