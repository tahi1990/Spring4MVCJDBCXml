package com.tahi1990.springmvc.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Transactional;

import com.tahi1990.springmvc.dao.UserDetailsDao;
import com.tahi1990.springmvc.model.UserDetails;
import com.tahi1990.springmvc.model.mapper.UserDetailsRowMapper;

public class UserDetailsDaoImpl implements UserDetailsDao {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  @Transactional
  public UserDetails getUserDetails(int id) {
    UserDetails userDetails = jdbcTemplate.queryForObject("SELECT * FROM user_details WHERE id = ?",
        new Object[] {id}, new UserDetailsRowMapper());
    return userDetails;
  }

  @Override
  @Transactional
  public List<UserDetails> getAllUserDetails() {
    List<UserDetails> userDetails =
        jdbcTemplate.query("SELECT * FROM user_details", new UserDetailsRowMapper());
    return userDetails;
  }

  @Override
  @Transactional
  public int addUserDetails(UserDetails userDetails) {
    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    simpleJdbcInsert.withTableName("user_details").usingGeneratedKeyColumns("id");
    Map<String, Object> parameters = new HashMap<String, Object>(4);
    parameters.put("first_name", userDetails.getFirstName());
    parameters.put("last_name", userDetails.getLastName());
    parameters.put("email", userDetails.getEmail());
    parameters.put("dob", userDetails.getDob());
    Number insertedId = simpleJdbcInsert.executeAndReturnKey(parameters);
    return insertedId.intValue();
  }

  @Override
  @Transactional
  public int updateUserDetails(UserDetails userDetails) {
    String sql =
        "update user_details set first_name = ?, last_name = ?, email = ?, dob = ? where id = ?";
    int resp = jdbcTemplate.update(sql,
        new Object[] {userDetails.getFirstName(), userDetails.getLastName(), userDetails.getEmail(),
            userDetails.getDob(), userDetails.getId()});
    return resp;
  }

  @Override
  @Transactional
  public int deleteUserDetails(int id) {
    int resp = jdbcTemplate.update("delete from user_details where id = ?", id);
    return resp;
  }

}
