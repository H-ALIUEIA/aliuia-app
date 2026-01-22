package io.github.h_aliueia;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.media3.common.util.UnstableApi;
import androidx.work.BackoffPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.orhanobut.hawk.Hawk;
import io.github.h_aliueia.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
{
    private static final int RC_NOTIFICATION = 99;
    public ActivityMainBinding binding;
    public int shortscategoryint = 0;
    public int pressed = 0;

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

    @UnstableApi
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        getWindow().getDecorView().setPadding(0, getStatusBarHeight(), 0, getNavigationBarHeight());
        ViewCompat.setOnApplyWindowInsetsListener(getWindow().getDecorView(), (v, insets) -> {
            return ViewCompat.onApplyWindowInsets(v, WindowInsetsCompat.CONSUMED);
        });
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        replaceFramgment(new NewsFragment());
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        Hawk.init(this).build();
        getLifecycle().addObserver(new workerobserver());
        frgtoloadfun();
        try
        {
            shortscategoryint = getIntent().getExtras().getInt("shortscategory");
        }
        catch (Exception e){}
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.news)
            {
                replaceFramgment(new NewsFragment());
            }
            else if(item.getItemId() == R.id.quotes)
            {
                replaceFramgment(new QuotesFragment());
            }
            else if(item.getItemId() == R.id.shorts)
            {
                replaceFramgment(new ShortsFragment());
            }
            else if(item.getItemId() == R.id.lvideos)
            {
                replaceFramgment(new LVideoFragment());
            }
            return true;
        });
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        {
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS},RC_NOTIFICATION);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            notificationhelper.createChannel(this);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            new jsonnoti().execute(getResources().getString(R.string.server)+"/api/getnotifications");
            notificationhelper.createChannel(this);
            PeriodicWorkRequest.Builder workRequest = new PeriodicWorkRequest.Builder(customworkermanager.class, 1, TimeUnit.HOURS);
            WorkRequest workRequest1 = workRequest.setBackoffCriteria(BackoffPolicy.LINEAR, Duration.ofSeconds(15)).build();
            WorkManager workManager = WorkManager.getInstance(MainActivity.this);
            workManager.enqueue(workRequest1);
        }
        new json().execute(getResources().getString(R.string.server)+"/api/version");
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

    public class startvideojson extends AsyncTask<videoparams, String, String>
    {
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        protected String doInBackground(videoparams... params)
        {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try
            {
                SharedPreferences sharedPref = getSharedPreferences(getString(R.string.localstorage), Context.MODE_PRIVATE);
                if(!(offlinegetter.offlinechecker(MainActivity.this,3)))
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
            if(!(offlinegetter.offlinechecker(MainActivity.this,3)))
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
                if(!(offlinegetter.offlinechecker(MainActivity.this,3)))
                {
                    JSONArray obj = new JSONArray(jso);
                    for (int i = 0; i < obj.length(); i++)
                    {
                        if (i == part - 1)
                        {
                            JSONObject obj2 = (JSONObject) obj.get(i);
                            String videourl = obj2.getString("videourl");
                            Intent myIntent = new Intent(MainActivity.this, longvideoactivity.class);
                            myIntent.putExtra("link", videourl);
                            myIntent.putExtra("part", part);
                            myIntent.putExtra("book", book);
                            startActivity(myIntent);
                            pressed = 0;
                        }
                    }
                }
                else
                {
                    Intent myIntent = new Intent(MainActivity.this, longvideoactivity.class);
                    myIntent.putExtra("link", getApplicationInfo().dataDir+"/files/prophecy/day"+book+"/part"+part+".mp4");
                    myIntent.putExtra("part", part);
                    myIntent.putExtra("book", book);
                    startActivity(myIntent);
                    pressed = 0;
                }
            }
            catch (Exception e){}
        }
    }

    public class unlearnjson extends AsyncTask<String, String, String>
    {
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        protected String doInBackground(String... params)
        {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try
            {
                SharedPreferences sharedPref = getSharedPreferences(getString(R.string.localstorage), Context.MODE_PRIVATE);
                URL url = new URL(params[0]);
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
                return buffer.toString();
            }
            catch (Exception e)
            {
                if(offlinegetter.offlinechecker(MainActivity.this,0))
                {
                    return params[0];
                }
            }
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
            sorter(result);
        }

        public void sorter(String jso)
        {
            try
            {
                SharedPreferences sharedPref = getSharedPreferences(getString(R.string.localstorage), Context.MODE_PRIVATE);
                if(!(offlinegetter.offlinechecker(MainActivity.this,4)))
                {
                    JSONArray obj = new JSONArray(jso);
                    JSONObject obj2 = (JSONObject) obj.get(0);
                    String videourl = obj2.getString("videourl");
                    Intent myIntent = new Intent(MainActivity.this, longvideoactivity.class);
                    myIntent.putExtra("link", videourl);
                    myIntent.putExtra("mode", false);
                    startActivity(myIntent);
                    pressed = 0;
                }
                else
                {
                    Intent myIntent = new Intent(MainActivity.this, longvideoactivity.class);
                    myIntent.putExtra("link", getApplicationInfo().dataDir+"/files/unlearn/unlearn"+jso.substring(jso.length() - 1)+".mp4");
                    myIntent.putExtra("mode", false);
                    startActivity(myIntent);
                    pressed = 0;
                }
            }
            catch (Exception e){}
        }
    }


    public void startvideo(View view)
    {
        if (pressed == 0)
        {
            LinearLayout qwe = (LinearLayout) view;
            FrameLayout qwe2 = (FrameLayout) qwe.getChildAt(0);
            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.localstorage), Context.MODE_PRIVATE);
            int currentlevel = sharedPref.getInt("prophecylevel", 0);
            boolean secondlevel = sharedPref.getBoolean("secondprophecylevel", false);
            boolean twelvelevel = sharedPref.getBoolean("twelveprophecylevel", false);
            boolean fourteenlevel = sharedPref.getBoolean("fourteenprophecylevel", false);
            String buttontag = view.getTag().toString();
            String[] buttontags = buttontag.split("-");
            if (currentlevel == 1)
            {
                if (2 >= calculatelevel(Integer.parseInt(buttontags[1]), Integer.parseInt(buttontags[2])))
                {
                    pressed = 1;
                    videoparams videoparamsvar = new videoparams(getResources().getString(R.string.server) + "/api/prophecylessons/" + buttontags[1], Integer.parseInt(buttontags[2]), Integer.parseInt(buttontags[1]));
                    new startvideojson().execute(videoparamsvar);
                }
            }
            else if (currentlevel == 3)
            {
                if(!(secondlevel))
                {
                    if (2 >= calculatelevel(Integer.parseInt(buttontags[1]), Integer.parseInt(buttontags[2])))
                    {
                        pressed = 1;
                        videoparams videoparamsvar = new videoparams(getResources().getString(R.string.server) + "/api/prophecylessons/" + buttontags[1], Integer.parseInt(buttontags[2]), Integer.parseInt(buttontags[1]));
                        new startvideojson().execute(videoparamsvar);
                    }
                }
                else
                {
                    if (currentlevel >= calculatelevel(Integer.parseInt(buttontags[1]), Integer.parseInt(buttontags[2])))
                    {
                        pressed = 1;
                        videoparams videoparamsvar = new videoparams(getResources().getString(R.string.server) + "/api/prophecylessons/" + buttontags[1], Integer.parseInt(buttontags[2]), Integer.parseInt(buttontags[1]));
                        new startvideojson().execute(videoparamsvar);
                    }
                }
            }
            else if (currentlevel == 11)
            {
                if (12 >= calculatelevel(Integer.parseInt(buttontags[1]), Integer.parseInt(buttontags[2])))
                {
                    pressed = 1;
                    videoparams videoparamsvar = new videoparams(getResources().getString(R.string.server) + "/api/prophecylessons/" + buttontags[1], Integer.parseInt(buttontags[2]), Integer.parseInt(buttontags[1]));
                    new startvideojson().execute(videoparamsvar);
                }
            }
            else if (currentlevel == 13)
            {
                if(!(twelvelevel))
                {
                    if (12 >= calculatelevel(Integer.parseInt(buttontags[1]), Integer.parseInt(buttontags[2])))
                    {
                        pressed = 1;
                        videoparams videoparamsvar = new videoparams(getResources().getString(R.string.server) + "/api/prophecylessons/" + buttontags[1], Integer.parseInt(buttontags[2]), Integer.parseInt(buttontags[1]));
                        new startvideojson().execute(videoparamsvar);
                    }
                }
                else
                {
                    if (14 >= calculatelevel(Integer.parseInt(buttontags[1]), Integer.parseInt(buttontags[2])))
                    {
                        pressed = 1;
                        videoparams videoparamsvar = new videoparams(getResources().getString(R.string.server) + "/api/prophecylessons/" + buttontags[1], Integer.parseInt(buttontags[2]), Integer.parseInt(buttontags[1]));
                        new startvideojson().execute(videoparamsvar);
                    }
                }
            }
            else if (currentlevel == 15)
            {
                if(!(fourteenlevel))
                {
                    if (14 >= calculatelevel(Integer.parseInt(buttontags[1]), Integer.parseInt(buttontags[2])))
                    {
                        pressed = 1;
                        videoparams videoparamsvar = new videoparams(getResources().getString(R.string.server) + "/api/prophecylessons/" + buttontags[1], Integer.parseInt(buttontags[2]), Integer.parseInt(buttontags[1]));
                        new startvideojson().execute(videoparamsvar);
                    }
                }
                else
                {
                    if (currentlevel >= calculatelevel(Integer.parseInt(buttontags[1]), Integer.parseInt(buttontags[2])))
                    {
                        pressed = 1;
                        videoparams videoparamsvar = new videoparams(getResources().getString(R.string.server) + "/api/prophecylessons/" + buttontags[1], Integer.parseInt(buttontags[2]), Integer.parseInt(buttontags[1]));
                        new startvideojson().execute(videoparamsvar);
                    }
                }
            }
            else if (currentlevel >= calculatelevel(Integer.parseInt(buttontags[1]), Integer.parseInt(buttontags[2])))
            {
                pressed = 1;
                videoparams videoparamsvar = new videoparams(getResources().getString(R.string.server) + "/api/prophecylessons/" + buttontags[1], Integer.parseInt(buttontags[2]), Integer.parseInt(buttontags[1]));
                new startvideojson().execute(videoparamsvar);
            }
        }
    }

    public void unlearn(View view)
    {
        if (pressed == 0)
        {
            String buttontag = view.getTag().toString();
            if (buttontag.equals("unlearn1"))
            {
                pressed = 1;
                new unlearnjson().execute(getResources().getString(R.string.server) + "/api/unlearn/1");
            }
        }
    }

    public int calculatelevel(int book, int part)
    {
        int[][] lessons = {{1,1},{1,2},{1,3},{1,4},{1,5},{1,6},{2,1},{2,2},{2,3},{2,4},{2,5},{2,6},{2,7},{2,8},{2,9},{2,10},{2,11},{2,12},{2,13},{2,14},{2,15},{2,16},{3,1},{3,2},{3,3},{3,4},{3,5},{3,6},{3,7},{4,1},{5,1},{5,2},{5,3},{5,4},{6,1},{6,2},{6,3},{6,4},{6,5},{7,1},{7,2},{7,3},{7,4},{7,5},{7,6}};
        for(int i=0; i<lessons.length; i++)
        {
            if(lessons[i][0] == book)
            {
                if(lessons[i][1] == part)
                {
                    return i;
                }
            }
        }
        return 0;
    }

    public class jsonnoti extends AsyncTask<String, String, String>
    {
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        protected String doInBackground(String... params)
        {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try
            {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("accept","application/json");
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                //StringBuffer buffer = new StringBuffer();
                if(connection.getResponseCode() == 200)
                {
                    FileOutputStream out = openFileOutput("notifications.json", MODE_PRIVATE);
                    String line = "";
                    while ((line = reader.readLine()) != null)
                    {
                        String tempfw = line + "\n";
                        out.write(tempfw.getBytes());
                    }
                    out.close();
                }
                return "";
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
            sorter(result);
        }

        public void sorter(String jso)
        {
            try
            {

            }
            catch (Exception e){}
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, int deviceId)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId);
        if (requestCode == RC_NOTIFICATION)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {

            }
        }
    }

    public void frgtoloadfun()
    {
        try {
            int intentFragment = getIntent().getIntExtra("frgtoload",0);
            switch (intentFragment) {
                case 1:
                    replaceFramgment(new ShortsFragment());
                    binding.bottomNavigationView.getMenu().getItem(3).setChecked(true);
                    break;
                case 2:
                    Bundle bundle = new Bundle();
                    bundle.putString("quoteid", getIntent().getExtras().getString("quoteid",""));
                    QuotesFragment quotefragment = new QuotesFragment();
                    quotefragment.setArguments(bundle);
                    replaceFramgment(quotefragment);
                    binding.bottomNavigationView.getMenu().getItem(1).setChecked(true);
                    break;
                case 3:
                    replaceFramgment(new QuotesFragment());
                    binding.bottomNavigationView.getMenu().getItem(1).setChecked(true);
                    break;
                case 4:
                    replaceFramgment(new LVideoFragment());
                    binding.bottomNavigationView.getMenu().getItem(4).setChecked(true);
                    break;
            }
        }
        catch (Exception e){}
    }

    private class json extends AsyncTask<String, String, String>
    {
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        protected String doInBackground(String... params)
        {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try
            {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("accept","application/json");
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null)
                {
                    buffer.append(line+"\n");
                }
                return buffer.toString();
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
            sorter(result);
        }

        public void sorter(String jso)
        {
            try
            {
                JSONObject obj = new JSONObject(jso);
                String remoteversion = obj.get("version").toString();
                PackageInfo localversion = getPackageManager().getPackageInfo(getPackageName(), 0);
                if(!(remoteversion.trim().equals(String.valueOf(localversion.versionCode).trim())))
                {
                    bottomsheet bottomSheet = new bottomsheet();
                    bottomSheet.show(getSupportFragmentManager(), "ModalBottomSheet");
                }
            }
            catch (Exception e){}
        }
    }

    public int shortscategory()
    {
        return shortscategoryint;
    }

    private void replaceFramgment(Fragment fragment)
    {
        FragmentManager fragmentmanager = getSupportFragmentManager();
        FragmentTransaction fragmenttransaction = fragmentmanager.beginTransaction();
        fragmenttransaction.replace(R.id.fragment_layout,fragment);
        fragmenttransaction.commit();
        NewsFragment fragment1 = (NewsFragment) getSupportFragmentManager().findFragmentByTag("NewsFragment");
        QuotesFragment fragment2 = (QuotesFragment) getSupportFragmentManager().findFragmentByTag("QuotesFragment");
        ShortsFragment fragment4 = (ShortsFragment) getSupportFragmentManager().findFragmentByTag("ShortsFragment");
        LVideoFragment fragment5 = (LVideoFragment) getSupportFragmentManager().findFragmentByTag("LVideoFragment");
        if (fragment1 != null && fragment.equals(fragment1))
        {
            binding.bottomNavigationView.getMenu().getItem(0).setChecked(true);
        }
        else if (fragment2 != null && fragment.equals(fragment2))
        {
            binding.bottomNavigationView.getMenu().getItem(1).setChecked(true);
        }
        else if (fragment4 != null && fragment.equals(fragment4))
        {
            binding.bottomNavigationView.getMenu().getItem(3).setChecked(true);
        }
        else if (fragment5 != null && fragment.equals(fragment5))
        {
            binding.bottomNavigationView.getMenu().getItem(4).setChecked(true);
        }
    }
}
