#ifndef VERTEX_H
#define VERTEX_H

#include <iostream>

class Vertex {

  public:

    float x;
    float y;

    Vertex(float, float);
    Vertex(Vertex *);
    Vertex(const Vertex&);
    Vertex();

    float length(Vertex *);

    friend std::ostream& operator<<(std::ostream&, Vertex *);

};

#endif
