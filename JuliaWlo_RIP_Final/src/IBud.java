// Interface for buds that sprout new buds

public interface IBud {

  /**
   *  increment this bud's ability to create new buds
   */
  public void bloom();

  /**
   * create a new bud near this Bud
   */
  public Bud sprout();

  /**
   * follow the mouse when clicked and dragged
   */
  public void drag(float mousex, float mousey);

}
