package csc435.moocme.a3;

class ReqJsonObject {
    public String title;
    public String platform;
    public String description;
    public String url;
    public Boolean free;

    public ReqJsonObject() {
        super();
    }

    public ReqJsonObject(String titl, String plat, String desc, String uri, Boolean price) {
        this.title = titl;
        this.platform = plat;
        this.description = desc;
        this.url = uri;
        this.free = price;
    }   
}