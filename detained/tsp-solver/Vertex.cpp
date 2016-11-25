#include "Vertex.h"
#include <cmath>
#include <iostream>
#include <iomanip>
#include <cstdlib>

std::ostream &operator << (std::ostream &os, Vertex v) {
    os << '('  << v.x << ',' << v.y << ')' << std::flush;
    return os;
}

std::ostream &operator << (std::ostream &os, Vertex *v) {
    os << '('  << std::setw(4) << v->x << ',' << std::setw(4) << v->y << ')' << std::flush;
    return os;
}

Vertex::Vertex() {
  x = rand()%1024; 
  y = rand()%768; 
}

Vertex::Vertex(float x, float y) {
  this->x = x;
  this->y = y;
}

Vertex::Vertex(Vertex *v) {
  this->x = v->x;
  this->y = v->y;
}

Vertex::Vertex(const Vertex &v) {
  this->x = v.x;
  this->y = v.y;
}

float Vertex::length(Vertex *v) {
  return sqrt( 
    pow(v->x - this->x, 2) +
    pow(v->y - this->y, 2) 
  );
}

