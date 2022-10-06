package tacos.artemis;

import org.springframework.stereotype.Component;

import tacos.entity.TacoOrder;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

@Component
public class JmsUtil {

    @Bean
    public MappingJackson2MessageConverter massageConverter(){
        MappingJackson2MessageConverter massageConverter = new MappingJackson2MessageConverter();
        massageConverter.setTypeIdPropertyName("_typeId");

        Map<String, Class<?>> typeIdMapping = new HashMap<>();
        typeIdMapping.put("order", TacoOrder.class);

        massageConverter.setTypeIdMappings(typeIdMapping);

        return massageConverter;
    }
}
