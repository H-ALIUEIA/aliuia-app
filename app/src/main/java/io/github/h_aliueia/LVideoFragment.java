package io.github.h_aliueia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener;
import org.imaginativeworld.whynotimagecarousel.model.CarouselGravity;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.imaginativeworld.whynotimagecarousel.model.CarouselType;
import org.jetbrains.annotations.NotNull;
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

public class LVideoFragment extends Fragment
{
    public ImageView prophecydaysindicator;

    public LVideoFragment()
    {
    }

    public static LVideoFragment newInstance()
    {
        LVideoFragment fragment = new LVideoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public void fabbuttoncreator()
    {
        FloatingActionButton g = getActivity().findViewById(R.id.fab);
        g.setImageResource(R.drawable.setting);
        g.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent myIntent = new Intent(getActivity(), Settings.class);
                getActivity().startActivity(myIntent);
            }
        });
    }

    public void prophecydayindicator()
    {
        SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.localstorage), Context.MODE_PRIVATE);
        int currentlevel = sharedPref.getInt("prophecylevel",0);
        if(currentlevel < 6)
        {
            prophecydaysindicator.setImageResource(R.drawable.day1);
        }
        else if (currentlevel > 5 && currentlevel < 22)
        {
            prophecydaysindicator.setImageResource(R.drawable.day2);
        }
        else if (currentlevel > 21 && currentlevel < 29)
        {
            prophecydaysindicator.setImageResource(R.drawable.day3);
        }
        else if (currentlevel > 28 && currentlevel < 30)
        {
            prophecydaysindicator.setImageResource(R.drawable.day4);
        }
        else if (currentlevel > 29 && currentlevel < 34)
        {
            prophecydaysindicator.setImageResource(R.drawable.day5);
        }
        else if (currentlevel > 33 && currentlevel < 39)
        {
            prophecydaysindicator.setImageResource(R.drawable.day6);
        }
        else if (currentlevel > 38 && currentlevel < 45)
        {
            prophecydaysindicator.setImageResource(R.drawable.day7);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_l_video, container, false);
        prophecydaysindicator = (ImageView) rootView.findViewById(R.id.prophecydaysindicator);
        prophecydayindicator();
        fabbuttoncreator();
        return rootView;
    }
}
