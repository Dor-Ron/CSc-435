package csc435.moocme

class MoocModel {

    String title
    String institution
    String uri
    Boolean free
    String platform

    static mapping = {
        table 'courses'
        version false
    }
}