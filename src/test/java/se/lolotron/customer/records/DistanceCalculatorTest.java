package se.lolotron.customer.records;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * the distances are taken from the site https://www.distancecalculator.net/
 */
@RunWith(Parameterized.class)
public class DistanceCalculatorTest {

  private static final Location DUBLIN = new Location(53.3498, -6.2603);
  private static final Location LONDON = new Location(51.5074, -0.1278);
  private static final Location CORK = new Location(51.8985, -8.4756);
  private static final double ALLOWED_DELTA = 1.0;
  private Location locationA;
  private Location locationB;
  private double expectedDistance;

  public DistanceCalculatorTest(Location locationA, Location locationB, double expectedDistance) {
    this.locationA = locationA;
    this.locationB = locationB;
    this.expectedDistance = expectedDistance;
  }

  @Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
      {DUBLIN, LONDON, 463.97},
      {LONDON, DUBLIN, 463.97},
      {DUBLIN, CORK, 219.04},
      {CORK, LONDON, 576.49}
    });
  }

  @Test
  public void shouldCalculateDistance() {
    DistanceCalculator distanceCalculator = new DistanceCalculator();

    double calculatedDistance = distanceCalculator.distanceInKm(locationA, locationB);

    Assert.assertEquals(expectedDistance, calculatedDistance, ALLOWED_DELTA);
  }
}
