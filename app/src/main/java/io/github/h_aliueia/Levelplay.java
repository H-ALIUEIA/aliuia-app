package io.github.h_aliueia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Space;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.text.HtmlCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nex3z.flowlayout.FlowLayout;

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Levelplay extends AppCompatActivity
{
    public TextView questiontextview;
    public String level;
    public int levelspecific;
    public LinearLayout buttonsspace;
    public int selectedone = -1;
    public int wrong = 0;
    public int won = 0;
    public TextView levelx;

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
        setContentView(R.layout.activity_levelplay);
        EdgeToEdge.enable(this);
        questiontextview = (TextView)findViewById(R.id.question);
        buttonsspace = (LinearLayout)findViewById(R.id.buttonsspace);
        levelx = (TextView)findViewById(R.id.levelx);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            level = extras.getString("level","");
            levelspecific = extras.getInt("levelspecific", 0);
            new json().execute(getResources().getString(R.string.server)+"/api/level/"+level.split("/")[levelspecific]);
            levelx.setText("Επίπεδο "+String.valueOf(levelspecific+1)+"/"+level.split("/").length);
        }
    }

    public void openbible(View view)
    {
        Intent myIntent = new Intent(Levelplay.this, bibleactivity.class);
        myIntent.putExtra("hint", "1:1:0");
        startActivity(myIntent);
    }

    public int dpToPx(int dp)
    {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public void addimages(String[] imagesx, String solution, String hint, String aftertext)
    {
        GridLayout gridlayout = new GridLayout(Levelplay.this);
        LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0,5);
        layoutparams.setMargins(dpToPx(5),dpToPx(5),dpToPx(5),dpToPx(5));
        layoutparams.gravity = Gravity.CENTER;
        gridlayout.setLayoutParams(layoutparams);
        gridlayout.setColumnCount(2);
        gridlayout.setRowCount(2);
        gridlayout.setRowOrderPreserved(true);
        gridlayout.setColumnOrderPreserved(true);
        List<CardView> buttons = new ArrayList<CardView>();
        for(int i = 0; i<imagesx.length;i++)
        {
            LinearLayout cardholder = new LinearLayout(Levelplay.this);
            cardholder.setPadding(dpToPx(5),dpToPx(5),dpToPx(5),dpToPx(5));
            CardView cardview = new CardView(Levelplay.this);
            cardview.setPreventCornerOverlap(true);
            cardview.setRadius(dpToPx(15));
            cardview.setElevation(0);
            cardview.setPadding(dpToPx(5),dpToPx(5),dpToPx(5),dpToPx(5));
            cardview.setBackground(getResources().getDrawable(R.drawable.round_back_non_white_stroke2_10));
            cardview.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(wrong == 0)
                    {
                        for (int i = 0; i < buttons.size(); i++)
                        {
                            if (view == buttons.get(i))
                            {
                                selectedone = i;
                                cardview.setBackground(getResources().getDrawable(R.drawable.round_back_non_and_blue_stroke2_10));
                            }
                            else
                            {
                                buttons.get(i).setBackground(getResources().getDrawable(R.drawable.round_back_non_white_stroke2_10));
                            }
                        }
                    }
                }
            });
            ImageView imageview = new ImageView(Levelplay.this);
            int width = 400;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            {
                Point size = new Point();
                getWindowManager().getDefaultDisplay().getSize(size);
                width = size.x;
            }else{
                Display d = getWindowManager().getDefaultDisplay();
                width= d.getWidth();
            }
            LinearLayout.LayoutParams layoutparams2 = new LinearLayout.LayoutParams((width/2)-dpToPx(15), LinearLayout.LayoutParams.WRAP_CONTENT);
            imageview.setLayoutParams(layoutparams2);
            imageview.setScaleType(ImageView.ScaleType.CENTER);
            imageview.setAdjustViewBounds(true);
            imageview.setPadding(dpToPx(5),dpToPx(5),dpToPx(5),dpToPx(5));
            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.localstorage), Context.MODE_PRIVATE);
            if(!(offlinegetter.offlinechecker(Levelplay.this,2)))
            {
                new DownloadImageTask(imageview).execute(getResources().getString(R.string.storageserver) + "/" + imagesx[i]);
            }
            else
            {
                imageview.setImageBitmap(BitmapFactory.decodeFile(getApplicationInfo().dataDir+"/files/lessons/"+imagesx[i]));
            }
            cardview.addView(imageview);
            cardholder.addView(cardview);
            gridlayout.addView(cardholder);
            buttons.add(cardview);
        }
        buttonsspace.addView(gridlayout);
        addcheckandread(solution,hint, buttons,1, aftertext);
    }

    public void addcheckandread(String solution, String hint, List<CardView> buttons, int type, String aftertext)
    {
        AppCompatButton appcompatbutton = new AppCompatButton(Levelplay.this);
        LinearLayout.LayoutParams layoutparams3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0,1);
        layoutparams3.setMargins(dpToPx(20),dpToPx(20),dpToPx(20),0);
        appcompatbutton.setLayoutParams(layoutparams3);
        appcompatbutton.setText("ελεγξε");
        appcompatbutton.setTextColor(Color.parseColor("#000000"));
        appcompatbutton.setBackground(getResources().getDrawable(R.drawable.round_back_green_stroke2_10));
        appcompatbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                NumberPicker num = null;
                if(type == 2)
                {
                    num = (NumberPicker) buttons.get(0).getChildAt(0);
                    selectedone = num.getValue()-1;
                }
                if (selectedone != -1 || type == 2)
                {
                    if (selectedone + 1 == Integer.parseInt(solution))
                    {
                        if(won == 0)
                        {
                            won = 1;
                            final MediaPlayer mp = MediaPlayer.create(Levelplay.this, R.raw.right);
                            mp.start();
                            SharedPreferences sharedPref = Levelplay.this.getSharedPreferences(getString(R.string.localstorage), Context.MODE_PRIVATE);
                            int savedlevel = sharedPref.getInt("level", 0);
                            if (Integer.parseInt(level.split("/")[levelspecific]) > savedlevel)
                            {
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putInt("level", Integer.parseInt(level.split("/")[levelspecific]));
                                editor.apply();
                            }
                            wrong = 1;
                            if(type == 1)
                            {
                                buttons.get(selectedone).setBackground(getResources().getDrawable(R.drawable.round_back_green_stroke2_10));
                            }
                            else if (type == 2)
                            {
                                buttons.get(0).setBackground(getResources().getDrawable(R.drawable.round_back_green_stroke2_10));
                            }
                            else if (type == 3)
                            {
                                buttons.get(selectedone).getChildAt(0).setBackground(getResources().getDrawable(R.drawable.round_back_green_stroke2_10));
                            }
                            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                            {
                                public void onCompletion(MediaPlayer mp)
                                {
                                    mp.release();
                                    if(aftertext.strip().equals(""))
                                    {
                                        if(levelspecific < level.split("/").length-1)
                                        {
                                            finish();
                                            Intent in = new Intent(Levelplay.this, Levelplay.class);
                                            in.putExtra("level", level);
                                            in.putExtra("levelspecific", levelspecific + 1);
                                            startActivity(in);
                                        }
                                        else
                                        {
                                            finish();
                                        }
                                    }
                                    else
                                    {
                                        finish();
                                        Intent intent = new Intent(Levelplay.this, aftertext.class);
                                        intent.putExtra("aftertext", aftertext);
                                        intent.putExtra("level", level);
                                        intent.putExtra("levelspecific", levelspecific);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                    }
                    else
                    {
                        if(wrong == 0)
                        {
                            final MediaPlayer mp = MediaPlayer.create(Levelplay.this, R.raw.wrong);
                            mp.start();
                            if(type == 1)
                            {
                                buttons.get(selectedone).setBackground(getResources().getDrawable(R.drawable.round_back_red_stroke2_10));
                            }
                            else if (type == 2)
                            {
                                buttons.get(0).setBackground(getResources().getDrawable(R.drawable.round_back_red_stroke2_10));
                                num.setMinValue(selectedone+1);
                                num.setMaxValue(selectedone+1);
                                num.setFocusable(false);
                                num.setClickable(false);
                                buttons.get(0).setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
                            }
                            else if (type == 3)
                            {
                                buttons.get(selectedone).getChildAt(0).setBackground(getResources().getDrawable(R.drawable.round_back_red_stroke2_10));
                            }
                            wrong = 1;
                            appcompatbutton.setText("δοκιμασε ξανα ⟳");
                            appcompatbutton.setBackground(getResources().getDrawable(R.drawable.round_back_red_stroke2_10));
                            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                            {
                                public void onCompletion(MediaPlayer mp)
                                {
                                    mp.release();
                                }
                            });
                        }
                        else
                        {
                            wrong = 0;
                            appcompatbutton.setText("ελεγξε");
                            appcompatbutton.setBackground(getResources().getDrawable(R.drawable.round_back_green_stroke2_10));
                            if(type == 1)
                            {
                                buttons.get(selectedone).setBackground(getResources().getDrawable(R.drawable.round_back_non_and_blue_stroke2_10));
                            }
                            else if (type == 2)
                            {
                                buttons.get(0).setBackground(getResources().getDrawable(R.drawable.round_back_non_white_stroke2_10));
                                num.setMinValue(0);
                                num.setMaxValue(999);
                                num.setFocusable(true);
                                num.setClickable(true);
                                buttons.get(0).setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
                            }
                            else if (type == 3)
                            {
                                buttons.get(selectedone).getChildAt(0).setBackground(getResources().getDrawable(R.drawable.round_back_non_and_blue_stroke2_10));
                            }
                        }
                    }
                }
            }
        });
        buttonsspace.addView(appcompatbutton);
        AppCompatButton appcompatbutton2 = new AppCompatButton(Levelplay.this);
        LinearLayout.LayoutParams layoutparams4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0,1);
        layoutparams4.setMargins(dpToPx(20),dpToPx(20),dpToPx(20),0);
        appcompatbutton2.setLayoutParams(layoutparams4);
        appcompatbutton2.setText("διαβαστε");
        appcompatbutton2.setTextColor(Color.parseColor("#000000"));
        appcompatbutton2.setBackground(getResources().getDrawable(R.drawable.round_back_bible_stroke2_10));
        appcompatbutton2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Levelplay.this, bibleactivity.class);
                intent.putExtra("hint", hint);
                startActivity(intent);
            }
        });
        buttonsspace.addView(appcompatbutton2);
    }

    public void addnumberquenstion(String hint, String solution, String aftertext)
    {
        List<CardView> buttons = new ArrayList<CardView>();
        LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0,5);
        CardView cardview = new CardView(Levelplay.this);
        cardview.setPreventCornerOverlap(true);
        cardview.setRadius(dpToPx(15));
        cardview.setElevation(0);
        cardview.setPadding(dpToPx(5),dpToPx(5),dpToPx(5),dpToPx(5));
        cardview.setBackground(getResources().getDrawable(R.drawable.round_back_non_white_stroke2_10));
        cardview.setLayoutParams(layoutparams);
        NumberPicker num = new NumberPicker(Levelplay.this);
        num.setMinValue(0);
        num.setMaxValue(999);
        cardview.addView(num);
        buttonsspace.addView(cardview);
        buttons.add(cardview);
        addcheckandread(solution, hint, buttons,2, aftertext);
    }

    public void addquestions(String[] answersx, String solution, String hint, String aftertext)
    {
        LinearLayout linearLayout = new LinearLayout(Levelplay.this);
        LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0,5);
        List<CardView> buttons = new ArrayList<CardView>();
        for(int i = 0; i<answersx.length;i++)
        {
            AppCompatButton appcompatbutton = new AppCompatButton(Levelplay.this);
            LinearLayout.LayoutParams layoutparams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0,1);
            layoutparams2.setMargins(dpToPx(20),dpToPx(20),dpToPx(20),0);
            CardView cardView = new CardView(Levelplay.this);
            cardView.setLayoutParams(layoutparams2);
            //appcompatbutton.setLayoutParams(layoutparams2);
            appcompatbutton.setText(answersx[i]);
            appcompatbutton.setAllCaps(false);
            appcompatbutton.setTextColor(Color.parseColor("#000000"));
            appcompatbutton.setBackground(getResources().getDrawable(R.drawable.round_back_white_stroke2_10));
            appcompatbutton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(wrong == 0)
                    {
                        for (int i = 0; i < buttons.size(); i++)
                        {
                            if (view == buttons.get(i).getChildAt(0))
                            {
                                selectedone = i;
                                view.setBackground(getResources().getDrawable(R.drawable.round_back_non_and_blue_stroke2_10));
                            }
                            else
                            {
                                buttons.get(i).getChildAt(0).setBackground(getResources().getDrawable(R.drawable.round_back_white_stroke2_10));
                            }
                        }
                    }
                }
            });
            cardView.addView(appcompatbutton);
            buttonsspace.addView(cardView);
            buttons.add(cardView);
        }
        addcheckandread(solution,hint,buttons,3, aftertext);
    }

    public void addzeros(String answert, String aftertext)
    {
        LinearLayout answertextlayout = new LinearLayout(Levelplay.this);
        LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0,5);
        answertextlayout.setOrientation(LinearLayout.VERTICAL);
        answertextlayout.setLayoutParams(layoutparams);
        AppCompatButton appcompatbutton2 = new AppCompatButton(Levelplay.this);
        LinearLayout.LayoutParams layoutparams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0,1);
        layoutparams2.setMargins(dpToPx(20),dpToPx(20),dpToPx(20),0);
        appcompatbutton2.setLayoutParams(layoutparams2);
        appcompatbutton2.setText(answert.replace("\\n", System.getProperty("line.separator")));
        appcompatbutton2.setTextAlignment(AppCompatButton.TEXT_ALIGNMENT_CENTER);
        appcompatbutton2.setAllCaps(false);
        appcompatbutton2.setTextColor(Color.parseColor("#000000"));
        appcompatbutton2.setBackground(getResources().getDrawable(R.drawable.round_back_white_stroke2_10));
        Space space = new Space(Levelplay.this);
        space.setLayoutParams(layoutparams2);
        Space space2 = new Space(Levelplay.this);
        space.setLayoutParams(layoutparams2);
        Space space3 = new Space(Levelplay.this);
        space.setLayoutParams(layoutparams2);
        AppCompatButton appcompatbutton = new AppCompatButton(Levelplay.this);
        LinearLayout.LayoutParams layoutparams3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0,1);
        layoutparams3.setMargins(dpToPx(20),dpToPx(20),dpToPx(20),0);
        appcompatbutton.setLayoutParams(layoutparams3);
        appcompatbutton.setText("συνεχεια");
        appcompatbutton.setTextColor(Color.parseColor("#000000"));
        appcompatbutton.setBackground(getResources().getDrawable(R.drawable.round_back_green_stroke2_10));
        appcompatbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(won == 0)
                {
                    won = 1;
                    final MediaPlayer mp = MediaPlayer.create(Levelplay.this, R.raw.right);
                    mp.start();
                    SharedPreferences sharedPref = Levelplay.this.getSharedPreferences(getString(R.string.localstorage), Context.MODE_PRIVATE);
                    int savedlevel = sharedPref.getInt("level", 0);
                    if (Integer.parseInt(level.split("/")[levelspecific]) > savedlevel)
                    {
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt("level", Integer.parseInt(level.split("/")[levelspecific]));
                        editor.apply();
                    }
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                    {
                        public void onCompletion(MediaPlayer mp)
                        {
                            mp.release();
                            if (aftertext.strip().equals(""))
                            {
                                if (levelspecific < level.split("/").length - 1)
                                {
                                    finish();
                                    Intent in = new Intent(Levelplay.this, Levelplay.class);
                                    in.putExtra("level", level);
                                    in.putExtra("levelspecific", levelspecific + 1);
                                    startActivity(in);
                                } else
                                {
                                    finish();
                                }
                            }
                            else
                            {
                                finish();
                                Intent intent = new Intent(Levelplay.this, aftertext.class);
                                intent.putExtra("aftertext", aftertext);
                                intent.putExtra("level", level);
                                intent.putExtra("levelspecific", levelspecific);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });
        answertextlayout.addView(appcompatbutton2);
        answertextlayout.addView(space);
        answertextlayout.addView(space2);
        answertextlayout.addView(space3);
        buttonsspace.addView(answertextlayout);
        buttonsspace.addView(appcompatbutton);
    }

    public void fillthetext(String[] answersarray, String hint, String aftertext)
    {
        DiffMatchPatch dmp = new DiffMatchPatch();
        LinkedList<DiffMatchPatch.Diff> diff = dmp.diffMain(answersarray[0], answersarray[1], false);
        LinearLayout holder = new LinearLayout(Levelplay.this);
        LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0,5);
        holder.setOrientation(LinearLayout.VERTICAL);
        holder.setLayoutParams(layoutparams);

        TextView bigtext = new TextView(Levelplay.this);
        LinearLayout.LayoutParams layoutparams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0,3);
        FlowLayout answersholder = new FlowLayout(Levelplay.this);
        LinearLayout.LayoutParams layoutparams4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1);
        answersholder.setLayoutParams(layoutparams4);
        answersholder.setGravity(Gravity.CENTER_HORIZONTAL);
        answersholder.setPadding(0,dpToPx(20),0,0);
        answersholder.setChildSpacing(dpToPx(10));
        answersholder.setRowSpacing(dpToPx(10));
        StringBuilder primarytext = new StringBuilder();
        List<AppCompatButton> appbuttons = new ArrayList<AppCompatButton>();
        int secondloop = 0;
        List<String> missingwords = new ArrayList<String>();
        for(int i = 0; i < diff.size(); i++)
        {
            if(diff.get(i).operation.equals(DiffMatchPatch.Operation.EQUAL))
            {
                primarytext.append(diff.get(i).text);
            }
            else if (diff.get(i).operation.equals(DiffMatchPatch.Operation.DELETE))
            {
                missingwords.add(diff.get(i).text);
                secondloop += 1;
                if(selectedone == -1 && secondloop == 1)
                {
                    primarytext.append("<font color=\"#00FF00\">"+"____"+"</font>");
                }
                else
                {
                    primarytext.append("<font color=\"#FF0000\">" + "____" + "</font>");
                }
                AppCompatButton appcompatbutton = new AppCompatButton(Levelplay.this);
                appcompatbutton.setText(diff.get(i).text);
                appcompatbutton.setAllCaps(false);
                appcompatbutton.setTextColor(Color.parseColor("#000000"));
                appcompatbutton.setBackground(getResources().getDrawable(R.drawable.round_back_non_white_stroke2_10));
                appcompatbutton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Button qwe = (Button) view;
                        if(qwe.getText().equals(missingwords.get(selectedone+1)))
                        {
                            if(selectedone == missingwords.size()-2)
                            {
                                AppCompatButton appcompatbutton = new AppCompatButton(Levelplay.this);
                                LinearLayout.LayoutParams layoutparams3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0,1);
                                layoutparams3.setMargins(dpToPx(20),dpToPx(20),dpToPx(20),0);
                                appcompatbutton.setLayoutParams(layoutparams3);
                                appcompatbutton.setText("συνεχεια");
                                appcompatbutton.setTextColor(Color.parseColor("#000000"));
                                appcompatbutton.setBackground(getResources().getDrawable(R.drawable.round_back_green_stroke2_10));
                                appcompatbutton.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View view)
                                    {
                                        if(won == 0)
                                        {
                                            won = 1;
                                            final MediaPlayer mp = MediaPlayer.create(Levelplay.this, R.raw.right);
                                            mp.start();
                                            SharedPreferences sharedPref = Levelplay.this.getSharedPreferences(getString(R.string.localstorage), Context.MODE_PRIVATE);
                                            int savedlevel = sharedPref.getInt("level", 0);
                                            if (Integer.parseInt(level.split("/")[levelspecific]) > savedlevel)
                                            {
                                                SharedPreferences.Editor editor = sharedPref.edit();
                                                editor.putInt("level", Integer.parseInt(level.split("/")[levelspecific]));
                                                editor.apply();
                                            }
                                            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                                            {
                                                public void onCompletion(MediaPlayer mp)
                                                {
                                                    mp.release();
                                                    if (aftertext.strip().equals(""))
                                                    {
                                                        if (levelspecific < level.split("/").length - 1)
                                                        {
                                                            finish();
                                                            Intent in = new Intent(Levelplay.this, Levelplay.class);
                                                            in.putExtra("level", level);
                                                            in.putExtra("levelspecific", levelspecific + 1);
                                                            startActivity(in);
                                                        } else
                                                        {
                                                            finish();
                                                        }
                                                    } else
                                                    {
                                                        finish();
                                                        Intent intent = new Intent(Levelplay.this, aftertext.class);
                                                        intent.putExtra("aftertext", aftertext);
                                                        intent.putExtra("level", level);
                                                        intent.putExtra("levelspecific", levelspecific);
                                                        startActivity(intent);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                                buttonsspace.addView(appcompatbutton,buttonsspace.getChildCount()-1);
                            }
                            final MediaPlayer mp = MediaPlayer.create(Levelplay.this, R.raw.put);
                            mp.start();
                            selectedone += 1;
                            qwe.setVisibility(View.GONE);
                            reloadtext(diff, bigtext);
                            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                            {
                                public void onCompletion(MediaPlayer mp)
                                {
                                    mp.release();
                                }
                            });
                        }
                        else
                        {
                            final MediaPlayer mp = MediaPlayer.create(Levelplay.this, R.raw.unput);
                            mp.start();
                            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                            {
                                public void onCompletion(MediaPlayer mp)
                                {
                                    mp.release();
                                }
                            });
                        }
                    }
                });
                appbuttons.add(appcompatbutton);
            }
            bigtext.setText(HtmlCompat.fromHtml(primarytext.toString(),HtmlCompat.FROM_HTML_MODE_LEGACY));
        }
        Collections.shuffle(appbuttons);
        for(int j = 0; j<appbuttons.size();j++)
        {
            answersholder.addView(appbuttons.get(j));
        }
        holder.addView(bigtext);
        holder.addView(answersholder);
        buttonsspace.addView(holder);
        AppCompatButton appcompatbutton2 = new AppCompatButton(Levelplay.this);
        LinearLayout.LayoutParams layoutparams5 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0,1);
        layoutparams5.setMargins(dpToPx(20),dpToPx(20),dpToPx(20),0);
        appcompatbutton2.setLayoutParams(layoutparams5);
        appcompatbutton2.setText("διαβαστε");
        appcompatbutton2.setTextColor(Color.parseColor("#000000"));
        appcompatbutton2.setBackground(getResources().getDrawable(R.drawable.round_back_bible_stroke2_10));
        appcompatbutton2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Levelplay.this, bibleactivity.class);
                intent.putExtra("hint", hint);
                startActivity(intent);
            }
        });
        buttonsspace.addView(appcompatbutton2);
    }

    public void goback(View view)
    {
        finish();
    }

    public void reloadtext(LinkedList<DiffMatchPatch.Diff> diff,TextView bigtext)
    {
        StringBuilder primarytext = new StringBuilder();
        int secondloop = 0;
        for(int i = 0; i < diff.size(); i++)
        {
            if (diff.get(i).operation.equals(DiffMatchPatch.Operation.EQUAL))
            {
                primarytext.append(diff.get(i).text);
            }
            else if (diff.get(i).operation.equals(DiffMatchPatch.Operation.DELETE))
            {
                secondloop += 1;
                if(secondloop-2<selectedone)
                {
                    primarytext.append(diff.get(i).text);
                }
                else if (selectedone == secondloop-2)
                {
                    primarytext.append("<font color=\"#00FF00\">" + "____" + "</font>");
                }
                else
                {
                    primarytext.append("<font color=\"#FF0000\">" + "____" + "</font>");
                }
            }
        }
        bigtext.setText(HtmlCompat.fromHtml(primarytext.toString(),HtmlCompat.FROM_HTML_MODE_LEGACY));
    }

    public void addspecialquestion(String imagequestion, String[] answersa, String solution, String hint, String aftertext)
    {
        LinearLayout holder = new LinearLayout(Levelplay.this);
        LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0,5);
        holder.setOrientation(LinearLayout.VERTICAL);
        holder.setLayoutParams(layoutparams);
        TextView bigtext = new TextView(Levelplay.this);
        String[] imagequestionsplited = imagequestion.split("\\[\\]");
        StringBuilder completeimagequestion = new StringBuilder();
        completeimagequestion.append(imagequestionsplited[0]);
        completeimagequestion.append("<font color=\"#00FF00\">" + "____" + "</font>");
        completeimagequestion.append(imagequestionsplited[1]);
        bigtext.setText(HtmlCompat.fromHtml(completeimagequestion.toString(),HtmlCompat.FROM_HTML_MODE_LEGACY));
        holder.addView(bigtext);
        List<CardView> buttons = new ArrayList<CardView>();
        for(int i = 0; i<answersa.length;i++)
        {
            AppCompatButton appcompatbutton = new AppCompatButton(Levelplay.this);
            LinearLayout.LayoutParams layoutparams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0,1);
            layoutparams2.setMargins(dpToPx(20),dpToPx(20),dpToPx(20),0);
            CardView cardView = new CardView(Levelplay.this);
            cardView.setLayoutParams(layoutparams2);
            appcompatbutton.setText(answersa[i]);
            appcompatbutton.setAllCaps(false);
            appcompatbutton.setTextColor(Color.parseColor("#000000"));
            appcompatbutton.setBackground(getResources().getDrawable(R.drawable.round_back_white_stroke2_10));
            appcompatbutton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(wrong == 0)
                    {
                        for (int i = 0; i < buttons.size(); i++)
                        {
                            if (view == buttons.get(i).getChildAt(0))
                            {
                                selectedone = i;
                                view.setBackground(getResources().getDrawable(R.drawable.round_back_non_and_blue_stroke2_10));
                            }
                            else
                            {
                                buttons.get(i).getChildAt(0).setBackground(getResources().getDrawable(R.drawable.round_back_white_stroke2_10));
                            }
                        }
                    }
                }
            });
            cardView.addView(appcompatbutton);
            holder.addView(cardView);
            buttons.add(cardView);
        }
        buttonsspace.addView(holder);
        addcheckandread(solution,hint,buttons,3, aftertext);
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
                SharedPreferences sharedPref = Levelplay.this.getSharedPreferences(getString(R.string.localstorage), Context.MODE_PRIVATE);
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
                if(offlinegetter.offlinechecker(Levelplay.this,0))
                {
                    String url = params[0];
                    String offlinestring = offlinegetter.levelgetter(Levelplay.this, Integer.parseInt(url.substring(url.indexOf("api/level/")+10)));
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
                JSONObject obj = new JSONObject(jso);
                int type = obj.getInt("type");
                if(type == 0)
                {
                    String questiontitle = obj.getString("question");
                    questiontextview.setText(questiontitle.replace("\\n", System.getProperty("line.separator")));
                    String answertitle = obj.getString("answer");
                    String aftertext = obj.optString("aftertext", "");
                    addzeros(answertitle,aftertext);
                }
                if(type == 1)
                {
                    String questiontitle = obj.getString("question");
                    questiontextview.setText(questiontitle.replace("\\n", System.getProperty("line.separator")));
                    String solution = obj.getString("solution");
                    String hint = obj.getString("hint");
                    String aftertext = obj.optString("aftertext", "");
                    JSONObject images = obj.getJSONObject("images");
                    JSONArray imagesanswers = images.getJSONArray("answers");
                    String[] imageanswersarray = new String[imagesanswers.getJSONObject(0).length()];
                    for(int i = 1; i< imagesanswers.getJSONObject(0).length()+1; i++)
                    {
                        imageanswersarray[i-1] = imagesanswers.getJSONObject(0).getString(String.valueOf(i));
                    }
                    addimages(imageanswersarray,solution,hint,aftertext);
                }
                else if (type == 2)
                {
                    String questiontitle = obj.getString("question");
                    questiontextview.setText(questiontitle.replace("\\n", System.getProperty("line.separator")));
                    String hint = obj.getString("hint");
                    String solution = obj.getString("solution");
                    String aftertext = obj.optString("aftertext", "");
                    addnumberquenstion(hint, solution,aftertext);
                }
                else if (type == 3)
                {
                    String questiontitle = obj.getString("question");
                    questiontextview.setText(questiontitle.replace("\\n", System.getProperty("line.separator")));
                    String aftertext = obj.optString("aftertext", "");
                    String hint = obj.getString("hint");
                    String solution = obj.getString("solution");
                    JSONObject answers = obj.getJSONObject("answers");
                    JSONArray answersanswers = answers.getJSONArray("answers");
                    String[] answeranswersarray = new String[answersanswers.getJSONObject(0).length()];
                    for(int i = 1; i< answersanswers.getJSONObject(0).length()+1; i++)
                    {
                        answeranswersarray[i-1] = answersanswers.getJSONObject(0).getString(String.valueOf(i));
                    }
                    addquestions(answeranswersarray, solution, hint, aftertext);
                }
                else if (type == 4)
                {
                    String questiontitle = obj.getString("question");
                    questiontextview.setText(questiontitle.replace("\\n", System.getProperty("line.separator")));
                    String aftertext = obj.optString("aftertext", "");
                    String hint = obj.getString("hint");
                    JSONObject answers = obj.getJSONObject("answers");
                    JSONArray answersanswers = answers.getJSONArray("answers");
                    String[] answeranswersarray = new String[answersanswers.getJSONObject(0).length()];
                    for(int i = 1; i< answersanswers.getJSONObject(0).length()+1; i++)
                    {
                        answeranswersarray[i-1] = answersanswers.getJSONObject(0).getString(String.valueOf(i));
                    }
                    fillthetext(answeranswersarray, hint, aftertext);
                }
                else if (type == 5)
                {
                    String questiontitle = obj.getString("question");
                    questiontextview.setText(questiontitle.replace("\\n", System.getProperty("line.separator")));
                    String aftertext = obj.optString("aftertext", "");
                    String hint = obj.getString("hint");
                    String solution = obj.getString("solution");
                    JSONObject answers = obj.getJSONObject("answers");
                    JSONArray answersanswers = answers.getJSONArray("answers");
                    String[] answeranswersarray = new String[answersanswers.getJSONObject(0).length()];
                    for(int i = 1; i< answersanswers.getJSONObject(0).length()+1; i++)
                    {
                        answeranswersarray[i-1] = answersanswers.getJSONObject(0).getString(String.valueOf(i));
                    }
                    JSONObject images = obj.getJSONObject("images");
                    JSONArray imagesanswers = images.getJSONArray("answers");
                    String imageanswersarray = imagesanswers.getJSONObject(0).getString(String.valueOf(1));
                    addspecialquestion(imageanswersarray, answeranswersarray, solution, hint, aftertext);
                }
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
                InputStream in = new URL(urldisplay).openStream();
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

    @Override
    protected void onResume()
    {
        super.onResume();
        getSupportActionBar().hide();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        getSupportActionBar().show();
    }
}
