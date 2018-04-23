package csc435.moocme.a6.dao;

import java.util.List;

import csc435.moocme.a6.api.Mooc;

import javax.ws.rs.core.Response;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface CoursesDAO {


    //    /courses
    @SqlQuery("select * from courses")
    public List<Mooc> getAll();

    @SqlQuery("select * from courses where (free = true)")
    public List<Mooc> getFree();


    //    /courses/{platform}
    @SqlQuery("select * from courses where (platform = :plat)")
    public List<Mooc> getPlatCourses(@Bind("plat") String plat);

    @SqlQuery("select * from courses where (platform = :plat) and (free = true)")
    public List<Mooc> getPlatFreeCourses(@Bind("plat") String plat);

    @SqlUpdate("insert into courses(title, institution, uri, free, platform) values (:title, :inst, :uri, :free, :plat)")
    public void postMooc(@Bind("title") String title, 
                         @Bind("inst") String inst,
                         @Bind("uri") String uri,
                         @Bind("free") Boolean free,
                         @Bind("plat") String plat
                        );
    }