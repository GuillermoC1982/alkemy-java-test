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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/subsciptions")
public class SubsciptionsController {

    @Autowired
    private SubjetsRepository subjectsRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/subscribe/{idSubject}")
    public ResponseEntity<Map<String, Object>> suscribe(Authentication authentication, @PathVariable("idSubject") Long idSubject){

        ResponseEntity<Map<String, Object>> response;
        User oUser = userRepository.findByDni(authentication.getName());
        String sRole = oUser.getRole();
        if(!AlkemyController.isUserAuthenticated(authentication)){
            response = new ResponseEntity<>(AlkemyController.makeMap("error", "Must Login"), HttpStatus.FORBIDDEN);
        } else if(sRole.toUpperCase().equals("STUDENT")) {

            Subject oSubject = subjectsRepository.findById(Math.abs(idSubject)).get();

            if(idSubject>0) {
                try {

                    List<Subscription> oSameTimeSubscriptions = oUser.getSubscriptions().stream().filter(ss -> ss.getSubject().getTime().equals(oSubject.getTime())).collect(toList());
                    if (oSameTimeSubscriptions.size() > 0)
                        response = new ResponseEntity<>(AlkemyController.makeMap("error", "Time overlapped with another Subject"), HttpStatus.FORBIDDEN);
                    else
                    {
                        if(oSubject.getAvailability() > 0 ) {
                            oSubject.setAvailability(oSubject.getAvailability() - 1);
                            subjectsRepository.save(oSubject);
                            response = new ResponseEntity<>(AlkemyController.makeMap("entityDTO", DtoMaker.GetFromEntity(subscriptionRepository.save(new Subscription(oSubject, oUser)))), HttpStatus.CREATED);
                        }
                        else {
                            response = new ResponseEntity<>(AlkemyController.makeMap("error", "Not Available"), HttpStatus.FORBIDDEN);
                        }
                    }
                }
                catch (Exception ex) {
                    //System.out.println(ex.getMessage());
                    response = new ResponseEntity<>(AlkemyController.makeMap("error", ex.getMessage()), HttpStatus.FORBIDDEN);
                }
            }
            else
            {
                boolean bUnsubscribeResult = false;
                List<Subscription> oUserSubscriptions = oUser.getSubscriptions().stream().filter(ss -> ss.getSubject().getId() == oSubject.getId()).collect(toList());
                if(oUserSubscriptions.size() == 1) {
                    subscriptionRepository.deleteById(oUserSubscriptions.get(0).getId());
                    oSubject.setAvailability(oSubject.getAvailability() + 1);
                    subjectsRepository.save(oSubject);
                    bUnsubscribeResult = true;
                }
                response = new ResponseEntity<>(AlkemyController.makeMap("entityDTO", bUnsubscribeResult), HttpStatus.CREATED);
            }
        }
        else
            response = new ResponseEntity<>(AlkemyController.makeMap("error", "Must be an Student"), HttpStatus.FORBIDDEN);

        return response;
    }
}

