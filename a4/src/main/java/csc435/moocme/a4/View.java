package csc435.moocme.a4;

import static spark.Spark.*;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.JsonProcessingException;

class View {
    public View() {
        super();
    }

    /**
    * Turns parameter into json string
    *
    * @param  most likely a SQL.ResultSet or ArrayList<ReqJsonObject> rs 
    * @throws JsonProcessingException
    * @return JSON string of object
    */
    public String jsonify(Object rs) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter()
                     .writeValueAsString(rs); 
    }
}