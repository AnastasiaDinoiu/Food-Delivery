package domain;

public class Address implements Cloneable {
    protected String county;
    protected String city;
    protected String street;

    @Override
    public Object clone() throws CloneNotSupportedException {
        Address address = new Address();
        address.county = county;
        address.city = city;
        address.street = street;

        return address;
    }

    public Address() {
    }

    public Address(String county, String city, String street) {
        this.county = county;
        this.city = city;
        this.street = street;
    }

    public static class Builder {
        private Address address = new Address();

        public Builder(String county, String city, String street) {
            address.county = county;
            address.city = city;
            address.street = street;
        }

        public Address build() {
            return this.address;
        }
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address{" +
                "county='" + county + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}
