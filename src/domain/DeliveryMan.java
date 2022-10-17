package domain;

import java.util.Objects;

public class DeliveryMan extends Person {
    private Integer deliveryManID;
    private String licensePlate;

    public DeliveryMan() {
        super();
    }

    public DeliveryMan(String name, Address address, String phoneNumber, String licensePlate) {
        super(name, address, phoneNumber);
        this.licensePlate = licensePlate;
    }

    public DeliveryMan(DeliveryMan deliveryMan) {
        super(deliveryMan);
        this.deliveryManID = deliveryMan.deliveryManID;
        this.licensePlate = deliveryMan.licensePlate;
    }

    public static class Builder {
        private DeliveryMan deliveryMan = new DeliveryMan();

        public Builder(String name, Address address, String phoneNumber) {
            deliveryMan.name = name;
            deliveryMan.address = address;
            deliveryMan.phoneNumber = phoneNumber;
        }

        public Builder(String licensePlate) {
            deliveryMan.licensePlate = licensePlate;
        }

        public DeliveryMan.Builder withName(String name) {
            deliveryMan.name = name;
            return this;
        }

        public DeliveryMan.Builder withAddress(Address address) {
            deliveryMan.address = address;
            return this;
        }

        public DeliveryMan.Builder withPhoneNumber(String phoneNumber) {
            deliveryMan.phoneNumber = phoneNumber;
            return this;
        }

        public DeliveryMan.Builder withLicensePlate(String licensePlate) {
            deliveryMan.licensePlate = licensePlate;
            return this;
        }

        public DeliveryMan build() {
            return this.deliveryMan;
        }
    }

    public Integer getDeliveryManID() {
        return deliveryManID;
    }

    public void setDeliveryManID(Integer deliveryManID) {
        this.deliveryManID = deliveryManID;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryMan that = (DeliveryMan) o;
        return Objects.equals(deliveryManID, that.deliveryManID) && Objects.equals(licensePlate, that.licensePlate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryManID, licensePlate);
    }

    @Override
    public String toString() {
        return "DeliveryMan{" +
                "deliveryManID=" + deliveryManID +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                '}';
    }
}
