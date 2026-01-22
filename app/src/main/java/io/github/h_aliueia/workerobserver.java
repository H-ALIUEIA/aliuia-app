package io.github.h_aliueia;

import android.app.Application;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.orhanobut.hawk.Hawk;

public class workerobserver extends Application implements LifecycleObserver
{


    @Override
    public void onCreate()
    {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onAppBackgrounded()
    {
        Hawk.put("active", false);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onAppForegrounded()
    {
        Hawk.put("active", true);
    }

}
