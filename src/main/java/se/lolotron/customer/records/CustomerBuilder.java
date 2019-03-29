package se.lolotron.customer.records;

public class CustomerBuilder {
  private Integer userId;
  private String name;
  private Location location;

  public static CustomerBuilder customerBuilder() {
    return new CustomerBuilder();
  }

  public CustomerBuilder userId(Integer userId) {
    this.userId = userId;
    return this;
  }

  public CustomerBuilder name(String name) {
    this.name = name;
    return this;
  }

  public CustomerBuilder location(Location location) {
    this.location = location;
    return this;
  }

  public Customer build() {
    return new Customer(userId, name, location);
  }
}