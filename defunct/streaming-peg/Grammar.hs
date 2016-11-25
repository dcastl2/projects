module Grammar where

import Rule

gram :: [Pat]
gram = [
           (Pat "A" ([ (Lit 'a'),
                       Or (Patt "B")
                          (Patt "C")
                    ])
           ),

           (Pat "B" ([ (Lit  'b'),
                       (Patt "C")
                    ])
           ),

           (Pat "C" [ (Lit 'c')
                    ]
           ),

           (Pat "D" [ (Patt "E"),
                      (Patt "C")
                    ]
           ),

           (Pat "E" [ (Lit 'e'),
                      (Lit 'e')
                    ]
           ),

           (Pat "Y" [ (Lit 'a'), 
                      (Patt "X") 
                    ]
           ),

           (Pat "X" [ Or (Patt "Y")
                         (Lit 'a')
                    ]
           )

        ]
