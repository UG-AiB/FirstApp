package pl.edu.ug.aib.firstApp;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.RequiresHeader;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import pl.edu.ug.aib.firstApp.data.Person;
import pl.edu.ug.aib.firstApp.data.PhoneBook;

@Rest(rootUrl = "http://pegaz.wzr.ug.edu.pl:8080/rest",
      converters = { MappingJackson2HttpMessageConverter.class })
@RequiresHeader({"X-Dreamfactory-Application-Name"})
public interface PhoneBookRestClient extends RestClientHeaders {

    @Get("/db/person")
    PhoneBook getPhoneBook();

    @Post("/db/person")
    void addPhoneBookEntry(Person person);

}