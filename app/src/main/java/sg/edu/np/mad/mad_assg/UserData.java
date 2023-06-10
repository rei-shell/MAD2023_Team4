package sg.edu.np.mad.mad_assg;


public class UserData {
    private String username;
    private String password;
    private static String email;

    public UserData() {
    }

    public UserData(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
    }

    public String getPassword() {return password;}

    public void setPassword(String password) {
        this.password = password;
    }

    public static String getEmail() { return email; }

    public void setEmail(String email) { this.email = email;}
}