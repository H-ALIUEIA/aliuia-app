package io.github.h_aliueia;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DefaultDataSource;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.datasource.FileDataSource;
import androidx.media3.datasource.cache.CacheDataSink;
import androidx.media3.datasource.cache.CacheDataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.hls.HlsMediaSource;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.ui.AspectRatioFrameLayout;
import androidx.media3.ui.PlayerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.github.h_aliueia.utils.cache;


public class longvideoactivity extends AppCompatActivity
{
    public ExoPlayer exoPlayer;
    public PlayerView longvideoview;
    @SuppressLint("UnsafeOptInUsageError")
    private CacheDataSource.Factory cacheDataSourceFactory;
    public int bookglo;
    public int partglo;
    public int nexbookglo;
    public int nexpartglo;
    public int[][] lessons = {{1,1},{1,2},{1,3},{1,4},{1,5},{1,6},{2,1},{2,2},{2,3},{2,4},{2,5},{2,6},{2,7},{2,8},{2,9},{2,10},{2,11},{2,12},{2,13},{2,14},{2,15},{2,16},{3,1},{3,2},{3,3},{3,4},{3,5},{3,6},{3,7},{4,1},{5,1},{5,2},{5,3},{5,4},{6,1},{6,2},{6,3},{6,4},{6,5},{7,1},{7,2},{7,3},{7,4},{7,5},{7,6}};

    public int currentlesson = 0;
    public Boolean mode = true;

    @UnstableApi
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_longvideoactivity);
        cache();
        String link = "";
        try
        {
            link = getIntent().getExtras().getString("link");
            mode = getIntent().getExtras().getBoolean("mode", true);
            if(mode)
            {
                bookglo = getIntent().getExtras().getInt("book");
                partglo = getIntent().getExtras().getInt("part");
                for (int i = 0; i < lessons.length; i++)
                {
                    if (lessons[i][0] == bookglo)
                    {
                        if (lessons[i][1] == partglo)
                        {
                            nexbookglo = lessons[i + 1][0];
                            nexpartglo = lessons[i + 1][1];
                            currentlesson = i;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {

        }
        longvideoview = (PlayerView) findViewById(R.id.longvideoview);
        longvideoview.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        exoPlayer = new ExoPlayer.Builder(this).build();
        longvideoview.setPlayer(exoPlayer);
        Uri uri = Uri.parse(link);
        MediaItem mediaItem = MediaItem.fromUri(uri);
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.localstorage), Context.MODE_PRIVATE);
        if(mode)
        {
            if(!(offlinegetter.offlinechecker(longvideoactivity.this,3)))
            {
                MediaSource mediaSource = new HlsMediaSource.Factory(cacheDataSourceFactory).createMediaSource(mediaItem);
                exoPlayer.setMediaSource(mediaSource);
            } else
            {
                exoPlayer.setMediaItem(mediaItem);
            }
        }
        else
        {
            if(!(offlinegetter.offlinechecker(longvideoactivity.this,4)))
            {
                MediaSource mediaSource = new HlsMediaSource.Factory(cacheDataSourceFactory).createMediaSource(mediaItem);
                exoPlayer.setMediaSource(mediaSource);
            } else
            {
                exoPlayer.setMediaItem(mediaItem);
            }
        }
        exoPlayer.prepare();
        exoPlayer.setPlayWhenReady(true);
        exoPlayer.addListener(new Player.Listener()
        {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState)
            {
                Player.Listener.super.onPlayerStateChanged(playWhenReady, playbackState);
                if(playbackState == ExoPlayer.STATE_ENDED)
                {
                    if (mode)
                    {
                        SharedPreferences sharedPref = longvideoactivity.this.getSharedPreferences(getString(R.string.localstorage), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        if (sharedPref.getInt("prophecylevel", 0) < currentlesson + 1)
                        {
                            editor.putInt("prophecylevel", currentlesson + 1);
                            editor.apply();
                        }
                        if (currentlesson == 1)
                        {
                            editor.putBoolean("secondprophecylevel", true);
                            editor.apply();
                            longvideoactivity.videoparams videoparamsvar = new longvideoactivity.videoparams(getResources().getString(R.string.server) + "/api/prophecylessons/" + String.valueOf(nexbookglo), nexpartglo, nexbookglo);
                            new longvideoactivity.startvideojson().execute(videoparamsvar);
                        } else if (currentlesson == 2)
                        {
                            if (sharedPref.getBoolean("secondprophecylevel", false))
                            {
                                longvideoactivity.videoparams videoparamsvar = new longvideoactivity.videoparams(getResources().getString(R.string.server) + "/api/prophecylessons/" + String.valueOf(nexbookglo), nexpartglo, nexbookglo);
                                new longvideoactivity.startvideojson().execute(videoparamsvar);
                                return;
                            } else
                            {
                                longvideoactivity.videoparams videoparamsvar = new longvideoactivity.videoparams(getResources().getString(R.string.server) + "/api/prophecylessons/" + String.valueOf(1), 2, 1);
                                new longvideoactivity.startvideojson().execute(videoparamsvar);
                                return;
                            }
                        } else if (currentlesson == 11)
                        {
                            editor.putBoolean("twelveprophecylevel", true);
                            editor.apply();
                            longvideoactivity.videoparams videoparamsvar = new longvideoactivity.videoparams(getResources().getString(R.string.server) + "/api/prophecylessons/" + String.valueOf(nexbookglo), nexpartglo, nexbookglo);
                            new longvideoactivity.startvideojson().execute(videoparamsvar);
                        } else if (currentlesson == 12)
                        {
                            if (sharedPref.getBoolean("twelveprophecylevel", false))
                            {
                                longvideoactivity.videoparams videoparamsvar = new longvideoactivity.videoparams(getResources().getString(R.string.server) + "/api/prophecylessons/" + String.valueOf(nexbookglo), nexpartglo, nexbookglo);
                                new longvideoactivity.startvideojson().execute(videoparamsvar);
                                return;
                            } else
                            {
                                longvideoactivity.videoparams videoparamsvar = new longvideoactivity.videoparams(getResources().getString(R.string.server) + "/api/prophecylessons/" + String.valueOf(2), 6, 2);
                                new longvideoactivity.startvideojson().execute(videoparamsvar);
                                return;
                            }
                        } else if (currentlesson == 13)
                        {
                            editor.putBoolean("fourteenprophecylevel", true);
                            editor.apply();
                            longvideoactivity.videoparams videoparamsvar = new longvideoactivity.videoparams(getResources().getString(R.string.server) + "/api/prophecylessons/" + String.valueOf(nexbookglo), nexpartglo, nexbookglo);
                            new longvideoactivity.startvideojson().execute(videoparamsvar);
                        } else if (currentlesson == 14)
                        {
                            if (sharedPref.getBoolean("fourteenprophecylevel", false))
                            {
                                longvideoactivity.videoparams videoparamsvar = new longvideoactivity.videoparams(getResources().getString(R.string.server) + "/api/prophecylessons/" + String.valueOf(nexbookglo), nexpartglo, nexbookglo);
                                new longvideoactivity.startvideojson().execute(videoparamsvar);
                                return;
                            } else
                            {
                                longvideoactivity.videoparams videoparamsvar = new longvideoactivity.videoparams(getResources().getString(R.string.server) + "/api/prophecylessons/" + String.valueOf(2), 8, 2);
                                new longvideoactivity.startvideojson().execute(videoparamsvar);
                                return;
                            }
                        } else
                        {
                            longvideoactivity.videoparams videoparamsvar = new longvideoactivity.videoparams(getResources().getString(R.string.server) + "/api/prophecylessons/" + String.valueOf(nexbookglo), nexpartglo, nexbookglo);
                            new longvideoactivity.startvideojson().execute(videoparamsvar);
                        }
                    }
                }
            }
        });
    }

    @UnstableApi
    public void cache()
    {
        try
        {
            CacheDataSink.Factory cacheSink = new CacheDataSink.Factory().setCache(cache.initCache(longvideoactivity.this));
            DefaultDataSource.Factory upstreamFactory = new DefaultDataSource.Factory(longvideoactivity.this, new DefaultHttpDataSource.Factory());
            FileDataSource.Factory downStreamFactory = new FileDataSource.Factory();
            cacheDataSourceFactory = new CacheDataSource.Factory()
                .setCache(cache.initCache(longvideoactivity.this))
                .setCacheWriteDataSinkFactory(cacheSink)
                .setCacheReadDataSourceFactory(downStreamFactory)
                .setUpstreamDataSourceFactory(upstreamFactory)
                .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);
        }
        catch (IllegalStateException e)
        {

        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        exoPlayer.play();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        exoPlayer.stop();
    }

    private static class videoparams {
        String url;
        int part;
        int book;

        videoparams(String url, int part, int book)
        {
            this.url = url;
            this.part = part;
            this.book = book;
        }
    }

    public class startvideojson extends AsyncTask<longvideoactivity.videoparams, String, String>
    {
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        protected String doInBackground(longvideoactivity.videoparams... params)
        {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try
            {
                SharedPreferences sharedPref = getSharedPreferences(getString(R.string.localstorage), Context.MODE_PRIVATE);
                if(!(offlinegetter.offlinechecker(longvideoactivity.this,3)))
                {
                    URL url = new URL(params[0].url);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("accept", "application/json");
                    connection.connect();
                    InputStream stream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuffer buffer = new StringBuffer();
                    String line = "";
                    while ((line = reader.readLine()) != null)
                    {
                        buffer.append(line + "\n");
                    }
                    return String.valueOf(params[0].book)+"/"+String.valueOf(params[0].part)+"/"+buffer.toString();
                }
                else
                {
                    return String.valueOf(params[0].book)+"/"+String.valueOf(params[0].part);
                }
            }
            catch (MalformedURLException url){}
            catch (IOException e){}
            finally
            {
                if(connection != null)
                {
                    connection.disconnect();
                }
                try
                {
                    if(reader != null)
                    {
                        reader.close();
                    }
                }
                catch (IOException e){}
            }
            return null;
        }

        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.localstorage), Context.MODE_PRIVATE);
            if(!(offlinegetter.offlinechecker(longvideoactivity.this,3)))
            {
                int firstslash = result.indexOf("/");
                int secondslash = result.substring(firstslash + 1, result.length()).indexOf("/") + firstslash + 1;
                int book = Integer.parseInt(result.substring(0, firstslash));
                int part = Integer.parseInt(result.substring(firstslash + 1, secondslash));
                String results = result.substring(secondslash + 1, result.length());
                sorter(results, part, book);
            }
            else
            {
                int firstslash = result.indexOf("/");
                int book = Integer.parseInt(result.substring(0, firstslash));
                int part = Integer.parseInt(result.substring(firstslash + 1, result.length()));
                sorter("",part,book);
            }
        }

        public void sorter(String jso, int part, int book)
        {
            try
            {
                SharedPreferences sharedPref = getSharedPreferences(getString(R.string.localstorage), Context.MODE_PRIVATE);
                if(!(offlinegetter.offlinechecker(longvideoactivity.this,3)))
                {
                    JSONArray obj = new JSONArray(jso);
                    for (int i = 0; i < obj.length(); i++)
                    {
                        if (i == part - 1)
                        {
                            JSONObject obj2 = (JSONObject) obj.get(i);
                            String videourl = obj2.getString("videourl");
                            Intent myIntent = new Intent(longvideoactivity.this, longvideoactivity.class);
                            myIntent.putExtra("link", videourl);
                            myIntent.putExtra("part", part);
                            myIntent.putExtra("book", book);
                            startActivity(myIntent);
                            finish();
                        }
                    }
                }
                else
                {
                    Intent myIntent = new Intent(longvideoactivity.this, longvideoactivity.class);
                    myIntent.putExtra("link", getApplicationInfo().dataDir+"/files/prophecy/day"+book+"/part"+part+".mp4");
                    myIntent.putExtra("part", part);
                    myIntent.putExtra("book", book);
                    startActivity(myIntent);
                    finish();
                }
            }
            catch (Exception e){}
        }
    }

}
