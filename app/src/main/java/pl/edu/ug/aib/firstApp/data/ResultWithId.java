package pl.edu.ug.aib.firstApp.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultWithId {

    public Integer id;

}

