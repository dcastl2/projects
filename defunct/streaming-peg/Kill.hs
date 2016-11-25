module Kill where

import Debug.Trace
import Data.List
import Rule
import Stream


-- True iff a begins with b
beginswith :: String -> String -> Bool
beginswith    [    ]    [    ]  = True
beginswith    (a:xa)    [    ]  = True
beginswith    [    ]    (b:xb)  = False
beginswith    (a:xa)    (b:xb)  =  case a == b  of
                                         True   ->  beginswith xa xb
                                         False  ->  False


-- Retain only streams which are not kill target
kill :: [Stream]  -> [Stream] -> [Stream]
kill [    ] [    ]  =  [ ]
kill  s     [    ]  =  [ ]
kill [    ]  t      =  t
kill (s:xs) (t:xt)  = case beginswith (is t) (ks s)  &&   -- Stream ID begins with the kill ID
                                not (s == t)         of   -- and they are not the same
                                True                 ->   kill (  xs) (t:xt)
                                False                ->   kill (  xs) (  xt)  ++ [t]

-- Determine if a stream has a Kill rule or not.
iskiller  :: Stream -> Bool
iskiller s = case (rs s) of
                    []   -> False
                    r    -> case head(rs s)  of
                                 (Kill k)    ->  True
                                 (r)         ->  False


-- If the list is empty
emptylist   :: Stream -> Bool
emptylist s =  (rs s)==[]


-- Process all streams
killall :: [Stream] -> [Stream]
killall s = kill (filter (iskiller)        s) 
                 (filter (not . emptylist) s)


-- Decide pruning
prune :: Stream -> Bool
prune s = (bs s)
