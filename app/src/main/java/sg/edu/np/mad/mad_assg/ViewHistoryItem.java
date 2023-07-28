package sg.edu.np.mad.mad_assg;

public class ViewHistoryItem {
    String Name;
    String Category;
    int image;

    public ViewHistoryItem(String name, String category, int image) {
        Name = name;
        Category = category;
        this.image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
