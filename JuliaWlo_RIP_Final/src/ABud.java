import processing.core.PVector;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Julia on 11/15/2014.
 */
abstract class ABud implements IBud  {
  // the tree this is in; only used for snapping?
  Tree tree;
  // the number of new buds this bud can currently generate
  int bloomCount;
  // where this bud is
  PVector pos;
  // if the mouse has clicked on this bud
  boolean clicked;
  // the size of the circle to draw
  static int DIAMETER = 50;
  // the color of the circle to draw when it can sprout
  static int SPROUT_COLOR = 0xFF88FF44;
  // the color of the cirlce to draw when it cannot sprout
  static int NOSPROUT_COLOR = 0xFFFFFF00;
  // the thickness to draw the outline of each bud
  static int STROKE_WEIGHT = 10;
  // the list of branches growing off of this bud
  ArrayList<Branch> branches = new ArrayList<>();
  // the tension force on this bud
  PVector tension;
  // the average max tension a bud can handle before breaking
  static double AVG_BREAK = 500d;
  // the break force of this bud
  double breakforce;

  public Branch butt;

  public ABud() {
    this.clicked = true;
    this.bloomCount = 0;
    this.tension = new PVector(0, 0);
    this.breakforce = AVG_BREAK + Math.random() * (AVG_BREAK/2);
  }

  @Override
  public void bloom() {
    bloomCount++;
  }

  // PRECONDITION: canSprout();
  @Override
  public Bud sprout() {
    bloomCount--;
    System.out.println("new bud!");
    Bud result = new Bud(new PVector(pos.x, pos.y), tree);
    Branch br = new Branch(this, result, tree);
    result.butt = br;
    branches.add(br);
    return result;
  }

  /**
   * is the given point within the circle of this bud?
   * @param p the point to bounds check
   * @return whether p is in the circle
   */
  public boolean containsPoint(PVector p) {
    double dist = pos.dist(p);
    return dist < DIAMETER / 2d;
  }

  /**
   * can this bud sprout?
   */
  public boolean canSprout() {
    return bloomCount > 0;
  }

  /**
   * destroy this bud
   */
  public void snap() {
    snapAllBranches();
    tree.buds.remove(this);
    System.out.println("Bud removed!");
    butt.start.branches.remove(butt);
  }

  /**
   * Stupid ConcurrentModickficationExceptions
   */
  public void snapAllBranches() {
    Set<Branch> brokens = new HashSet<>(branches);
    brokens.forEach(Branch :: snap);
  }

  /**
   * update the tension on this bud
   * tension = avg of all branch ends
   * The way this works is that all the branches STARTING from this bud (not
   * ending on it) pull away from it in different directions. So,
   * two branches extending in opposite directions will negate each other's
   * tensions, while two branches extending the same direction will add to a
   * larger tension.
   */
  public void updateTension() {
    PVector result = new PVector();
    if (branches.size() > 0) {
      branches.forEach(b ->
        result.add(PVector.sub(b.end.pos, pos))
                       // get the sum of the vectors of branches from this bud
      );
      tension = result;
    }
  }

  /**
   * how close are we to breaking?
   */
  public double breakPercent() {
    return tension.mag() / breakforce;
  }
}
