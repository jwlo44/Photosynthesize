import processing.core.*;

/**
 * The base bud.
 */
public class RootBud extends ABud {

  private RootBud(PVector p, Tree tree) {
    super();
    this.pos = p;
    this. tree = tree;
    this.clicked = false;
  }

  public static RootBud NewRoot(PVector p, Tree tree) {
    RootBud r = new RootBud(p, tree);
    r.bloom();
    r.bloom();
    r.bloom();
    return r;
  }

  @Override
  public void drag(float mousex, float mousey) {
    //do nothing; rootbuds don't move
  }

  @Override
  public void snap() {
    // do nothing; rootbuds don't break.
  }
}
