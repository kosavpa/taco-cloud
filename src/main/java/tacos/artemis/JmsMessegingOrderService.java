package tacos.artemis;


import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;

import org.hibernate.engine.query.spi.ReturnMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Service;

import tacos.entity.TacoOrder;

@Service
public class JmsMessegingOrderService implements OrderMessagingService{

    @Bean
    public MappingJackson2MessageConverter massageConverter(){
        MappingJackson2MessageConverter massageConverter = new MappingJackson2MessageConverter();
        massageConverter.setTypeIdPropertyName("_typeId");

        Map<String, Class<?>> typeIdMapping = new HashMap<>();
        typeIdMapping.put("order", TacoOrder.class);

        massageConverter.setTypeIdMappings(typeIdMapping);

        return massageConverter;
    }

    private JmsTemplate jms;

    @Autowired
    public JmsMessegingOrderService(JmsTemplate jms) {
        this.jms = jms;
    }

    @Override
    public void sendOrder(TacoOrder order) {
        jms.convertAndSend("Kitchen",order,
            message -> {
                message.setStringProperty("X_ORDER_SOURCE", "WEB");
                return message;
            }
        );
    }
}
