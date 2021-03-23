package com.example.yt_tut;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GalleryAdapter galleryAdapter;
    GridLayoutManager gridLayoutManager;
    LinearLayoutManager layoutManager;
    private static final int MY_READ_PERMISSION_CODE =  101;
    public boolean fullScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview_gallery_images);
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_READ_PERMISSION_CODE);
        }
        else
        {
            loadImages();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_READ_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Read external storage permission granted!", Toast.LENGTH_SHORT).show();
                loadImages();
            }
            else
            {
                Toast.makeText(this, "Read external storage permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void loadImages()
    {
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        gridLayoutManager = new GridLayoutManager(this, 4);
        ImageGallery.getAllShownImagesPath(this);
        galleryAdapter = new GalleryAdapter(this, ImageGallery.images, gridLayoutManager,
                (GalleryAdapter.PhotoListener) this::goFullscreen);
                recyclerView.setAdapter(galleryAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    public void goFullscreen(String path, int position)
    {
        fullScreen = true;
        Intent intent = new Intent(this, FullscreenActivity2.class);
        intent.putExtra("PATH", path);
        intent.putExtra("POSITION", position);
        startActivity(intent);
    }
}