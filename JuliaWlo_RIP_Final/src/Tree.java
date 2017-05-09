import processing.core.PVector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Julia on 11/25/2014.
 */
// a group of buds and branches
public class Tree {
  final RootBud rootBud;
  final HashSet<Bud> buds = new HashSet<>();
  final int treeColor;

  // maybe this will work? Not for now; doesn't call mousePress more than once
  /*Bud clickedBud;
  boolean hasClickedBud; */

  public Tree(PVector p, int treeColor) {
    this.rootBud = RootBud.NewRoot(p, this);
    this.treeColor = treeColor;
  }

  // break buds
  // break branches
  // update bud tension
  public void update() {
    rootBud.updateTension();
    buds.forEach(Bud :: updateTension);
    breakBuds();
    breakBranches();
  }


  /**
   * remove an accumulated list of broken branches
   */
  public void breakBranches() {
    Set<Branch> brokens = new HashSet<>();
    for (Branch br: rootBud.branches) {
      if (br.breakPercent() >= 1) {
        brokens.add(br);
      }
    }
    for (Bud b : buds) {
      for (Branch br : b.branches) {
        if (br.breakPercent() >= 1) {
          brokens.add(br);
        }
      }
    }
    brokens.forEach(Branch :: snap);
  }

  /**
   * remove an accumulated list of broken buds
   */
  public void breakBuds() {
    Set<Bud> brokens = new HashSet<Bud>();
    // rootBud never breaks
    for (Bud b : buds) {
      if (b.breakPercent() >= 1) {
        brokens.add(b);
      }
    }
    brokens.forEach(Bud :: snap);
  }

  /**
   * can any bud on this tree sprout?
   */
  public boolean canSprout() {
   /* if (hasClickedBud) {
      return false;
    }*/
    if (rootBud.canSprout()) {
      return true;
    }
    for (Bud b : buds) {
      if (b.canSprout()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Does any bud on this tree contain the point where the mouse clicked? If
   * so, set the treeapplet's clicked bud to the first bud found containing
   * that point.
   */
  public boolean mousePressed(float mouseX, float mouseY, TreeApplet pa) {
    if (rootBud.containsPoint(new PVector(mouseX, mouseY))
        && rootBud.canSprout()) {
      Bud sprout = rootBud.sprout();
      pa.clickedBud = sprout;
      buds.add(sprout);
      pa.hasClickedBud = true;
      return true;
    } else {
      for (Bud b : buds) {
        if (b.containsPoint(new PVector(mouseX, mouseY))
            && b.canSprout()) {
          Bud sprout = b.sprout();
          pa.clickedBud = sprout;
          buds.add(sprout);
          pa.hasClickedBud = true;
          return true;
        }
      }
      return false;
    }
  }

  /**
   * get all the branches on this tree?
   */
  public ArrayList<Branch> getAllBranches() {
    ArrayList<Branch> dest = new ArrayList<>();
    dest.addAll(rootBud.branches);
    buds.forEach(b -> dest.addAll(b.branches));
    return dest;
  }

  /**
   * bloom() buds that receive sun, don't bloom buds that don't!
   */
  public void shadeBuds(Sun sun, Tree t2, TreeApplet pa) {
    ArrayList<Branch> branches = new ArrayList<>();
    branches.addAll(getAllBranches());
    branches.addAll(t2.getAllBranches());
    if (Raycast.raytrace(rootBud, sun.getDirection(), branches, pa)) {
      rootBud.bloom();
    }
    for (Bud b : buds) {
      if (Raycast.raytrace(b, sun.getDirection(), branches, pa)) {
        b.bloom();
      }
    }
  }
}
