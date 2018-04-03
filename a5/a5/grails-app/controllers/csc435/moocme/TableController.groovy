package csc435.moocme

import csc435.moocme.MoocModel

import grails.rest.*
import grails.converters.*

class TableController {
	static responseFormats = ['json', 'xml']
	
    def index() { 
        render MoocModel.list() as JSON
    }
}
