package org.alkemy.Alkemytestjava.DTO;

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
                dto.put("subject", DtoMaker.GetFromEntity(oSubscription.getSubject()));
            }
            else if (oEntity instanceof Subject) {
                Subject oSubject = (Subject)oEntity;
                dto.put("id", oSubject.getId());
                dto.put("name", oSubject.getName());
                dto.put("time", oSubject.getTime());
                dto.put("availability", oSubject.getAvailability());
                dto.put("teacher", DtoMaker.GetFromEntity(oSubject.getTeacher()));
            }
            else if (oEntity instanceof Teacher) {
                Teacher oTeacher = (Teacher)oEntity;
                dto.put("id", oTeacher.getId());
                dto.put("name", oTeacher.getName());
                dto.put("last_name", oTeacher.getLast_name());
                dto.put("dni", oTeacher.getDni());
                dto.put("active", oTeacher.isActive());
            }
        }
        return dto;
    }
}
