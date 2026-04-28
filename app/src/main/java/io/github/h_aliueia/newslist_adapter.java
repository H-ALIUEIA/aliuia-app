package io.github.h_aliueia;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.InputStream;
import java.net.URL;

public class newslist_adapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] titles;
    private final String[] imagesurls;

    public newslist_adapter(Activity context, String[] titles, String[] imagesurls) {
        super(context, R.layout.newslist_layout, titles);
        this.context = context;
        this.titles = titles;
        this.imagesurls = imagesurls;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.newslist_layout, null, true);
        TextView title = (TextView) rowView.findViewById(R.id.newslist_text);
        ImageView image = (ImageView) rowView.findViewById(R.id.newslist_image);
        title.setText(titles[position]);
        new DownloadImageTask(image).execute(rowView.getResources().getString(R.string.storageserver) + "/" + imagesurls[position]);
        return rowView;
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap>
    {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage)
        {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls)
        {
            String urldisplay = urls[0];
            Bitmap myImage = null;
            try
            {
                InputStream in = new URL(urldisplay).openStream();
                myImage = BitmapFactory.decodeStream(in);
            }
            catch (Exception e) {}
            return myImage;
        }

        protected void onPostExecute(Bitmap result)
        {
            bmImage.setImageBitmap(result);
        }
    }
}
