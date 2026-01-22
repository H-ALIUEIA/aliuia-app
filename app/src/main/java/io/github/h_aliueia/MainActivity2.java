package io.github.h_aliueia;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.card.MaterialCardView;

public class MainActivity2 extends AppCompatActivity
{

    public MaterialCardView card1;
    public MaterialCardView card2;
    public MaterialCardView card3;
    public MaterialCardView card4;
    public MaterialCardView card5;
    public MaterialCardView card6;
    public MaterialCardView card7;
    public MaterialCardView card8;
    public MaterialCardView card9;
    public MaterialCardView card10;
    public MaterialCardView card11;
    public MaterialCardView card12;
    public LinearLayout cardlayout1;
    public LinearLayout cardlayout2;
    public LinearLayout cardlayout3;
    public LinearLayout cardlayout4;
    public LinearLayout cardlayout5;
    public LinearLayout cardlayout6;
    public LinearLayout cardlayout7;
    public LinearLayout cardlayout8;
    public LinearLayout cardlayout9;
    public LinearLayout cardlayout10;
    public LinearLayout cardlayout11;
    public LinearLayout cardlayout12;


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
        setContentView(R.layout.activity_main2);
        EdgeToEdge.enable(this);
        card1 = (MaterialCardView)findViewById(R.id.card);
        card2 = (MaterialCardView)findViewById(R.id.card2);
        card3 = (MaterialCardView)findViewById(R.id.card3);
        card4 = (MaterialCardView)findViewById(R.id.card4);
        card5 = (MaterialCardView)findViewById(R.id.card5);
        card6 = (MaterialCardView)findViewById(R.id.card6);
        card7 = (MaterialCardView)findViewById(R.id.card7);
        card8 = (MaterialCardView)findViewById(R.id.card8);
        card9 = (MaterialCardView)findViewById(R.id.card9);
        card10 = (MaterialCardView)findViewById(R.id.card10);
        card11 = (MaterialCardView)findViewById(R.id.card11);
        card12 = (MaterialCardView)findViewById(R.id.card12);
        cardlayout1 = (LinearLayout)findViewById(R.id.card1layout);
        cardlayout2 = (LinearLayout)findViewById(R.id.card2layout);
        cardlayout3 = (LinearLayout)findViewById(R.id.card3layout);
        cardlayout4 = (LinearLayout)findViewById(R.id.card4layout);
        cardlayout5 = (LinearLayout)findViewById(R.id.card5layout);
        cardlayout6 = (LinearLayout)findViewById(R.id.card6layout);
        cardlayout7 = (LinearLayout)findViewById(R.id.card7layout);
        cardlayout8 = (LinearLayout)findViewById(R.id.card8layout);
        cardlayout9 = (LinearLayout)findViewById(R.id.card9layout);
        cardlayout10 = (LinearLayout)findViewById(R.id.card10layout);
        cardlayout11 = (LinearLayout)findViewById(R.id.card11layout);
        cardlayout12 = (LinearLayout)findViewById(R.id.card12layout);
        try
        {
            int selected = getIntent().getExtras().getInt("selected");
            if(selected == 0)
            {
                cardlayout1.setBackgroundColor(Color.parseColor("#0000FF"));
                cardlayout2.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout3.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout4.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout5.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout6.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout7.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout8.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout9.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout10.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout11.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout12.setBackgroundColor(Color.parseColor("#000000"));
            }
            else if (selected == 1)
            {
                cardlayout1.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout2.setBackgroundColor(Color.parseColor("#0000FF"));
                cardlayout3.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout4.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout5.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout6.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout7.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout8.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout9.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout10.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout11.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout12.setBackgroundColor(Color.parseColor("#000000"));
            }
            else if (selected == 2)
            {
                cardlayout1.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout2.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout3.setBackgroundColor(Color.parseColor("#0000FF"));
                cardlayout4.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout5.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout6.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout7.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout8.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout9.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout10.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout11.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout12.setBackgroundColor(Color.parseColor("#000000"));
            }
            else if (selected == 3)
            {
                cardlayout1.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout2.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout3.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout4.setBackgroundColor(Color.parseColor("#0000FF"));
                cardlayout5.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout6.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout7.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout8.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout9.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout10.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout11.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout12.setBackgroundColor(Color.parseColor("#000000"));
            }
            else if (selected == 4)
            {
                cardlayout1.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout2.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout3.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout4.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout5.setBackgroundColor(Color.parseColor("#0000FF"));
                cardlayout6.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout7.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout8.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout9.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout10.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout11.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout12.setBackgroundColor(Color.parseColor("#000000"));
            }
            else if (selected == 5)
            {
                cardlayout1.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout2.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout3.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout4.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout5.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout6.setBackgroundColor(Color.parseColor("#0000FF"));
                cardlayout7.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout8.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout9.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout10.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout11.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout12.setBackgroundColor(Color.parseColor("#000000"));
            }
            else if (selected == 6)
            {
                cardlayout1.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout2.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout3.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout4.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout5.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout6.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout7.setBackgroundColor(Color.parseColor("#0000FF"));
                cardlayout8.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout9.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout10.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout11.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout12.setBackgroundColor(Color.parseColor("#000000"));
            }
            else if (selected == 7)
            {
                cardlayout1.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout2.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout3.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout4.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout5.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout6.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout7.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout8.setBackgroundColor(Color.parseColor("#0000FF"));
                cardlayout9.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout10.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout11.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout12.setBackgroundColor(Color.parseColor("#000000"));
            }
            else if (selected == 8)
            {
                cardlayout1.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout2.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout3.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout4.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout5.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout6.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout7.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout8.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout9.setBackgroundColor(Color.parseColor("#0000FF"));
                cardlayout10.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout11.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout12.setBackgroundColor(Color.parseColor("#000000"));
            }
            else if (selected == 9)
            {
                cardlayout1.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout2.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout3.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout4.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout5.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout6.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout7.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout8.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout9.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout10.setBackgroundColor(Color.parseColor("#0000FF"));
                cardlayout11.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout12.setBackgroundColor(Color.parseColor("#000000"));
            }
            else if (selected == 10)
            {
                cardlayout1.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout2.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout3.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout4.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout5.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout6.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout7.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout8.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout9.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout10.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout11.setBackgroundColor(Color.parseColor("#0000FF"));
                cardlayout12.setBackgroundColor(Color.parseColor("#000000"));
            }
            else if (selected == 11)
            {
                cardlayout1.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout2.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout3.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout4.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout5.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout6.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout7.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout8.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout9.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout10.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout11.setBackgroundColor(Color.parseColor("#000000"));
                cardlayout12.setBackgroundColor(Color.parseColor("#0000FF"));
            }
        }
        catch (Exception e){}
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent i = new Intent(this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("frgtoload", 1);
                startActivity(i);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("frgtoload", 1);
        startActivity(i);
        finish();
        super.onBackPressed();
    }

    public void setCard(View view)
    {
        String buttontag = view.getTag().toString();
        if(buttontag.equals("card1"))
        {
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("frgtoload", 1);
            i.putExtra("shortscategory", 0);
            startActivity(i);
            finish();
        }
        else if (buttontag.equals("card2"))
        {
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("frgtoload", 1);
            i.putExtra("shortscategory", 1);
            startActivity(i);
            finish();
        }
        else if (buttontag.equals("card3"))
        {
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("frgtoload", 1);
            i.putExtra("shortscategory", 2);
            startActivity(i);
            finish();
        }
        else if (buttontag.equals("card4"))
        {
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("frgtoload", 1);
            i.putExtra("shortscategory", 3);
            startActivity(i);
            finish();
        }
        else if (buttontag.equals("card5"))
        {
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("frgtoload", 1);
            i.putExtra("shortscategory", 4);
            startActivity(i);
            finish();
        }
        else if (buttontag.equals("card6"))
        {
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("frgtoload", 1);
            i.putExtra("shortscategory", 5);
            startActivity(i);
            finish();
        }
        else if (buttontag.equals("card7"))
        {
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("frgtoload", 1);
            i.putExtra("shortscategory", 6);
            startActivity(i);
            finish();
        }
        else if (buttontag.equals("card8"))
        {
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("frgtoload", 1);
            i.putExtra("shortscategory", 7);
            startActivity(i);
            finish();
        }
        else if (buttontag.equals("card9"))
        {
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("frgtoload", 1);
            i.putExtra("shortscategory", 8);
            startActivity(i);
            finish();
        }
        else if (buttontag.equals("card10"))
        {
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("frgtoload", 1);
            i.putExtra("shortscategory", 9);
            startActivity(i);
            finish();
        }
        else if (buttontag.equals("card11"))
        {
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("frgtoload", 1);
            i.putExtra("shortscategory", 10);
            startActivity(i);
            finish();
        }
        else if (buttontag.equals("card12"))
        {
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("frgtoload", 1);
            i.putExtra("shortscategory", 11);
            startActivity(i);
            finish();
        }
    }
}
