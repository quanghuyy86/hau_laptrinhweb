package vn.edu.hau.teddy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "username")
  private String userName;
  @Column(name = "password")
  private String passWord;
  @Column(name = "fullname")
  private String fullName;
  @Column(name = "email")
  private String email;
  @Column(name = "telephone")
  private String telephone;
  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  private Set<User_Role> userRoles;

  public User() {
    super();
    // TODO Auto-generated constructor stub
  }

  public User(Long id, String userName, String passWord, String fullName, String email, String telephone, Set<User_Role> userRoles) {
    super();
    this.id = id;
    this.userName = userName;
    this.passWord = passWord;
    this.fullName = fullName;
    this.email = email;
    this.telephone = telephone;
    this.userRoles = userRoles;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassWord() {
    return passWord;
  }

  public void setPassWord(String passWord) {
    this.passWord = passWord;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public Set<User_Role> getUserRoles() {
    return userRoles;
  }

  public void setUserRoles(Set<User_Role> userRoles) {
    this.userRoles = userRoles;
  }
}
