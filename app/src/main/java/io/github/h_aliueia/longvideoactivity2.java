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


public class longvideoactivity2 extends AppCompatActivity
{
    public ExoPlayer exoPlayer;
    public PlayerView longvideoview;
    @SuppressLint("UnsafeOptInUsageError")
    private CacheDataSource.Factory cacheDataSourceFactory;
    public int bookglo;
    public int partglo;
    public int nexbookglo;
    public int nexpartglo;

    @UnstableApi
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_longvideoactivity2);
        cache();
        String link = "";
        try
        {
            link = getIntent().getExtras().getString("link");
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
        if(!(offlinegetter.offlinechecker(longvideoactivity2.this,4)))
        {
            MediaSource mediaSource = new HlsMediaSource.Factory(cacheDataSourceFactory).createMediaSource(mediaItem);
            exoPlayer.setMediaSource(mediaSource);
        } else
        {
            exoPlayer.setMediaItem(mediaItem);
        }
        exoPlayer.prepare();
        exoPlayer.setPlayWhenReady(true);
    }

    @UnstableApi
    public void cache()
    {
        try
        {
            CacheDataSink.Factory cacheSink = new CacheDataSink.Factory().setCache(cache.initCache(longvideoactivity2.this));
            DefaultDataSource.Factory upstreamFactory = new DefaultDataSource.Factory(longvideoactivity2.this, new DefaultHttpDataSource.Factory());
            FileDataSource.Factory downStreamFactory = new FileDataSource.Factory();
            cacheDataSourceFactory = new CacheDataSource.Factory()
                .setCache(cache.initCache(longvideoactivity2.this))
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
        videoparams(String url)
        {
            this.url = url;
        }
    }

}
