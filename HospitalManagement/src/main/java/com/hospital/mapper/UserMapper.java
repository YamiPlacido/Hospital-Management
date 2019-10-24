package com.hospital.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.hospital.model.Users;
public class UserMapper implements RowMapper<Users>{
	
	public static final String BASE_SQL = "select u.user_id, u.username, u.password from users u";
	
	@Override
    public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
 
        Long user_id = rs.getLong("user_id");
        String userName = rs.getString("username");
        String password = rs.getString("password");
 
        return new Users(user_id, userName, password);
    }
}
