package br.com.selat.template.person;

import br.com.selat.template.contract.v1.PersonInput;
import br.com.selat.template.contract.v1.PersonOutput;
import br.com.selat.template.contract.v1.PersonQueryRequest;
import br.com.selat.template.contract.v1.PersonService;
import br.com.selat.template.shared.exceptions.InternalErrorException;
import br.com.selat.template.shared.exceptions.NotFoundException;
import br.com.selat.template.shared.queries.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonDao personDao;

    @Autowired
    public PersonServiceImpl(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public Optional<PersonOutput> findById(Long id) {
        PersonEntity entity = personDao.findById(id);
        if (entity == null) {
            return Optional.empty();
        }
        return Optional.of(toPersonOutput(entity));
    }

    @Override
    @Transactional
    public PersonOutput save(PersonInput personInput) {
        PersonEntity entity = toPersonEntity(null, personInput);
        PersonEntity response = personDao.save(entity);
        return toPersonOutput(response);
    }

    @Override
    @Transactional
    public PersonOutput update(Long id, PersonInput personInput) {
        if (findById(id).isEmpty()){
            throw new NotFoundException("person not found");
        }
        PersonEntity entity = toPersonEntity(id, personInput);
        if (!personDao.update(entity)){
            throw new InternalErrorException("error updating person");
        }
        return toPersonOutput(entity);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        if (findById(id).isEmpty()){
            throw new NotFoundException("person not found");
        }
        if (!personDao.remove(id)){
            throw new InternalErrorException("error updating person");
        }
    }

    @Override
    public QueryResult<PersonOutput> query(PersonQueryRequest personQueryRequest) {
        return null;
    }

    private PersonOutput toPersonOutput(PersonEntity personInput) {
        if (personInput == null){
            return null;
        }
        return new PersonOutput(personInput.id(),
                personInput.name(),
                personInput.address(),
                personInput.birthDate() != null ? personInput.birthDate().toEpochMilli() : null,
                personInput.email(),
                personInput.gender());
    }

    private PersonEntity toPersonEntity(Long id, PersonInput personInput){
        return new PersonEntity(id,
                personInput.name(),
                personInput.address(),
                personInput.birthDate() != null ? Instant.ofEpochMilli(personInput.birthDate()) : null,
                personInput.email(),
                personInput.gender());
    }
}
