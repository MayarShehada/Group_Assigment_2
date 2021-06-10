package com.birzeit.group_assignment_2.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.birzeit.group_assignment_2.Activities.DetailesShoeActivity;
import com.birzeit.group_assignment_2.Models.Shoes;
import com.birzeit.group_assignment_2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter_ItemList extends RecyclerView.Adapter<Adapter_ItemList.ViewHolder> {

    private Context context;
    private List<Shoes> shoesList;

    public Adapter_ItemList(Context context, List<Shoes> shoesList) {
        this.context = context;
        this.shoesList = shoesList;
    }

    public Adapter_ItemList() {

    }

    public void filteredList(ArrayList<Shoes> filterList) {
        shoesList = filterList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public Adapter_ItemList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_ItemList.ViewHolder holder, int position) {

        Shoes shoes = shoesList.get(position);
        CardView cardView = holder.cardView;

        ImageView image = cardView.findViewById(R.id.imageView);
        Picasso.get().load(shoes.getPhoto()).into(image);

        TextView price_txt = cardView.findViewById(R.id.total_txt);
        price_txt.setText(shoes.getPrice());

        TextView name_txt = cardView.findViewById(R.id.brand_txt);
        name_txt.setText(shoes.getName());

        TextView rate_txt = cardView.findViewById(R.id.rate_txt);
        rate_txt.setText("Rate: " + shoes.getRate());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DetailesShoeActivity.class);
                intent.putExtra("id_data", shoes.getId());
                intent.putExtra("name_data", shoes.getName());
                intent.putExtra("brand_data", shoes.getBrand());
                intent.putExtra("category_data", shoes.getCategoryName());
                intent.putExtra("price_data", shoes.getPrice());
                intent.putExtra("rate_data", shoes.getRate());
                intent.putExtra("photo_data", shoes.getPhoto());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return shoesList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardView;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull CardView cardView) {
            super(cardView);
            this.cardView = cardView;
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}