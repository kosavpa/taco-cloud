package tacos.artemis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import tacos.entity.TacoOrder;

@Service
public class JmsMessegingOrderService implements OrderMessagingService{

    private JmsTemplate jms;

    @Autowired
    public JmsMessegingOrderService(JmsTemplate jms) {
        this.jms = jms;
    }

    @Override
    public void sendOrder(TacoOrder order) {
        jms.convertAndSend(order,
            message -> {
                message.setStringProperty("X_ORDER_SOURCE", "WEB");

                return message;
            }
        );
    }
}
