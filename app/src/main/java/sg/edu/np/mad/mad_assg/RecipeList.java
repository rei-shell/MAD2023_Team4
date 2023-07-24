package sg.edu.np.mad.mad_assg;

public class RecipeList {
    private String title;
    private String description;
    private String photoUrl;
    private String category;
    private int numberOfPersons;
    private String ingredients;
    private String recipeSteps;
    private int preparationTime;
    private int cookingTime;
    private int totalTime;

    public RecipeList(String title, String description, String photoUrl, String category, int numberOfPersons, String ingredients, String recipeSteps, int preparationTime, int cookingTime, int totalTime) {
        this.title = title;
        this.description = description;
        this.photoUrl = photoUrl;
        this.category = category;
        this.numberOfPersons = numberOfPersons;
        this.ingredients = ingredients;
        this.recipeSteps = recipeSteps;
        this.preparationTime = preparationTime;
        this.cookingTime = cookingTime;
        this.totalTime = totalTime;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(String recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

}


