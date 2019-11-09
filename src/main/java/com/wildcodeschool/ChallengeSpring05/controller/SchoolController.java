package com.wildcodeschool.ChallengeSpring05.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.wildcodeschool.ChallengeSpring05.model.School;

@Controller
@ResponseBody
public class SchoolController {
	private final static String DB_URL = "jdbc:mysql://localhost:3306/wild_db_quest?serverTimezone=GMT";
    private final static String DB_USER = "h4rryp0tt3r";
    private final static String DB_PASSWORD = "Horcrux4life!";
    
    @GetMapping("/api/schools")
    public List<School> getSchools(@RequestParam(required=false, defaultValue = "%") String country) {
    	String sql = "SELECT * FROM school WHERE country LIKE ?";	
    	
    	try(
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, country);

            try(
                ResultSet resulSet = statement.executeQuery();
            ) {
            	List<School> schools = new ArrayList<School>();

            	while(resulSet.next()){
            	    int id = resulSet.getInt("id");
            	    String name = resulSet.getString("name");
            		int capacity = resulSet.getInt("capacity");
            		String state = resulSet.getString("country");
            		
            	    schools.add(new School(id, name, capacity, state));
            	}

            	return schools;
            }
        }
        catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "", e);
        }
    }

}
