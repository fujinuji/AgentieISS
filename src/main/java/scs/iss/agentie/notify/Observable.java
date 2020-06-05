package scs.iss.agentie.notify;

import scs.iss.agentie.entities.Product;

import java.util.List;

public interface Observable {
    void update(List<Product> products);
}
