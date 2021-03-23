package com.example.yt_tut;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class FullscreenActivity2 extends AppCompatActivity implements  View.OnTouchListener, onSwipeListener{
    ImageView imageView;

    int curImage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen2);
        imageView = findViewById(R.id.full_image_view);
        Intent intent = getIntent();
        curImage = intent.getIntExtra("POSITION", 0);
        Glide.with(this).load(ImageGallery.images.get(curImage)).into(imageView);
        gestureDetector = new GestureDetector(this, new GestureListener(this));
        findViewById(R.id.full_image_view).setOnTouchListener(this);
    }

    private GestureDetector gestureDetector;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public void swipeRight() {
        if(curImage > 0){
            curImage -= 1;
            Glide.with(this).load(ImageGallery.images.get(curImage)).into(imageView);
        }
    }

    @Override
    public void swipeTop() {
        finish();
    }

    @Override
    public void swipeBottom() {

    }

    @Override
    public void swipeLeft() {
        if(curImage < ImageGallery.images.size()-1){
            curImage += 1;
            Glide.with(this).load(ImageGallery.images.get(curImage)).into(imageView);
        }

    }

    public class GestureListener extends
            GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;
        public GestureListener(onSwipeListener swipeListener )
        {
            onSwipe = swipeListener;
        }
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        result = true;
                    }
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                    result = true;
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }
    void onSwipeRight() {
        this.onSwipe.swipeRight();
    }
    void onSwipeLeft() {
        this.onSwipe.swipeLeft();
    }
    void onSwipeTop() {
        this.onSwipe.swipeTop();
    }
    void onSwipeBottom() {
        this.onSwipe.swipeBottom();
    }

    onSwipeListener onSwipe;
}

interface onSwipeListener {
    void swipeRight();
    void swipeTop();
    void swipeBottom();
    void swipeLeft();
}