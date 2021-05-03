package org.alkemy.Alkemytestjava.Controllers;

import org.alkemy.Alkemytestjava.DTO.DtoMaker;
import org.alkemy.Alkemytestjava.Models.*;
import org.alkemy.Alkemytestjava.Repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/getInfo")
    public Map<String, Object> getGames(Authentication authentication) {

        Map<String, Object> dto = new LinkedHashMap<String, Object>();

        User user = null;
        if (isUserAuthenticated(authentication))
            user = userRepository.findByDni(authentication.getName());

        dto.put("user", DtoMaker.GetFromEntity(user));

        dto.put("teachers", teachersRepository
                .findAll()
                .stream()
                .map(item -> DtoMaker.GetFromEntity(item))
                .collect(toList()));

        dto.put("subjects", subjectsRepository
                .findAll()
                .stream()
                .map(item -> DtoMaker.GetFromEntity(item))
                .collect(toList()));

        return dto;

    }

    private boolean isUserAuthenticated(Authentication authentication) {
        return authentication != null && (authentication instanceof AnonymousAuthenticationToken == false);
    }

}

