package sg.edu.np.mad.mad_assg;

public class CategoryData {

    private static String category;
    private static String imageUrl;

    public CategoryData(String category, String imageUrl) {
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public static void setCategory(String category) {
        CategoryData.category = category;
    }

    public static String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        CategoryData.imageUrl = imageUrl;
    }

    public CategoryData() {

    }

    public static CategoryData get(int position) {
        // Assuming you have a list called recipeList to store the recipe items
        if (position >= 0 && position < CategoryData.size()) {
            return CategoryData.get(position);
        }
        return null; // Return null or handle the out-of-bounds case as per your requirements
    }

    private static int size() {
        return CategoryData.size();
    }

    public static String get_category()
    {return category;}




}
