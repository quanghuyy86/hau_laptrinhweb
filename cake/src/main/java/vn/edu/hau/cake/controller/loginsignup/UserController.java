package vn.edu.hau.cake.controller.loginsignup;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

  @RequestMapping(value = "/login")
  public String login(final Model model){
    return "login";
  }

  @RequestMapping(value = "/register", method = RequestMethod.GET)
  public String register(final Model model){
    return "register";
  }

}
