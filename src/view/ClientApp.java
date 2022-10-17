package view;

import domain.*;
import exceptions.CustomException;
import service.*;

import java.util.*;

public class ClientApp {
    private final Scanner scanner = new Scanner(System.in);

    private final UserService userService = new UserService();
    private final RestaurantService restaurantService = new RestaurantService();
    private final ProductService productService = new ProductService();
    private final OrderService orderService = new OrderService();
    private final ReviewService reviewService = new ReviewService();
    private final DeliveryManService deliveryManService = new DeliveryManService();

    private static ClientApp instance;

    private ClientApp() {
    }

    public static ClientApp getInstance() {
        if (instance == null) {
            instance = new ClientApp();
        }
        return instance;
    }

    public void start() throws InstantiationException, IllegalAccessException, CustomException {

        while (true) {
            showMenu();
            int option = readOption();
            switch (option) {
                case 1 -> addRestaurant();
                case 2 -> listAllRestaurants();
                case 3 -> findRestaurantByName();
                case 4 -> findRestaurantsByCity();
                case 5 -> listRestaurantsByDeliveryCharge();
                case 6 -> updateRestaurantDetails();
                case 7 -> removeRestaurant();
                case 8 -> addProduct();
                case 9 -> listAllProducts();
                case 10 -> removeProduct();
                case 11 -> addUser();
                case 12 -> listAllUsers();
                case 13 -> findUserByUsername();
                case 14 -> updateUserDetails();
                case 15 -> removeUser();
                case 16 -> makeOrder();
                case 17 -> listAllOrders();
                case 18 -> leaveReview();
                case 19 -> addDeliveryMan();
                case 20 -> listAllDeliveryMen();
                case 21 -> assignOrdersToDeliveryMen();
                case 0 -> {
                    scanner.close();
                    System.exit(0);
                }
            }
        }
    }

    private void showMenu() {
        System.out.println("---------------------------------------");
        System.out.println("Choose one of the options below:");

        // RESTAURANT
        System.out.println("1. Add a restaurant");
        System.out.println("2. Show all restaurants");
        System.out.println("3. Find restaurant by name");
        System.out.println("4. Show all restaurants from a specific city");
        System.out.println("5. Show all restaurants by their delivery charge");
        System.out.println("6. Modify a restaurant");
        System.out.println("7. Remove a restaurant");

        // PRODUCT
        System.out.println("8. Add a product");
        System.out.println("9. Show all products");
        System.out.println("10. Remove a product");

        // USER
        System.out.println("11. Add a client");
        System.out.println("12. Show all clients");
        System.out.println("13. Find client by their username");
        System.out.println("14. Modify a client's details");
        System.out.println("15. Remove a client");


        // ORDER
        System.out.println("16. Make an order");
        System.out.println("17. View orders");

        // REVIEW
        System.out.println("18. Leave a review to a restaurant");

        // DELIVERY_MAN
        System.out.println("19. Add delivery man");
        System.out.println("20. Show all delivery men");
        System.out.println("21. Assign orders to delivery men");

        // End
        System.out.println("0. Exit");
    }

    private int readOption() {
        int option = 0;
        boolean incorrectInput = true;
        while (incorrectInput) {
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                if (option < 0 || option > 21) {
                    System.out.println("Invalid option! Try again!");
                } else {
                    incorrectInput = false;
                }
            } else {
                scanner.next();
                System.out.println("Invalid number! Try again!");
            }
        }
        return option;
    }

    private Product readProductDetails() throws InstantiationException, IllegalAccessException {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        boolean validID = false;
        Integer restaurantID = null;
        while (!validID) {
            System.out.println("Restaurant ID: ");
            restaurantID = scanner.nextInt();
            for (Restaurant restaurant : restaurants) {
                if (Objects.equals(restaurant.getRestaurantID(), restaurantID)) {
                    validID = true;
                    break;
                }
            }
            if (!validID) {
                System.out.println("Cannot find a delivery man having the provided id: " + restaurantID + ". Try again!");
            }
        }
        System.out.println("Name: ");
        String name = scanner.next();
        System.out.println("Price: ");
        Double price = scanner.nextDouble();

        return new Product(restaurantID, name, price);
    }

    private void addProduct() throws InstantiationException, IllegalAccessException {
        Product product = readProductDetails();
        try {
            productService.registerNewProduct(product.getRestaurantID(), product.getName(), product.getPrice());
            System.out.println("Added new product: " + product);
        } catch (CustomException customException) {
            System.out.println(customException.getMessage());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void listAllProducts() throws InstantiationException, IllegalAccessException {
        System.out.println("Currently registered products: ");
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products registered yet!");
        } else {
            products.forEach(System.out::println);
        }
    }

    private void removeProduct() {
        System.out.println("Name: ");
        String name = scanner.next();
        try {
            productService.removeProduct(name);
            System.out.println("Removed product!");
        } catch (CustomException | InstantiationException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    private User readUserDetails() {
        System.out.println("Name: ");
        String name = scanner.next();
        System.out.println("County: ");
        String county = scanner.next();
        System.out.println("City: ");
        String city = scanner.next();
        System.out.println("Street: ");
        String street = scanner.next();
        Address address = new Address(county, city, street);
        System.out.println("Phone number: ");
        String phoneNumber = scanner.next();
        System.out.println("Username: ");
        String username = scanner.next();
        System.out.println("Email: ");
        String email = scanner.next();
        System.out.println("Password: ");
        String password = scanner.next();

        return new User(name, address, phoneNumber, username, email, password);
    }

    private User readUserDetailsWithoutUsername() {
        System.out.println("Name: ");
        String name = scanner.next();
        System.out.println("County: ");
        String county = scanner.next();
        System.out.println("City: ");
        String city = scanner.next();
        System.out.println("Street: ");
        String street = scanner.next();
        Address address = new Address(county, city, street);
        System.out.println("Phone number: ");
        String phoneNumber = scanner.next();
        System.out.println("Email: ");
        String email = scanner.next();
        System.out.println("Password: ");
        String password = scanner.next();

        return new User(name, address, phoneNumber, email, password);
    }

    private void addUser() {
        User user = readUserDetails();
        try {
            userService.registerNewUser(user.getName(), user.getAddress().getCounty(),
                    user.getAddress().getCity(), user.getAddress().getStreet(),
                    user.getPhoneNumber(), user.getUsername(), user.getEmail(), user.getPassword());
            System.out.println("Registered new user: " + user);
        } catch (CustomException customException) {
            System.out.println(customException.getMessage());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void listAllUsers() throws InstantiationException, IllegalAccessException {
        System.out.println("Currently registered users: ");
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users registered yet!");
        } else {
            users.forEach(System.out::println);
        }
    }

    private void findUserByUsername() {
        System.out.println("User username: ");
        String username = scanner.next();
        try {
            User user = userService.getUserByUsername(username);
            System.out.println("Found user: " + user);
        } catch (CustomException | InstantiationException | IllegalAccessException customException) {
            System.out.println(customException.getMessage());
        }
    }

    private void updateUserDetails() {
        System.out.println("User username: ");
        String username = scanner.next();
        try {
            User user = userService.getUserByUsername(username);
            user = readUserDetailsWithoutUsername();
            user.setUsername(username);
            userService.updateDetailsForUser(user);
        } catch (CustomException customException) {
            System.out.println(customException.getMessage());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void removeUser() {
        System.out.println("Username: ");
        String username = scanner.next();
        try {
            userService.removeUser(username);
            System.out.println("Removed user!");
        } catch (CustomException | InstantiationException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    private Restaurant readRestaurantDetails() {
        System.out.println("Name: ");
        String name = scanner.next();
        System.out.println("County: ");
        String county = scanner.next();
        System.out.println("City: ");
        String city = scanner.next();
        System.out.println("Street: ");
        String street = scanner.next();
        Address address = new Address(county, city, street);
        System.out.println("Phone number: ");
        String phoneNumber = scanner.next();
        System.out.println("Restaurant type: ");
        String restaurantType = scanner.next();
        System.out.println("Delivery charge: ");
        Double deliveryCharge = scanner.nextDouble();

        return new Restaurant(name, phoneNumber, address, restaurantType, deliveryCharge);
    }

    private Restaurant readRestaurantDetailsWithoutName() {
        System.out.println("County: ");
        String county = scanner.next();
        System.out.println("City: ");
        String city = scanner.next();
        System.out.println("Street: ");
        String street = scanner.next();
        Address address = new Address(county, city, street);
        System.out.println("Phone number: ");
        String phoneNumber = scanner.next();
        System.out.println("Restaurant type: ");
        String restaurantType = scanner.next();
        System.out.println("Delivery charge: ");
        Double deliveryCharge = scanner.nextDouble();

        return new Restaurant(phoneNumber, address, restaurantType, deliveryCharge);
    }

    private void addRestaurant() {
        Restaurant restaurant = readRestaurantDetails();
        try {
            restaurantService.registerNewRestaurant(restaurant.getName(), restaurant.getPhoneNumber(),
                    restaurant.getAddress().getCounty(), restaurant.getAddress().getCity(), restaurant.getAddress().getStreet(),
                    restaurant.getRestaurantType(), restaurant.getDeliveryCharge());
            System.out.println("Registered new restaurant: " + restaurant);
        } catch (CustomException | InstantiationException | IllegalAccessException customException) {
            System.out.println(customException.getMessage());
        }
    }

    private void listAllRestaurants() throws InstantiationException, IllegalAccessException {
        System.out.println("Currently registered restaurants: ");
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        if (restaurants.isEmpty()) {
            System.out.println("No restaurants registered yet!");
        } else {
            restaurants.forEach(System.out::println);
        }
    }

    private void findRestaurantByName() {
        System.out.println("Restaurant name: ");
        String name = scanner.next();
        try {
            Restaurant restaurant = restaurantService.getRestaurantByName(name);
            System.out.println("Found restaurant: " + restaurant);
        } catch (CustomException | InstantiationException | IllegalAccessException customException) {
            System.out.println(customException.getMessage());
        }
    }

    private void updateRestaurantDetails() {
        System.out.println("Restaurant name: ");
        String name = scanner.next();
        try {
            Restaurant restaurant = restaurantService.getRestaurantByName(name);
            restaurant = readRestaurantDetailsWithoutName();
            restaurant.setName(name);
            restaurantService.updateDetailsForRestaurant(restaurant);
        } catch (CustomException | InstantiationException | IllegalAccessException customException) {
            System.out.println(customException.getMessage());
        }
    }

    private void removeRestaurant() {
        System.out.println("Restaurant name: ");
        String name = scanner.next();
        try {
            restaurantService.removeRestaurant(name);
            System.out.println("Removed restaurant!");
        } catch (CustomException | InstantiationException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    private void findRestaurantsByCity() throws InstantiationException, IllegalAccessException {
        System.out.println("City: ");
        String city = scanner.next();
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        List<Restaurant> restaurantsByCity = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            if (Objects.equals(restaurant.getAddress().getCity(), city)) {
                restaurantsByCity.add(restaurant);
            }
        }
        if (restaurantsByCity.isEmpty()) {
            System.out.println("No restaurants in city " + city);
        } else {
            restaurantsByCity.forEach(System.out::println);
        }
    }

    private void listRestaurantsByDeliveryCharge() throws InstantiationException, IllegalAccessException {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        Comparator<Restaurant> deliveryComparator = (Restaurant rest1, Restaurant rest2)
                -> (int) (rest1.getDeliveryCharge() - rest2.getDeliveryCharge());
        restaurants.sort(deliveryComparator);

        if (restaurants.isEmpty()) {
            System.out.println("No restaurants registered yet!");
        } else {
            restaurants.forEach(System.out::println);
        }
    }

    private void makeOrder() throws InstantiationException, IllegalAccessException {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        if (restaurants.isEmpty()) {
            System.out.println("No restaurants registered yet!");
        } else {
            System.out.println("Username of the user who will make the order: ");
            String username = scanner.next();
            try {
                User user = userService.getUserByUsername(username);

                Restaurant restaurant = chooseRestaurant();
                assert restaurant != null;
                Order order = chooseProducts(user, restaurant);
                assert order != null;

                if (order.getProductsOrdered().isEmpty()) {
                    System.out.println("Your cart is empty! The order will not be processed");
                } else {
                    Double finalPrice = 0.0;
                    for (Map.Entry mapElem : order.getProductsOrdered().entrySet()) {
                        Product product = (Product) mapElem.getKey();
                        Integer noProducts = (Integer) mapElem.getValue();

                        finalPrice += product.getPrice() * noProducts;
                    }

                    finalPrice += restaurant.getDeliveryCharge();
                    order.setFinalPrice(finalPrice);

                    orderService.registerNewOrder(user.getUserID(), restaurant.getRestaurantID(),
                            order.getProductsOrdered(), order.getFinalPrice());
                    orderService.registerNewProductsFromOrder(user.getUserID(), restaurant.getRestaurantID(),
                            order.getProductsOrdered(), order.getFinalPrice());
                    System.out.println("Registered new order: " + order);
                }
            } catch (CustomException customException) {
                System.out.println(customException.getMessage());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private Restaurant chooseRestaurant() throws CustomException, InstantiationException, IllegalAccessException {
        System.out.println("Choose one of the restaurants below: ");
        listAllRestaurants();

        boolean foundRestaurant = false;
        while (!foundRestaurant) {
            System.out.println("The name of the restaurant: ");
            String name = scanner.next();
            Restaurant restaurant = restaurantService.getRestaurantByName2(name);
            if (restaurant != null) {
                return restaurant;
            } else {
                System.out.println("Cannot find a restaurant having the provided name: " + name + ". Try again!");
            }
        }
        return null;
    }

    private Order chooseProducts(User user, Restaurant restaurant) throws InstantiationException, IllegalAccessException {
        HashMap<Product, Integer> productsOrdered = new HashMap<>();
        Order order = new Order.Builder(user.getUserID(), restaurant.getRestaurantID()).withProducts(productsOrdered).build();

        List<Product> products = productService.getAllProducts();

        List<Product> restaurantProducts = new ArrayList<>();
        for (Product product : products) {
            if (Objects.equals(product.getRestaurantID(), restaurant.getRestaurantID())) {
                restaurantProducts.add(product);
            }
        }
        if (restaurantProducts.isEmpty()) {
            System.out.println("The restaurant has no products in stock at the moment!");
            return null;
        } else {
            restaurantProducts.forEach(System.out::println);
            boolean wantsToOrder = true;
            int option = 0;
            while (wantsToOrder) {
                System.out.println("Choose an option: 1. View restaurant's products; 2. Add a product; 3. View products in cart; 0. Finish order");
                option = scanner.nextInt();
                switch (option) {
                    case 1 -> restaurantProducts.forEach(System.out::println);
                    case 2 -> {
                        System.out.println("Choose the index of the product to order");
                        for (int i = 1; i <= restaurantProducts.size(); i++) {
                            System.out.println(i + ": " + restaurantProducts.get(i - 1));
                        }
                        int index = 0;
                        boolean incorrectIndex = true;
                        while (incorrectIndex) {
                            if (scanner.hasNextInt()) {
                                index = scanner.nextInt();
                                if (index <= 0 || index > restaurantProducts.size()) {
                                    System.out.println("Invalid index! Try again!");
                                } else {
                                    incorrectIndex = false;
                                }
                            } else {
                                scanner.next();
                                System.out.println("Invalid number! Try again!");
                            }
                        }
                        index--;
                        System.out.println("Type the number of products of that type to order: ");
                        int noProducts = scanner.nextInt();
                        if (productsOrdered.containsKey(restaurantProducts.get(index))) {
                            noProducts += productsOrdered.get(restaurantProducts.get(index));
                            productsOrdered.put(restaurantProducts.get(index), noProducts);
                        } else {
                            productsOrdered.put(restaurantProducts.get(index), noProducts);
                        }
                    }
                    case 3 -> {
                        if (order.getProductsOrdered().isEmpty()) {
                            System.out.println("Your cart is empty!");
                        } else {
                            order.getProductsOrdered().forEach((key, value) -> System.out.println(key + " " + value));
                        }
                    }
                    case 0 -> {
                        wantsToOrder = false;
                        Double finalPrice = 0.0;
                        for (Map.Entry mapElem : order.getProductsOrdered().entrySet()) {
                            Product product = (Product) mapElem.getKey();
                            Integer noProducts = (Integer) mapElem.getValue();

                            finalPrice += product.getPrice() * noProducts;
                        }
                        finalPrice += restaurant.getDeliveryCharge();
                        order.setFinalPrice(finalPrice);
                    }
                    default -> System.out.println("Invalid option! Try again!");
                }
            }
            return order;
        }
    }

    private void listAllOrders() throws InstantiationException, IllegalAccessException {
        System.out.println("Currently registered orders: ");
        List<Order> orders = orderService.getAllOrders();

        if (orders.isEmpty()) {
            System.out.println("No orders registered yet!");
        } else {
            orders.forEach(System.out::println);
        }
    }

    private void leaveReview() throws InstantiationException, IllegalAccessException {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        if (restaurants.isEmpty()) {
            System.out.println("No restaurants registered yet!");
        } else {
            System.out.println("Username of the user who will make the order: ");
            String username = scanner.next();
            try {
                User user = userService.getUserByUsername(username);

                Restaurant restaurant = chooseRestaurant(); // the restaurant that will receive the review

                List<Review> reviews = reviewService.getAllReviews();
                List<Review> restaurantReviews = new ArrayList<>();
                for (Review review : reviews) {
                    assert restaurant != null;
                    if (Objects.equals(review.getRestaurantID(), restaurant.getRestaurantID())) {
                        restaurantReviews.add(review);
                    }
                }

            boolean reviewedAlready = false;
            for (Review review : restaurantReviews) {
                if (review.getUserID().equals(user.getUserID())) {
                    reviewedAlready = true;
                    break;
                }

            }
            if (reviewedAlready) {
                System.out.println("This user already reviewed this restaurant!");
            } else {
                System.out.println("Write the review: ");
                String text = scanner.next();
                int grade = 0;
                boolean validGrade = false;
                while (!validGrade) {
                    System.out.println("Give a grade from 1 to 10: ");
                    grade = scanner.nextInt();
                    if (grade > 0 && grade < 11) {
                        validGrade = true;
                    } else {
                        System.out.println("Invalid grade! Try again!");
                    }
                }
                assert restaurant != null;
                Review review = new Review.Builder(user.getUserID(), restaurant.getRestaurantID(), grade).withText(text).build();
                reviewService.registerNewReview(review.getUserID(), review.getRestaurantID(), review.getText(), review.getGrade()); // register the review
                System.out.println("Registered new review: " + review);

                // add the review to the restaurant
                for (Restaurant rest : restaurants) { 
                    if (rest.equals(restaurant)) {
                        SortedSet<Review> updatedReviews = new TreeSet<Review>(new GradeReviewComparator());
                        updatedReviews.addAll(rest.getReviews());
                        updatedReviews.add(review);
                        rest.setReviews(updatedReviews);
                    }
                }
            }

            } catch (CustomException customException) {
                System.out.println(customException.getMessage());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private DeliveryMan readDeliveryManDetails() {
        System.out.println("Name: ");
        String name = scanner.next();
        System.out.println("County: ");
        String county = scanner.next();
        System.out.println("City: ");
        String city = scanner.next();
        System.out.println("Street: ");
        String street = scanner.next();
        Address address = new Address(county, city, street);
        System.out.println("Phone number: ");
        String phoneNumber = scanner.next();
        System.out.println("License plate (e.g., B-102-ABC): ");
        String licensePlate = scanner.next();

        return new DeliveryMan(name, address, phoneNumber, licensePlate);
    }

    private void addDeliveryMan() {
        DeliveryMan deliveryMan = readDeliveryManDetails();
        try {
            deliveryManService.registerNewDeliveryMan(deliveryMan.getName(), deliveryMan.getAddress().getCounty(),
                    deliveryMan.getAddress().getCity(), deliveryMan.getAddress().getStreet(),
                    deliveryMan.getPhoneNumber(), deliveryMan.getLicensePlate());
            System.out.println("Registered new delivery man" + deliveryMan);
        } catch (CustomException customException) {
            System.out.println(customException.getMessage());
        }
    }

    private void listAllDeliveryMen() throws InstantiationException, IllegalAccessException {
        System.out.println("Currently registered delivery men: ");
        List<DeliveryMan> deliveryMen = deliveryManService.getAllDeliveryMen();
        if (deliveryMen.isEmpty()) {
            System.out.println("No delivery men registered yet!");
        } else {
            deliveryMen.forEach(System.out::println);
        }
    }

    private void assignOrdersToDeliveryMen() throws InstantiationException, IllegalAccessException, CustomException {
        List<Order> orders = orderService.getAllOrders();
        List<DeliveryMan> deliveryMen = deliveryManService.getAllDeliveryMen();
        if (deliveryMen.isEmpty()) {
            System.out.println("No delivery men have been registered so far!");
            return;
        }
        if (orders.isEmpty()) {
            System.out.println("No orders have been registered so far!");
            return;
        }
        int noOrdersWithoutDelivery = 0;
        for (Order order : orders) {
            if (order.getDeliveryManID() == 0) {
                noOrdersWithoutDelivery++;
                System.out.println("Assign a delivery man for the order " + order);
                listAllDeliveryMen();
                int id = 0;
                boolean validID = false;
                while (!validID) {
                    System.out.println("Delivery man ID: ");
                    id = scanner.nextInt();
                    DeliveryMan deliveryMan = deliveryManService.getDeliveryManByID2(id);
                    if (deliveryMan != null) {
                        validID = true;
                    } else {
                        System.out.println("Cannot find a delivery man having the provided id: " + id + ". Try again!");
                    }
                }
                orderService.update(order, id);
            }
        }
        if (noOrdersWithoutDelivery == 0) {
            System.out.println("All orders have delivery men!");
        }
    }
}



