package sg.edu.np.mad.mad_assg;

public class CategoryData {

    private String category;
    private byte[] mainImg;

    public String get_category()
    {return category;}

    public byte[] get_mainImg()
    {return mainImg;}

    public CategoryData(String category, byte[] mainImg) {
        this.category = category;
        this.mainImg = mainImg;
    }

}
