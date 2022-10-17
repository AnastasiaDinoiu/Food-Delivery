package domain;

import java.util.Objects;

public class User extends Person {
    private Integer userID;
    private String username;
    private String email;
    private String password;

    public User() {
        super();
    }

    public User(String name, Address address, String phoneNumber, String username, String email, String password) {
        super(name, address, phoneNumber);
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String name, Address address, String phoneNumber, String email, String password) {
        super(name, address, phoneNumber);
        this.email = email;
        this.password = password;
    }

    public User(String name, String username, String email, String password) {
        super(name);
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(User user) {
        super(user);
        this.userID = user.userID;
        this.username = user.username;
        this.email = user.email;
        this.password = user.password;
    }

    public static class Builder {
        private User user = new User();

        public Builder(String email, String password) {
            user.email = email;
            user.password = password;
        }

        public Builder(String name, Address address, String phoneNumber) {
            user.name = name;
            user.address = address;
            user.phoneNumber = phoneNumber;
        }

        public Builder(String name, String username, String email, String password) {
            user.name = name;
            user.username = username;
            user.email = email;
            user.password = password;
        }

        public User.Builder withName(String name) {
            user.name = name;
            return this;
        }

        public User.Builder withUsername(String username) {
            user.username = username;
            return this;
        }

        public User.Builder withAddress(Address address) {
            user.address = address;
            return this;
        }

        public User.Builder withAddress(String county, String city, String street) {
            Address address = new Address(county, city, street);
            user.setAddress(address);
            return this;
        }

        public User.Builder withPhoneNumber(String phoneNumber) {
            user.phoneNumber = phoneNumber;
            return this;
        }

        public User build() {
            return this.user;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userID, user.userID) && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
