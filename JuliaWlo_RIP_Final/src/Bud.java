import processing.core.PApplet;
import processing.core.PVector;

/**
 * Created by Julia on 11/15/2014.
 */
public class Bud extends ABud {
  @Override
  public void drag(float mousex, float mousey) {
    pos.y = mousey;
    pos.x = mousex;
  }

  Bud(PVector p, Tree tree) {
    super();
    this.pos = p;
    this.tree = tree;
  }
}
