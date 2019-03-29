package se.lolotron.customer.records;

import java.util.stream.Stream;

public class CustomerInviter {

  private final DistanceCalculator distanceCalculator;

  public CustomerInviter(DistanceCalculator distanceCalculator) {
    this.distanceCalculator = distanceCalculator;
  }

  public Stream<Customer> findInvitedCustomers(Stream<Customer> customerStream, Location location, double radiusInKm) {
    return customerStream
      .filter(customer -> isCustomerWithinGeofence(customer, location, radiusInKm));
  }

  private boolean isCustomerWithinGeofence(Customer customer, Location location, double radiusInKm) {
    return distanceCalculator.distanceInKm(location, customer.getLocation()) < radiusInKm;
  }

}
