package io.github.h_aliueia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Prophecies extends Fragment
{
    public ScrollView prophecyscroll;
    public LinearLayout[] lessons = new LinearLayout[45];
    public LinearLayout twoandthree;
    public LinearLayout twelveandthreeteen;
    public LinearLayout fourteenandfifteen;
    public FrameLayout prophframe;

    public Prophecies()
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
        View rootView = inflater.inflate(R.layout.fragment_prophecies, container, false);
        ids(rootView);
        lessonschecker();
        fab();
        backpress(rootView);
        return rootView;
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
                    ((MainActivity)getActivity()).fragmentreplacerfromfrag(1);
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

    public void ids(View rootView)
    {
        prophecyscroll = (ScrollView) rootView.findViewById(R.id.prophecyscroll);
        twoandthree = (LinearLayout) rootView.findViewById(R.id.secondsandthirdlayout);
        twelveandthreeteen = (LinearLayout) rootView.findViewById(R.id.twelveandthreeteen);
        fourteenandfifteen = (LinearLayout) rootView.findViewById(R.id.fourteenandfifteen);
        prophframe = (FrameLayout) rootView.findViewById(R.id.framelayout);
        lessons[0] = (LinearLayout) rootView.findViewWithTag("lesson-1-1");
        lessons[1] = (LinearLayout) rootView.findViewWithTag("lesson-1-2");
        lessons[2] = (LinearLayout) rootView.findViewWithTag("lesson-1-3");
        lessons[3] = (LinearLayout) rootView.findViewWithTag("lesson-1-4");
        lessons[4] = (LinearLayout) rootView.findViewWithTag("lesson-1-5");
        lessons[5] = (LinearLayout) rootView.findViewWithTag("lesson-1-6");
        lessons[6] = (LinearLayout) rootView.findViewWithTag("lesson-2-1");
        lessons[7] = (LinearLayout) rootView.findViewWithTag("lesson-2-2");
        lessons[8] = (LinearLayout) rootView.findViewWithTag("lesson-2-3");
        lessons[9] = (LinearLayout) rootView.findViewWithTag("lesson-2-4");
        lessons[10] = (LinearLayout) rootView.findViewWithTag("lesson-2-5");
        lessons[11] = (LinearLayout) rootView.findViewWithTag("lesson-2-6");
        lessons[12] = (LinearLayout) rootView.findViewWithTag("lesson-2-7");
        lessons[13] = (LinearLayout) rootView.findViewWithTag("lesson-2-8");
        lessons[14] = (LinearLayout) rootView.findViewWithTag("lesson-2-9");
        lessons[15] = (LinearLayout) rootView.findViewWithTag("lesson-2-10");
        lessons[16] = (LinearLayout) rootView.findViewWithTag("lesson-2-11");
        lessons[17] = (LinearLayout) rootView.findViewWithTag("lesson-2-12");
        lessons[18] = (LinearLayout) rootView.findViewWithTag("lesson-2-13");
        lessons[19] = (LinearLayout) rootView.findViewWithTag("lesson-2-14");
        lessons[20] = (LinearLayout) rootView.findViewWithTag("lesson-2-15");
        lessons[21] = (LinearLayout) rootView.findViewWithTag("lesson-2-16");
        lessons[22] = (LinearLayout) rootView.findViewWithTag("lesson-3-1");
        lessons[23] = (LinearLayout) rootView.findViewWithTag("lesson-3-2");
        lessons[24] = (LinearLayout) rootView.findViewWithTag("lesson-3-3");
        lessons[25] = (LinearLayout) rootView.findViewWithTag("lesson-3-4");
        lessons[26] = (LinearLayout) rootView.findViewWithTag("lesson-3-5");
        lessons[27] = (LinearLayout) rootView.findViewWithTag("lesson-3-6");
        lessons[28] = (LinearLayout) rootView.findViewWithTag("lesson-3-7");
        lessons[29] = (LinearLayout) rootView.findViewWithTag("lesson-4-1");
        lessons[30] = (LinearLayout) rootView.findViewWithTag("lesson-5-1");
        lessons[31] = (LinearLayout) rootView.findViewWithTag("lesson-5-2");
        lessons[32] = (LinearLayout) rootView.findViewWithTag("lesson-5-3");
        lessons[33] = (LinearLayout) rootView.findViewWithTag("lesson-5-4");
        lessons[34] = (LinearLayout) rootView.findViewWithTag("lesson-6-1");
        lessons[35] = (LinearLayout) rootView.findViewWithTag("lesson-6-2");
        lessons[36] = (LinearLayout) rootView.findViewWithTag("lesson-6-3");
        lessons[37] = (LinearLayout) rootView.findViewWithTag("lesson-6-4");
        lessons[38] = (LinearLayout) rootView.findViewWithTag("lesson-6-5");
        lessons[39] = (LinearLayout) rootView.findViewWithTag("lesson-7-1");
        lessons[40] = (LinearLayout) rootView.findViewWithTag("lesson-7-2");
        lessons[41] = (LinearLayout) rootView.findViewWithTag("lesson-7-3");
        lessons[42] = (LinearLayout) rootView.findViewWithTag("lesson-7-4");
        lessons[43] = (LinearLayout) rootView.findViewWithTag("lesson-7-5");
        lessons[44] = (LinearLayout) rootView.findViewWithTag("lesson-7-6");
    }

    @Override
    public void onResume()
    {
        lessonschecker();
        super.onResume();
    }

    public void lessonschecker()
    {
        SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.localstorage), Context.MODE_PRIVATE);
        int currentlevel = sharedPref.getInt("prophecylevel",0);
        boolean secondlevel = sharedPref.getBoolean("secondprophecylevel",false);
        boolean twelvelevel = sharedPref.getBoolean("twelveprophecylevel",false);
        boolean fourteenlevel = sharedPref.getBoolean("fourteenprophecylevel",false);
        ViewTreeObserver vto = prophecyscroll.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            public void onGlobalLayout()
            {
                if(currentlevel == 1)
                {
                    prophecyscroll.scrollTo(0, twoandthree.getBottom() - twoandthree.getHeight());
                }
                else if(currentlevel == 2)
                {
                    prophecyscroll.scrollTo(0, twoandthree.getBottom() - twoandthree.getHeight());
                }
                else if(currentlevel == 3 && (!(secondlevel)))
                {
                    prophecyscroll.scrollTo(0, twoandthree.getBottom() - twoandthree.getHeight());
                }
                else if(currentlevel == 11)
                {
                    prophecyscroll.scrollTo(0, twelveandthreeteen.getBottom() - twelveandthreeteen.getHeight());
                }
                else if(currentlevel == 12)
                {
                    prophecyscroll.scrollTo(0, twelveandthreeteen.getBottom() - twelveandthreeteen.getHeight());
                }
                else if(currentlevel == 13)
                {
                    if(twelvelevel)
                    {
                        prophecyscroll.scrollTo(0, fourteenandfifteen.getBottom() - fourteenandfifteen.getHeight());
                    }
                    else
                    {
                        prophecyscroll.scrollTo(0, twelveandthreeteen.getBottom() - twelveandthreeteen.getHeight());
                    }
                }
                else if(currentlevel == 14)
                {
                    prophecyscroll.scrollTo(0, fourteenandfifteen.getBottom() - fourteenandfifteen.getHeight());
                }
                else if(currentlevel == 15)
                {
                    if(!(fourteenlevel))
                    {
                        prophecyscroll.scrollTo(0, fourteenandfifteen.getBottom() - fourteenandfifteen.getHeight());
                    }
                }
                else
                {
                    prophecyscroll.scrollTo(0, lessons[currentlevel].getBottom() - lessons[currentlevel].getHeight());
                }
            }
        });
        if(currentlevel == 0)
        {
            for (int i = 1; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 1)
        {
            FrameLayout frametempzero = (FrameLayout) lessons[0].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            frametempzero = (FrameLayout) lessons[1].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            frametempzero = (FrameLayout) lessons[2].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 3; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 2)
        {
            for (int i = 0; i < 2; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[2].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 3; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 3)
        {
            if(!(secondlevel))
            {
                FrameLayout frametempzero = (FrameLayout) lessons[0].getChildAt(0);
                frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
                frametempzero = (FrameLayout) lessons[2].getChildAt(0);
                frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
                frametempzero = (FrameLayout) lessons[1].getChildAt(0);
                frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
                ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
                for (int i = 3; i < lessons.length; i++)
                {
                    FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                    frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                    ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
                }
            }
            else
            {
                for (int i = 0; i < 3; i++)
                {
                    FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                    frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                    ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
                }
                FrameLayout frametempzero = (FrameLayout) lessons[3].getChildAt(0);
                frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
                ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
                for (int i = 4; i < lessons.length; i++)
                {
                    FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                    frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                    ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
                }
            }
        }
        else if (currentlevel == 4)
        {
            for (int i = 0; i < 4; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[4].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 5; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 5)
        {
            for (int i = 0; i < 5; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[5].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 6; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 6)
        {
            for (int i = 0; i < 6; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[6].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 7; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 7)
        {
            for (int i = 0; i < 7; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[7].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 8; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 8)
        {
            for (int i = 0; i < 8; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[8].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 9; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 9)
        {
            for (int i = 0; i < 9; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[9].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 10; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 10)
        {
            for (int i = 0; i < 10; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[10].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 11; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 11)
        {
            for (int i = 0; i < 11; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[11].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            frametempzero = (FrameLayout) lessons[12].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 13; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 12)
        {
            for (int i = 0; i < 12; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[12].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 13; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 13)
        {
            if(twelvelevel)
            {
                for (int i = 0; i < 13; i++)
                {
                    FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                    frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                    ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
                }
                FrameLayout frametempzero = (FrameLayout) lessons[13].getChildAt(0);
                frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
                ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
                frametempzero = (FrameLayout) lessons[14].getChildAt(0);
                frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
                ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
                for (int i = 15; i < lessons.length; i++)
                {
                    FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                    frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                    ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
                }
            }
            else
            {
                for (int i = 0; i < 11; i++)
                {
                    FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                    frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                    ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
                }
                FrameLayout frametemp2 = (FrameLayout) lessons[12].getChildAt(0);
                frametemp2.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp2.getChildAt(1)).setImageResource(0);
                FrameLayout frametempzero = (FrameLayout) lessons[11].getChildAt(0);
                frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
                ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
                for (int i = 13; i < lessons.length; i++)
                {
                    FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                    frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                    ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
                }
            }
        }
        else if (currentlevel == 14)
        {
            for (int i = 0; i < 14; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[14].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 15; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 15)
        {
            if(fourteenlevel)
            {
                for (int i = 0; i < 15; i++)
                {
                    FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                    frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                    ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
                }
                FrameLayout frametempzero = (FrameLayout) lessons[15].getChildAt(0);
                frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
                ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
                for (int i = 16; i < lessons.length; i++)
                {
                    FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                    frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                    ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
                }
            }
            else
            {
                for (int i = 0; i < 13; i++)
                {
                    FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                    frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                    ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
                }
                FrameLayout frametemp2 = (FrameLayout) lessons[14].getChildAt(0);
                frametemp2.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp2.getChildAt(1)).setImageResource(0);
                FrameLayout frametempzero = (FrameLayout) lessons[13].getChildAt(0);
                frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
                ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
                for (int i = 15; i < lessons.length; i++)
                {
                    FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                    frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                    ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
                }
            }
        }
        else if (currentlevel == 16)
        {
            for (int i = 0; i < 16; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[16].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 17; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 17)
        {
            for (int i = 0; i < 17; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[17].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 18; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 18)
        {
            for (int i = 0; i < 18; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[18].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 19; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 19)
        {
            for (int i = 0; i < 19; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[19].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 20; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 20)
        {
            for (int i = 0; i < 20; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[20].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 21; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 21)
        {
            for (int i = 0; i < 21; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[21].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 22; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 22)
        {
            for (int i = 0; i < 22; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[22].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 23; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 23)
        {
            for (int i = 0; i < 23; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[23].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 24; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 24)
        {
            for (int i = 0; i < 24; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[24].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 25; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 25)
        {
            for (int i = 0; i < 25; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[25].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 26; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 26)
        {
            for (int i = 0; i < 26; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[26].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 27; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 27)
        {
            for (int i = 0; i < 27; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[27].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 28; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 28)
        {
            for (int i = 0; i < 28; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[28].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 29; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 29)
        {
            for (int i = 0; i < 29; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[29].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 30; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 30)
        {
            for (int i = 0; i < 30; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[30].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 31; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 31)
        {
            for (int i = 0; i < 31; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[31].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 32; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 32)
        {
            for (int i = 0; i < 32; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[32].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 33; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 33)
        {
            for (int i = 0; i < 33; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[33].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 34; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 34)
        {
            for (int i = 0; i < 34; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[34].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 35; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 35)
        {
            for (int i = 0; i < 35; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[35].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 36; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 36)
        {
            for (int i = 0; i < 36; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[36].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 37; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 37)
        {
            for (int i = 0; i < 37; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[37].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 38; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 38)
        {
            for (int i = 0; i < 38; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[38].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 39; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 39)
        {
            for (int i = 0; i < 39; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[39].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 40; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 40)
        {
            for (int i = 0; i < 40; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[40].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 41; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 41)
        {
            for (int i = 0; i < 41; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[41].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 42; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 42)
        {
            for (int i = 0; i < 42; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[42].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 43; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 43)
        {
            for (int i = 0; i < 43; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[43].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
            for (int i = 44; i < lessons.length; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circlegrey));
                ((ImageView)frametemp.getChildAt(1)).setImageDrawable(AppCompatResources.getDrawable(getContext(),R.drawable.lock));
            }
        }
        else if (currentlevel == 44)
        {
            for (int i = 0; i < 44; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
            FrameLayout frametempzero = (FrameLayout) lessons[44].getChildAt(0);
            frametempzero.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circleblue));
            ((ImageView)frametempzero.getChildAt(1)).setImageResource(0);
        }
        else if (currentlevel == 45)
        {
            for (int i = 0; i < 45; i++)
            {
                FrameLayout frametemp = (FrameLayout) lessons[i].getChildAt(0);
                frametemp.getChildAt(0).setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.circle));
                ((ImageView)frametemp.getChildAt(1)).setImageResource(0);
            }
        }
    }
}
