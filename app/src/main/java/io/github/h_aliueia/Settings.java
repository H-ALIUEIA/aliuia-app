package io.github.h_aliueia;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tashila.pleasewait.PleaseWaitDialog;
import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2.FetchListener;
import com.tonyodev.fetch2.NetworkType;
import com.tonyodev.fetch2.Priority;
import com.tonyodev.fetch2.Request;
import com.tonyodev.fetch2core.DownloadBlock;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.progress.ProgressMonitor;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import kotlin.io.FilesKt;

public class Settings extends AppCompatActivity
{

    public int progress = 0;
    public PleaseWaitDialog dialoger;
    public Button online;
    public Button offline;
    private Fetch fetch;

    public PopupMenu databasepopup;
    public PopupMenu shortspopup;
    public PopupMenu lessonspopup;
    public PopupMenu prophecypopup;
    public PopupMenu unlearnpopup;

    public int getStatusBarHeight()
    {
        int result = 0;
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
        if (resourceId > 0)
        {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int getNavigationBarHeight()
    {
        int result = 0;
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setPadding(0,getStatusBarHeight(), 0,getNavigationBarHeight());
        ViewCompat.setOnApplyWindowInsetsListener(getWindow().getDecorView(), (v, insets) -> {
            return ViewCompat.onApplyWindowInsets(v, WindowInsetsCompat.CONSUMED);
        });
        setContentView(R.layout.activity_settings);
        EdgeToEdge.enable(this);
        FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(this).setDownloadConcurrentLimit(1).build();
        fetch = Fetch.Impl.getInstance(fetchConfiguration);
        checker();
        LinearLayout databasebutton = (LinearLayout) findViewById(R.id.databasebutton);
        databasebutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                databasepopup.show();
            }
        });
        LinearLayout shortsbutton = (LinearLayout) findViewById(R.id.shortsbutton);
        shortsbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                shortspopup.show();
            }
        });
        LinearLayout lessonsbutton = (LinearLayout) findViewById(R.id.lessonsbutton);
        lessonsbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                lessonspopup.show();
            }
        });
        LinearLayout prophecybutton = (LinearLayout) findViewById(R.id.prophecybutton);
        prophecybutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                prophecypopup.show();
            }
        });
        LinearLayout unlearnbutton = (LinearLayout) findViewById(R.id.unlearnbutton);
        unlearnbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                unlearnpopup.show();
            }
        });
    }

    public void checker()
    {
        if(offlinegetter.offlinechecker(Settings.this,0))
        {
            databasepopup = null;
            TextView databaseellipsi = (TextView) findViewById(R.id.databaseellipsi);
            databasepopup = new PopupMenu(Settings.this, databaseellipsi);
            databasepopup.getMenuInflater().inflate(R.menu.offline_listitem_menu, databasepopup.getMenu());
            databasepopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
            {
                public boolean onMenuItemClick(MenuItem item)
                {
                    int id = item.getItemId();
                    if(id == R.id.update)
                    {
                        File file = new File(getApplicationInfo().dataDir+"/files/offlinedb.json");
                        file.delete();
                        pre_download_bd();
                    }
                    else if(id == R.id.delete)
                    {
                        File file = new File(getApplicationInfo().dataDir+"/files/offlinedb.json");
                        file.delete();
                        databasepopup = null;
                        checker();
                    }
                    return true;
                }
            });
        }
        else
        {
            databasepopup = null;
            TextView databaseellipsi = (TextView) findViewById(R.id.databaseellipsi);
            databasepopup = new PopupMenu(Settings.this, databaseellipsi);
            databasepopup.getMenuInflater().inflate(R.menu.online_listitem_menu, databasepopup.getMenu());
            databasepopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
            {
                public boolean onMenuItemClick(MenuItem item)
                {
                    pre_download_bd();
                    return true;
                }
            });
        }
        if(offlinegetter.offlinechecker(Settings.this,1))
        {
            shortspopup = null;
            TextView shortsellipsi = (TextView) findViewById(R.id.shortsellipsi);
            shortspopup = new PopupMenu(Settings.this, shortsellipsi);
            shortspopup.getMenuInflater().inflate(R.menu.offline_listitem_menu, shortspopup.getMenu());
            shortspopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
            {
                public boolean onMenuItemClick(MenuItem item)
                {
                    int id = item.getItemId();
                    if(id == R.id.update)
                    {
                        File file = new File(getApplicationInfo().dataDir+"/files/shorts.zip");
                        if(file.exists())
                        {
                            file.delete();
                        }
                        pre_download_shorts();
                    }
                    else if(id == R.id.delete)
                    {
                        File file = new File(getApplicationInfo().dataDir+"/files/shorts.zip");
                        if(file.exists())
                        {
                            file.delete();
                        }
                        file = new File(getApplicationInfo().dataDir+"/files/shorts/");
                        if(file.exists())
                        {
                            FilesKt.deleteRecursively(new File(getApplicationInfo().dataDir + "/files/shorts"));
                        }
                        databasepopup = null;
                        checker();
                    }
                    return true;
                }
            });
        }
        else
        {
            shortspopup = null;
            TextView shortsellipsi = (TextView) findViewById(R.id.shortsellipsi);
            shortspopup = new PopupMenu(Settings.this, shortsellipsi);
            shortspopup.getMenuInflater().inflate(R.menu.online_listitem_menu, shortspopup.getMenu());
            shortspopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
            {
                public boolean onMenuItemClick(MenuItem item)
                {
                    pre_download_shorts();
                    return true;
                }
            });
        }
        if(offlinegetter.offlinechecker(Settings.this,2))
        {
            lessonspopup = null;
            TextView lessonsellipsi = (TextView) findViewById(R.id.lessonsellipsi);
            lessonspopup = new PopupMenu(Settings.this, lessonsellipsi);
            lessonspopup.getMenuInflater().inflate(R.menu.offline_listitem_menu, lessonspopup.getMenu());
            lessonspopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
            {
                public boolean onMenuItemClick(MenuItem item)
                {
                    int id = item.getItemId();
                    if(id == R.id.update)
                    {
                        File file = new File(getApplicationInfo().dataDir+"/files/lessons.zip");
                        if(file.exists())
                        {
                            file.delete();
                        }
                        pre_download_lessons();
                    }
                    else if(id == R.id.delete)
                    {
                        File file = new File(getApplicationInfo().dataDir+"/files/lessons.zip");
                        if(file.exists())
                        {
                            file.delete();
                        }
                        file = new File(getApplicationInfo().dataDir+"/files/lessons/");
                        if(file.exists())
                        {
                            FilesKt.deleteRecursively(new File(getApplicationInfo().dataDir + "/files/lessons"));
                        }
                        databasepopup = null;
                        checker();
                    }
                    return true;
                }
            });
        }
        else
        {
            lessonspopup = null;
            TextView lessonsellipsi = (TextView) findViewById(R.id.lessonsellipsi);
            lessonspopup = new PopupMenu(Settings.this, lessonsellipsi);
            lessonspopup.getMenuInflater().inflate(R.menu.online_listitem_menu, lessonspopup.getMenu());
            lessonspopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
            {
                public boolean onMenuItemClick(MenuItem item)
                {
                    pre_download_lessons();
                    return true;
                }
            });
        }
        if(offlinegetter.offlinechecker(Settings.this,3))
        {
            prophecypopup = null;
            TextView prophecyellipsi = (TextView) findViewById(R.id.prophecyellipsi);
            prophecypopup = new PopupMenu(Settings.this, prophecyellipsi);
            prophecypopup.getMenuInflater().inflate(R.menu.offline_listitem_menu, prophecypopup.getMenu());
            prophecypopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
            {
                public boolean onMenuItemClick(MenuItem item)
                {
                    int id = item.getItemId();
                    if(id == R.id.update)
                    {
                        File file = new File(getApplicationInfo().dataDir+"/files/prophecy.zip");
                        if(file.exists())
                        {
                            file.delete();
                        }
                        pre_download_prophecyvideos();
                    }
                    else if(id == R.id.delete)
                    {
                        File file = new File(getApplicationInfo().dataDir+"/files/prophecy.zip");
                        if(file.exists())
                        {
                            file.delete();
                        }
                        file = new File(getApplicationInfo().dataDir+"/files/prophecy/");
                        if(file.exists())
                        {
                            FilesKt.deleteRecursively(new File(getApplicationInfo().dataDir + "/files/prophecy"));
                        }
                        databasepopup = null;
                        checker();
                    }
                    return true;
                }
            });
        }
        else
        {
            prophecypopup = null;
            TextView prophecyellipsi = (TextView) findViewById(R.id.prophecyellipsi);
            prophecypopup = new PopupMenu(Settings.this, prophecyellipsi);
            prophecypopup.getMenuInflater().inflate(R.menu.online_listitem_menu, prophecypopup.getMenu());
            prophecypopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
            {
                public boolean onMenuItemClick(MenuItem item)
                {
                    pre_download_prophecyvideos();
                    return true;
                }
            });
        }
        if(offlinegetter.offlinechecker(Settings.this,4))
        {
            unlearnpopup = null;
            TextView unlearnellipsi = (TextView) findViewById(R.id.unlearnellipsi);
            unlearnpopup = new PopupMenu(Settings.this, unlearnellipsi);
            unlearnpopup.getMenuInflater().inflate(R.menu.offline_listitem_menu, unlearnpopup.getMenu());
            unlearnpopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
            {
                public boolean onMenuItemClick(MenuItem item)
                {
                    int id = item.getItemId();
                    if(id == R.id.update)
                    {
                        File file = new File(getApplicationInfo().dataDir+"/files/unlearn.zip");
                        if(file.exists())
                        {
                            file.delete();
                        }
                        pre_download_unlearnvideos();
                    }
                    else if(id == R.id.delete)
                    {
                        File file = new File(getApplicationInfo().dataDir+"/files/unlearn.zip");
                        if(file.exists())
                        {
                            file.delete();
                        }
                        file = new File(getApplicationInfo().dataDir+"/files/unlearn/");
                        if(file.exists())
                        {
                            FilesKt.deleteRecursively(new File(getApplicationInfo().dataDir + "/files/unlearn"));
                        }
                        databasepopup = null;
                        checker();
                    }
                    return true;
                }
            });
        }
        else
        {
            unlearnpopup = null;
            TextView unlearnellipsi = (TextView) findViewById(R.id.unlearnellipsi);
            unlearnpopup = new PopupMenu(Settings.this, unlearnellipsi);
            unlearnpopup.getMenuInflater().inflate(R.menu.online_listitem_menu, unlearnpopup.getMenu());
            unlearnpopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
            {
                public boolean onMenuItemClick(MenuItem item)
                {
                    pre_download_unlearnvideos();
                    return true;
                }
            });
        }
    }

    public void pre_download_bd()
    {
        MyTaskParams myTaskParams = new MyTaskParams(getResources().getString(R.string.server)+"/api/download/db","offlinedb.json","");
        new json().execute(myTaskParams);
    }

    public void pre_download_lessons()
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                switch (which)
                {
                    case DialogInterface.BUTTON_POSITIVE:
                        if(offlinegetter.offlinechecker(Settings.this,0))
                        {
                            download_lessons();
                            break;
                        }
                        else
                        {
                            MyTaskParams myTaskParams = new MyTaskParams(getResources().getString(R.string.server) + "/api/download/db", "offlinedb.json", "donot2");
                            new json().execute(myTaskParams);
                            break;
                        }

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        builder.setMessage("Θέλετε να κατεβάσετε τα μαθήματα?").setPositiveButton("Ναι", dialogClickListener).setNegativeButton("Όχι", dialogClickListener).show();
    }

    public void download_lessons()
    {
        final Request request = new Request(getResources().getString(R.string.storageserver)+"/lessons.zip", getApplicationInfo().dataDir+"/files/lessons.zip");
        request.setPriority(Priority.HIGH);
        request.setNetworkType(NetworkType.ALL);
        FetchListener fetchListener = new FetchListener() {
            @Override
            public void onStarted(@NonNull Download download, @NonNull List<? extends DownloadBlock> list, int i)
            {
                dialoger = new PleaseWaitDialog(Settings.this);
                dialoger.setProgressStyle(PleaseWaitDialog.ProgressStyle.BOTH);
                dialoger.setIndeterminate(PleaseWaitDialog.ProgressStyle.LINEAR,false);
                dialoger.setIndeterminate(PleaseWaitDialog.ProgressStyle.CIRCULAR,true);
                dialoger.setCancelable(false);
                dialoger.show();
            }

            @Override
            public void onDownloadBlockUpdated(@NonNull Download download, @NonNull DownloadBlock downloadBlock, int i)
            {

            }

            @Override
            public void onError(@NonNull Download download, @NonNull com.tonyodev.fetch2.Error error, @Nullable Throwable throwable)
            {

            }

            @Override
            public void onWaitingNetwork(@NonNull Download download)
            {

            }

            @Override
            public void onAdded(@NonNull Download download)
            {

            }

            @Override
            public void onQueued(@NotNull Download download, boolean waitingOnNetwork) {
                if (request.getId() == download.getId()) {

                }
            }

            @Override
            public void onCompleted(@NotNull Download download)
            {
                try
                {
                    MyTaskParams myTaskParams = new MyTaskParams("lessons.zip","","lessons");
                    new json3().execute(myTaskParams);
                }
                catch (Exception e){}
            }

            @Override
            public void onProgress(@NotNull Download download, long etaInMilliSeconds, long downloadedBytesPerSecond) {
                if (request.getId() == download.getId())
                {
                    dialoger.setProgress((int)download.getProgress());
                }
            }

            @Override
            public void onPaused(@NotNull Download download) {

            }

            @Override
            public void onResumed(@NotNull Download download) {

            }

            @Override
            public void onCancelled(@NotNull Download download) {

            }

            @Override
            public void onRemoved(@NotNull Download download) {

            }

            @Override
            public void onDeleted(@NotNull Download download) {

            }
        };
        fetch.addListener(fetchListener);
        fetch.enqueue(request, updatedRequest -> {
            //Request was successfully enqueued for download.
        }, error -> {
            //An error occurred enqueuing the request.
        });
    }

    public void pre_download_shorts()
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                switch (which)
                {
                    case DialogInterface.BUTTON_POSITIVE:
                        if(offlinegetter.offlinechecker(Settings.this,0))
                        {
                            download_shorts();
                            break;
                        }
                        else
                        {
                            MyTaskParams myTaskParams = new MyTaskParams(getResources().getString(R.string.server) + "/api/download/db", "offlinedb.json", "donot");
                            new json().execute(myTaskParams);
                            break;
                        }

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        builder.setMessage("Θέλετε να κατεβάσετε τα μικρά βίντεο?").setPositiveButton("Ναι", dialogClickListener).setNegativeButton("Όχι", dialogClickListener).show();
    }

    public void download_shorts()
    {
        final Request request = new Request(getResources().getString(R.string.storageserver)+"/shorts.zip", getApplicationInfo().dataDir+"/files/shorts.zip");
        request.setPriority(Priority.HIGH);
        request.setNetworkType(NetworkType.ALL);
        FetchListener fetchListener = new FetchListener() {
            @Override
            public void onStarted(@NonNull Download download, @NonNull List<? extends DownloadBlock> list, int i)
            {
                dialoger = new PleaseWaitDialog(Settings.this);
                dialoger.setProgressStyle(PleaseWaitDialog.ProgressStyle.BOTH);
                dialoger.setIndeterminate(PleaseWaitDialog.ProgressStyle.LINEAR,false);
                dialoger.setIndeterminate(PleaseWaitDialog.ProgressStyle.CIRCULAR,true);
                dialoger.setCancelable(false);
                dialoger.show();
            }

            @Override
            public void onDownloadBlockUpdated(@NonNull Download download, @NonNull DownloadBlock downloadBlock, int i)
            {

            }

            @Override
            public void onError(@NonNull Download download, @NonNull com.tonyodev.fetch2.Error error, @Nullable Throwable throwable)
            {

            }

            @Override
            public void onWaitingNetwork(@NonNull Download download)
            {

            }

            @Override
            public void onAdded(@NonNull Download download)
            {

            }

            @Override
            public void onQueued(@NotNull Download download, boolean waitingOnNetwork) {
                if (request.getId() == download.getId()) {

                }
            }

            @Override
            public void onCompleted(@NotNull Download download)
            {
                try
                {
                    MyTaskParams myTaskParams = new MyTaskParams("shorts.zip","","shorts");
                    new json3().execute(myTaskParams);
                }
                catch (Exception e){}
            }

            @Override
            public void onProgress(@NotNull Download download, long etaInMilliSeconds, long downloadedBytesPerSecond) {
                if (request.getId() == download.getId())
                {
                    dialoger.setProgress((int)download.getProgress());
                }
            }

            @Override
            public void onPaused(@NotNull Download download) {

            }

            @Override
            public void onResumed(@NotNull Download download) {

            }

            @Override
            public void onCancelled(@NotNull Download download) {

            }

            @Override
            public void onRemoved(@NotNull Download download) {

            }

            @Override
            public void onDeleted(@NotNull Download download) {

            }
        };
        fetch.addListener(fetchListener);
        fetch.enqueue(request, updatedRequest -> {
            //Request was successfully enqueued for download.
        }, error -> {
            //An error occurred enqueuing the request.
        });
    }

    private static class MyTaskParams
    {
        String url;
        String filename;
        String extra;

        MyTaskParams(String url, String filename, String extra)
        {
            this.url = url;
            this.filename = filename;
            this.extra = extra;
        }
    }

    public void download_unlearnvideos()
    {
        final Request request = new Request(getResources().getString(R.string.storageserver)+"/unlearn.zip", getApplicationInfo().dataDir+"/files/unlearn.zip");
        request.setPriority(Priority.HIGH);
        request.setNetworkType(NetworkType.ALL);
        FetchListener fetchListener = new FetchListener() {
            @Override
            public void onStarted(@NonNull Download download, @NonNull List<? extends DownloadBlock> list, int i)
            {
                dialoger = new PleaseWaitDialog(Settings.this);
                dialoger.setProgressStyle(PleaseWaitDialog.ProgressStyle.BOTH);
                dialoger.setIndeterminate(PleaseWaitDialog.ProgressStyle.LINEAR,false);
                dialoger.setIndeterminate(PleaseWaitDialog.ProgressStyle.CIRCULAR,true);
                dialoger.setCancelable(false);
                dialoger.show();
            }

            @Override
            public void onDownloadBlockUpdated(@NonNull Download download, @NonNull DownloadBlock downloadBlock, int i)
            {

            }

            @Override
            public void onError(@NonNull Download download, @NonNull com.tonyodev.fetch2.Error error, @Nullable Throwable throwable)
            {

            }

            @Override
            public void onWaitingNetwork(@NonNull Download download)
            {

            }

            @Override
            public void onAdded(@NonNull Download download)
            {

            }

            @Override
            public void onQueued(@NotNull Download download, boolean waitingOnNetwork) {
                if (request.getId() == download.getId()) {

                }
            }

            @Override
            public void onCompleted(@NotNull Download download)
            {
                try
                {
                    MyTaskParams myTaskParams = new MyTaskParams("unlearn.zip","","unlearn");
                    new json3().execute(myTaskParams);
                }
                catch (Exception e){}
            }

            @Override
            public void onProgress(@NotNull Download download, long etaInMilliSeconds, long downloadedBytesPerSecond) {
                if (request.getId() == download.getId())
                {
                    dialoger.setProgress((int)download.getProgress());
                }
            }

            @Override
            public void onPaused(@NotNull Download download)
            {

            }

            @Override
            public void onResumed(@NotNull Download download) {

            }

            @Override
            public void onCancelled(@NotNull Download download)
            {

            }

            @Override
            public void onRemoved(@NotNull Download download)
            {

            }

            @Override
            public void onDeleted(@NotNull Download download)
            {

            }
        };
        fetch.addListener(fetchListener);
        fetch.enqueue(request, updatedRequest -> {
            //Request was successfully enqueued for download.
        }, error -> {

        });
    }

    public void download_prophecyvideos()
    {
        final Request request = new Request(getResources().getString(R.string.storageserver)+"/prophecy.zip", getApplicationInfo().dataDir+"/files/prophecy.zip");
        request.setPriority(Priority.HIGH);
        request.setNetworkType(NetworkType.ALL);
        FetchListener fetchListener = new FetchListener() {
            @Override
            public void onStarted(@NonNull Download download, @NonNull List<? extends DownloadBlock> list, int i)
            {
                dialoger = new PleaseWaitDialog(Settings.this);
                dialoger.setProgressStyle(PleaseWaitDialog.ProgressStyle.BOTH);
                dialoger.setIndeterminate(PleaseWaitDialog.ProgressStyle.LINEAR,false);
                dialoger.setIndeterminate(PleaseWaitDialog.ProgressStyle.CIRCULAR,true);
                dialoger.setCancelable(false);
                dialoger.show();
            }

            @Override
            public void onDownloadBlockUpdated(@NonNull Download download, @NonNull DownloadBlock downloadBlock, int i)
            {

            }

            @Override
            public void onError(@NonNull Download download, @NonNull com.tonyodev.fetch2.Error error, @Nullable Throwable throwable)
            {

            }

            @Override
            public void onWaitingNetwork(@NonNull Download download)
            {

            }

            @Override
            public void onAdded(@NonNull Download download)
            {

            }

            @Override
            public void onQueued(@NotNull Download download, boolean waitingOnNetwork) {
                if (request.getId() == download.getId()) {

                }
            }

            @Override
            public void onCompleted(@NotNull Download download)
            {
                try
                {
                    MyTaskParams myTaskParams = new MyTaskParams("prophecy.zip","","prophecy");
                    new json3().execute(myTaskParams);
                }
                catch (Exception e){}
            }

            @Override
            public void onProgress(@NotNull Download download, long etaInMilliSeconds, long downloadedBytesPerSecond) {
                if (request.getId() == download.getId())
                {
                    dialoger.setProgress((int)download.getProgress());
                }
            }

            @Override
            public void onPaused(@NotNull Download download)
            {

            }

            @Override
            public void onResumed(@NotNull Download download) {

            }

            @Override
            public void onCancelled(@NotNull Download download)
            {

            }

            @Override
            public void onRemoved(@NotNull Download download)
            {

            }

            @Override
            public void onDeleted(@NotNull Download download)
            {

            }
        };
        fetch.addListener(fetchListener);
        fetch.enqueue(request, updatedRequest -> {
            //Request was successfully enqueued for download.
        }, error -> {

        });
    }

    public void pre_download_prophecyvideos()
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                switch (which)
                {
                    case DialogInterface.BUTTON_POSITIVE:
                        if(offlinegetter.offlinechecker(Settings.this,0))
                        {
                            download_prophecyvideos();
                            break;
                        }
                        else
                        {
                            MyTaskParams myTaskParams = new MyTaskParams(getResources().getString(R.string.server) + "/api/download/db", "offlinedb.json", "donot3");
                            new json().execute(myTaskParams);
                            break;
                        }

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        builder.setMessage("Θέλετε να κατεβάσετε τις προφητείες?").setPositiveButton("Ναι", dialogClickListener).setNegativeButton("Όχι", dialogClickListener).show();
    }

    public void pre_download_unlearnvideos()
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                switch (which)
                {
                    case DialogInterface.BUTTON_POSITIVE:
                        if(offlinegetter.offlinechecker(Settings.this,0))
                        {
                            download_unlearnvideos();
                            break;
                        }
                        else
                        {
                            MyTaskParams myTaskParams = new MyTaskParams(getResources().getString(R.string.server) + "/api/download/db", "offlinedb.json", "donot4");
                            new json().execute(myTaskParams);
                            break;
                        }

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        builder.setMessage("Θέλετε να κατεβάσετε τα ξέμαθε?").setPositiveButton("Ναι", dialogClickListener).setNegativeButton("Όχι", dialogClickListener).show();
    }

    public class json3 extends AsyncTask<MyTaskParams, String, String>
    {
        protected void onPreExecute()
        {
            dialoger.dismiss();
            dialoger = new PleaseWaitDialog(Settings.this);
            dialoger.setProgressStyle(PleaseWaitDialog.ProgressStyle.CIRCULAR);
            dialoger.setIndeterminate(true);
            dialoger.show();
            super.onPreExecute();
        }

        protected String doInBackground(MyTaskParams... params)
        {
            try
            {
                if(params[0].extra.equals("lessons"))
                {
                    File file = new File(getApplicationInfo().dataDir+"/files/lessons/");
                    if(file.exists())
                    {
                        FilesKt.deleteRecursively(file);
                    }
                }
                else if(params[0].extra.equals("shorts"))
                {
                    File file = new File(getApplicationInfo().dataDir+"/files/shorts/");
                    if(file.exists())
                    {
                        FilesKt.deleteRecursively(file);
                    }
                }
                else if(params[0].extra.equals("prophecy"))
                {
                    File file = new File(getApplicationInfo().dataDir+"/files/prophecy/");
                    if(file.exists())
                    {
                        FilesKt.deleteRecursively(file);
                    }
                }
                else if (params[0].extra.equals("unlearn"))
                {
                    File file = new File(getApplicationInfo().dataDir+"/files/unlearn/");
                    if(file.exists())
                    {
                        FilesKt.deleteRecursively(file);
                    }
                }
                ZipFile zipFile = new ZipFile(getApplicationInfo().dataDir+"/files/"+params[0].url);
                zipFile.setRunInThread(true);
                zipFile.extractAll(getApplicationInfo().dataDir+"/files");
                ProgressMonitor progressMonitor = zipFile.getProgressMonitor();
                while (progressMonitor.getState() == ProgressMonitor.State.BUSY)
                {

                }
                if (progressMonitor.getResult().equals(ProgressMonitor.Result.SUCCESS))
                {
                    return params[0].url;
                }
                else if (progressMonitor.getResult().equals(ProgressMonitor.Result.ERROR)){}
                else if (progressMonitor.getResult().equals(ProgressMonitor.Result.CANCELLED)){}
                //return buffer.toString();
            }
            catch (Exception e){}
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values)
        {
            //super.onProgressUpdate(values);
            dialoger.setProgress(Integer.parseInt(values[0]));
        }

        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            sorter(result);
        }

        public void sorter(String jso)
        {
            try
            {
                new File(getApplicationInfo().dataDir+"/files/"+jso).delete();
                dialoger.setDismissDelay(5000);
                dialoger.dismiss();
                checker();
            }
            catch (Exception e)
            {
            }
        }

    }

    public class json extends AsyncTask<MyTaskParams, String, String>
    {
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        protected String doInBackground(MyTaskParams... params)
        {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try
            {
                FileOutputStream out = openFileOutput(params[0].filename,MODE_PRIVATE);
                URL url = new URL(params[0].url);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("accept","application/json");
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                //StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null)
                {
                    String tempfw = line+"\n";
                    out.write(tempfw.getBytes());
                }
                out.close();
                if(params[0].extra.equals("donot"))
                {
                    download_shorts();
                    return "donot";
                }
                else if(params[0].extra.equals("donot2"))
                {
                    download_lessons();
                    return "donot2";
                }
                else if(params[0].extra.equals("donot3"))
                {
                    download_prophecyvideos();
                    return "donot3";
                }
                else if(params[0].extra.equals("donot4"))
                {
                    download_unlearnvideos();
                    return "donot4";
                }
                else
                {
                    return "";
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

        @Override
        protected void onProgressUpdate(String... values)
        {
            //super.onProgressUpdate(values);
            dialoger.setProgress(Integer.parseInt(values[0]));
        }

        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            sorter(result);
        }

        public void sorter(String jso)
        {
            if(jso.equals("donot") || jso.equals("donot2")|| jso.equals("donot3")|| jso.equals("donot4"))
            {

            }
            else
            {
                checker();
            }
        }
    }
}
