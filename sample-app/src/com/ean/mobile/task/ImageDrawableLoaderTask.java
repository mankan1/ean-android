package com.ean.mobile.task;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import com.ean.mobile.HotelImageDrawable;
import com.ean.mobile.SampleConstants;

import java.io.IOException;

public final class ImageDrawableLoaderTask extends AsyncTask<HotelImageDrawable, Integer, Drawable> {

    private final ImageView view;

    private final boolean loadMain;

    public ImageDrawableLoaderTask(final ImageView thumb, final boolean loadMain) {
        super();
        this.view = thumb;
        this.loadMain = loadMain;
    }

    @Override
    protected Drawable doInBackground(HotelImageDrawable... hotelImageTuples) {
        try {
            if (loadMain) {
                return hotelImageTuples[0].loadMainImage();
            }
            return hotelImageTuples[0].loadThumbnailImage();
        } catch (IOException ioe) {
            Log.d(SampleConstants.DEBUG, "An error occurred when loading hotel's main thumbnail", ioe);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Drawable drawable) {
        view.setImageDrawable(drawable);
    }

}