package cook.mahdi.moradi.ActivitiesInApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import cook.mahdi.moradi.AdaptersInApp.FoodsListAdapter;
import cook.mahdi.moradi.DataBaseInApp.CookDB;
import cook.mahdi.moradi.DataModels.RecipeWithID;
import cook.mahdi.moradi.R;
public class FoodsActivity extends AppCompatActivity {
    private static final String TAG = "FoodsActivity";
    private TextView category_name;
    private RecyclerView recyclerView_rec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        setUpViews();
        try {
            String cat_id;
            cat_id =  getIntent().getStringExtra("cat_id");
            CookDB cookDB = new CookDB(this);
            String cat_name = cookDB.getCatByID(Integer.parseInt(cat_id));
            category_name.setText(cat_name);
            List<RecipeWithID> names = cookDB.getRecipesNameByCatID(Integer.parseInt(cat_id));
            FoodsListAdapter foodsListAdapter = new FoodsListAdapter(names, this);
            recyclerView_rec.setAdapter(foodsListAdapter);
            recyclerView_rec.setLayoutManager(new LinearLayoutManager(this , RecyclerView.VERTICAL , false));
            cookDB.close();
        }catch (Exception e){
            Log.i(TAG, "onCreate: " + e.toString());
        }
    }

    private void setUpViews(){
        category_name = findViewById(R.id.category);
        recyclerView_rec = findViewById(R.id.rc_rec_list);
    }
}
