package se.lolotron.customer.records;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;

import static java.util.Comparator.comparingInt;

public class Application {

  private static final Location DUBLIN_OFFICE_LOCATION = new Location(53.339428, -6.257664);
  private static final int RADIUS_IN_KILOMETERS = 100;

  private static CustomerService customerService = new CustomerService(
    new CustomerReader(new ObjectMapper()),
    new CustomerInviter(new DistanceCalculator()));

  public static void main(String[] args) {
    try (Stream<String> lines = Files.lines(Paths.get(Application.class.getClassLoader().getResource("customers.txt").toURI()))) {
      customerService.readAndFindInvitedCustomers(lines, DUBLIN_OFFICE_LOCATION, RADIUS_IN_KILOMETERS)
        .sorted(comparingInt(Customer::getUserId))
        .forEach(customer -> System.out.println(customer.getUserId() + " " + customer.getName()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
