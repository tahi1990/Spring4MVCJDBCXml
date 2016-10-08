package com.tahi1990.springmvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tahi1990.springmvc.dao.UserDetailsDao;
import com.tahi1990.springmvc.model.UserDetails;
import com.tahi1990.springmvc.service.UserDetailsService;

public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserDetailsDao userDetailsDao;

  @Override
  public UserDetails getUserDetails(int id) {
    return userDetailsDao.getUserDetails(id);
  }

  @Override
  public List<UserDetails> getAllUserDetails() {
    return userDetailsDao.getAllUserDetails();
  }

  @Override
  public int addUserDetails(UserDetails userDetails) {
    return userDetailsDao.addUserDetails(userDetails);
  }

  @Override
  public int updateUserDetails(UserDetails userDetails) {
    return userDetailsDao.updateUserDetails(userDetails);
  }

  @Override
  public int deleteUserDetails(int id) {
    return userDetailsDao.deleteUserDetails(id);
  }

  public UserDetailsDao getUserDetailsDao() {
    return userDetailsDao;
  }

}
