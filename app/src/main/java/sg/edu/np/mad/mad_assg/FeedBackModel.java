package sg.edu.np.mad.mad_assg;

public class FeedBackModel {
    private String userId;
    private String subject;
    private String description;

    public FeedBackModel(String userId, String subject, String description) {
        this.userId = userId;
        this.subject = subject;
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
