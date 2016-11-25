#ifndef EDGE_H
#define EDGE_H

#include "Vertex.h"

class Edge {

  private:

    Vertex *v1;
    Vertex *v2;

  public:

    Edge(Vertex *v1, Vertex *v2);
    float length();

};

#endif
