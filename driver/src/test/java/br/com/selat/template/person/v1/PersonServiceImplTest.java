package br.com.selat.template.person.v1;

import br.com.selat.template.TemplateServiceDriver;
import br.com.selat.template.builders.PersonBuilder;
import br.com.selat.template.contract.v1.PersonInput;
import br.com.selat.template.contract.v1.PersonOutput;
import br.com.selat.template.contract.v1.PersonService;
import br.com.selat.template.shared.exceptions.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static br.com.selat.template.builders.PersonBuilder.*;

class PersonServiceImplTest {

    private final PersonService service = TemplateServiceDriver.buildPersonService();

    @Test
    void shouldCreatePerson(){
        PersonInput input = PersonBuilder.buildPersonInput();
        PersonOutput output = service.save(input);
        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.id());
        Assertions.assertEquals(NAME, output.name());
        Assertions.assertEquals(ADDRESS, output.address());
        Assertions.assertEquals(BIRTH_DATE, output.birthDate());
        Assertions.assertEquals(EMAIL, output.email());
        Assertions.assertEquals(GENDER, output.gender());
        service.remove(output.id());
    }

    @Test
    void shouldUpdatePerson(){
        PersonInput input = PersonBuilder.buildPersonInput();
        PersonOutput output = service.save(input);
        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.id());

        PersonInput inputUpdate = PersonBuilder.buildPersonInput("New Name");
        PersonOutput outputUpdate = service.update(output.id(), inputUpdate);
        Assertions.assertNotNull(outputUpdate);
        Assertions.assertEquals(output.id(), outputUpdate.id());
        Assertions.assertEquals("New Name", outputUpdate.name());
        Assertions.assertEquals(ADDRESS, outputUpdate.address());
        Assertions.assertEquals(BIRTH_DATE, outputUpdate.birthDate());
        Assertions.assertEquals(EMAIL, outputUpdate.email());
        Assertions.assertEquals(GENDER, outputUpdate.gender());
        service.remove(outputUpdate.id());
    }

    @Test
    void shouldFindPersonById(){
        PersonInput input = PersonBuilder.buildPersonInput();
        PersonOutput output = service.save(input);
        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.id());

        Optional<PersonOutput> optional = service.findById(output.id());
        Assertions.assertTrue(optional.isPresent());
        PersonOutput outputFind = optional.get();

        Assertions.assertEquals(output.id(), outputFind.id());
        Assertions.assertEquals(NAME, outputFind.name());
        Assertions.assertEquals(ADDRESS, outputFind.address());
        Assertions.assertEquals(BIRTH_DATE, outputFind.birthDate());
        Assertions.assertEquals(EMAIL, outputFind.email());
        Assertions.assertEquals(GENDER, outputFind.gender());
        service.remove(outputFind.id());
    }

    @Test
    void shouldRemove(){
        PersonInput input = PersonBuilder.buildPersonInput();
        PersonOutput output = service.save(input);
        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.id());

        service.remove(output.id());
        Optional<PersonOutput> optional = service.findById(output.id());
        Assertions.assertTrue(optional.isEmpty());
    }
}
