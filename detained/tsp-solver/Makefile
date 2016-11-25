SRCS = $(filter-out main.cpp, $(wildcard *.cpp))
OBJS = $(SRCS:.cpp=.o)
CPPFLAGS = -std=c++11 -lX11 -g

main: main.cpp $(OBJS)
	g++ $^ $(CPPFLAGS) -o $@ 

%.o : %.cpp
	g++ $< $(CPPFLAGS) -c

clean:
	$(RM) -rf main $(OBJS)
