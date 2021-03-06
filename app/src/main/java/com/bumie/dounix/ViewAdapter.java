package com.bumie.dounix;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.CustomViewHolder> {

    ArrayList<Model> arrayList;
    private Context context;

    public ViewAdapter(ArrayList<Model> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewAdapter.CustomViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter.CustomViewHolder holder, final int position) {
        final Model post = arrayList.get(position);
        Picasso.with(context)
                .load(post.getImage())
                .placeholder(R.drawable.donut)
                .error(R.drawable.donut)
                .into(holder.pic);
        holder.pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Item.class);
                i.putExtra("Position", position);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView pic;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.imageView4);
        }
    }
}
