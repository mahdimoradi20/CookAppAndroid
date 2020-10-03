package cook.mahdi.moradi.DataBaseInApp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import co.intentservice.chatui.models.ChatMessage;
import cook.mahdi.moradi.ApiHandlers.SendDataToServer;
import cook.mahdi.moradi.DataModels.Category;
import cook.mahdi.moradi.DataModels.Recipe;
import cook.mahdi.moradi.DataModels.RecipeWithID;
import cook.mahdi.moradi.DataModels.SearchFor;
import cook.mahdi.moradi.DataModels.WeeklyRoutin;

public class CookDB extends SQLiteOpenHelper{
    private static String DB_PATH = "";
    private static final String DATABASE_NAME = "cook.db";
    private static final int DATABASE_VERSION = 1;
    public Context context;
    private static SQLiteDatabase sqliteDataBase;

    public CookDB(Context context) {
        super(context, DATABASE_NAME, null ,DATABASE_VERSION);
        this.context = context;
        DB_PATH = context.getApplicationInfo().dataDir +  "/databases/";
        createDataBase();
        openDataBase();
    }

    public void createDataBase() {
        try {
            boolean databaseExist = checkDataBase();
            if (databaseExist) {
            } else {
                this.getWritableDatabase();
                copyDataBase();
            }
        }catch (IOException e){

        }
    }


    public boolean checkDataBase(){
        File databaseFile = new File(DB_PATH + DATABASE_NAME);
        return databaseFile.exists();
    }


    private void copyDataBase() throws IOException{

        InputStream myInput = context.getAssets().open(DATABASE_NAME);
        String outFileName = DB_PATH + DATABASE_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException{

        String myPath = DB_PATH + DATABASE_NAME;
        sqliteDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if(sqliteDataBase != null)
            sqliteDataBase.close();
        super.close();
    }

    /**
     * Apply your methods and class to fetch data using raw or queries on data base using
     * following demo example code as:
     */

    public List<Category> getCats(){
        List<Category> categories = new ArrayList<>();
        Cursor c = sqliteDataBase.rawQuery("SELECT * FROM category" , null);
        c.moveToFirst();
        if(c.getCount() > 0) {
            while (!c.isAfterLast()) {
                Category category = new Category();
                category.setId(c.getInt(0));
                category.setText(c.getString(1));
                categories.add(category);
                c.moveToNext();
            }
            c.close();
            return categories;
        }
        return new ArrayList<>();
    }

    public String getCatByID(int id){
        Cursor c = sqliteDataBase.rawQuery("SELECT * from category where id = " + String.valueOf(id) , null);
        c.moveToFirst();
        if(c.getCount() > 0)
        {
            String name = c.getString(1);
            c.close();
            return name ;
        }
        c.close();
        return "";
    }

    public List<RecipeWithID> getRecipesNameByCatID(int id){
        List<RecipeWithID> recipeWithIDS = new ArrayList<>();
        Cursor c = sqliteDataBase.rawQuery( "SELECT Recipes.name,Recipes.id FROM category , Recipes where category.id = Recipes.catid and category.id ="
                + String.valueOf(id) ,null);
        c.moveToFirst();
        if (c.getCount() > 0){
            while(!c.isAfterLast()){
                RecipeWithID recipeWithID = new RecipeWithID();
                recipeWithID.setName(c.getString(0));
                recipeWithID.setId(c.getInt(1));
                recipeWithIDS.add(recipeWithID);
                c.moveToNext();
            }
            return recipeWithIDS;
        }
        return new ArrayList<>();
    }

    public Recipe getOneRecipeWithID(int ID){

        Recipe recipe = new Recipe();
        Cursor c = sqliteDataBase.rawQuery("SELECT id,catid,name,ingredient,rec From Recipes where id = " + ID,null);
        c.moveToFirst();
        if(c.getCount() == 1)
        {
            recipe.setId(c.getInt(0));
            recipe.setCatid(c.getInt(1));
            recipe.setName(c.getString(2));
            recipe.setIngredient(c.getString(3));
            recipe.setRec(c.getString(4));
            return recipe;
        }
        return new Recipe();
    }
    public void update_weekly(String day_name , int food_id , String date_time , String description){
        String query = "Update weekly set food_id = " + String.valueOf(food_id) +
                ",date_time = '"  + date_time + "' ,descriotion = \""  + description  +  "\" where day_name =\"" + day_name + "\"";
        Cursor c = sqliteDataBase.rawQuery(query , null);
        c.moveToFirst();
        c.close();
    }

    public List<WeeklyRoutin> getWeekklyRoutinRows(){
        String query = "Select food_id,date_time,day_name,descriotion from weekly";
        Cursor c = sqliteDataBase.rawQuery(query , null);
        c.moveToFirst();
        if(c.getCount() > 0){
            List<WeeklyRoutin> weeklyRoutins = new ArrayList<>();
            while (! c.isAfterLast()){
                WeeklyRoutin weeklyRoutin = new WeeklyRoutin();
                weeklyRoutin.setFood_id(c.getInt(0));
                weeklyRoutin.setFood_name(getOneRecipeWithID(c.getInt(0)).getName());
                weeklyRoutin.setDate_time(c.getString(1));
                weeklyRoutin.setDay_name(c.getString(2));
                weeklyRoutin.setDesciption(c.getString(3));
                weeklyRoutins.add(weeklyRoutin);
                c.moveToNext();
            }
            return weeklyRoutins;
        }
        return new ArrayList<>();
    }
    public void delete_a_weekly(String day_name){
        String query = "update weekly set food_id =\"\" , date_time =\"\" ,descriotion = \"\" where day_name = \"" + day_name + "\"";
        Cursor c = sqliteDataBase.rawQuery(query , null);
        c.moveToFirst();
        c.close();
    }

    private boolean checkIfIdExsist(int id){
        String query = "SELECT server_id from Recipes WHERE server_id =" + id;
        Log.i("TAG", "saveNewRecipes: " + "yes i am hehere"  + id);
        Cursor c = sqliteDataBase.rawQuery(query , null);
        c.moveToFirst();
        if (c.getCount() > 0 ){
            c.close();
            return true;
        }
        else {
            c.close();
            return false;
        }
    }

    public void saveNewRecipes(List<Recipe> recipes) {

        for (int i = 0; i < recipes.size(); i++) {
            if (!checkIfIdExsist(recipes.get(i).getId())) {
                String query = "INSERT INTO Recipes (server_id , catid , name , ingredient , rec , pic , res1 , res2) values (" +
                        recipes.get(i).getId() + "," + recipes.get(i).getCatid() + ",\'" + recipes.get(i).getName()
                        + "\',\'" + recipes.get(i).getIngredient() + "\',\'" + recipes.get(i).getRec()
                        + "\',\'" + recipes.get(i).getPic() + "\',\'" + recipes.get(i).getRes1() + "\',\'" +
                        recipes.get(i).getRes2() + "\'" + ")";
                Cursor c = sqliteDataBase.rawQuery(query, null);
                c.moveToFirst();
                c.close();
                SendDataToServer.sendStatic("addCountRecipes" , String.valueOf(recipes.get(i).getId()));
            }
        }
    }

    public List<RecipeWithID> get_list_by_search(List<SearchFor>searchFors){
        List<RecipeWithID> recipeWithIDS = new ArrayList<>();
        String query = "SELECT Recipes.name,Recipes.id FROM Recipes WHERE";
        int i;
        for(i = 0 ; i < searchFors.size()-1 ; i++){
            query += " Recipes.ingredient like \'%" + searchFors.get(i).getName() + "%\' and ";
        }
        query += " Recipes.ingredient like \'%" + searchFors.get(i).getName() + "%\'";
        Cursor c = sqliteDataBase.rawQuery( query,null);
        c.moveToFirst();
        if (c.getCount() > 0){
            while(!c.isAfterLast()){
                RecipeWithID recipeWithID = new RecipeWithID();
                recipeWithID.setName(c.getString(0));
                recipeWithID.setId(c.getInt(1));
                recipeWithIDS.add(recipeWithID);
                c.moveToNext();
            }
            return recipeWithIDS;
        }
        return new ArrayList<>();
    }

    public void addMessage(ChatMessage chatMessage){
        int type = 0;
        if(chatMessage.getType() == ChatMessage.Type.SENT){
            type = 1;
        }
        String query = "INSERT into Messages (text , type) values(\'" + chatMessage.getMessage() +"\' , " + type  + ")";
        Cursor c = sqliteDataBase.rawQuery(query , null);
        c.moveToFirst();
        c.close();
    }
    public List<ChatMessage> getAllMessages(){
        String query = "Select text , type , dateandtime from Messages";
        Cursor c = sqliteDataBase.rawQuery(query , null);
        if(c.getCount() > 0){
            c.moveToFirst();
            List<ChatMessage> messages = new ArrayList();
            while (! c.isAfterLast()){
                ChatMessage message;
                if(c.getInt(1) == 0){
                    message = new ChatMessage(c.getString(0) , c.getLong(2) , ChatMessage.Type.RECEIVED ) ;
                }
                else{
                    message = new ChatMessage(c.getString(0) , c.getLong(2) , ChatMessage.Type.SENT ) ;
                }
                messages.add(message);
                c.moveToNext();
            }
            return messages;
        }
        return new ArrayList<>();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // No need to write the create table query.
        // As we are using Pre built data base.
        // Which is ReadOnly.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No need to write the update table query.
        // As we are using Pre built data base.
        // Which is ReadOnly.
        // We should not update it as requirements of application.
    }
}