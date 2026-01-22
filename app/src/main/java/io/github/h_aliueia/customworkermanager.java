package io.github.h_aliueia;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.CoroutineWorker;
import androidx.work.WorkerParameters;

import com.orhanobut.hawk.Hawk;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

import kotlin.coroutines.Continuation;

public class customworkermanager extends CoroutineWorker
{

    public customworkermanager(@NonNull Context appContext, @NonNull WorkerParameters params)
    {
        super(appContext, params);
    }

    @Nullable
    @Override
    public Object doWork(@NonNull Continuation<? super Result> continuation)
    {
        if(Calendar.getInstance().getTime().getHours() == 21)
        {
            try
            {
                Boolean active = Hawk.get("active",false);
                if(!(active))
                {
                    JSONArray offlinestring = offlinegetter.notificationgetter(getApplicationContext());
                    if (offlinestring != null)
                    {
                        JSONObject qwe = offlinestring.getJSONObject((int) (Math.random() * offlinestring.length()));
                        if(qwe.getInt("type") == 1)
                        {
                            notificationhelper.sendNotification(getApplicationContext(), qwe.getString("title"), qwe.getString("description"), qwe.getString("quotesource"),2);
                        }
                        else if (qwe.getInt("type") == 2)
                        {
                            notificationhelper.sendNotification(getApplicationContext(), qwe.getString("title"), qwe.getString("description"), qwe.getString("quotesource"),4);
                        }
                    }
                }
            }
            catch (Exception e){}
        }
        return Result.success();
    }

}
