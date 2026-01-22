package io.github.h_aliueia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import java.util.Random;

public class QuotesFragment extends Fragment
{
    public TextView quoteauthor;
    public TextView quotetext;
    public Spinner spinner;
    public String ider = "";
    public String cate = "all";

    public String descriptioner = "";

    public String auther = "";

    public String sourcer = "";
    public List<String> quoteidarray;
    public List<String> quoteauthorarray;
    public List<String> quotedescriptionsarray;
    public List<String> quotesourcearrray;
    public Integer texttosource = 0;
    public ImageView searchbutton;
    public ImageView sourcebutton;
    public View line;
    public int firstrun = 0;
    public String quoteidfromfirstrun = "";
    private FloatingActionButton g;

    public QuotesFragment()
    {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static QuotesFragment newInstance()
    {
        QuotesFragment fragment = new QuotesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quotes, container, false);
        quoteidfromfirstrun = "";
        try
        {
            quoteidfromfirstrun = getArguments().getString("quoteid","");
        }
        catch (Exception e)
        {

        }
        quoteidarray = new ArrayList<String>();
        quoteauthorarray = new ArrayList<String>();
        quotedescriptionsarray = new ArrayList<String>();
        quotesourcearrray = new ArrayList<String>();
        line = (View)view.findViewById(R.id.line);
        searchbutton = (ImageView)view.findViewById(R.id.searchbutton);
        sourcebutton = (ImageView)view.findViewById(R.id.sourcebutton);
        quoteauthor = (TextView)view.findViewById(R.id.author);
        quotetext = (TextView) view.findViewById(R.id.description);
        spinner = (Spinner) view.findViewById(R.id.categories);
        String[] courses =
        {
            "ΟΛΕΣ ΟΙ ΚΑΤΗΓΟΡΙΕΣ", "ΑΓΙΑ ΓΡΑΦΗ", "ΣΥΝΩΜΟΣΙΑ", "ΤΑΙΝΙΕΣ/ΣΕΙΡΕΣ"
        };
        ArrayAdapter<String> ad = new ArrayAdapter<String>(view.getContext(), R.menu.spinner_item, R.id.text1, courses);
        ad.setDropDownViewResource(R.menu.spinner_dropdown_item);
        spinner.setAdapter(ad);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                switch (position)
                {
                    case 0:
                        cate = "all";
                        new json().execute(getResources().getString(R.string.server)+"/api/quote/0");
                        break;
                    case 1:
                        cate = "bible";
                        new json().execute(getResources().getString(R.string.server)+"/api/quote/1");
                        break;
                    case 2:
                        cate = "cons";
                        new json().execute(getResources().getString(R.string.server)+"/api/quote/2");
                        break;
                    case 3:
                        cate = "mov-ser";
                        new json().execute(getResources().getString(R.string.server)+"/api/quote/3");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        sourcebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(texttosource == 0)
                {
                    quoteauthor.setVisibility(View.GONE);
                    line.setVisibility(View.GONE);
                    quotetext.setText(sourcer);
                    texttosource = 1;
                }
                else
                {
                    quoteauthor.setVisibility(View.VISIBLE);
                    line.setVisibility(View.VISIBLE);
                    quoteauthor.setText(auther);
                    quotetext.setText(descriptioner);
                    texttosource = 0;
                }
            }
        });
        getActivity().findViewById(R.id.quotes).setEnabled(false);
        g = getActivity().findViewById(R.id.fab);
        g.setImageResource(R.drawable.baseline_360_24);
        g.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                if(texttosource == 0)
                {
                    quoteauthor.setVisibility(View.GONE);
                    line.setVisibility(View.GONE);
                    quotetext.setText(sourcer);
                    texttosource = 1;
                }
                else
                {
                    quoteauthor.setVisibility(View.VISIBLE);
                    line.setVisibility(View.VISIBLE);
                    quoteauthor.setText(auther);
                    quotetext.setText(descriptioner);
                    texttosource = 0;
                }
                return false;
            }
        });
        g.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                quotechange();
            }
        });
        searchbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editsavedprefs(ider);
            }
        });
        searchbutton.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                Intent i = new Intent(getActivity(), MainActivity3.class);
                startActivity(i);
                return false;
            }
        });
        return view;
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
                SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.localstorage), Context.MODE_PRIVATE);
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
                    String url = params[0];
                    String offlinestring = offlinegetter.quotegetter(getContext(), Integer.parseInt(url.substring(url.indexOf("api/quote/")+10)));
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
                quoteidarray.clear();
                quoteauthorarray.clear();
                quotedescriptionsarray.clear();
                quotesourcearrray.clear();
                for(int i=0; i<obj.length(); i++)
                {
                    JSONObject obj2 = (JSONObject) obj.get(i);
                    quoteidarray.add(obj2.getString("id"));
                    quoteauthorarray.add(obj2.getString("author").replace("-","\n"));
                    quotedescriptionsarray.add(obj2.getString("description").replace("\\n","\n"));
                    quotesourcearrray.add(obj2.getString("source").replace("\\n","\n").replace("<a>","").replace("</a>",""));
                }
                if(!(quoteidfromfirstrun.equals("")) && firstrun == 0)
                {
                    int index = quoteidarray.indexOf(quoteidfromfirstrun);
                    ider = quoteidarray.get(index);
                    auther = quoteauthorarray.get(index);
                    descriptioner = quotedescriptionsarray.get(index);
                    sourcer = quotesourcearrray.get(index);
                    quoteauthor.setVisibility(View.VISIBLE);
                    line.setVisibility(View.VISIBLE);
                    texttosource = 0;
                    quoteauthor.setText(auther);
                    quotetext.setText(descriptioner);
                    changefavicon();
                    firstrun = 1;
                }
                else
                {
                    quotechange();
                }
            }
            catch (Exception e){}
        }
    }
    public void quotechange()
    {
        if(quoteidarray == null || quoteidarray.isEmpty())
        {
            changefavicon();
            return;
        }
        quotesaveobject getwatchedlistobject = readprefs();
        List<String> getwatchedlist;
        if(getwatchedlistobject == null)
        {
            ider = quoteidarray.get(0);
            auther = quoteauthorarray.get(0);
            descriptioner = quotedescriptionsarray.get(0);
            sourcer = quotesourcearrray.get(0);
            quoteauthor.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
            texttosource = 0;
            quoteauthor.setText(auther);
            quotetext.setText(descriptioner);
            saveprefs(quoteidarray.get(0));
            changefavicon();
            return;
        }
        else
        {
            getwatchedlist = getwatchedlistobject.getId();
        }
        if(getwatchedlist.isEmpty())
        {
            ider = quoteidarray.get(0);
            auther = quoteauthorarray.get(0);
            descriptioner = quotedescriptionsarray.get(0);
            sourcer = quotesourcearrray.get(0);
            quoteauthor.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
            texttosource = 0;
            quoteauthor.setText(auther);
            quotetext.setText(descriptioner);
            saveprefs(quoteidarray.get(0));
            changefavicon();
            return;
        }
        else if(getwatchedlist.size() == quoteidarray.size())
        {
            int random = -1;
            while(random == -1 || (quoteidarray.get(random).equals(ider)))
            {
                random = new Random().nextInt(quoteidarray.size());
            }
            ider = quoteidarray.get(random);
            auther = quoteauthorarray.get(random);
            descriptioner = quotedescriptionsarray.get(random);
            sourcer = quotesourcearrray.get(random);
            quoteauthor.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
            texttosource = 0;
            quoteauthor.setText(auther);
            quotetext.setText(descriptioner);
            changefavicon();
            return;
        }
        else
        {
            for(int i=0; i<quoteidarray.size();i++)
            {
                if(!(getwatchedlist.contains(quoteidarray.get(i))))
                {
                    ider = quoteidarray.get(i);
                    auther = quoteauthorarray.get(i);
                    descriptioner = quotedescriptionsarray.get(i);
                    sourcer = quotesourcearrray.get(i);
                    quoteauthor.setVisibility(View.VISIBLE);
                    line.setVisibility(View.VISIBLE);
                    texttosource = 0;
                    quoteauthor.setText(auther);
                    quotetext.setText(descriptioner);
                    saveprefs(quoteidarray.get(i));
                    changefavicon();
                    return;
                }
            }
            int random = -1;
            while (random == -1 || (quoteidarray.get(random).equals(ider)))
            {
                random = new Random().nextInt(quoteidarray.size());
            }
            ider = quoteidarray.get(random);
            auther = quoteauthorarray.get(random);
            descriptioner = quotedescriptionsarray.get(random);
            sourcer = quotesourcearrray.get(random);
            quoteauthor.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
            texttosource = 0;
            quoteauthor.setText(auther);
            quotetext.setText(descriptioner);
            changefavicon();
            return;
        }
    }

    public quotesaveobject readsavedprefs()
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("SavedQuotes", getContext().MODE_PRIVATE);
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

    public void changefavicon()
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("SavedQuotes", getContext().MODE_PRIVATE);
        quotesaveobject quoteseen = readsavedprefs();
        if(quoteseen == null)
        {

        }
        else
        {
            if(quoteseen.getId().contains(ider))
            {
                searchbutton.setImageDrawable(getResources().getDrawable(R.drawable.fullline_bookmark_heart_24));
            }
            else
            {
                searchbutton.setImageDrawable(getResources().getDrawable(R.drawable.outline_bookmark_heart_24));
            }
        }
    }

    public void editsavedprefs(String id)
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("SavedQuotes", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        quotesaveobject quoteseen = readsavedprefs();
        if(quoteseen == null)
        {
            quoteseen = new quotesaveobject(id);
            editor.putString("quote", new Gson().toJson(quoteseen));
            editor.apply();
        }
        else
        {
            if(quoteseen.getId().contains(id))
            {
                quoteseen.removeid(id);
                editor.putString("quote", new Gson().toJson(quoteseen));
                editor.apply();
                Toast.makeText(getActivity(), "Διαγράφτηκε", Toast.LENGTH_LONG).show();
            }
            else
            {
                quoteseen.addid(id);
                editor.putString("quote", new Gson().toJson(quoteseen));
                editor.apply();
                Toast.makeText(getActivity(), "Αποθηκεύτηκε", Toast.LENGTH_LONG).show();
            }
        }
        changefavicon();
    }

    public quotesaveobject readprefs()
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Quotes", getContext().MODE_PRIVATE);
        String quoteseen = sharedPreferences.getString("quoteseen", "");
        if(quoteseen.equals(""))
        {
            return null;
        }
        else
        {
            return new Gson().fromJson(quoteseen, quotesaveobject.class);
        }
    }

    public void saveprefs(String id)
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Quotes", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        quotesaveobject quoteseen = readprefs();
        if(quoteseen == null)
        {
            quoteseen = new quotesaveobject(id);
            editor.putString("quoteseen", new Gson().toJson(quoteseen));
            editor.apply();
        }
        else
        {
            quoteseen.addid(id);
            editor.putString("quoteseen", new Gson().toJson(quoteseen));
            editor.apply();
        }
    }


    @Override
    public void onResume()
    {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}
