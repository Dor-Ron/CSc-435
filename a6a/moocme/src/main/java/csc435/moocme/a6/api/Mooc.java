package csc435.moocme.a6.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "courses")
public class Mooc {

    @Column(name = "title")
    @JsonProperty
    public String title;

    @Column(name = "platform")
    @JsonProperty
    public String platform;

    @Column(name = "institution")
    @JsonProperty
    public String institution;

    @Column(name = "uri")
    @JsonProperty
    public String url;

    @Column(name = "free")
    @JsonProperty
    public Boolean free;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    @JsonProperty
    public int id;


    public Mooc() {
        super();
    }

    public Mooc(String titl, String plat, String inst, String uri, Boolean price) {
        this.title = titl;
        this.platform = plat;
        this.institution = inst;
        this.url = uri;
        this.free = price;
    }  

    public Mooc(int uid, String titl, String plat, String inst, String uri, Boolean price) {
        this.id = uid;
        this.title = titl;
        this.platform = plat;
        this.institution = inst;
        this.url = uri;
        this.free = price;
    } 
}