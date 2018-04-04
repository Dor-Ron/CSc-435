package csc435.moocme

import grails.rest.*
import grails.converters.*

class PlatformController {
	static responseFormats = ['json', 'xml']
	
    def index() { 
        def query = "from MoocModel as mm where mm.platform=? and free=?"
        def plat = request.forwardURI.split("/")[-1]  // platform string
 
        if (plat == "edx" || plat == "coursera" || plat == "udacity") {
            if (params.free != null) {
                if (params.free == "true") {
                    render MoocModel.findAll(query, [plat, true]) as JSON
                }    
            }
            render MoocModel.findAll(query, [plat, false]) as JSON
        }
        render "{ \"success\": false }" 
    }

    def postMooc() { 
        def reqJson = request.JSON
        if (params.authenticated != null) {
            if (params.authenticated == "true") {
                try {
                    def mm = new MoocModel(title: reqJson.title, 
                                           institution: reqJson.institution,
                                           uri: reqJson.uri,
                                           free: reqJson.free.toBoolean(),
                                           platform: reqJson.platform)
                    mm.save()
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
