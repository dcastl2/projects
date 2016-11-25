#include <iostream>

template <class T>
class Vector {
  private:
    T x;
    T y;

  public:
    Vector<T> *add(Vector<T> A, Vector<T> B);
};
