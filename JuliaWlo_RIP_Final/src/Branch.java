import processing.core.PApplet;
import processing.core.PVector;


/**
 * Created by Julia on 11/16/2014.
 *
 * Connects two buds.
 * Adds tension to connected buds.
 */
public class Branch {
  // the tree containing this branch; used for snapping
  Tree tree;
  // the start point of this branch, us. the sprouting bud
  ABud start;
  // the end point of this branch, us. the new bud when sprouting
  ABud end;
  // the color to draw all branches
  static int COLOR = 0xFFFFFFFF; // white
  // the max thickness to draw all branches
  static final int STROKE_WEIGHT = 25;
  // the maximum length this Branch can stretch before it breaks
  double breaklength;
  // the average breaklength of all branches
  static double AVGBREAK = 200;

  /**
   * Create a new branch with start and end at the pos of the given Buds
   */
  public Branch(ABud start, ABud end, Tree tree) {
    this.tree = tree;
    this.start = start;
    this.end = end;
    this.breaklength = AVGBREAK + Math.random() * 200;
  }


  /**
   * how close are we to breaking?
   */
  public double breakPercent() {
    return end.pos.dist(start.pos) / breaklength;
  }

  /**
   * destroy this branch and the end node by deleting references to it
   * - remove this branch from its parent node's list of branches
   * - remove the end bud from the Applet's list of buds
   */
  public void snap() {
    end.snap();
    System.out.println("End snapped!");
    start.branches.remove(this);
    System.out.println("Branch removed!");
  }

  /**
   * does this branch contain a given point?
   */
  public boolean containsPoint(PVector p) {
    // bounds for checking if the point is on the line segment
    float xlowbound = Math.min(end.pos.x, start.pos.x);
    float xupbound = Math.max(end.pos.x, start.pos.x);
    float ylowbound = Math.min(end.pos.y, start.pos.y);
    float yupboud = Math.max(end.pos.y, start.pos.y);

    // do super math skillz to project the point onto the line
    PVector slope = PVector.sub(end.pos, start.pos);
    float t = slope.mag();
    slope.mult(1f / t);
    PVector projection =
        PVector.sub(PVector.sub(start.pos, p),
                       PVector.mult(slope,(
                           PVector.sub(start.pos, p).dot(slope))));
    // is the point on the line segment? (not just on the line)
    if (projection.x < xupbound && projection.x > xlowbound &&
        projection.y < yupboud && projection.y > ylowbound) {
      // is the point within the stroke?
      double distance = projection.mag();
      return distance < STROKE_WEIGHT;
    } else return false;
  }

  // calculate how heavy the stroke of this branch should be based on tension
  public float calculateStroke() {
    return STROKE_WEIGHT * (1 - (float) breakPercent());
  }


  float getDistanceTo(PVector p) {
    PVector pa = PVector.sub(p, start.pos); PVector ba = PVector.sub(end.pos,
                                                                     start.pos);
    float h = PApplet.constrain(PVector.dot(pa, ba) / PVector.dot(ba, ba),
                                0.0f, 1.0f);
    return PVector.sub(pa, PVector.mult(ba, h)).mag();

  }

}
