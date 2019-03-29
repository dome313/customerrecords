package se.lolotron.customer.records;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CustomerInviterTest {
  private static final Location DESTINATION_LOCATION = new Location(100, 100);
  private static final Location CUSTOMER_LOCATION = new Location(12, 12);
  private static final Customer CUSTOMER = new Customer(12, "Test", CUSTOMER_LOCATION);
  private static Stream<Customer> customerStream;
  private DistanceCalculator distanceCalculator;
  private CustomerInviter customerInviter;

  @Before
  public void setup() {
    customerStream = Stream.of(CUSTOMER);
    distanceCalculator = Mockito.mock(DistanceCalculator.class);

    customerInviter = new CustomerInviter(distanceCalculator);
  }


  @Test
  public void shouldInviteCustomerWithinGeofence() {
    when(distanceCalculator.distanceInKm(DESTINATION_LOCATION, CUSTOMER_LOCATION)).thenReturn(100.0);

    List<Customer> customers = customerInviter.findInvitedCustomers(customerStream, DESTINATION_LOCATION, 120).collect(Collectors.toList());

    assertEquals(1, customers.size());
    assertEquals(CUSTOMER, customers.get(0));
  }

  @Test
  public void shouldNotInviteCustomerOutsideOfGeofence() {
    when(distanceCalculator.distanceInKm(DESTINATION_LOCATION, CUSTOMER_LOCATION)).thenReturn(5000.0);

    List<Customer> customers = customerInviter.findInvitedCustomers(customerStream, DESTINATION_LOCATION, 120).collect(Collectors.toList());

    assertEquals(0, customers.size());
  }

}
