module Match where 

import Control.Parallel
import Debug.Trace
import Rule
import Grammar
import Kill
import Id
import Stream
import Stack
import PTree
import Packrat


-- Show stream while matching.
tmatch s = trace (show s) (match s)


-- Checks if rule list has pattern name or OC as head:
--  Exists s in S | head(Rs) = Pat, Or
has_phead :: Stream -> Bool
has_phead s  = case (rs s) of
                      []   -> False
                      r    -> case   head(r)     of
                                   (Patt _   )   ->   True
                                   (Plus _   )   ->   True
                                   (Star _   )   ->   True
                                   (Or   _ _ )   ->   True
                                   (Kill _   )   ->   True
                                   (r)           ->   False


-- Checks if rule list has pattern name or OC as head:
--  Exists s in S | head(Rs) = Pat, Or
l_has_phead :: [Stream] -> Bool
l_has_phead [    ]  = False
l_has_phead (s:xs)  = case (has_phead s) of
                              True       ->  True
                              False      ->  l_has_phead(xs)


-- Break up rules in the stream list
expand :: Stream -> [Stream]
expand s = case (rs s) of
                  []   -> [s]
                  r    -> let t = tail r
                              h = head r in
                         case       h        of
                              (Or   r1 r2)   ->   let i1 =    (is  s)      ++      "0"
                                                      i2 =    (is  s)      ++      "1" 
                                             in  [( ((s <| ([r1, (Kill (i1))]++t)) <# i1)           )] ++   -- Kill substitution
                                                 [( ((s <| ([r2             ]++t)) <# i2)           )]
                              (Star r   )    ->  [(   s <| ([(Or r Nil)     ]++t)                   )]
                              (Plus r   )    ->  [(   s <| ([r, (Or r Nil)  ]++t)                   )]
                              (Patt n   )    ->  [(  (s <| ((look n gram)    ++t) << 
                                                             [(Stack n (xs s) (xs s) 
                                                                  (attachdown
                                                                            (PTree n (xs s) (xs s) [])
                                                                            ( tz   (head (zs s))     ) 
                                                                  )
                                                             )]
                                                     )
                                                 )]
                  --          (Pop      )    ->  
                              (Kill    k)    ->  [( s <| t) </ k]
                              (r)            ->  [  s           ]



-- Condition to exit and return stream list
exitcond :: [Stream] -> Bool -> Bool
exitcond [    ] b = b
exitcond (s:xs) b = case (rs s)==[] && (bs s)==True of
                                --True              ->  False
                                  True              ->  True
                                  False             ->  exitcond(xs)      b


-- Condition to continue with expansion
contcond :: [Stream] -> Bool
contcond s = l_has_phead s


-- Matches until exitcond is satisfied,
-- expands until continue condition is falsified
matches :: [Stream] -> [Stream]
matches []  = []
matches s   = case (exitcond(s) False) of
                   True                -> s
                   False               -> case (contcond(s)) of 
                                                False        -> matches ( trace (show Nil) matchlist (s) )
                                                True         -> matches 
                                                                (
                                                                          (  killall 
                                                                             (
                                                                                (
                                                                                   (concatMap (expand) (filter       (has_phead)  s))
                                                                                        ++             (filter (not . has_phead)  s)
                                                                                )
                                                                             )
                                                                          )
                                                                )



-- For all streams, memoize results, then match, then prune
matchlist   :: [Stream] -> [Stream]
matchlist s  = filter (prune)
                  (map (tmatch) s)


-- Handles matching of characters
match :: Stream -> Stream
match             (s)  =
    let    r = (rs s)               -- rule list
           l = (ls s) in            -- lookahead flag set
    case   r    of
           []   -> s
           r    -> case  head(r) of 
                         (Lit a) -> case  length(ss s) > (xs s) of
                            False  -> (s <? False)
                            True   -> case ((ss s) !! (xs s)) == a of 
                              False  ->  (s <? False)
                              True   -> case (null (tail r)) of  
                                  True   -> (((s <@ ((xs s)+1)) <| tail(r)) <? False)
                                  False  ->  ((s <@ ((xs s)+1)) <| tail(r))

