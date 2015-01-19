package pl.edu.ug.aib.firstApp;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.RequiresHeader;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import pl.edu.ug.aib.firstApp.data.EmailAndPassword;
import pl.edu.ug.aib.firstApp.data.Person;
import pl.edu.ug.aib.firstApp.data.PhoneBook;
import pl.edu.ug.aib.firstApp.data.Picture;
import pl.edu.ug.aib.firstApp.data.ResultWithId;
import pl.edu.ug.aib.firstApp.data.User;

@Rest(rootUrl = "http://pegaz.wzr.ug.edu.pl:8080/rest",
      converters = { MappingJackson2HttpMessageConverter.class })
@RequiresHeader({"X-Dreamfactory-Application-Name"})
public interface PhoneBookRestClient extends RestClientHeaders {

    @Get("/db/person")
    PhoneBook getPhoneBook();

    @Post("/db/person")
    @RequiresHeader({"X-Dreamfactory-Application-Name", "X-Dreamfactory-Session-Token"})
    void addPhoneBookEntry(Person person);

    @Post("/user/session")
    User login(EmailAndPassword emailAndPassword);

    @Get("/db/pictures/{id}")
    Picture getPictureById(int id);

    @Post("/db/pictures")
    @RequiresHeader({"X-Dreamfactory-Application-Name", "X-Dreamfactory-Session-Token"})
    ResultWithId addPicture(Picture picture);

}