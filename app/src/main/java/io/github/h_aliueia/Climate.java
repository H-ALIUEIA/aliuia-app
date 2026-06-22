package io.github.h_aliueia;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Climate extends Fragment
{

    public Climate()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_climate, container, false);
        backpress(rootView);
        fab();
        return rootView;
    }

    public void fab()
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
                    ((MainActivity)getActivity()).fragmentreplacerfromfrag(6);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }
}
