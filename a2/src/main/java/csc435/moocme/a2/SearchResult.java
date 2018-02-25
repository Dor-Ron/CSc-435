package csc435.moocme.a2;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;

class SearchResult {
    public String title;
    public String platform;
    public String description;
    public String url;
    public String moocmeURL;
    public Boolean free;

    public SearchResult() {
        super();
    }

    public SearchResult(String titl, String plat, String desc, String uri, String relURL, Boolean price) {
        this.title = titl;
        this.platform = plat;
        this.description = desc;
        this.url = uri;
        this.moocmeURL = relURL;
        this.free = price;
    }   
}