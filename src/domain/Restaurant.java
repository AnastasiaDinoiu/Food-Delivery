package domain;

import comparator.GradeReviewComparator;

import java.util.*;

public class Restaurant implements Cloneable {
    private Integer restaurantID;
    private String name;
    private String phoneNumber;
    private Address address;
    private String restaurantType;
    private Double deliveryCharge = 0.0;

    @Override
    public Object clone() throws CloneNotSupportedException {
        Restaurant restaurant = new Restaurant();
        restaurant.name = name;
        restaurant.phoneNumber = phoneNumber;
        restaurant.address = (Address) address.clone();
        restaurant.restaurantType = restaurantType;
        restaurant.deliveryCharge = deliveryCharge;

        return restaurant;
    }

    public Restaurant() {
    }

    public Restaurant(String phoneNumber, Address address, String restaurantType, Double deliveryCharge) {
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.restaurantType = restaurantType;
        this.deliveryCharge = deliveryCharge;
    }

    public Restaurant(String name, String phoneNumber, Address address, String restaurantType, Double deliveryCharge) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.restaurantType = restaurantType;
        this.deliveryCharge = deliveryCharge;
    }

    public Restaurant(Restaurant restaurant) {
        this.restaurantID = restaurant.restaurantID;
        this.name = restaurant.name;
        this.phoneNumber = restaurant.phoneNumber;
        this.address = restaurant.address;
        this.restaurantType = restaurant.restaurantType;
        this.deliveryCharge = restaurant.deliveryCharge;
    }

    public static class Builder {
        private Restaurant restaurant = new Restaurant();

        public Builder(String name, Address address) {
            restaurant.name = name;
            restaurant.address = address;
        }

        public Restaurant.Builder withPhoneNumber(String phoneNumber) {
            restaurant.phoneNumber = phoneNumber;
            return this;
        }

        public Restaurant.Builder withRestaurantType(String restaurantType) {
            restaurant.restaurantType = restaurantType;
            return this;
        }

        public Restaurant.Builder withDeliveryCharge(Double deliveryCharge) {
            restaurant.deliveryCharge = deliveryCharge;
            return this;
        }

        public Restaurant build() {
            return this.restaurant;
        }
    }

    public Integer getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(Integer restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getRestaurantType() {
        return restaurantType;
    }

    public void setRestaurantType(String restaurantType) {
        this.restaurantType = restaurantType;
    }

    public Double getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(Double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(restaurantID, that.restaurantID) && Objects.equals(name, that.name) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(address, that.address) && restaurantType == that.restaurantType && Objects.equals(deliveryCharge, that.deliveryCharge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantID, name, phoneNumber, address, restaurantType, deliveryCharge);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantID=" + restaurantID +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address=" + address +
                ", restaurantType=" + restaurantType +
                ", deliveryCharge=" + deliveryCharge +
                '}';
    }
}
