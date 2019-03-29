package se.lolotron.customer.records;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomerReader {
  private final Logger log = LoggerFactory.getLogger(CustomerReader.class);
  private final ObjectMapper objectMapper;

  public CustomerReader(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public Stream<Customer> readCustomers(Stream<String> stream) {
    return stream.map(this::convertToCustomerObject)
      .flatMap(Optional::stream);
  }

  private Optional<Customer> convertToCustomerObject(String customerJson) {
    try {
      return Optional.of(objectMapper.readValue(customerJson, Customer.class));
    } catch (IOException e) {
      log.error("Cannot convert string '{}' into customer", customerJson, e);
      return Optional.empty();
    }
  }
}
