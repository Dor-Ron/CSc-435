package csc435.moocme.a3;

class ResJsonObject {
    public int id;
    public String title;
    public String platform;
    public String description;
    public String url;
    public Boolean free;

    public ResJsonObject() {
        super();
    }

    public ResJsonObject(String titl, String plat, String desc, String uri, Boolean price, int uid) {
        this.title = titl;
        this.platform = plat;
        this.description = desc;
        this.url = uri;
        this.free = price;
        this.id = uid;
    }   
}