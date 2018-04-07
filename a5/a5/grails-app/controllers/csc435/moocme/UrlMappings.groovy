package csc435.moocme

class UrlMappings {

    static mappings = {
        // delete "/$controller/$id(.$format)?"(action:"delete")
        // get "/$controller(.$format)?"(action:"index")
        // get "/$controller/$id(.$format)?"(action:"show")
        // post "/$controller(.$format)?"(action:"save")
        // put "/$controller/$id(.$format)?"(action:"update")
        // patch "/$controller/$id(.$format)?"(action:"patch")

        "/"(controller: 'application', action:'index')

        get "/courses"(controller: 'table')

        get "/courses/$platform"(controller: 'platform', action: 'index')
        post "/courses/$platform"(controller: 'platform', action: 'postMooc')

        get "/courses/$platform/$id"(controller: 'course', action: 'index')
        put "/courses/$platform/$id"(controller: 'course', action: 'updateMooc')
        delete "/courses/$platform/$id"(controller: 'course', action: 'delMooc')

        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
