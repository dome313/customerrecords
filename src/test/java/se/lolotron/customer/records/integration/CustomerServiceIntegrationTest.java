package se.lolotron.customer.records.integration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import se.lolotron.customer.records.Customer;
import se.lolotron.customer.records.CustomerInviter;
import se.lolotron.customer.records.CustomerReader;
import se.lolotron.customer.records.CustomerService;
import se.lolotron.customer.records.DistanceCalculator;
import se.lolotron.customer.records.Location;

import static org.junit.Assert.assertEquals;
import static se.lolotron.customer.records.CustomerBuilder.customerBuilder;

public class CustomerServiceIntegrationTest {

  private static final Location DUBLIN = new Location(53.3498, -6.2603);
  private static final String CUSTOMER_CLOSE_TO_DUBLIN = "{\"latitude\": \"52.986375\", \"user_id\": 12, \"name\": \"Christina McArdle\", \"longitude\": \"-6.043701\"}";
  private static final String CUSTOMER_IN_RIO = "{\"latitude\": \"-22.90278\", \"user_id\": 11, \"name\": \"Bon dia\", \"longitude\": \"-43.2075\"}";
  private static final String INVALID_CUSTOMER = "{\"latitude\": \"-22.90278\",  \"name\": \"Bon dia\", \"longitude\": \"-43.2075\"}";
  private static final String MALFORMED_CUSTOMER = "<customer><id>123</id><customer>";
  private static final Customer EXPECTED_CUSTOMER = customerBuilder()
    .userId(12)
    .name("Christina McArdle")
    .location(new Location(52.986375, -6.043701))
    .build();

  private CustomerService customerService;

  @Before
  public void setup() {
    customerService = new CustomerService(
      new CustomerReader(new ObjectMapper()),
      new CustomerInviter(new DistanceCalculator()));
  }

  @Test
  public void shouldReadAndInviteCustomersWithinGeofence() {
    Stream<String> lines = Stream.of(CUSTOMER_CLOSE_TO_DUBLIN, CUSTOMER_IN_RIO);

    List<Customer> customers = customerService.readAndFindInvitedCustomers(lines, DUBLIN, 5000).collect(Collectors.toList());

    assertEquals(1, customers.size());
    assertEquals(EXPECTED_CUSTOMER, customers.get(0));
  }

  @Test
  public void shouldSkipInvalidCustomers() {
    Stream<String> lines = Stream.of(INVALID_CUSTOMER, CUSTOMER_CLOSE_TO_DUBLIN);

    List<Customer> customers = customerService.readAndFindInvitedCustomers(lines, DUBLIN, 5000).collect(Collectors.toList());

    assertEquals(1, customers.size());
    assertEquals(EXPECTED_CUSTOMER, customers.get(0));
  }

  @Test
  public void shouldSkipMalformedCustomers() {
    Stream<String> lines = Stream.of(MALFORMED_CUSTOMER, CUSTOMER_CLOSE_TO_DUBLIN);

    List<Customer> customers = customerService.readAndFindInvitedCustomers(lines, DUBLIN, 5000).collect(Collectors.toList());

    assertEquals(1, customers.size());
    assertEquals(EXPECTED_CUSTOMER, customers.get(0));
  }

}
