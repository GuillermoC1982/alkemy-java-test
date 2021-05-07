package org.alkemy.Alkemytestjava.Dto;

import org.alkemy.Alkemytestjava.Models.*;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class DtoMaker {

    public static Map<String, Object> GetFromEntity(Object oEntity)
    {
        Map<String, Object> dto = null;
        if(oEntity != null) {
            dto = new LinkedHashMap<String, Object>();

            if (oEntity instanceof User) {
                User oUser = (User)oEntity;
                dto.put("id", oUser.getId());
                dto.put("dni", oUser.getDni());
                dto.put("role", oUser.getRole());
                if (oUser.getRole().toUpperCase().equals("STUDENT"))
                    dto.put("subscriptions", oUser.getSubscriptions()
                            .stream()
                            .map(subscription -> DtoMaker.GetFromEntity(subscription))
                            .collect(toList()));
            }
            else if (oEntity instanceof Subscription) {
                Subscription oSubscription = (Subscription)oEntity;
                dto.put("id", oSubscription.getId());
                dto.put("subject", oSubscription.getSubject());
            }
        }
        return dto;
    }
}