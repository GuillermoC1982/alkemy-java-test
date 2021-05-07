package org.alkemy.Alkemytestjava.Controllers;

import org.alkemy.Alkemytestjava.Models.Teacher;
import org.alkemy.Alkemytestjava.Repositories.SubjetsRepository;
import org.alkemy.Alkemytestjava.Repositories.SubscriptionRepository;
import org.alkemy.Alkemytestjava.Repositories.TeachersRepository;
import org.alkemy.Alkemytestjava.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/teachers")
public class TeachersController {

    @Autowired
    private SubjetsRepository subjectsRepository;

    @Autowired
    private TeachersRepository teachersRepository;

    @PostMapping("{idTeacher}")
    public ResponseEntity<Map<String, Object>> insertUpdate(Authentication authentication, @PathVariable("idTeacher") Long idTeacher, @RequestBody Teacher teacher){

        ResponseEntity<Map<String, Object>> response;
        if(!AlkemyController.isUserAuthenticated(authentication)){
            response = new ResponseEntity<>(AlkemyController.makeMap("error", "Must Login"), HttpStatus.FORBIDDEN);
        } else {
            Optional<Teacher> oTeacher = teachersRepository.findById(idTeacher);
            Teacher oNewTeacher = oTeacher.isPresent() ? oTeacher.get() : new Teacher();
            oNewTeacher.setName(teacher.getName());
            oNewTeacher.setLast_name(teacher.getLast_name());
            oNewTeacher.setActive(teacher.isActive());
            oNewTeacher.setDni(teacher.getDni());

            response = new ResponseEntity<>(AlkemyController.makeMap("id", teachersRepository.save(oNewTeacher).getId()), HttpStatus.CREATED);
        }
        return response;
    }

    @DeleteMapping("{idTeacher}")
    public ResponseEntity<Map<String, Object>> delete(Authentication authentication, @PathVariable("idTeacher") Long idTeacher){

        ResponseEntity<Map<String, Object>> response;
        if(!AlkemyController.isUserAuthenticated(authentication)){
            response = new ResponseEntity<>(AlkemyController.makeMap("error", "Must Login"), HttpStatus.FORBIDDEN);
        } else {
            Optional<Teacher> oTeacher = teachersRepository.findById(idTeacher);
            if(oTeacher.isPresent()) {

                subjectsRepository.findAllByTeacher(oTeacher.get()).forEach(subject -> {
                    subject.setTeacher(null);
                    subjectsRepository.save(subject);
                });

                teachersRepository.deleteById(oTeacher.get().getId());
                response = new ResponseEntity<>(AlkemyController.makeMap("id", idTeacher), HttpStatus.ACCEPTED);
            }
            else
                response = new ResponseEntity<>(AlkemyController.makeMap("error", "Teacher ID " + idTeacher + " doesn't exist"), HttpStatus.FORBIDDEN);
        }
        return response;
    }
}

