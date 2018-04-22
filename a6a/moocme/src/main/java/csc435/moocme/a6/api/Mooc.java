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
    private String title;

    @Column(name = "platform")
    @JsonProperty
    private String platform;

    @Column(name = "institution")
    @JsonProperty
    private String institution;

    @Column(name = "uri")
    @JsonProperty
    private String url;

    @Column(name = "free")
    @JsonProperty
    private Boolean free;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


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

    @JsonIgnore
    public String getTitle() {
        return title;
    }

    @JsonIgnore
    public String getPlatform() {
        return platform;
    }

    @JsonIgnore
    public String getInst() {
        return institution;
    }

    @JsonIgnore
    public String getUrl() {
        return url;
    }

    @JsonIgnore
    public Boolean getPrice() {
        return free;
    }

    @JsonIgnore
    public int getId() {
        return id;
    }
}