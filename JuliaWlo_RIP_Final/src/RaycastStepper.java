import java.util.function.Predicate;

/**
 * Created by Julia on 11/17/2014.
 */
public class RaycastStepper {
  private RaycastStepper() {}

  static int STEPSIZE = 26;

  /**
   * Does the given bud get sun?
   * Determined by whether a ray cast from this bud upwards at the angle of
   * the sun collides with any other buds or branches.
   */
  // PRECONDITION: stepsize is positive
  /*public static boolean doesntCollide(ABud b, TreeApplet pa) {
    // get the slope of the sun
    Point sunslope = new Point(-pa.sun.angle, -pa.height);
    // convert slope into unit vector
    sunslope.scaleBy( 1f/ (float) sunslope.magnitude());
    // scale slope to stepsize
    sunslope.scaleBy(STEPSIZE);
    // the point we're currently stepping on
    Point cur = new Point(b.pos.x, b.pos.y); // copied so we can mutate
    while (0 < cur.x && cur.x < pa.width && cur.y > 0 && cur.y < pa.height) {
      cur.add(sunslope);
      for (Bud bud : pa.buds) {
        if (bud.containsPoint(cur)) {
          System.out.println("Bud blocked!");
          return false;
        }
        for (Branch branch : bud.branches) {
          System.out.println("Check out this branch!");
          if (branch.containsPoint(cur)) {
            System.out.println("Branch blocked!");
            return false;
          }
        }
      }
    }
    return true;
  }
  */
}
