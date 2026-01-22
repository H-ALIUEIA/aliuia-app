package io.github.h_aliueia;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class aftertext extends AppCompatActivity
{
    public TextView aftertext;
    public TextView textwithanim;
    public ConstraintLayout pressanywhere;
    public String level;
    public int levelspecific;

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
        setContentView(R.layout.activity_aftertext);
        EdgeToEdge.enable(this);
        aftertext = (TextView)findViewById(R.id.aftertext);
        textwithanim = (TextView)findViewById(R.id.textwithanim);
        pressanywhere = (ConstraintLayout) findViewById(R.id.pressanywhere);
        pressanywhere.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(levelspecific < level.split("/").length)
                {
                    finish();
                    Intent in = new Intent(aftertext.this, Levelplay.class);
                    in.putExtra("level", level);
                    in.putExtra("levelspecific", levelspecific + 1);
                    startActivity(in);
                }
                else
                {
                    finish();
                }
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            level = extras.getString("level","");
            levelspecific = extras.getInt("levelspecific", 0);
            aftertext.setText(extras.getString("aftertext").replace("\\n", System.getProperty("line.separator")));
        }
        animate();
    }

    public void animate()
    {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(1000);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(1000);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeOut);
        animation.addAnimation(fadeIn);
        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setAnimationListener(new Animation.AnimationListener()
        {
            public void onAnimationStart(Animation arg0)
            {
                // TODO Auto-generated method stub
            }

            public void onAnimationRepeat(Animation arg0)
            {
                // TODO Auto-generated method stub
            }

            public void onAnimationEnd(Animation arg0)
            {
                textwithanim.startAnimation(animation);
            }
        });
        textwithanim.startAnimation(animation);
    }
}
