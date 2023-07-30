package sg.edu.np.mad.mad_assg;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class RecipeList{
    private String userid;
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
    public RecipeList() {
    }
    public RecipeList(String userid, String title, String description, String photoUrl, String category, int numberOfPersons, String ingredients, String recipeSteps, int preparationTime, int cookingTime, int totalTime) {
        this.userid = userid;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    private String convertRecipeToJsonString(RecipeList recipe) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userid", recipe.getUserid());
            jsonObject.put("title", recipe.getTitle());
            jsonObject.put("description", recipe.getDescription());
            jsonObject.put("photoUrl", recipe.getPhotoUrl());
            jsonObject.put("category", recipe.getCategory());
            jsonObject.put("numberOfPersons", recipe.getNumberOfPersons());
            jsonObject.put("ingredients", recipe.getIngredients());
            jsonObject.put("recipeSteps", recipe.getRecipeSteps());
            jsonObject.put("preparationTime", recipe.getPreparationTime());
            jsonObject.put("cookingTime", recipe.getCookingTime());
            jsonObject.put("totalTime", recipe.getTotalTime());
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}


