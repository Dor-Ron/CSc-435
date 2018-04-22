package csc435.moocme.a6;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class moocmeConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public void setDataSource(DataSourceFactory db) {
        this.database = db;
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSource() {
      return database;
    }
}