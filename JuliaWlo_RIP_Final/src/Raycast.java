import processing.core.PVector;

import java.util.ArrayList;

/**
 * Created by Julia on 11/26/2014.
 */
public class Raycast {

  private Raycast() {}

  /**
   * does a ray cast from this bud in the direction of the sun collide with
   * any branches not attached to this bud?
    */
  public static boolean raytrace(ABud bud, PVector direction,
                   ArrayList<Branch> branches,TreeApplet pa) {
    boolean collided = false;
    PVector p;
    int c = 0;
    float max = (float) Math.sqrt(pa.width * pa.width + pa.height * pa.height
                                      + 1.0);
    for (float t = 0; (t < max) && (c < 64); c++) {
      p = PVector.add(bud.pos.get(), PVector.mult(direction, -t));

      float md = 99999.0f;
      for (Branch b : branches) {
        if (!bud.branches.contains(b) && b != bud.butt) {
          md = Math.min(md, b.getDistanceTo(p));
        }
      }
      //pa.ellipse(p.x, p.y, 2, 2); // for showing raycast steps
      if (md < 4.0) { // tweak this for closeness to hit detection
        collided = true;
        System.out.println("Bud shaded!");
        break;
      }

      if ((p.x < 0.0) || (p.y < 0.0) || (p.x > pa.width) || (p.y > pa.height)) {
        // we made it out of bounds and didn't collide with anything
        System.out.println("Bud lit!");
        break;
      }

      t += 1.5 + md * 0.8; // adaptive step size, don't change these
    }

    //text(str(c), origin.x+ 20, origin.y);

    return !collided;
  }
}
