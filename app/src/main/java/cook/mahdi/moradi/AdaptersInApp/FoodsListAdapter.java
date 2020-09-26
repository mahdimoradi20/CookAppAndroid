package cook.mahdi.moradi.AdaptersInApp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import cook.mahdi.moradi.ActivitiesInApp.FoodContent;
import cook.mahdi.moradi.DataModels.RecipeWithID;
import cook.mahdi.moradi.R;

public class FoodsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RecipeWithID> recipeWithIDS;
    private Context context;

    public FoodsListAdapter(List<RecipeWithID> recipeWithIDS, Context context){

        this.recipeWithIDS = recipeWithIDS;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.foods_list , parent , false);
        return new FoodsListAdapter.VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        VHolder vHolder = (VHolder) holder;
        vHolder.rec_name.setText(String.valueOf(recipeWithIDS.get(position).getName()));
        vHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , FoodContent.class);
                intent.putExtra("id_for_content" , recipeWithIDS.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeWithIDS.size();
    }

     public class VHolder extends RecyclerView.ViewHolder{
        public TextView rec_name;
         public VHolder(@NonNull View itemView) {
             super(itemView);
             rec_name = itemView.findViewById(R.id.food_name);
         }
     }
}
