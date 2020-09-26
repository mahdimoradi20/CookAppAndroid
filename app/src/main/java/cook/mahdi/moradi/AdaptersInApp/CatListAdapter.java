package cook.mahdi.moradi.AdaptersInApp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cook.mahdi.moradi.ActivitiesInApp.FoodsActivity;
import cook.mahdi.moradi.DataModels.Category;
import cook.mahdi.moradi.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Category> categories;

    public CatListAdapter (Context context , List<Category> categories){
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cats_list, parent , false);
        return new CatListAdapter.VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        VHolder vHolder = (VHolder) holder;
        vHolder.catName.setText(categories.get(position).getText());
        vHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(context , FoodsActivity.class);
                intent.putExtra("cat_id" , String.valueOf(categories.get(position).getId()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class VHolder extends RecyclerView.ViewHolder{

        public TextView catName;
        public VHolder(@NonNull View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.cat_name);
        }
    }
}
