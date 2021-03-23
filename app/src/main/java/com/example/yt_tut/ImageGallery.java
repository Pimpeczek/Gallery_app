package com.example.yt_tut;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

public class ImageGallery
{
    public static List<String> images;
    public static void getAllShownImagesPath(Context activity) {
        @SuppressWarnings("deprecation") final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        final String orderBy = MediaStore.Images.Media._ID;
        Cursor cursor = activity.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy);
        int count = cursor.getCount();
        ArrayList<String> arrPath;
        arrPath = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            cursor.moveToPosition(i);
            @SuppressWarnings("deprecation") int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            arrPath.add(cursor.getString(dataColumnIndex));
        }
        cursor.close();
        images = arrPath;
    }
}
