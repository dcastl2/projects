#include "Vertex.h"
#include "Edge.h"
#include <cmath> 

Edge::Edge(Vertex *v1, Vertex *v2) {
      this->v1 = new Vertex(v1);
      this->v2 = new Vertex(v2);
}

float Edge::length() {
  return sqrt( 
    pow(v2->x - v1->x, 2) +
    pow(v2->y - v1->y, 2) 
  );
}

