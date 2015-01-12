package pl.edu.ug.aib.firstApp;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

import pl.edu.ug.aib.firstApp.data.Person;
import pl.edu.ug.aib.firstApp.data.PhoneBook;

@EBean
public class RestBackgroundTask {

    @RootContext
    FirstActivity activity;

    @RestService
    PhonebookRestClient restClient;

    @Background
    void getPhoneBook() {
        try {
            restClient.setHeader("X-Dreamfactory-Application-Name", "phonebook");
            PhoneBook phoneBook = restClient.getPhoneBook();
            publishResult(phoneBook);
        } catch (Exception e) {
            publishError(e);
        }
    }

    @Background
    void addPhoneBookEntry(Person person) {
        try {
            restClient.setHeader("X-Dreamfactory-Application-Name", "phonebook");
            restClient.addPhoneBookEntry(person);
            PhoneBook phoneBook = restClient.getPhoneBook();
            publishResult(phoneBook);
        } catch (Exception e) {
            publishError(e);
        }
    }

    @UiThread
    void publishResult(PhoneBook phoneBook) {
        activity.updatePhonebook(phoneBook);
    }

    @UiThread
    void publishError(Exception e) {
        activity.showError(e);
    }

}
