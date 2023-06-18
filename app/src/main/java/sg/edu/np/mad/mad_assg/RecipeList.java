package sg.edu.np.mad.mad_assg;

public class RecipeList {

    private long id;
    private String imageUrl;
    private String recipeName;
    private String category;
    private String username;
    private String description;
    private String ingredients;
    private String steps;

    public RecipeList(long id, String imageUrl, String recipeName, String category, String username, String description, String ingredients, String steps) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.recipeName = recipeName;
        this.category = category;
        this.username = username;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public RecipeList() {

    }

    public long getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }
}

