package service;

import domain.Address;
import domain.DeliveryMan;
import exceptions.CustomException;
import persistence.DeliveryManRepository;

import java.util.List;
import java.util.Optional;

public class DeliveryManService {
    private final DeliveryManRepository deliveryManRepository = DeliveryManRepository.getInstance();

    public DeliveryMan registerNewDeliveryMan(String name, String county, String city, String street, String phoneNumber,
                                              String licensePlate) throws CustomException {
        if (phoneNumber == null || phoneNumber.isEmpty() || !phoneNumber.matches("(07)[0-9]{8}")) {
            throw new CustomException("Invalid user phone number: " + phoneNumber);
        }
        if (licensePlate == null || licensePlate.isEmpty() || !licensePlate.matches("[A-Z]{1,2}-[0-9]{2,3}-[A-Z]{3}")) {
            throw new CustomException("Invalid user license plate: " + licensePlate);
        }
        DeliveryMan deliveryMan = new DeliveryMan.Builder(name, new Address(county, city, street), phoneNumber)
                .withLicensePlate(licensePlate)
                .build();

        return deliveryManRepository.save(deliveryMan);
    }

    public List<DeliveryMan> getAllDeliveryMen() throws InstantiationException, IllegalAccessException {
        return deliveryManRepository.findAll();
    }

    public DeliveryMan getDeliveryManByID(Integer id) throws CustomException, InstantiationException, IllegalAccessException {
        Optional<DeliveryMan> deliveryMan = deliveryManRepository.findById(id);
        return deliveryMan.orElseThrow(() -> new CustomException("Cannot find an user having the provided username: " + id));
    }

    public DeliveryMan getDeliveryManByID2(Integer id) throws InstantiationException, IllegalAccessException {
        return deliveryManRepository.findById2(id);
    }


    public void updateDetailsForDeliveryMan(DeliveryMan deliveryMan) throws CustomException, InstantiationException, IllegalAccessException {
        if (deliveryMan.getPhoneNumber() == null || deliveryMan.getPhoneNumber().isEmpty() || !deliveryMan.getPhoneNumber().matches("(07)[0-9]{8}")) {
            throw new CustomException("Invalid delivery man phone number: " + deliveryMan.getPhoneNumber());
        }
        deliveryManRepository.findById(deliveryMan.getDeliveryManID())
                .orElseThrow(() -> new CustomException("Cannot update provided entity: " + deliveryMan + " It does not exist!"));
        deliveryManRepository.update(deliveryMan);
    }

    public void removeDeliveryMan(Integer id) throws CustomException, InstantiationException, IllegalAccessException {
        deliveryManRepository.findById(id)
                .orElseThrow(() -> new CustomException("Cannot delete the provided entity. It does not exist!"));
        deliveryManRepository.delete(id);
    }
}
