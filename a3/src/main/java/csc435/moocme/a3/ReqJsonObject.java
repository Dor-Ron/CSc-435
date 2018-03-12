package csc435.moocme.a3;

class ReqJsonObject {
    public String title;
    public String platform;
    public String institution;
    public String url;
    public Boolean free;
    public int id;

    public ReqJsonObject() {
        super();
    }

    public ReqJsonObject(int uid, String titl, String plat, String inst, String uri, Boolean price) {
        this.id = uid;
        this.title = titl;
        this.platform = plat;
        this.institution = inst;
        this.url = uri;
        this.free = price;
    }   

    public ReqJsonObject(String titl, String plat, String inst, String uri, Boolean price) {
        this.title = titl;
        this.platform = plat;
        this.institution = inst;
        this.url = uri;
        this.free = price;
    }  
}