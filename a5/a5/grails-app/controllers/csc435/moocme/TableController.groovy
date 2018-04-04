package csc435.moocme

import csc435.moocme.MoocModel

import grails.rest.*
import grails.converters.*

class TableController {
	static responseFormats = ['json', 'xml']
	
    def index() {  // For GET requests 
        def query = "from MoocModel as mm where mm.free=true"
        if (params.free != null) {
            if (params.free == "true") {
                render MoocModel.findAll(query) as JSON
            }
        }
        render MoocModel.list() as JSON
    }
}
