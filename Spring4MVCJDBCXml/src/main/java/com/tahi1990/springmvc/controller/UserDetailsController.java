package com.tahi1990.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tahi1990.springmvc.model.UserDetails;
import com.tahi1990.springmvc.service.UserDetailsService;

@Controller
@RequestMapping("/")
public class UserDetailsController {

  @Autowired
  private UserDetailsService userDetailsService;

  @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
  public String getUserDetails(@PathVariable int id, ModelMap userModel) {
    userModel.addAttribute("userDetails", userDetailsService.getUserDetails(id));
    return "user";
  }

  @RequestMapping(value = "users", method = RequestMethod.GET)
  public String getUsersDetails(ModelMap userModel) {
    userModel.addAttribute("userDetails", userDetailsService.getAllUserDetails());
    return "users";
  }

  @RequestMapping(value = "addUser")
  public String addPage() {
    return "add";
  }

  @RequestMapping(value = "add/user", method = RequestMethod.POST)
  public String addUser(@RequestParam(value = "fname", required = true) String fname,
      @RequestParam(value = "lname", required = true) String lname,
      @RequestParam(value = "email", required = true) String email,
      @RequestParam(value = "dob", required = true) String dob, ModelMap userModel) {
    UserDetails userDetails = new UserDetails();
    userDetails.setFirstName(fname);
    userDetails.setLastName(lname);
    userDetails.setEmail(email);
    userDetails.setDob(dob);
    int resp = userDetailsService.addUserDetails(userDetails);
    if (resp > 0) {
      userModel.addAttribute("msg", "User with id : " + resp + " added successfully.");
      userModel.addAttribute("userDetails", userDetailsService.getAllUserDetails());
      return "users";
    } else {
      userModel.addAttribute("msg", "User addition failed.");
      return "add";
    }
  }

  @RequestMapping(value = "delete/user/{id}", method = RequestMethod.GET)
  public String deleteUser(@PathVariable("id") int id, ModelMap userModel) {
    int resp = userDetailsService.deleteUserDetails(id);
    userModel.addAttribute("userDetails", userDetailsService.getAllUserDetails());
    if (resp > 0) {
      userModel.addAttribute("msg", "User with id : " + id + " deleted successfully.");
    } else {
      userModel.addAttribute("msg", "User with id : " + id + " deletion failed.");
    }
    return "users";
  }

  @RequestMapping(value = "update/user/{id}", method = RequestMethod.GET)
  public String updatePage(@PathVariable("id") int id, ModelMap userModel) {
    userModel.addAttribute("id", id);
    userModel.addAttribute("userDetails", userDetailsService.getUserDetails(id));
    return "update";
  }

  @RequestMapping(value = "update/user", method = RequestMethod.POST)
  public String updateUser(@RequestParam int id,
      @RequestParam(value = "fname", required = true) String fname,
      @RequestParam(value = "lname", required = true) String lname,
      @RequestParam("email") String email, @RequestParam("dob") String dob, ModelMap userModel) {
    UserDetails userDetails = new UserDetails();
    userDetails.setId(id);
    userDetails.setFirstName(fname);
    userDetails.setLastName(lname);
    userDetails.setEmail(email);
    userDetails.setDob(dob);
    int resp = userDetailsService.updateUserDetails(userDetails);
    userModel.addAttribute("id", id);
    if (resp > 0) {
      userModel.addAttribute("msg", "User with id : " + id + " updated successfully.");
      userModel.addAttribute("userDetails", userDetailsService.getAllUserDetails());
      return "users";
    } else {
      userModel.addAttribute("msg", "User with id : " + id + " updation failed.");
      userModel.addAttribute("userDetails", userDetailsService.getUserDetails(id));
      return "update";
    }
  }

}
