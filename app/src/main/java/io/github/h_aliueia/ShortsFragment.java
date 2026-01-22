package io.github.h_aliueia;

import static androidx.recyclerview.widget.RecyclerView.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.media3.common.util.UnstableApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.github.h_aliueia.interfaces.interfaceclass;
import io.github.h_aliueia.models.MediaObject;
import io.github.h_aliueia.utils.Resources;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShortsFragment extends Fragment
{

    private ViewPager2 viewpager2;
    private VideoPlayerRecyclerView mRecyclerView;
    private TextView categorytext;
    private LinearLayout shortssourcebutton;
    public View rootView;
    public String source;
    private MediaObject[] shortslistarraycopy;

    private LinearLayoutManager mLayoutManager;
    private SnapHelper snapHelper;
    private int runonce = 0;
    private int total = 0;
    private FloatingActionButton g;

    public ShortsFragment()
    {
        // Required empty public constructor
    }

    public void setSource(String source1)
    {
        source = source1;
    }

    public void fabbutton()
    {
        g = getActivity().findViewById(R.id.fab);
        g.setImageResource(R.drawable.baseline_apps_24);
        g.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Animation animation = AnimationUtils.loadAnimation(g.getContext(), R.anim.zoom);
                animation.setAnimationListener(new Animation.AnimationListener()
                {
                    @Override
                    public void onAnimationStart(Animation animation)
                    {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation)
                    {
                        rootView.getRootView().setBackgroundColor(getResources().getColor(R.color.black));
                        getActivity().findViewById(R.id.circle).setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation)
                    {

                    }
                });
                getActivity().findViewById(R.id.circle).setVisibility(View.VISIBLE);
                g.setVisibility(View.INVISIBLE);
                getActivity().findViewById(R.id.circle).startAnimation(animation);
                MainActivity activity = (MainActivity) getActivity();
                int shortscategoryint = activity.shortscategory();
                Intent myIntent = new Intent(getActivity(), MainActivity2.class);
                myIntent.putExtra("selected", shortscategoryint);
                getActivity().startActivity(myIntent);
                getActivity().finish();
            }
        });
        g.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                setClipboard(getContext(), source);
                return false;
            }
        });
    }

    private void setClipboard(Context context, String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB)
        {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        }
        else
        {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
        Toast.makeText(getActivity(), "Αντιγράφτηκε", Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_shorts, container, false);
        categoryheight();
        fabbutton();
        categorytext = (TextView) rootView.findViewById(R.id.categorytext);
        shortssourcebutton = (LinearLayout)rootView.findViewById(R.id.shortssourcebutton);
        shortssourcebutton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                setClipboard(getContext(), source);
            }
        });
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.interfacelistener(new interfaceclass()
        {
                @Override
                public void changedata(int current)
                {
                    TextView categorytext = (TextView)rootView.findViewById(R.id.categorytext);
                    if(shortslistarraycopy == null || shortslistarraycopy.length == 0)
                    {

                    }
                    else
                    {
                        categorytext.setText(String.valueOf(current + 1) + "/" + String.valueOf(total) + "\n" + shortslistarraycopy[current].getCategory() + "\n↓");
                        categoryheight();
                        source = shortslistarraycopy[current].getSource();
                    }
                }
            });
        initRecyclerView();
        return rootView;
    }

    public void categoryheight()
    {
        BottomAppBar bottombar = getActivity().findViewById(R.id.bottomnav);
        TextView categorytext = rootView.findViewById(R.id.categorytext);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) categorytext.getLayoutParams();
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        params.setMargins(0, 0, 0, bottombar.getHeight()+fab.getSize()+categorytext.getLineHeight()*2);
        categorytext.setLayoutParams(params);
    }

    private void initRecyclerView()
    {
        MainActivity activity = (MainActivity) getActivity();
        int shortscategoryint = activity.shortscategory();
        LinearLayoutManager layoutManager = (new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false)
        {
            @UnstableApi
            @Override
            public void onLayoutCompleted(State state)
            {
                super.onLayoutCompleted(state);
                if(runonce == 1)
                {
                    mRecyclerView.first();
                    runonce = 0;
                }
            }
        });
        mRecyclerView.setLayoutManager(layoutManager);
        if(shortscategoryint == 0)
        {
            new json().execute(getResources().getString(R.string.server)+"/api/shorts/0");
        }
        else if(shortscategoryint == 1)
        {
            new json().execute(getResources().getString(R.string.server)+"/api/shorts/5");
        }
        else if(shortscategoryint == 2)
        {
            new json().execute(getResources().getString(R.string.server)+"/api/shorts/6");
        }
        else if(shortscategoryint == 3)
        {
            new json().execute(getResources().getString(R.string.server)+"/api/shorts/9");
        }
        else if(shortscategoryint == 4)
        {
            new json().execute(getResources().getString(R.string.server)+"/api/shorts/1");
        }
        else if(shortscategoryint == 5)
        {
            new json().execute(getResources().getString(R.string.server)+"/api/shorts/3");
        }
        else if(shortscategoryint == 6)
        {
            new json().execute(getResources().getString(R.string.server)+"/api/shorts/2");
        }
        else if(shortscategoryint == 7)
        {
            new json().execute(getResources().getString(R.string.server)+"/api/shorts/8");
        }
        else if(shortscategoryint == 8)
        {
            new json().execute(getResources().getString(R.string.server)+"/api/shorts/7");
        }
        else if(shortscategoryint == 9)
        {
            new json().execute(getResources().getString(R.string.server)+"/api/shorts/4");
        }
        else if(shortscategoryint == 10)
        {
            new json().execute(getResources().getString(R.string.server)+"/api/shorts/10");
        }
        else if(shortscategoryint == 11)
        {
            new json().execute(getResources().getString(R.string.server)+"/api/shorts/11");
        }
    }

    private RequestManager initGlide(){
        RequestOptions options = new RequestOptions()
            .placeholder(R.drawable.white)
            .error(R.drawable.white);

        return Glide.with(this)
            .setDefaultRequestOptions(options);
    }


    @Override
    public void onDestroy() {
        if(mRecyclerView!=null)
            mRecyclerView.releasePlayer();
        super.onDestroy();
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

    public class json extends AsyncTask<String, String, String>
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
                    String offlinestring = offlinegetter.shortsgetter(getContext(), Integer.parseInt(url.substring(url.indexOf("api/shorts/")+11)));
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
                SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.localstorage), Context.MODE_PRIVATE);
                JSONArray obj = new JSONArray(jso);
                List<MediaObject> shortslist = new ArrayList<MediaObject>();
                for(int i=0; i<obj.length(); i++)
                {
                    JSONObject obj2 = (JSONObject) obj.get(i);
                    if(!(offlinegetter.offlinechecker(getContext(),1)))
                    {
                        shortslist.add(new MediaObject(obj2.getString("link"), getResources().getString(R.string.storageserver) + "/" + obj2.getString("thumbnail"), obj2.getString("source"), obj2.getString("category")));
                    }
                    else
                    {
                        shortslist.add(new MediaObject(getContext().getApplicationInfo().dataDir+"/files/shorts/"+obj2.getString("thumbnail").substring(0,obj2.getString("thumbnail").length()-4)+".mp4", getContext().getApplicationInfo().dataDir+"/files/shorts/"+obj2.getString("thumbnail"), obj2.getString("source"), obj2.getString("category")));
                    }
                }
                Resources cal1 = new Resources();
                MediaObject[] shortslistarray = new MediaObject[ shortslist.size() ];
                total = shortslist.size();
                shortslistarraycopy = shortslistarray;
                shortslist.toArray( shortslistarray );
                cal1.setMedia_objects(shortslistarray);
                ArrayList<MediaObject> mediaObjects = new ArrayList<MediaObject>(Arrays.asList(cal1.getMedia_objects()));
                mRecyclerView.setMediaObjects(mediaObjects);
                VideoPlayerRecyclerAdapter adapter = new VideoPlayerRecyclerAdapter(mediaObjects, initGlide());
                mRecyclerView.setAdapter(adapter);
                runonce = 1;
            }
            catch (Exception e){}
        }
    }
}
