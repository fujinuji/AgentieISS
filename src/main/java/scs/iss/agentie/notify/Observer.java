package scs.iss.agentie.notify;

import scs.iss.agentie.entities.Product;

import java.util.List;

public interface Observer {
    void notifyObservers(List<Product> products);
    void addObservable(Observable observable);
}
