package com.example.yt_tut;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private final Context context;
    private final List<String> images;
    protected PhotoListener photoListener;
    GridLayoutManager gridLayoutManager;
    public GalleryAdapter(MainActivity mainActivity, List<String> images, GridLayoutManager gridLayoutManager, PhotoListener photoListener) {
        context = mainActivity;
        this.images = images;
        this.photoListener = photoListener;
        this.gridLayoutManager = gridLayoutManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder((LayoutInflater.from(context).inflate(R.layout.gallery_item, parent, false)));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String image = images.get(position);
        Glide.with(context).load(image).into(holder.image);
        holder.itemView.setOnClickListener(v -> photoListener.onPhotoClick(image, position));
    }


    @Override
    public int getItemCount() {
        if(images != null)
        {
            return images.size();
        }

        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
    public interface PhotoListener
    {
        void onPhotoClick(String path, int position);
    }
}
