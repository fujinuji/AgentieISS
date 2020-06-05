package scs.iss.agentie.services;

import scs.iss.agentie.entities.Order;
import scs.iss.agentie.entities.Product;
import scs.iss.agentie.entities.User;
import scs.iss.agentie.entities.UserType;
import scs.iss.agentie.notify.Observable;
import scs.iss.agentie.notify.Observer;
import scs.iss.agentie.repositories.OrderRepository;
import scs.iss.agentie.repositories.ProductRepository;
import scs.iss.agentie.repositories.UserRepository;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class Service implements Observer {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    private List<Observable> observables = new ArrayList<>();

    public Service(UserRepository userRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public User checkUser(String userName, String password) throws LoginException {
        User loggedUser = userRepository.findUser(userName, password);

        if (loggedUser == null) {
            throw new LoginException("User not found");
        }
        return loggedUser;
    }

    public void registerUser(String userName, String password, String email) {
        User user = new User(null, userName, password,email, UserType.AGENT);
        userRepository.save(user);
    }

    public void makeOrder(Integer productId, Integer amount, String customerName) {
        Order order = new Order(null, productId, amount, customerName);
        orderRepository.save(order);
        Product product = productRepository.getById(productId);
        product.setAmount(product.getAmount() - amount);
        productRepository.updateProduct(product);

        List<Product> products = productRepository.getAll();
        notifyObservers(products);
    }

    public List<Product> getProducts() {
        return productRepository.getAll();
    }

    @Override
    public void notifyObservers(List<Product> products) {
        observables.forEach(x -> x.update(products));
    }

    @Override
    public void addObservable(Observable observable) {
        observables.add(observable);
    }

    public void addProduct(String productName, Integer amount, Float price) {
        Product product = new Product(null, productName, amount, price);
        productRepository.addProduct(product);

        List<Product> products = productRepository.getAll();
        notifyObservers(products);
    }

    public void updateProduct(Integer productId, String productName, Integer amount, Float price) {
        Product product = new Product(productId, productName, amount, price);
        productRepository.updateProduct(product);

        List<Product> products = productRepository.getAll();
        notifyObservers(products);
    }

    public void deleteProduct(Integer productId) {
        productRepository.deleteProduct(productId);
        List<Product> products = productRepository.getAll();
        notifyObservers(products);
    }
}
