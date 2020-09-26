package cook.mahdi.moradi.ActivitiesInApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cook.mahdi.moradi.DataBaseInApp.CookDB;
import cook.mahdi.moradi.DataModels.Recipe;
import cook.mahdi.moradi.R;

public class FoodContent extends AppCompatActivity {

    private TextView textHeader ;
    private TextView textIngredient ;
    private TextView textRec ;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_content);
        setUpViews();
        id = getIntent().getIntExtra("id_for_content" , 0);
        CookDB cookDB = new CookDB(this);
        Recipe recipe = cookDB.getOneRecipeWithID(id);
        textHeader.setText(recipe.getName());
        textIngredient.setText(recipe.getIngredient());
        textRec.setText(recipe.getRec());
    }

    private void setUpViews(){
        textHeader = findViewById(R.id.food_header);
        textIngredient = findViewById(R.id.food_ingredient);
        textRec = findViewById(R.id.food_rec);
    }

    public void WannaCookBtn(View view) {
        try{
            Intent intent = new Intent(this, PlanningActivity.class);
            intent.putExtra("id" , id);
            startActivity(intent);
        }catch (Exception e){
            //do nothing
        }
    }
}
