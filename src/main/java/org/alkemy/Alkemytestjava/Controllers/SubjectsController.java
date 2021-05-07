package org.alkemy.Alkemytestjava.Controllers;

import org.alkemy.Alkemytestjava.Dto.DtoMaker;
import org.alkemy.Alkemytestjava.Models.Subject;
import org.alkemy.Alkemytestjava.Models.Subscription;
import org.alkemy.Alkemytestjava.Models.User;
import org.alkemy.Alkemytestjava.Repositories.SubjetsRepository;
import org.alkemy.Alkemytestjava.Repositories.SubscriptionRepository;
import org.alkemy.Alkemytestjava.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.callback.PrivateKeyCallback;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/subjects")
public class SubjectsController {

    @Autowired
    private SubjetsRepository subjectsRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("{idSubject}")
    public ResponseEntity<Map<String, Object>> insertUpdate(Authentication authentication, @PathVariable("idSubject") Long idSubject, @RequestBody Subject subject){

        ResponseEntity<Map<String, Object>> response;
        if(!AlkemyController.isUserAuthenticated(authentication)){
            response = new ResponseEntity<>(AlkemyController.makeMap("error", "Must Login"), HttpStatus.FORBIDDEN);
        } else {
            Optional<Subject> oSubject = subjectsRepository.findById(idSubject);
            Subject oNewSubject = oSubject.isPresent() ? oSubject.get() : new Subject();
            oNewSubject.setName(subject.getName());
            oNewSubject.setTeacher(subject.getTeacher());
            oNewSubject.setAvailability(subject.getAvailability());
            oNewSubject.setTime(subject.getTime());

            response = new ResponseEntity<>(AlkemyController.makeMap("entityDTO", subjectsRepository.save(oNewSubject)), HttpStatus.CREATED);
        }
        return response;
    }

    @DeleteMapping("{idSubject}")
    public ResponseEntity<Map<String, Object>> delete(Authentication authentication, @PathVariable("idSubject") Long idSubject){

        ResponseEntity<Map<String, Object>> response;
        if(!AlkemyController.isUserAuthenticated(authentication)){
            response = new ResponseEntity<>(AlkemyController.makeMap("error", "Must Login"), HttpStatus.FORBIDDEN);
        } else {
            Optional<Subject> oSubject = subjectsRepository.findById(idSubject);
            if(oSubject.isPresent()) {

                subscriptionRepository.findAllBySubject(oSubject.get()).forEach(subscription -> {
                    subscriptionRepository.deleteById(subscription.getId());
                });

                subjectsRepository.deleteById(oSubject.get().getId());
                response = new ResponseEntity<>(AlkemyController.makeMap("id", idSubject), HttpStatus.ACCEPTED);
            }
            else
                response = new ResponseEntity<>(AlkemyController.makeMap("error", "Subject ID " + idSubject + " doesn't exist"), HttpStatus.FORBIDDEN);
        }
        return response;
    }

    @GetMapping("/{subjectId}")
    public ResponseEntity<Object> getEntityById(@PathVariable("subjectId") Long subjectId){
        Optional<Subject> subject = subjectsRepository.findById(subjectId);
        if(subject.isEmpty())
            return new ResponseEntity<>("Not Found", HttpStatus.FORBIDDEN);

        return ResponseEntity.ok().body(subject);
    }
}

