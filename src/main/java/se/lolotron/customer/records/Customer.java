package se.lolotron.customer.records;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer {
  private final Integer userId;
  private final String name;
  private final Location location;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public Customer(@JsonProperty("user_id") Integer userId, @JsonProperty("name") String name,
                  @JsonProperty("latitude") double locationLatitude, @JsonProperty("longitude") double locationLongitude) {
    this(userId, name, new Location(locationLatitude, locationLongitude));
  }

  public Customer(Integer userId, String name, Location location) {
    this.userId = Objects.requireNonNull(userId);
    this.name = Objects.requireNonNull(name);
    this.location = Objects.requireNonNull(location);
  }

  public Integer getUserId() {
    return userId;
  }

  public String getName() {
    return name;
  }

  public Location getLocation() {
    return location;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Customer customer = (Customer) o;
    return userId.equals(customer.userId) &&
      name.equals(customer.name) &&
      location.equals(customer.location);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, name, location);
  }

  @Override
  public String toString() {
    return "Customer{" +
      "userId=" + userId +
      ", name='" + name + '\'' +
      ", location=" + location +
      '}';
  }
}
