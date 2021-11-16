package com.yooda.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yooda.app.R;
import com.yooda.app.model.HeadCategory;

import java.util.List;

public class HeadCategoryAdapter extends RecyclerView.Adapter<HeadCategoryAdapter.HeadCategoryViewHolder> {

    Context context;
    List<HeadCategory> categories;

    public HeadCategoryAdapter(Context context, List<HeadCategory> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public HeadCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View categoryItems = LayoutInflater.from(context).inflate(R.layout.head_element_chunk, parent, false);
        return new HeadCategoryViewHolder(categoryItems);
    }

    @Override
    public void onBindViewHolder(@NonNull HeadCategoryViewHolder holder, int position) {
        holder.title.setText(categories.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static final class HeadCategoryViewHolder extends RecyclerView.ViewHolder{

        TextView title;

        public HeadCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.menuText);
        }
    }
}
