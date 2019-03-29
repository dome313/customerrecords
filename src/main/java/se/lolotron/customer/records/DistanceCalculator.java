package se.lolotron.customer.records;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

public class DistanceCalculator {

  private static final double EARTH_RADIUS_IN_KILOMETERS = 6371;

  public double distanceInKm(Location locationA, Location locationB) {
    double deltaLongitude = toRadians(locationA.getLongitude()) - toRadians(locationB.getLongitude());
    double locationALatitudeRadians = toRadians(locationA.getLatitude());
    double locationBLatitudeRadians = toRadians(locationB.getLatitude());
    double centralAngle = acos(sin(locationALatitudeRadians) * sin(locationBLatitudeRadians) + cos(locationALatitudeRadians) * cos(locationBLatitudeRadians) * cos(deltaLongitude));
    return centralAngle * EARTH_RADIUS_IN_KILOMETERS;
  }

}
