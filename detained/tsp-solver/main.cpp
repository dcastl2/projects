#include <iostream>
#include <climits>
#include <unistd.h>

#include "Vertex.h"
#include "Path.h"

int main() {

  Path *p = new Path(8);
  List<Path>   perms = p->permutations();

  int mindex = -1;
  int index  =  1; 
  int min    = INT_MAX;
  int length;

  p->Xinit();
  Node<Path> *node = perms.begin();
  while (node != perms.end()) {
    length = node->key->length();
    node->key->draw();
    if (min    > length) {
        min    = length;
        mindex = index;
    }
    std::cout << std::setw(4) << index  << ':';
    std::cout << std::setw(4) << length << std::endl;
    node->key->undraw();
    node = node->next;
    index++;
  }

  node = perms.begin();
  for (index = 1; index != mindex; index++) 
    node = node->next;

  node->key->draw();
  std::cout << std::endl;
  std::cout << std::setw(4) << index << ':';
  std::cout << std::setw(4) << min   << std::endl;
  sleep(10);

}
