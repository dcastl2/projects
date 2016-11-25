#ifndef EDGE_H
#define EDGE_H

#include "Vertex.h"
#include "Edge.h"
#include "List.h"

class Path {

  private:

    List<Vertex> *path;

  public:

    void  add(Vertex *vertex); 
    float length();
    int   size();

    Path();
    Path(int);
    Path(Path *);
    Path(List<Vertex> *);
    Path(List<Vertex>);

    void  Xinit();
    void  draw();
    void  undraw();

    List<Path> *permutations();

    friend std::ostream& operator << (std::ostream& os, Path *p);

};

class Quadrangle : public Path {

  public:

    void add(Vertex *vertex);

};

#endif
