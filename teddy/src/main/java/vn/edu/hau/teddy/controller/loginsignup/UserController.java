package vn.edu.hau.teddy.controller.loginsignup;

import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.edu.hau.teddy.model.Role;
import vn.edu.hau.teddy.model.User;
import vn.edu.hau.teddy.model.User_Role;
import vn.edu.hau.teddy.service.RoleService;
import vn.edu.hau.teddy.service.UserRoleService;
import vn.edu.hau.teddy.service.UserService;

@Controller
public class UserController {

  private final UserService userService;


  private final RoleService roleService;


  private final UserRoleService userRoleService;

  public UserController(UserService userService, RoleService roleService,
      UserRoleService userRoleService) {
    this.userService = userService;
    this.roleService = roleService;
    this.userRoleService = userRoleService;
  }

  @RequestMapping(value = "/login")
  public String login(final Model model){
    return "login/login";
  }

  @RequestMapping(value = "/register", method = RequestMethod.GET)
  public String register(final Model model){
    model.addAttribute("user", new User());
    return "login/register";
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public String processRegistration(@ModelAttribute("user") User user, final Model model) {
    try {
      user.setPassWord(new BCryptPasswordEncoder().encode(user.getPassWord()));

      userService.save(user);

      Role role = roleService.findRoleByName("GUEST");
      User_Role userRole = new User_Role();
      userRole.setUser(user);
      userRole.setRole(role);
      Long idUser = userRole.getUser().getId();
      Long idRole = userRole.getRole().getId();

      userRoleService.saveUserRole(idUser,idRole);

      return "redirect:/login";
    }
    catch (Exception e){
      e.printStackTrace();
      model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
      return "error/error";
    }
  }

  @RequestMapping(value = "/admin/register", method = RequestMethod.GET)
  public String registerAdmin(final Model model){
    model.addAttribute("user_admin", new User());
    return "admin/admin/registeradmin";
  }

  @RequestMapping(value = "/admin/register", method = RequestMethod.POST)
  public String processRegistrationAdmin(@ModelAttribute("user_admin") User user, final Model model) {

    try {
      user.setPassWord(new BCryptPasswordEncoder().encode(user.getPassWord()));

      userService.save(user);

      Role role = roleService.findRoleByName("ADMIN");
      User_Role userRole = new User_Role();
      userRole.setUser(user);
      userRole.setRole(role);
      Long idUser = userRole.getUser().getId();
      Long idRole = userRole.getRole().getId();

      userRoleService.saveUserRole(idUser,idRole);

      return "redirect:/listadmin";
    }
    catch (Exception e){
      e.printStackTrace();
      model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
      return "error/error";
    }
  }

  @RequestMapping(value = "/admin/listadmin", method = RequestMethod.GET)
  public String categoriesList(final Model model){
    List<User> userList = userService.findAll();
    model.addAttribute("userlist", userList);
    return "admin/admin/listadmin";
  }

}
