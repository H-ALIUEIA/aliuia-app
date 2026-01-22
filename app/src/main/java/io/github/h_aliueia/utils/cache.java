package io.github.h_aliueia.utils;

import android.app.Application;
import android.content.Context;

import androidx.media3.common.util.UnstableApi;
import androidx.media3.database.StandaloneDatabaseProvider;
import androidx.media3.datasource.cache.NoOpCacheEvictor;
import androidx.media3.datasource.cache.SimpleCache;

import java.io.File;

@UnstableApi
public class cache extends Application
{
    public static SimpleCache simpleCache;

    @UnstableApi
    public static SimpleCache initCache(Context missi)
    {
        try
        {
            String DOWNLOAD_CONTENT_DIRECTORY = "downloads";
            File downloadContentDirectory = new File(missi.getCacheDir(), DOWNLOAD_CONTENT_DIRECTORY);
            simpleCache = new SimpleCache(downloadContentDirectory, new NoOpCacheEvictor(), new StandaloneDatabaseProvider(missi));
            return simpleCache;
        }
        catch (IllegalStateException e)
        {
            return  simpleCache;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
