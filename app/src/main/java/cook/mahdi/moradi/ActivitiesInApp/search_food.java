package cook.mahdi.moradi.ActivitiesInApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cook.mahdi.moradi.AdaptersInApp.FoodsListAdapter;
import cook.mahdi.moradi.AdaptersInApp.SearchAddAdapter;
import cook.mahdi.moradi.DataBaseInApp.CookDB;
import cook.mahdi.moradi.DataModels.RecipeWithID;
import cook.mahdi.moradi.DataModels.SearchFor;
import cook.mahdi.moradi.R;

public class search_food extends AppCompatActivity {
    private EditText textAdd;
    private Button btnAdd;
    private List<SearchFor> searchFors;
    private RecyclerView recAddIngredients;
    private SearchAddAdapter searchAddAdapter;
    private Button btnSearch;
    private RecyclerView recShowResults;
    private FoodsListAdapter foodsListAdapter;
    private List<RecipeWithID> results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food);
        textAdd = findViewById(R.id.text_add_to_search_list);
        btnAdd = findViewById(R.id.btn_add_to_search_list);
        recAddIngredients = findViewById(R.id.rc_add_ingredients);
        recShowResults = findViewById(R.id.rc_show_results);
        btnSearch = findViewById(R.id.btn_search_foods);
        searchFors = new ArrayList<>();
        searchAddAdapter = new SearchAddAdapter(searchFors , this);
        recAddIngredients.setAdapter(searchAddAdapter);
        recAddIngredients.setLayoutManager( new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false));
        results = new ArrayList<>();
        foodsListAdapter = new FoodsListAdapter(results , this);
        recShowResults.setAdapter(foodsListAdapter);
        recShowResults.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL , false));
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textAdd.getText().toString() != "") {
                    SearchFor search = new SearchFor();
                    search.setName(textAdd.getText().toString());
                    searchFors.add(search);
                    searchAddAdapter.notifyDataSetChanged();
                    textAdd.setText("");
                }
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchFors.size() >0){
                    CookDB cookDB = new CookDB(search_food.this);
                    results.clear();
                    results.addAll(cookDB.get_list_by_search(searchFors));
                    foodsListAdapter.notifyDataSetChanged();
                    cookDB.close();
                }else{
                    Toast.makeText(search_food.this , "opt an option!:)" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
