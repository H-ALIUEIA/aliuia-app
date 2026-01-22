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

    private LinearLayout scrollayout;
    private FloatingActionButton g;
    private LinearLayout selectorlayout;
    private FrameLayout learnprophecieslayout;
    private LinearLayout learnbiblelayout;
    private LinearLayout unlearnlayout;
    private LinearLayout levels;
    private MaterialCardView card1;
    private MaterialCardView card2;
    private MaterialCardView card3;
    private int location = 0;

    public LinearLayout[] lessons = new LinearLayout[45];
    public int currentlyselectedbook = 0;
    public ScrollView prophecyscroll;

    public LinearLayout twoandthree;
    public LinearLayout twelveandthreeteen;
    public LinearLayout fourteenandfifteen;
    public ImageView prophecydaysindicator;
    public int pressed = 0;
    public TextView zerooutof;
    public ImageCarousel carousel;

    public LVideoFragment()
    {
    }

    public void learnprophecies()
    {
        location = 1;
        selectorlayout.setVisibility(View.GONE);
        learnprophecieslayout.setVisibility(View.VISIBLE);
        fabbutton();
        lessonschecker();
    }

    public void learnbible()
    {
        location = 2;
        selectorlayout.setVisibility(View.GONE);
        learnbiblelayout.setVisibility(View.VISIBLE);
        fabbutton();
        addbooks();
    }

    public void unlearn()
    {
        location = 4;
        selectorlayout.setVisibility(View.GONE);
        unlearnlayout.setVisibility(View.VISIBLE);
        fabbutton();
    }

    public void addbooks()
    {
        levels.removeAllViews();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.CENTER;
        ConstraintLayout coordinatorLayout = new ConstraintLayout(getContext());
        coordinatorLayout.setLayoutParams(lp);
        ConstraintLayout.LayoutParams lp2 = new ConstraintLayout.LayoutParams(0, ConstraintLayout.LayoutParams.MATCH_PARENT);
        lp2.bottomToBottom = coordinatorLayout.getId();
        lp2.topToTop = coordinatorLayout.getId();
        lp2.endToEnd = coordinatorLayout.getId();
        lp2.startToStart = coordinatorLayout.getId();
        lp2.horizontalBias = 1;
        lp2.verticalBias = 1;
        //levels.setBackgroundColor(Color.parseColor("#555555"));
        carousel = new ImageCarousel(getContext(),null);
        carousel.setLayoutParams(lp2);
        carousel.registerLifecycle(getLifecycle());
        carousel.setCarouselType(CarouselType.BLOCK);
        carousel.setInfiniteCarousel(false);
        carousel.setScaleOnScroll(true);
        carousel.setCarouselListener(new CarouselListener()
        {

            @Override
            public void onLongClick(int i, @NonNull CarouselItem carouselItem)
            {

            }

            @Override
            public void onBindViewHolder(@NonNull ViewBinding viewBinding, @NonNull CarouselItem carouselItem, int i)
            {

            }

            @Nullable
            @Override
            public ViewBinding onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup)
            {
                return null;
            }

            @Override
            public void onClick(int position, @NotNull CarouselItem carouselItem)
            {
                //Προσοχη!!! remove if statement for furture
                if(position == 0)
                {
                    if(pressed == 0)
                    {
                        pressed = 1;
                        location = 3;
                        currentlyselectedbook = position + 1;
                        new json2().execute(getResources().getString(R.string.server) + "/api/getlevellist/" + String.valueOf(position + 1));
                        fabbutton();
                        return;
                    }
                }
                else
                {
                    return;
                }
            }
        });

        carousel.setCarouselGravity(CarouselGravity.CENTER);
        List<CarouselItem> list = new ArrayList<>();

        list.add(
            new CarouselItem(
                R.drawable.lesson1,
                "Αμαρτία και Σωτηρία"
            )
        );
        list.add(
            new CarouselItem(
                R.drawable.coming_soon,
                ""
            )
        );
        carousel.setImageScaleType(ImageView.ScaleType.CENTER_INSIDE);
        carousel.setData(list);
        coordinatorLayout.addView(carousel);
        levels.addView(coordinatorLayout);
    }

    public int dpToPx(int dp)
    {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public void addlevels(List<String> titles, List<String> images, List<List<String>> levelslist)
    {
        SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.localstorage), Context.MODE_PRIVATE);
        int savedlevel = sharedPref.getInt("level",0);
        levels.removeAllViews();
        for(int i = 0; i < titles.size(); i++)
        {
            int levelspecific = 0;
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setPadding(0, 0, 0, 150);
            ImageView button = new ImageView(getContext());
            LinearLayout.LayoutParams buttonlp = new LinearLayout.LayoutParams(250, 250);
            button.setLayoutParams(buttonlp);
            button.setPadding(50,50,50,50);
            if(!(offlinegetter.offlinechecker(getContext(),2)))
            {
                AsyncTask<String, Void, Bitmap> execute = new DownloadImageTask(button).execute(getResources().getString(R.string.storageserver) + "/" + images.get(i));
            }
            else
            {
                try
                {
                    Bitmap myBitmap = BitmapFactory.decodeFile(getContext().getApplicationInfo().dataDir + "/files/lessons/" + images.get(i));
                    button.setImageBitmap(myBitmap);
                }
                catch (Exception e){}
            }
            TextView title = new TextView(getContext());
            title.setLayoutParams(lp);
            ProgressBar progressBar = new ProgressBar(new ContextThemeWrapper(getContext(), android.R.style.Widget_Holo_ProgressBar_Horizontal),null,0);
            progressBar.setLayoutParams(lp);
            progressBar.setIndeterminate(false);
            TextView leveldetails = new TextView(getContext());
            leveldetails.setLayoutParams(lp);
            title.setText(titles.get(i).replace("\\n", System.getProperty("line.separator")));
            title.setGravity(Gravity.CENTER);
            boolean locked = false;
            int calculatetemp = Integer.parseInt(levelslist.get(i).get(0)) + levelslist.get(i).size()-1;
            if(savedlevel < calculatetemp-levelslist.get(i).size())
            {
                locked = true;
                progressBar.setMax(levelslist.get(i).size());
                progressBar.setProgress(0);
                button.setBackgroundResource(R.drawable.circlegrey);
                leveldetails.setText("0/"+String.valueOf(levelslist.get(i).size()));
            }
            else if (savedlevel >= calculatetemp)
            {
                locked = false;
                progressBar.setMax(levelslist.get(i).size());
                progressBar.setProgress(levelslist.get(i).size());
                button.setBackgroundResource(R.drawable.circle);
                leveldetails.setText(String.valueOf(levelslist.get(i).size())+"/"+String.valueOf(levelslist.get(i).size()));
            }
            else
            {
                locked = false;
                button.setBackgroundResource(R.drawable.circleblue);
                if(savedlevel == 0)
                {
                    progressBar.setMax(levelslist.get(i).size());
                    progressBar.setProgress(0);
                    leveldetails.setText(0 + "/" + String.valueOf(levelslist.get(i).size()));
                }
                else
                {
                    levelspecific = savedlevel + 1 - Integer.parseInt(levelslist.get(i).get(0));
                    progressBar.setMax(levelslist.get(i).size());
                    progressBar.setProgress(levelspecific);
                    leveldetails.setText(String.valueOf(savedlevel + 1 - Integer.parseInt(levelslist.get(i).get(0))) + "/" + String.valueOf(levelslist.get(i).size()));
                }
            }
            final int levelspecificfinal = levelspecific;
            //leveldetails.setText(String.valueOf(levelslist.get(i).getFirst().toString())+"/"+String.valueOf(levelslist.get(i).size()));
            int k = i;
            boolean lockedfinal = locked;
            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(!(lockedfinal))
                    {
                        Intent in = new Intent(getActivity(), Levelplay.class);
                        in.putExtra("level", k);
                        StringBuilder stringtemp = new StringBuilder();
                        for (int tempi = 0; tempi < levelslist.get(k).size(); tempi++)
                        {
                            stringtemp.append(levelslist.get(k).get(tempi));
                            stringtemp.append("/");
                        }
                        in.putExtra("level", stringtemp.toString());
                        in.putExtra("levelspecific", levelspecificfinal);
                        startActivity(in);
                    }
                }
            });
            linearLayout.addView(button);
            linearLayout.addView(title);
            linearLayout.addView(progressBar);
            linearLayout.addView(leveldetails);
            levels.addView(linearLayout);
        }
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

    public void fabbutton()
    {
        if(location == 0)
        {
            g.setImageResource(R.drawable.setting);
        }
        else if(location == 1)
        {
            g.setImageResource(R.drawable.setting);
        }
        else if(location == 2)
        {
            g.setImageResource(R.drawable.enter);
        }
        else if(location == 3)
        {
            g.setImageResource(R.drawable.baseline_apps_24);
        }
        else if(location == 4)
        {
            g.setImageResource(R.drawable.setting);
        }
    }

    public void fabbuttoncreator()
    {
        g = getActivity().findViewById(R.id.fab);
        g.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(location == 0)
                {
                    Intent myIntent = new Intent(getActivity(), Settings.class);
                    getActivity().startActivity(myIntent);
                }
                else if(location == 1)
                {
                    Intent myIntent = new Intent(getActivity(), Settings.class);
                    getActivity().startActivity(myIntent);
                }
                else if (location == 2)
                {
                    pressed = 1;
                    location = 3;
                    int position = carousel.getCurrentPosition();
                    currentlyselectedbook = position + 1;
                    new json2().execute(getResources().getString(R.string.server) + "/api/getlevellist/" + String.valueOf(currentlyselectedbook));
                    fabbutton();
                }
                else if (location == 3)
                {
                    location = 2;
                    learnbible();
                }
                else
                {
                    Intent myIntent = new Intent(getActivity(), Settings.class);
                    getActivity().startActivity(myIntent);
                }
            }
        });
    }

    public void lessonschecker()
    {
        SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.localstorage), Context.MODE_PRIVATE);
        int currentlevel = sharedPref.getInt("prophecylevel",0);
        boolean secondlevel = sharedPref.getBoolean("secondprophecylevel",false);
        boolean twelvelevel = sharedPref.getBoolean("twelveprophecylevel",false);
        boolean fourteenlevel = sharedPref.getBoolean("fourteenprophecylevel",false);
        ViewTreeObserver vto = prophecyscroll.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
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

    public int getlocation()
    {
        return location;
    }

    @Override
    public void onResume()
    {
        if(location == 1)
        {
            lessonschecker();
        }
        else if (location == 3)
        {
            new json2().execute(getResources().getString(R.string.server)+"/api/getlevellist/"+String.valueOf(currentlyselectedbook));
        }
        super.onResume();
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

    public void levelindicator()
    {
        int[][][] levels = {
            {
                {1,2,3,4,5,6,7,8,9,10,11,12,13},
                {14,15,16,17,18,19,20,21,22,23},
                {24,25,26,27,28,29,30,31,32,33,34,35,36,37,38},
                {39,40,41,42,43,44,45,46},
                {47,48,49,50,51,52,53,54}
            },
        };
        SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.localstorage), Context.MODE_PRIVATE);
        int savedlevel = sharedPref.getInt("level", 0);
        if(savedlevel < levels[0][0][levels[0][0].length-1])
        {
            zerooutof.setText("0/5");
        }
        else if (savedlevel < levels[0][1][levels[0][1].length-1])
        {
            zerooutof.setText("1/5");
        }
        else if (savedlevel < levels[0][2][levels[0][2].length-1])
        {
            zerooutof.setText("2/5");
        }
        else if (savedlevel < levels[0][3][levels[0][3].length-1])
        {
            zerooutof.setText("3/5");
        }
        else if (savedlevel < levels[0][4][levels[0][4].length-1])
        {
            zerooutof.setText("4/5");
        }
        else
        {
            zerooutof.setText("5/5");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_l_video, container, false);
        scrollayout = (LinearLayout) rootView.findViewById(R.id.scrollayout);
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
        prophecyscroll = (ScrollView) rootView.findViewById(R.id.prophecyscroll);
        twoandthree = (LinearLayout) rootView.findViewById(R.id.secondsandthirdlayout);
        twelveandthreeteen = (LinearLayout) rootView.findViewById(R.id.twelveandthreeteen);
        fourteenandfifteen = (LinearLayout) rootView.findViewById(R.id.fourteenandfifteen);
        prophecydaysindicator = (ImageView) rootView.findViewById(R.id.prophecydaysindicator);
        zerooutof = (TextView) rootView.findViewById(R.id.zerooutof);
        prophecydayindicator();
        levelindicator();
        fabbuttoncreator();
        fabbutton();
        selectorlayout = (LinearLayout) rootView.findViewById(R.id.menuselector);
        learnprophecieslayout = (FrameLayout) rootView.findViewById(R.id.framelayout);
        learnbiblelayout = (LinearLayout) rootView.findViewById(R.id.learnbiblelayout);
        unlearnlayout = (LinearLayout) rootView.findViewById(R.id.unlearnlayout);
        levels = (LinearLayout) rootView.findViewById(R.id.levels);
        card1 = (MaterialCardView) rootView.findViewById(R.id.card);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                learnprophecies();
            }
        });
        card2 = (MaterialCardView) rootView.findViewById(R.id.card2);
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                learnbible();
            }
        });
        card3 = (MaterialCardView) rootView.findViewById(R.id.card3);
        card3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                unlearn();
            }
        });
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener()
        {
        @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK)
                {
                    if(location == 2 || location == 1)
                    {
                        location = 0;
                        unlearnlayout.setVisibility(View.GONE);
                        learnbiblelayout.setVisibility(View.GONE);
                        learnprophecieslayout.setVisibility(View.GONE);
                        selectorlayout.setVisibility(View.VISIBLE);
                        prophecydayindicator();
                        levelindicator();
                        fabbutton();
                        pressed = 0;
                        return true;
                    }
                    else if(location == 3)
                    {
                        location = 2;
                        learnbible();
                        return true;
                    }
                    else if(location == 4)
                    {
                        location = 0;
                        unlearnlayout.setVisibility(View.GONE);
                        learnbiblelayout.setVisibility(View.GONE);
                        learnprophecieslayout.setVisibility(View.GONE);
                        selectorlayout.setVisibility(View.VISIBLE);
                        prophecydayindicator();
                        levelindicator();
                        fabbutton();
                        pressed = 0;
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                return false;
            }
        });
        return rootView;
    }

    public class json2 extends AsyncTask<String, String, String>
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
                    String offlinestring = offlinegetter.levellistgetter(getContext(), Integer.parseInt(params[0].substring(params[0].indexOf("getlevellist")+13,params[0].length())));
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
                int savedlevel = sharedPref.getInt("level", 0);
                JSONArray obj = new JSONArray(jso);
                List<String> titles = new ArrayList<String>();
                List<String> images = new ArrayList<String>();
                List<List<String>> levels = new ArrayList<List<String>>();
                for(int i=0; i<obj.length(); i++)
                {
                    JSONObject obj2 = (JSONObject) obj.get(i);
                    titles.add(obj2.getString("title"));
                    images.add(obj2.getString("image"));
                    JSONArray groupsarray = obj2.getJSONArray("groups");
                    List<String> levelstemp = new ArrayList<String>();
                    for(int j = 0; j< groupsarray.length(); j++)
                    {
                        levelstemp.add(groupsarray.getJSONObject(j).getString("id"));
                    }
                    levels.add(levelstemp);
                }
                addlevels(titles, images, levels);
            }
            catch (Exception e){}
        }
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap>
    {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage)
        {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls)
        {
            String urldisplay = urls[0];
            Bitmap myImage = null;
            try
            {
                InputStream in = new java.net.URL(urldisplay).openStream();
                myImage = BitmapFactory.decodeStream(in);
            }
            catch (Exception e) {}
            return myImage;
        }

        protected void onPostExecute(Bitmap result)
        {
            bmImage.setImageBitmap(result);
        }
    }
}
