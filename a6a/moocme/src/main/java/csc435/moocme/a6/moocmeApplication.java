package csc435.moocme.a6;

import csc435.moocme.a6.resources.CoursesResource;
import csc435.moocme.a6.resources.PlatformResource;

import csc435.moocme.a6.view.CoursesMapper;
// import csc435.moocme.a6.view.PostMapper;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;
import com.github.arteam.jdbi3.JdbiFactory;

public class moocmeApplication extends Application<moocmeConfiguration> {

    public static void main(final String[] args) throws Exception {
        new moocmeApplication().run(args);
    }

    @Override
    public String getName() {
        return "moocme";
    }

    @Override
    public void initialize(final Bootstrap<moocmeConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final moocmeConfiguration configuration,
                    final Environment environment) {

        final JdbiFactory orm = new JdbiFactory();
        final Jdbi db = orm.build(environment, configuration.getDataSource(), "mysql");

        db.registerRowMapper(new CoursesMapper());
        // db.registerRowMapper(new PostMapper());

        // db.registerRowMapper(new BookMapper());
        // db.registerRowMapper(new BookAuthorMapper());

        final CoursesResource resource = new CoursesResource(db);
        final PlatformResource resource2 = new PlatformResource(db);

        environment.jersey().register(resource);
        environment.jersey().register(resource2);
    }
}
