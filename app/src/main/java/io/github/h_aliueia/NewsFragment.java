package io.github.h_aliueia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

public class NewsFragment extends Fragment
{
    public TextView newswait;
    public ListView newslist;

    public NewsFragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        backpress(rootView);
        fab();
        ids(rootView);
        new json().execute(getResources().getString(R.string.server)+"/api/articles/");
        return rootView;
    }

    public void ids(View rootView)
    {
        newswait = (TextView) rootView.findViewById(R.id.newswait);
        newslist = (ListView) rootView.findViewById(R.id.newslist);
    }

    public void backpress(View rootView)
    {
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }

    public void fab()
    {
        FloatingActionButton g = getActivity().findViewById(R.id.fab);
        g.setImageResource(R.drawable.baseline_360_24);
        g.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new json().execute(getResources().getString(R.string.server)+"/api/articles/");
            }
        });
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
                if(offlinegetter.offlinechecker(getContext(),0))
                {
                    //String url = params[0];
                    //String offlinestring = offlinegetter.quotegetter(getContext(), Integer.parseInt(url.substring(url.indexOf("api/quote/")+10)));
                    //return offlinestring;
                    return "";
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
                List<String> titles = new ArrayList<String>();
                List<String> imagesurls = new ArrayList<String>();
                List<String> bodyrefs = new ArrayList<String>();
                for(int i=0; i<obj.length(); i++)
                {
                    JSONObject obj2 = (JSONObject) obj.get(i);
                    titles.add(obj2.getString("title"));
                    imagesurls.add(obj2.getString("image"));
                    bodyrefs.add(obj2.getString("bodyref"));
                }
                newswait.setVisibility(View.GONE);
                newslist_adapter adapter = new newslist_adapter(getActivity(), titles.toArray(new String[titles.size()]), imagesurls.toArray(new String[imagesurls.size()]));
                newslist.setAdapter(adapter);
                newslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                    {
                        ((MainActivity)getActivity()).loadarticlefragment(bodyrefs.get(i));
                    }
                });
            }
            catch (Exception e){}
        }
    }
}
