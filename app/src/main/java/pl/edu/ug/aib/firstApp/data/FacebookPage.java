package pl.edu.ug.aib.firstApp.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FacebookPage  implements Serializable {

    public String name;
    public String about;
    public String phone;
    public String website;
    public int likes;
    public String category;

}