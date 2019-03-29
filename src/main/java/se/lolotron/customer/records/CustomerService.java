package se.lolotron.customer.records;

import java.util.stream.Stream;

public class CustomerService {
  private final CustomerReader customerReader;
  private final CustomerInviter customerInviter;

  public CustomerService(CustomerReader customerReader, CustomerInviter customerInviter) {
    this.customerReader = customerReader;
    this.customerInviter = customerInviter;
  }

  public Stream<Customer> readAndFindInvitedCustomers(Stream<String> lines, Location location, double radiusInKm) {
    Stream<Customer> allValidCustomer = customerReader.readCustomers(lines);
    return customerInviter.findInvitedCustomers(allValidCustomer, location, radiusInKm);
  }
}
