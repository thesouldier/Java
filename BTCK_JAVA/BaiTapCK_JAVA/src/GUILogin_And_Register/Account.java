package GUILogin_And_Register;
/**
 *
 * @author Nguyen Viett Anh Quyen
 */
public class Account {
     private String fullname,username,password,numberphone,email;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumberphone() {
        return numberphone;
    }

    public void setNumberphone(String numberphone) {
        this.numberphone = numberphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
  
    public Account() {
        super();
    }

    @Override
    public String toString() {
        return "Account{" + "fullname=" + fullname + ", username=" + username + ", password=" + password + ", numberphone=" + numberphone + ", email=" + email + '}';
    }
    
}
