#include "Path.h"
#include <X11/Xlib.h>
#include <assert.h>
#include <unistd.h>
#include <cmath>

#define NIL (0)
#define X 1024
#define Y 768

Display *dpy;
Window w;
GC gc;

void Path::Xinit() {

      dpy = XOpenDisplay(NIL);
      assert(dpy);
      bool white = true;
      int blackColor = BlackPixel(dpy, DefaultScreen(dpy));
      int whiteColor = WhitePixel(dpy, DefaultScreen(dpy));
      w = XCreateSimpleWindow(dpy, DefaultRootWindow(dpy), 0, 0, 
             X, Y, 0, blackColor, blackColor);
      XSelectInput(dpy, w, StructureNotifyMask);
      XMapWindow(dpy, w);
      gc = XCreateGC(dpy, w, 0, NIL);
      while (true) {
       XEvent e;
       XNextEvent(dpy, &e);
       if (e.type == MapNotify)
       break;
      }

}

void Path::draw() {

      int whiteColor = WhitePixel(dpy, DefaultScreen(dpy));
      XSetForeground(dpy, gc, whiteColor);
      Node<Vertex> *pos = path->begin();
      while (pos->next) {
          int x1 = pos->key->x;
          int y1 = pos->key->y;
          XDrawPoint(dpy, w, gc, x1, y1);
          XFlush(dpy);
          //usleep(3000);
          pos = pos->next;
      }
      //usleep(500);
      
      pos = path->begin();
      while (pos->next) {
          int x1 = pos->key->x;
          int y1 = pos->key->y;
          int x2 = pos->next->key->x;
          int y2 = pos->next->key->y;
          XDrawLine(dpy, w, gc, x1, y1, x2, y2);
          XFlush(dpy);
          usleep(10000);
          pos = pos->next;
      }
      XDrawLine(dpy, w, gc, path->begin()->key->x, path->begin()->key->y, path->end()->key->x, path->end()->key->y);
      XFlush(dpy);
      usleep(300000);

}

void Path::undraw() {

      int blackColor = BlackPixel(dpy, DefaultScreen(dpy));
      XSetForeground(dpy, gc, blackColor);
      Node<Vertex> *pos = path->begin();
      while (pos->next) {
          int x1 = pos->key->x;
          int y1 = pos->key->y;
          XDrawPoint(dpy, w, gc, x1, y1);
          XFlush(dpy);
          usleep(5000);
          pos = pos->next;
      }
      usleep(5000);
      
      pos = path->begin();
      while (pos->next) {
          int x1 = pos->key->x;
          int y1 = pos->key->y;
          int x2 = pos->next->key->x;
          int y2 = pos->next->key->y;
          XDrawLine(dpy, w, gc, x1, y1, x2, y2);
          XFlush(dpy);
          usleep(5000);
          pos = pos->next;
      }
      XDrawLine(dpy, w, gc, path->begin()->key->x, path->begin()->key->y, path->end()->key->x, path->end()->key->y);
      XFlush(dpy);
      usleep(5000);

}
