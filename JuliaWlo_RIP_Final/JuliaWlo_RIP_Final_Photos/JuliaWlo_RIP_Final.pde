/**
 * Continuous Lines. 
 * 
 * Click and drag the mouse to draw a line. 
 */
 
  boolean startPointNotSet = true;
  Point startPoint;

void setup() {
  size(640, 360);
  background(102);
  ellipseMode(CORNERS);
}

void draw() {
  stroke(255);
  clear();
  if (mousePressed == true) {
    if (startPointNotSet) {
    startPoint = new Point(mouseX, mouseY);
    startPointNotSet = false;
    }
    line(mouseX, mouseY, startPoint.x, startPoint.y);
  }
}

void mouseReleased() {
  startPointNotSet = true;
}

class Point {
  int x;
  int y;
  
  Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

}

