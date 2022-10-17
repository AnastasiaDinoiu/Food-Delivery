package service;

import domain.Address;
import domain.Restaurant;
import exceptions.CustomException;
import persistence.RestaurantRepository;

import java.util.List;
import java.util.Optional;

public class RestaurantService {

    private final RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();

    public Restaurant registerNewRestaurant(String name, String phoneNumber, String county, String city, String street,
                                            String restaurantType, Double deliveryCharge) throws CustomException, InstantiationException, IllegalAccessException {
        if (phoneNumber == null || phoneNumber.isEmpty() || !phoneNumber.matches("(07)[0-9]{8}")) {
            throw new CustomException("Invalid restaurant phone number: " + phoneNumber);
        }
        if (name == null || name.isEmpty() || restaurantRepository.findById(name).isPresent()) {
            throw new CustomException("Invalid restaurant name: " + name);
        }
        if (restaurantRepository.findById(name).isPresent()) {
            throw new CustomException("This restaurant name already exists!: " + name);
        }

        Address address = new Address(county, city, street);

        Restaurant restaurant = new Restaurant.Builder(name, address)
                .withPhoneNumber(phoneNumber)
                .withRestaurantType(restaurantType)
                .withDeliveryCharge(deliveryCharge)
                .build();

        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAllRestaurants() throws InstantiationException, IllegalAccessException {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantByName(String name) throws CustomException, InstantiationException, IllegalAccessException {
        if (restaurantRepository.findById(name).isEmpty()) {
            throw new CustomException("Cannot find a restaurant having the provided name: " + name);
        }
        Optional<Restaurant> restaurant = restaurantRepository.findById(name);
        return restaurant.orElseThrow(() -> new CustomException("Cannot find a restaurant having the provided name: " + name));
    }

    public Restaurant getRestaurantByName2(String name) throws CustomException, InstantiationException, IllegalAccessException {
        if (restaurantRepository.findById(name).isEmpty()) {
            throw new CustomException("Cannot find a restaurant having the provided name: " + name);
        }
        return restaurantRepository.findById2(name);
    }

    public void updateDetailsForRestaurant(Restaurant restaurant) throws CustomException, InstantiationException, IllegalAccessException {
        if (restaurant.getPhoneNumber() == null || restaurant.getPhoneNumber().isEmpty() || !restaurant.getPhoneNumber().matches("(07)[0-9]{8}")) {
            throw new CustomException("Invalid restaurant phone number: " + restaurant.getPhoneNumber());
        }
        restaurantRepository.findById(restaurant.getName())
                .orElseThrow(() -> new CustomException("Cannot update provided restaurant: " + restaurant + " It does not exist!"));
        restaurantRepository.update(restaurant);
    }

    public void removeRestaurant(String name) throws CustomException, InstantiationException, IllegalAccessException {
        restaurantRepository.findById(name)
                .orElseThrow(() -> new CustomException("Cannot delete the provided restaurant. It does not exist!"));
        restaurantRepository.delete(name);
    }
}
