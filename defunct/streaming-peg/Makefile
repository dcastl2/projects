src = $(wildcard *.hs)
exe = $(src:%.hs=%)
hi  = $(src:%.hs=%.hi)
obj = $(src:%.hs=%.o)

all:
	ghc --make main.hs

clean:
	rm -f $(exe) $(hi) $(obj)
