import sys
from numpy import polyfit
from numpy import linalg
from time import time

def copy_patterns(patterns):
  result = []
  for p in patterns:
    if isinstance(p,Kill):
      if p.used:
        pass
      else:
        result.append(p)
    else:
      result.append(p)
  return result

class Lit:
  def __init__(self,letter):
    self.letter = letter
  def __str__(self):
#   return "Lit("+self.letter+")"
    return self.letter

class Seq:
  def __init__(self,patterns):
    if type(patterns) == type([]):
      self.patterns = patterns
    elif type(patterns) == type(""):
      self.patterns=[]
      for p in patterns:
        self.patterns.append(Lit(p))
  def __str__(self):
    if len(self.patterns)==0:
#     return "[]"
      return ""
#   s = "["
    s = ""
    for i in range(len(self.patterns)):
      p = self.patterns[i]
      if i > 0:
#       s += ","
        s += ""
      s += str(p)
#   s += "]"
    return s

class Or:
  def __init__(self,patterns):
    self.patterns = patterns
  def __str__(self):
    if len(self.patterns)==0:
#     return "[]"
      return ""
#  tween = "Or["
    tween = "("
    s = ""
    for p in self.patterns:
      s += tween
      s += str(p)
#     tween = ","
      tween = "|"
    s += ")"
    return s

class Kill:
  def __init__(self,sid):
    self.sid = sid
    self.used = False
  def __str__(self):
#   return "Kill("+str(self.sid)+")"
    return "x("+str(trinary(self.sid))+")"

class Pat:
  def __init__(self,name):
    self.name = name
  def __str__(self):
#   return "Pat("+self.name+")"
    return self.name

# Like pat, but does not put anything
# into the parse tree. It's much faster.
class NoPat:
  def __init__(self,name):
    self.name = name
  def __str__(self):
#   return "NoPat("+self.name+")"
    return self.name

class Node:
  def __init__(self,patternName,startPos,endPos,patterns):
    self.patternName = patternName
    self.startPos = startPos
    self.endPos = endPos
    self.patterns = patterns
    self.children = []
  def __str__(self):
    s = self.patternName+"("+str(self.startPos)+","+str(self.endPos)+",["
    for i in range(len(self.children)):
      if i > 0:
        s += ","
      s += str(self.children[i])
    s += "]"
    if False:
      s += ",{"
      for i in range(len(self.patterns)):
        if i > 0:
          s += ","
        s += str(self.patterns[i])
      s += "}"
    s += ")"
    return s
  def clone(self):
    node = Node(self.patternName,self.startPos,self.endPos,copy_patterns(self.patterns))
    node.children = self.children #[n for n in self.children]
    return node

global next_sid, schildren, byid
next_sid = 1
schildren = {}
byid = {}

class Stream:
  def __init__(self,parent,patternName,patterns,text,pos,grammar):
    global next_sid, schildren, byid
    # Tuple
    self.sid = next_sid
    next_sid += 1
    byid[self.sid] = self
    if parent == None:
      pass
    elif parent.sid in schildren:
      schildren[parent.sid].append(self.sid)
    else:
      schildren[parent.sid] = [self.sid]
    self.grammar = grammar
    self.patterns = copy_patterns(patterns) # R = sequence of rules
    self.text = text # s = string
    self.pos = pos # x = position in string
    self.node = Node(patternName,pos,-1,[]) # n
    self.good = True # b = success flag
    self.mcalls = 0
    self.parse_stack = [] # T

  def clone(self):
    stream = Stream(self,self.node.patternName,self.patterns,self.text,self.pos,self.grammar)
    for ps in self.parse_stack:
      stream.parse_stack.append(ps.clone())
    return stream

  def __str__(self):
    s  = "Stream{ "
    s +=      "sid=..." +ssid(self.sid)
    s +=  ", \t tsid="    +str(trinary(self.sid))
#   s  = "Stream{id=" +str(self.sid)
    s +=  ", \t pos=" +str(self.pos)
    s +=  ", \t good="+str(self.good)
#   s +=  ",\tcall="+str(self.mcalls)
    s +=  ",\t patterns=["
    for i in range(len(self.patterns)):
      p = self.patterns[i]
      if i > 0:
        s += ","
      s += str(p)
    s += "]"
#    s += ",parse_stack=["
#    for i in range(len(self.parse_stack)):
#      p = self.parse_stack[i]
#      if i > 0:
#        s += ","
#      s += str(p)
#    s += "]"
    s += "}"
    return s

  def match(self,kills):

    global schildren, byid

    # **Missing from algorithm:
    # what happens if we can't pop?
    while len(self.patterns)==0:
      if len(self.parse_stack) > 0 and self.good:
        assert len(self.patterns)==0
        n = self.node.clone()
        n.endPos = self.pos
        self.node = self.parse_stack.pop().clone()
        self.node.children.append(n)
        self.patterns = copy_patterns(self.node.patterns)
      elif self.good:
        return [self]
      else:
        return []

    pat = self.patterns.pop() # r <- head(R)

    if isinstance(pat,Lit): # If rs = Lit(a):
      if self.pos < len(self.text) and pat.letter == self.text[self.pos]:
        self.pos += 1
      else:
        self.good = False
        del byid[self.sid]
      return [self]
    elif isinstance(pat,Seq):
      for i in range(len(pat.patterns)-1,-1,-1):
        self.patterns += [pat.patterns[i]]
      return [self]
    elif isinstance(pat,NoPat): # if r = Pat(n)
      self.patterns += self.grammar[pat.name]
      return [self]
    elif isinstance(pat,Pat): # if r = Pat(n)
      self.node.patterns = self.patterns
      self.parse_stack.append(self.node)
      self.patterns = [p for p in self.grammar[pat.name]]
      self.node = Node(pat.name,self.pos,-1,self.patterns)
      return [self]
    elif isinstance(pat,Or): # Else if r = Or(r1,r2)
      streams = []
      for j in range(len(pat.patterns)):
        p = pat.patterns[j]
        stream = self.clone()
        if j+1 < len(pat.patterns): 
          stream.patterns.append(Kill(stream.sid+1))
        stream.patterns.append(p)
        streams.append(stream)
      return streams
    elif isinstance(pat,Kill):
      if not pat.used:
        kills.append(pat.sid)
        pat.used = True
        cur_kills = []
        next_kills = [pat.sid]
        while len(next_kills)>0:
          cur_kills = next_kills
          next_kills = []
          for kill_id in cur_kills:
            if kill_id not in byid:
              continue
            byid[kill_id].good = False
            del byid[kill_id]
            if kill_id in schildren:
              next_kills += schildren[kill_id]
              del schildren[kill_id]
      return [self]
    else:
      raise BaseException("pat="+str(pat))

class Matcher:
  def __init__(self,patternName,text,pos,grammar):
    self.streams = [Stream(None,patternName,grammar[patternName],text,pos,grammar)] # S
    self.maxstreams = 0

  def match_stream_list(self):
    mat = 0
    print "=" * 50
    streams = self.streams

    while True:
          if len(streams) > self.maxstreams:
            self.maxstreams = len(streams)
          newstreams = [] # S'
    
          # **Missing from algorithm:
          # Want to only advance the stream
          # that's farthest behind
          minpos = None
          for stream in streams:
            if len(stream.patterns) == 0 and len(stream.parse_stack) == 0:
              pass # This is an inactive stream
            elif minpos == None:
              minpos = stream.pos
            elif stream.pos < minpos:
              minpos = stream.pos
          if minpos == None:
            return False
    
          kills = []
          #print ""
          for stream in streams:
            #print stream
            if stream.good: # This prunes the stream
              if stream.pos == minpos:
                mat += 1
                newstreams += stream.match(kills) # S' <- S' Union Match(s)
              else:
                newstreams.append(stream)
    
          streams = newstreams
          if len(streams) == 0:
            return (False,mat)
    
          # Exit condition
          if len(streams) == 1 and streams[0].good and len(streams[0].patterns)==0 and len(streams[0].parse_stack)==0:
            streams[0].node.endPos = streams[0].pos
            print str(streams[0].node) # for debugging purposes
            return (True,mat)

grammar = {
  "fish":[Seq("fish")],
  "fo":[Seq("fo")],
  "ul":[Seq("ul")],
  "foul":[Seq([Pat("fo"),Pat("ul")])],
  "game":[Or([Pat("fish"),Pat("foul")])],
  "A":[Or([Seq([Lit('a'),NoPat("A")]),Lit('a')])]
  }
m = Matcher("game","foul",0,grammar)
print m.match_stream_list()
m = Matcher("game","fish",0,grammar)
print m.match_stream_list()

m = Matcher("A","aaa",0,grammar)
print m.match_stream_list()

astr = "aa"
x = []
y = []
z = []
for i in range(10):
  mat = 0
  astr += astr
  m = Matcher("A",astr,0,grammar)
  t1 = time()
  (res,mat) = m.match_stream_list()
  t2 = time()
  assert res
  print (res, mat)
  print 'iteration:',i,' maxstreams:',m.maxstreams,' chars:',len(astr),'; time per char:',(t2-t1)/len(astr)
  x.append(len(astr))
  y.append(t2-t1)
  z.append(mat)

print z
coeffsA = polyfit(x,y,2)
print [ "%12.8f" % el for el in coeffsA ]
coeffsB = polyfit(x,z,2)
print [ "%12.8f" % el for el in coeffsB ]
