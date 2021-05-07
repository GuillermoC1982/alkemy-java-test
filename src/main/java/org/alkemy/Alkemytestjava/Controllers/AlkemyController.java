package org.alkemy.Alkemytestjava.Controllers;

import org.alkemy.Alkemytestjava.Dto.DtoMaker;
import org.alkemy.Alkemytestjava.Models.*;
import org.alkemy.Alkemytestjava.Repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AlkemyController {

    @Autowired
    private SubjetsRepository subjectsRepository;

    @Autowired
    private TeachersRepository teachersRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/getInfo")
    public Map<String, Object> getInfo(Authentication authentication) {

        Map<String, Object> dto = new LinkedHashMap<String, Object>();

        User user = null;
        if (isUserAuthenticated(authentication))
            user = userRepository.findByDni(authentication.getName());

        dto.put("user", DtoMaker.GetFromEntity(user));
        dto.put("teachers", teachersRepository
                .findAll()
                .stream()
                .collect(toList()));

        dto.put("subjects", subjectsRepository
                .findAll()
                .stream()
                .collect(toList()));

        return dto;
    }

    public static boolean isUserAuthenticated(Authentication authentication) {
        return authentication != null && (authentication instanceof AnonymousAuthenticationToken == false);
    }

    public static Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

}

