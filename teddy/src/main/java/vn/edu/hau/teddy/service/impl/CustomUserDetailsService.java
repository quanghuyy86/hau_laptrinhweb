package vn.edu.hau.teddy.service.impl;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.edu.hau.teddy.model.CustomUserDetails;
import vn.edu.hau.teddy.model.User;
import vn.edu.hau.teddy.model.User_Role;
import vn.edu.hau.teddy.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {


  private final UserService userService;

  public CustomUserDetailsService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userService.findByUserName(username);

    if(user == null){
      throw new UsernameNotFoundException("Sai");
    }

    Collection<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
    Set<User_Role> roles = user.getUserRoles();

    for (User_Role userRole : roles){
      grantedAuthoritySet.add(new SimpleGrantedAuthority(userRole.getRole().getName()));
    }

    return new CustomUserDetails(user,grantedAuthoritySet);
  }
}
