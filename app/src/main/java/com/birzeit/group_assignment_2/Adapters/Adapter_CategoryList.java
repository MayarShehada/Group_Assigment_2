package com.birzeit.group_assignment_2.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.birzeit.group_assignment_2.Activities.ItemListActivity;
import com.birzeit.group_assignment_2.Models.Category;
import com.birzeit.group_assignment_2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter_CategoryList extends RecyclerView.Adapter<Adapter_CategoryList.ViewHolder>{

    private Context context;
    private  List<Category> categories;

    public Adapter_CategoryList(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    public Adapter_CategoryList() {}

    public void filteredList(ArrayList<Category> filterList) {
        categories = filterList;
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public Adapter_CategoryList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v= (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_view,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Category cat = categories.get(position);
        CardView cardView = holder.cardView;

        ImageView image = cardView.findViewById(R.id.imageView);
        Picasso.get().load(cat.getCategoryPhoto()).into(image);

        TextView txt = cardView.findViewById(R.id.text);
        txt.setText(cat.getCategoryName());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(context, ItemListActivity.class);
                intent.putExtra("Category",cat.getCategoryName());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }



    public  class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardView;
        LinearLayout mainLayout;

        public ViewHolder(@NonNull CardView cardView) {
            super(cardView);
            this.cardView= cardView;
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
