package io.github.h_aliueia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import io.github.h_aliueia.models.quotesaveobject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity
{
    public List<String> quoteidarray;
    public List<String> quoteauthorarray;
    public List<String> quotedescriptionsarray;
    public List<String> quotesourcearrray;

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
        setContentView(R.layout.activity_main3);
        EdgeToEdge.enable(this);
        quoteidarray = new ArrayList<String>();
        quoteauthorarray = new ArrayList<String>();
        quotedescriptionsarray = new ArrayList<String>();
        quotesourcearrray = new ArrayList<String>();
        new json().execute(getResources().getString(R.string.server)+"/api/quote/0");
    }

    public int dpToPx(int dp)
    {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public void lister()
    {
        quotesaveobject savedprefs = readsavedprefs();
        if(savedprefs != null)
        {
            ListView l = findViewById(R.id.list);
            ArrayList<String> favstrings = new ArrayList<String>();
            ArrayList<Integer> quotesidarraygetter = new ArrayList<Integer>();
            for (int i = 0; i < quoteidarray.size(); i++)
            {
                if(savedprefs.getId().contains(quoteidarray.get(i)))
                {
                    favstrings.add(quoteauthorarray.get(i));
                    quotesidarraygetter.add(i);
                }
            }
            int nothing = 0;
            if(favstrings.size() == 0)
            {
                favstrings.add("Δεν έχεις αποθηκεύσει τίποτα");
                nothing = 1;
            }
            final int nothingfinal = nothing;
            String[] favstringsArray = favstrings.toArray(new String[favstrings.size()]);
            ArrayAdapter<String> arr = new ArrayAdapter<String>(MainActivity3.this, android.R.layout.simple_list_item_1, (String[]) favstringsArray);
            l.setAdapter(arr);
            l.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    if(nothingfinal == 0)
                    {
                        Intent intent = new Intent(MainActivity3.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("frgtoload", 2);
                        intent.putExtra("quoteid", quoteidarray.get(quotesidarraygetter.get(i)));
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
        else
        {
            ListView l = findViewById(R.id.list);
            ArrayList<String> favstrings = new ArrayList<String>();
            ArrayList<Integer> quotesidarraygetter = new ArrayList<Integer>();
            favstrings.add("Δεν έχεις αποθηκεύσει τίποτα");
            String[] favstringsArray = favstrings.toArray(new String[favstrings.size()]);
            ArrayAdapter<String> arr = new ArrayAdapter<String>(MainActivity3.this, android.R.layout.simple_list_item_1, (String[]) favstringsArray);
            l.setAdapter(arr);
        }
    }

    public quotesaveobject readsavedprefs()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("SavedQuotes", MODE_PRIVATE);
        String quoteseen = sharedPreferences.getString("quote", "");
        if(quoteseen.equals(""))
        {
            return null;
        }
        else
        {
            return new Gson().fromJson(quoteseen, quotesaveobject.class);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent i = new Intent(this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("frgtoload", 3);
                startActivity(i);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("frgtoload", 3);
        startActivity(i);
        finish();
        super.onBackPressed();
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
                SharedPreferences sharedPref = MainActivity3.this.getSharedPreferences(getString(R.string.localstorage), Context.MODE_PRIVATE);
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
                if(offlinegetter.offlinechecker(MainActivity3.this,0))
                {
                    String url = params[0];
                    String offlinestring = offlinegetter.quotegetter(MainActivity3.this, Integer.parseInt(url.substring(url.indexOf("api/quote/")+10)));
                    return offlinestring;
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
                JSONArray obj = new JSONArray(jso);
                for(int i=0; i<obj.length(); i++)
                {
                    JSONObject obj2 = (JSONObject) obj.get(i);
                    quoteidarray.add(obj2.getString("id"));
                    quoteauthorarray.add(obj2.getString("author").replace("-","\n"));
                    quotedescriptionsarray.add(obj2.getString("description").replace("\\n","\n"));
                    quotesourcearrray.add(obj2.getString("source"));
                }
                lister();
            }
            catch (Exception e){}
        }
    }
}
