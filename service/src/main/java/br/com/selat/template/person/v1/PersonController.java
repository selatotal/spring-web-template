package br.com.selat.template.person.v1;

import br.com.selat.template.contract.v1.PersonInput;
import br.com.selat.template.contract.v1.PersonOutput;
import br.com.selat.template.contract.v1.PersonService;
import br.com.selat.template.shared.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/{id}")
    public PersonOutput findById(@PathVariable Long id){
        return personService
                .findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public PersonOutput save(@RequestBody PersonInput personInput){
        return personService
                .save(personInput);
    }

    @PutMapping("/{id}")
    public PersonOutput update(@PathVariable Long id, @RequestBody PersonInput personInput){
        return personService
                .update(id, personInput);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id){
        personService.remove(id);
    }
}
