package tacos.artemis;

import tacos.entity.TacoOrder;

public interface OrderMessagingService {
    void sendOrder(TacoOrder order);
}
