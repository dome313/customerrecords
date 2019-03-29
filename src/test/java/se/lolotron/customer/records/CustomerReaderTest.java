package se.lolotron.customer.records;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.databind.ObjectMapper;

import static java.util.stream.Collectors.toList;
import static org.mockito.Mockito.when;

public class CustomerReaderTest {
  private static final String VALID_CUSTOMER_STRING = "{\"latitude\": \"52.2559432\", \"user_id\": 9, \"name\": \"Jack Dempsey\", \"longitude\": \"-7.1048927\"}";
  private static final String MALFORMED_CUSTOMER_STRING = "{\"latitude\": ";
  private static Customer VALID_CUSTOMER = CustomerBuilder.customerBuilder()
    .userId(9)
    .name("Jack Dempsey")
    .location(new Location(52.2559432, -7.1048927))
    .build();

  private CustomerReader customerReader;

  @Before
  public void setup() throws IOException {
    ObjectMapper objectMapper = Mockito.mock(ObjectMapper.class);
    when(objectMapper.readValue(VALID_CUSTOMER_STRING, Customer.class)).thenReturn(VALID_CUSTOMER);
    when(objectMapper.readValue(MALFORMED_CUSTOMER_STRING, Customer.class)).thenThrow(new IOException());
    customerReader = new CustomerReader(objectMapper);
  }

  @Test
  public void shouldConvertValidStringIntoCustomer() {
    Stream<String> customersStrings = Stream.of(VALID_CUSTOMER_STRING);

    List<Customer> customers = customerReader.readCustomers(customersStrings).collect(toList());

    Assert.assertEquals(1, customers.size());
    Assert.assertEquals(customers.get(0), VALID_CUSTOMER);
  }

  @Test
  public void shouldNotConvertInvalidString() {
    Stream<String> customersStrings = Stream.of(MALFORMED_CUSTOMER_STRING);

    List<Customer> customers = customerReader.readCustomers(customersStrings).collect(toList());

    Assert.assertEquals(0, customers.size());
  }

  @Test
  public void shouldNotStopReadingIfEncountersInvalidString() {
    Stream<String> customersStrings = Stream.of(MALFORMED_CUSTOMER_STRING, VALID_CUSTOMER_STRING);

    List<Customer> customers = customerReader.readCustomers(customersStrings).collect(toList());

    Assert.assertEquals(1, customers.size());
    Assert.assertEquals(customers.get(0), VALID_CUSTOMER);
  }
}
