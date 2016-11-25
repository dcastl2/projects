nextperm (string a, int n)
  m = size of a
  for i = m-1 down to 2
    if n mod i! is 0
      rRotate a at i (n/i!) times

Now the array is split s.t. half is in increasing, half in decreasing. Use rotations to fix.
