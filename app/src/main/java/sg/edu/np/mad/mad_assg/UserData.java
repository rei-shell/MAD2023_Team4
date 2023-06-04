package sg.edu.np.mad.mad_assg;


public class UserData {
    private String username;
    private String password;
    private String email;
    private String repwd;

    public UserData(String username, String password, String email, String repwd) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.repwd = repwd;
    }

    public UserData() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {return password;}

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email;}
}