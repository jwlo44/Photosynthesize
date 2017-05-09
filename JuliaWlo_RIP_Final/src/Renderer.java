import processing.core.PVector;

import java.util.ArrayList;

/**
 * Created by Julia on 11/25/2014.
 */
// draws all the game objects using an applet
public class Renderer {
  private final TreeApplet pa;

  public Renderer(TreeApplet pa) {
    this.pa = pa;
  }

  // renders a bud AND all its branches
  public void render(ABud b, int treeColor) {
    int color;
    if (b.canSprout()) {
      color = b.SPROUT_COLOR;
      pa.noStroke();
      pa.fill(0xAAFFAA);
      pa.ellipse(b.pos.x, b.pos.y,  ABud.DIAMETER * 1.5f, ABud.DIAMETER * 1.5f);
    } else{
      color = b.NOSPROUT_COLOR;
    }
    int diameter = pa.lerpColor(ABud.DIAMETER, 0,(float) b.breakPercent());
    pa.stroke(treeColor);
    pa.strokeWeight(ABud.STROKE_WEIGHT);
    pa.fill(color);
    pa.ellipse(b.pos.x, b.pos.y, diameter, diameter);
    b.branches.forEach(br -> render(br));
  }


  /**
   * draw the given branch to the screen
   */
  public void render(Branch b) {
    pa.stroke(Branch.COLOR);
    pa.strokeWeight(b.calculateStroke());
    pa.line(b.start.pos.x, b.start.pos.y, b.end.pos.x, b.end.pos.y);
  }

  public void drawShadows(ArrayList<Branch> branches) {
    branches.forEach(b -> drawShadow(b));
  }

  // draw shadows for branches
  public void drawShadow(Branch b) {

    PVector endE = PVector.add(b.end.pos,
                               PVector.mult(pa.sun.getDirection(), 900.0f));

    PVector endS = PVector.add(b.start.pos,
                               PVector.mult(pa.sun.getDirection(), 900.0f));

    pa.fill(125);
    pa.noStroke();
    pa.beginShape();
    pa.vertex(b.start.pos.x, b.start.pos.y);
    pa.vertex(endS.x, endS.y);
    pa.vertex(endE.x, endE.y);
    pa.vertex(b.end.pos.x, b.end.pos.y);
    pa.endShape();
  }

  /**
   * draw the sunlight
   * for now I think i want this to be like a bunch of lines on the screen
   */
  public void render(Sun s){
    pa.strokeWeight(Sun.STROKE_WEIGHT);
    pa.stroke(Sun.SUN_COLOR);
    pa.fill(Sun.SUN_COLOR);
    pa.pushMatrix();
    for (int i = 0; i <= pa.width/Sun.STROKE_WEIGHT/2; i++) {
      pa.line(Sun.STROKE_WEIGHT - s.angle, 0, Sun.STROKE_WEIGHT, pa.height);
      pa.translate(Sun.STROKE_WEIGHT * 3, 0);
    }
    pa.popMatrix();
  }

  /**
   * draw a tree by drawing each bud and each of its bracnches
   */
  public void render(Tree t) {
    render(t.rootBud, t.treeColor);
    t.buds.forEach(b -> render(b, t.treeColor));
  }
}
