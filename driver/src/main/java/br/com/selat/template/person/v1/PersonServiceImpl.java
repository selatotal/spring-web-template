package br.com.selat.template.person.v1;

import br.com.selat.template.contract.v1.PersonInput;
import br.com.selat.template.contract.v1.PersonOutput;
import br.com.selat.template.contract.v1.PersonQueryRequest;
import br.com.selat.template.contract.v1.PersonService;
import br.com.selat.template.shared.HandleHttpResponse;
import br.com.selat.template.shared.exceptions.InternalErrorException;
import br.com.selat.template.shared.exceptions.ServiceValidationException;
import br.com.selat.template.shared.queries.QueryResult;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class PersonServiceImpl implements PersonService {

    private static final String BASE_URL = Optional.ofNullable(System.getenv("PERSON_SERVICE_URL")).orElse(System.getProperty("person-service.url", ""));
    private static final HandleHttpResponse<PersonOutput> HANDLE_RESPONSE = new HandleHttpResponse<>();
    private final RestTemplate restTemplate;

    public PersonServiceImpl() {
        restTemplate = new RestTemplate();
    }

    @Override
    public Optional<PersonOutput> findById(Long id) {
        try {
            ResponseEntity<PersonOutput> response = restTemplate.getForEntity(BASE_URL + "/v1/person/{id}", PersonOutput.class, id);
            return Optional.ofNullable(HANDLE_RESPONSE.handle(response));
        } catch (RestClientResponseException e){
            if (e.getRawStatusCode() == HttpStatus.NOT_FOUND.value()){
                return Optional.empty();
            }
            HANDLE_RESPONSE.handleException(e);
            return Optional.empty();
        } catch (Exception e){
            throw new ServiceValidationException(e);
        }
    }

    @Override
    public PersonOutput save(PersonInput personInput) {
        ResponseEntity<PersonOutput> response = restTemplate.postForEntity(BASE_URL + "/v1/person", personInput, PersonOutput.class);
        return HANDLE_RESPONSE.handle(response);
    }

    @Override
    public PersonOutput update(Long id, PersonInput personInput) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PersonInput> entity = new HttpEntity<>(personInput, headers);
        ResponseEntity<PersonOutput> response = restTemplate.exchange(BASE_URL + "/v1/person/{id}", HttpMethod.PUT, entity, PersonOutput.class, id);
        return HANDLE_RESPONSE.handle(response);
    }

    @Override
    public void remove(Long id) {
        try {
            restTemplate.delete(BASE_URL + "/v1/person/{id}", id);
        } catch (RestClientResponseException e){
            HANDLE_RESPONSE.handleException(e);
        } catch (Exception e){
            throw new InternalErrorException(e);
        }
    }

    @Override
    public QueryResult<PersonOutput> query(PersonQueryRequest personQueryRequest) {
        return null;
    }


}
