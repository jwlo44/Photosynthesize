import processing.core.PVector;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Julia on 11/17/2014.
 *
 * The sun shines down on the buds and determines which ones will sprout the
 * next day
 */
public class Sun {
  // the angle the sun is shining
  public int angle;
  // the color to draw the sunlight
  public static final int SUN_COLOR = 0x44FFFFFF; // white overlay
  // the thickness to draw the sunbeams
  public static final int STROKE_WEIGHT = 25;
  // the set of angles the sun can possibly be at
  public static final ArrayList<Integer> ANGLES = new ArrayList<>(
      Arrays.asList(-500, -300,
                    0,
                    300, 500));
  // where we are in angles
  private int angleIndex;

  /**
   * Make a new sun
   */
  public Sun() {
    this.angle = 0;
    this.angleIndex = 3;
  }

  /**
   * get a PVector of the angle of the sun
   */
  public PVector getDirection() {
    PVector res = new PVector(angle, 600);
    res.normalize();
    return res;
  }
  // 600 is totally arbitrary for now


  /**
   * set the angle to the next angle in angles
   */
  public void nextAngle() {
    angleIndex++;
    angleIndex %= ANGLES.size();
    angle = ANGLES.get(angleIndex);
  }
}
