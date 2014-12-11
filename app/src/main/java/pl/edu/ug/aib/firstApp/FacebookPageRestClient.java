package pl.edu.ug.aib.firstApp;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import pl.edu.ug.aib.firstApp.data.FacebookPage;

@Rest(rootUrl = "https://graph.facebook.com",
      converters = { MappingJackson2HttpMessageConverter.class })
public interface FacebookPageRestClient {

    @Get("/{name}")
    FacebookPage getPage(CharSequence name);

}