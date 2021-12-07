package br.com.selat.template.contract.v1;

import br.com.selat.template.shared.queries.QueryResult;

import java.util.Optional;

public interface PersonService {

    Optional<PersonOutput> findById(Long id);
    PersonOutput save(PersonInput personInput);
    PersonOutput update(Long id, PersonInput personInput);
    void remove(Long id);
    QueryResult<PersonOutput> query(PersonQueryRequest personQueryRequest);

}
