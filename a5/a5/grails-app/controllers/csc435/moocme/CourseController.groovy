package csc435.moocme


import grails.rest.*
import grails.converters.*

class CourseController {
	static responseFormats = ['json', 'xml']
	
    def index() { 
        def query = "from MoocModel as mm where mm.platform=? and mm.id=?"
        def id = request.forwardURI.split("/")[-1] as Long
        def plat = request.forwardURI.split("/")[-2]

        if (!(MoocModel.findAll(query, [plat, id]) as String == "[]")) {
            try {
                render MoocModel.findAll(query, [plat, id]) as JSON
            } catch (Exception ex) {
                println ex
            } 
        }
        render "{ \"success\": false }" 
    }

    def updateMooc() { 
        def id = request.forwardURI.split("/")[-1] as Long

        def reqJson = request.JSON
        if (params.authenticated != null) {
            if (params.authenticated == "true") {
                try {
                    def mm = MoocModel.get(id)
                    println mm
                    mm.title = reqJson.title
                    mm.institution = reqJson.institution
                    mm.uri = reqJson.uri
                    mm.free = reqJson.free.toBoolean()
                    mm.platform = reqJson.platform

                    mm.save(flush: true)
                    render "{ \"success\": true }" 
                } catch (Exception ex) {
                    render "{ \"success\": false }" 
                }
            }
        } else {
            render "{ \"success\": false, \"reason\": \"Not authenticated\" }" 
        }
    }

    def delMooc() { 
        def id = request.forwardURI.split("/")[-1] as Long

        def reqJson = request.JSON
        if (params.authenticated != null) {
            if (params.authenticated == "true") {
                try {
                    def mm = MoocModel.get(id)
                    mm.delete(flush: true)
                    render "{ \"success\": true }" 
                } catch (Exception ex) {
                    render "{ \"success\": false }" 
                }
            }
        } else {
            render "{ \"success\": false, \"reason\": \"Not authenticated\" }" 
        }
    }
}
