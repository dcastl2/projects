#include "Path.h"
#include "List.h"
#include <cstdlib>
#include <ctime>


Path::Path() {
  path = new List<Vertex>;
  srand(time(NULL));
}

Path::Path(Path *p) {
  path = new List<Vertex>;
  Node<Vertex> *pos = p->path->begin();
  while (pos) {
    path->add(pos->key);
    pos = pos->next;
  }
}

Path::Path(List<Vertex> *list) {
  path = new List<Vertex>;
  Node<Vertex> *pos = list->begin();
  while (pos) {
    path->add(pos->key);
    pos = pos->next;
  }
}

Path::Path(int n) {
  path = new List<Vertex>;
  srand(time(NULL));
  for (int i=0; i<n; i++)
    add(new Vertex);
}

void Path::add(Vertex *vertex) {
  path->add(vertex);
}

float Path::length() {
  Node<Vertex> *node = path->begin();
  float sum  = 0;
  while (node->next) {
    sum += node->key->length(node->next->key);
    node = node->next;
  }
  sum += path->begin()->key->length(path->end()->key);
  return sum;
}

int Path::size() {
  return path->size();
}

// Converts List<List<Vertex>> of permutations into List<Path>
List<Path> *Path::permutations() {
  List<List<Vertex>> *vll = path->permutations();
  Node<List<Vertex>> *pos = vll->begin();
  List<Path>          *pl = new List<Path>;
  while (pos) {
    pl->add(new Path(pos->key));
    pos = pos->next;
  }
  return pl;
}

void Quadrangle::add(Vertex *vertex) {
  if (size() < 4)
    this->Path::add(vertex);
}

std::ostream& operator << (std::ostream& os, Path *p) {
  os << p->path;
  return os;
}
