package pl.edu.ug.aib.firstApp.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {

    public Integer ownerId;
    public String name;
    public String company;
    public String phone;
    public Integer pictureId;

    @JsonIgnore
    public String pictureBytes;

}

