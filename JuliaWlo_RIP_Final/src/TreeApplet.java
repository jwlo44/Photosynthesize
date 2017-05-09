import processing.core.*;

/**
 * Created by Julia on 11/15/2014.
 */
public class TreeApplet extends PApplet {

  // Main method. Uncomment and run as java application for present mode.
 public static void main(String args[]) {
    PApplet.main(new String[] { "--present", "TreeApplet" });
  }

  Renderer renderer;
  Sun sun;
  Tree tree1;
  Tree tree2;
  Bud clickedBud;
  boolean hasClickedBud;
  boolean night;
  static int BG_COLOR_DAY = 0xFF00AAFF; // blue cyan
  static int BG_COLOR_NIGHT = 0xFF440066; // dark purple
  static int TREE_COLOR_1 = 0xFF0000FF; // blue
  static int TREE_COLOR_2 = 0xFFFF0000; // red

  /**
   * Plant root buds
   * create sun
   * clicked bud is false
   */
  public void gameSetup() {
    renderer = new Renderer(this);
    tree1 = new Tree(new PVector(width/3, height - RootBud.DIAMETER / 2),
                     TREE_COLOR_1);
    tree2 = new Tree(new PVector(2*width/3, height - RootBud.DIAMETER / 2),
                     TREE_COLOR_2);
    sun = new Sun();
    hasClickedBud = false;
    night = true;
  }

  @Override
  public void setup() {
    size(500, 1000);
    background(BG_COLOR_DAY);
    gameSetup();
  }


  // draw things
  @Override
  public void draw() {
    if (night) {
      nightPause();
    } else {
      daytime();
    }
  }

  // stop updating and wait for click
  // display a message "Ready?"
  public void nightPause() {
    background(BG_COLOR_NIGHT);
    drawAllTheThings();
    textAlign(CENTER);
    textSize(72);
    fill(255);
    text("Ready?", width/2, height/2);
  }

  // move the clicked bud
  // break broken things
  public void daytime() {
    if (noOneCanSprout()) {
      background(BG_COLOR_NIGHT);
      night = true;
      nighttime();
    } else {
      background(BG_COLOR_DAY);
      if (hasClickedBud) {
        clickedBud.drag(mouseX, mouseY);
      }
      tree1.update();
      tree2.update();
    }
    drawAllTheThings();
  }

  // draw all the things
  public void drawAllTheThings() {
    renderer.render(sun);
    renderer.drawShadows(tree1.getAllBranches());
    renderer.drawShadows(tree2.getAllBranches());
    renderer.render(tree1);
    renderer.render(tree2);
  }

  // click and drag buds
  @Override
  public void mousePressed() {
    if (night) {
      sun.nextAngle();

      night = false;
    } else {
      if (tree1.mousePressed(mouseX, mouseY, this)) {
        return;
      }
      else tree2.mousePressed(mouseX, mouseY, this);
    }
  }

  // release clickedBud
  @Override
  public void mouseReleased() {
    hasClickedBud = false;
    clickedBud = null;
  }

  /**
   * check if it's nighttime, i.e. no more buds canSprout
   */
  public boolean noOneCanSprout() {
    return !(hasClickedBud || tree1.canSprout() || tree2.canSprout());
  }

  /**
   * Do nighttime stuff: check if buds get sunlight, sprout
   */
  public void nighttime() {
    tree1.shadeBuds(sun, tree2, this);
    tree2.shadeBuds(sun, tree1, this);
  }

}
