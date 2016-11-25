#ifndef LIST_H
#define LIST_H

#include <iostream>
#include <iomanip>

template <class T>
class Node {
  public:
    T     *key;
    Node *next;

    Node<T>();
    Node<T>(T *);
    Node<T>(T);
    Node<T>(Node<T> *);
};


/*
template <class T>
std::ostream& operator<<(std::ostream& os, T t){
  os << t << std::flush;
  return os;
}
*/


template <class T>
Node<T>::Node() {
}


template <class T>
Node<T>::Node(T *key) {
  //this->key = new T(key);
  this->key = new T(key);
}


template <class T>
Node<T>::Node(T key) {
  this->key = new T(key);
}


template <class T>
Node<T>::Node(Node<T> *that) {
  this->key = new T(that->key);
}


template <class T>
class List {

  private:

    int       num;
    Node<T> *head;
    Node<T> *tail;

  public:

    List<T>();
    List<T>(List<T> *);
    //void  print(); 

    void  add(T key); 
    void  add(T *key); 
    int   size();

    Node<T> *begin();
    Node<T> *end();

    List<T> *lRotate();
    List<T> *lRotate(int);

    List<List<T>> *permutations();

};


template <class T>
List<T>::List() {
  head = tail = NULL;
  num  = 0;
}


template <class T>
List<T>::List(List<T> *list) {
  Node<T> *pos=list->begin();
  Node<T> *left=NULL, *right;
  do {
    right = new Node<T>(pos->key);
    if (left) left->next = right;
    else      head       = right;
    left = right;
  } while (pos = pos->next);
  tail = right;
}


template <class T>
void List<T>::add(T key) {
  if (tail) {
    Node<T> *node = new Node<T>();
    node->key     = new T(key);
    tail->next    = node;
    tail          = node;
  } else {
    tail          = new Node<T>();
    tail->key     = new T(key);
    head          = tail;
    tail->next    = NULL;
  }
  num++;
}


template <class T>
void List<T>::add(T *key) {
  if (tail) {
    Node<T> *node = new Node<T>();
    node->key     = new T(key);
    tail->next    = node;
    tail          = node;
  } else {
    tail          = new Node<T>();
    tail->key     = new T(key);
    head          = tail;
    tail->next    = NULL;
  }
  num++;
}


template <class T>
int List<T>::size() {
  return num;
}


template <class T>
Node<T> *List<T>::begin() {
  return head;
}


template <class T>
Node<T> *List<T>::end() {
  return tail;
}


template <class T>
List<T> *List<T>::lRotate() {
  if (head==tail) return this;
  Node<T> *temp = head->next;
  tail->next = head;
  head->next = NULL;
  tail       = head;
  head       = temp;
  return this;
}


template <class T>
List<T> *List<T>::lRotate(int index) {
  if (index < 1) 
    return this;
  int i = 0;
  Node<T> *pos = head;
  while (i < index-1) 
    if (!(pos = pos->next)) 
      return this;
    else i++;
  Node<T> *newhead, *newtail;
  if (!(newtail = pos->next))
    return this;
  if (!(newhead = newtail->next))
    return this;
  newtail->next = NULL;
  pos->next     = newhead;
  tail->next    = newtail;
  tail          = newtail;
  return this;
}


template <class T>
List<List<T>> *List<T>::permutations() {
  List<List<T>> *permutations = new List<List<T>>();
  List<T> *A = this; 
  List<T> *B;
  for (int i=1; i<A->size(); i++) {
    B = new List<T>(A->lRotate(i));
    for (int j=1; j<A->size(); j++) {
      B->lRotate(j);
      permutations->add(B);
    }
  }
  return permutations;
}


template <class T>
std::ostream& operator << (std::ostream& os, List<T> *list) {
  Node<T> *pos = list->begin();
  os << '[';
  while (pos != list->end()) {
    os << std::setw(4) << pos->key;
    os << ',';
    pos = pos->next;
  }
  os << std::setw(4) << list->end()->key;
  os << std::setw(4) << ' ' << ']';
  os << std::endl;
  return os;
}

#endif
