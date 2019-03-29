package se.lolotron.customer.records;

import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

  private CustomerReader customerReader;
  private CustomerInviter customerInviter;
  private CustomerService customerService;

  @Before
  public void setup() {
    customerReader = mock(CustomerReader.class);
    customerInviter = mock(CustomerInviter.class);

    customerService = new CustomerService(customerReader, customerInviter);
  }

  @Test
  public void shouldReadAndFindInvitedCustomers() {
    Stream<String> lines = Stream.of("{\"latitude\": \"52.2559432\", \"user_id\": 9, \"name\": \"Jack Dempsey\", \"longitude\": \"-7.1048927\"}");
    double radiusInKm = 123;
    Location location = new Location(12, 123);
    Customer customer = new Customer(12, "123", new Location(12, 12));
    Stream<Customer> customerStream = Stream.of(customer);

    when(customerReader.readCustomers(lines)).thenReturn(customerStream);
    when(customerInviter.findInvitedCustomers(customerStream, location, radiusInKm)).thenReturn(Stream.empty());

    customerService.readAndFindInvitedCustomers(lines, location, radiusInKm);

    verify(customerReader).readCustomers(lines);
    verify(customerInviter).findInvitedCustomers(customerStream, location, radiusInKm);
  }

}
