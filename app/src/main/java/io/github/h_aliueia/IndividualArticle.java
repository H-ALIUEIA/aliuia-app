package io.github.h_aliueia;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleCoroutineScope;
import androidx.lifecycle.LifecycleOwnerKt;

import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class IndividualArticle extends Fragment
{

    public FrameLayout background;
    public WebView articlewebview;

    public IndividualArticle()
    {

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
                    ((MainActivity)getActivity()).fragmentreplacerfromfrag(5);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_individual_article, container, false);
        articlewebview = (WebView)rootView.findViewById(R.id.articlewebview);
        background = (FrameLayout) rootView.findViewById(R.id.background);
        articlewebview.getSettings().setJavaScriptEnabled(true);
        articlewebview.getSettings().setAllowFileAccess(true);
        articlewebview.getSettings().setAllowUniversalAccessFromFileURLs(true);
        articlewebview.getSettings().setAllowFileAccessFromFileURLs(true);
        articlewebview.getSettings().setAllowContentAccess(true);
        articlewebview.getSettings().setSupportZoom(true);
        articlewebview.getSettings().setBuiltInZoomControls(true);
        boolean isDarkThemeOn = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)  == Configuration.UI_MODE_NIGHT_YES;
        String key = getArguments().getString("key");
        if(isDarkThemeOn)
        {
            background.setBackgroundColor(Color.parseColor("#4C4D4C"));
            articlewebview.loadUrl(getResources().getString(R.string.storageserver)+"/articles/"+key+".html");
        }
        else
        {
            background.setBackgroundColor(Color.parseColor("#FFFFFF"));
            articlewebview.loadUrl(getResources().getString(R.string.storageserver)+"/articles/"+key+"-white.html");
        }
        backpress(rootView);
        fab();
        return rootView;
    }

    public void fab() {
        FloatingActionButton g = getActivity().findViewById(R.id.fab);
        g.setImageResource(R.drawable.printer);
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPrint();
            }
        });
    }

    public void buttonPrint(){
        PrintManager printManager = (PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter printDocumentAdapter = articlewebview.createPrintDocumentAdapter("Print1");
        printManager.print("PrintTest1", printDocumentAdapter, new  PrintAttributes.Builder().build());
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
                String body = "<!DOCTYPE html>\n<html lang=\"el-GR\">\n\n<head>\n\t<meta charset=\"UTF-8\" />\n</head>\n<body>"+obj.getString(0)+"</body></html>";
                //String base64version = Base64.encodeToString(body.getBytes(), Base64.DEFAULT);
                //articlewebview.loadData(body,"text/css", "base64");
            }
            catch (Exception e){}
        }
    }
}
