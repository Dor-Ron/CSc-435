
package csc435.moocme.a6.view;

import csc435.moocme.a6.api.Mooc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;


public class CoursesMapper implements RowMapper<Mooc> {

  @Override
  public Mooc map(ResultSet rs, StatementContext ctx) throws SQLException {
    if ((Integer)rs.getInt("id") != null) {
      return new Mooc(rs.getInt("id"),
                      rs.getString("title"),
                      rs.getString("platform"),
                      rs.getString("institution"),
                      rs.getString("uri"),
                      rs.getBoolean("free"));
    } 
    return new Mooc(rs.getString("title"),
                    rs.getString("platform"),
                    rs.getString("institution"),
                    rs.getString("uri"),
                    rs.getBoolean("free"));
  }

}