package io.github.h_aliueia;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class bibleactivity extends AppCompatActivity
{
    public LinearLayout booksholder;
    public LinearLayout bookindholder;
    public TextView selectbook;
    public ScrollView selectbookscroll;
    public ScrollView selectchapterscroll;
    public LinearLayout selectchapter;
    public TextView bibletext;
    public int state = 0;
    public int currentbook = 1;
    public int currentchapter = 1;
    public String currentbookname = "";
    public int currentmax = 0;
    public String[] booksnames = {"ΓΕΝΕΣΙΣ","ΕΞΟΔΟΣ","ΛΕΥΙΤΙΚΟΝ","ΑΡΙΘΜΟΙ","ΔΕΥΤΕΡΟΝΟΜΙΟΝ","ΙΗΣΟΥΣ ΤΟΥ ΝΑΥΗ","ΚΡΙΤΑΙ","ΡΟΥΘ","Α΄ ΣΑΜΟΥΗΛ","Β΄ ΣΑΜΟΥΗΛ","Α΄ ΒΑΣΙΛΕΩΝ","Β΄ ΒΑΣΙΛΕΩΝ","Α΄ ΧΡΟΝΙΚΩΝ","Β΄ ΧΡΟΝΙΚΩΝ","ΕΣΔΡΑΣ","ΝΕΕΜΙΑΣ","ΕΣΘΗΡ","ΙΩΒ","ΨΑΛΜΟΙ","ΠΑΡΟΙΜΙΑΙ","ΕΚΚΛΗΣΙΑΣΤΗΣ","ΑΣΜΑ ΑΣΜΑΤΩΝ","ΗΣΑΪΑΣ","ΙΕΡΕΜΙΑΣ","ΘΡΗΝΟΙ","ΙΕΖΕΚΙΗΛ","ΔΑΝΙΗΛ","ΩΣΗΕ","ΙΩΗΛ","ΑΜΩΣ","ΟΒΔΙΟΥ","ΙΩΝΑΣ","ΜΙΧΑΙΑΣ","ΝΑΟΥΜ","ΑΒΒΑΚΟΥΜ","ΣΟΦΟΝΙΑΣ","ΑΓΓΑΙΟΣ","ΖΑΧΑΡΙΑΣ","ΜΑΛΑΧΙΑΣ","ΚΑΤΑ ΜΑΤΘΑΙΟΝ","ΚΑΤΑ ΜΑΡΚΟΝ","ΚΑΤΑ ΛΟΥΚΑΝ","ΚΑΤΑ ΙΩΑΝΝΗΝ","ΠΡΑΞΕΙΣ ΑΠΟΣΤΟΛΩΝ","ΠΡΟΣ ΡΩΜΑΙΟΥΣ","ΠΡΟΣ ΚΟΡΙΝΘΙΟΥΣ Α","ΠΡΟΣ ΚΟΡΙΝΘΙΟΥΣ Β","ΠΡΟΣ ΓΑΛΑΤΑΣ","ΠΡΟΣ ΕΦΕΣΙΟΥΣ","ΠΡΟΣ ΦΙΛΙΠΠΗΣΙΟΥΣ","ΠΡΟΣ ΚΟΛΟΣΣΑΕΙΣ","ΠΡΟΣ ΘΕΣΣΑΛΟΝΙΚΕΙΣ Α","ΠΡΟΣ ΘΕΣΣΑΛΟΝΙΚΕΙΣ Β","Α ΠΡΟΣ ΤΙΜΟΘΕΟΝ","Β ΠΡΟΣ ΤΙΜΟΘΕΟΝ","ΠΡΟΣ ΤΙΤΟΝ","ΠΡΟΣ ΦΙΛΗΜΟΝΑ","ΠΡΟΣ ΕΒΡΑΙΟΥΣ","ΙΑΚΩΒΟΥ","Α΄ ΠΕΤΡΟΥ","Β΄ ΠΕΤΡΟΥ","Α΄ ΙΩΑΝΝΟΥ","Β΄ ΙΩΑΝΝΟΥ","Γ΄ ΙΩΑΝΝΟΥ","ΙΟΥΔΑ","ΑΠΟΚΑΛΥΨΗ ΙΩΑΝΝΟΥ"};

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
        setContentView(R.layout.activity_bibleactivity);
        EdgeToEdge.enable(this);
        booksholder = (LinearLayout) findViewById(R.id.booksholder);
        bookindholder = (LinearLayout) findViewById(R.id.bookindholder);
        selectbook = (TextView) findViewById(R.id.selectbook);
        selectbookscroll = (ScrollView) findViewById(R.id.selectbookscroll);
        selectchapterscroll = (ScrollView) findViewById(R.id.selectchapterscrool);
        selectchapter = (LinearLayout) findViewById(R.id.selectchapter);
        bibletext = (TextView) findViewById(R.id.bibletext);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            String hint = extras.getString("hint");
            String hintsplit[] = hint.split(":");
            loadspecific(hintsplit[0],hintsplit[1],hintsplit[2]);
        }
    }

    @Override
    public void onBackPressed() {
        if(state == 0)
        {
            finish();
            return;
        }
        else if (state == 1)
        {
            bookindholder.setVisibility(View.VISIBLE);
            selectbookscroll.setVisibility(View.GONE);
            booksholder.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(50)));
            state = 0;
            return;
        }
        else if (state == 2)
        {
            selectbookscroll.setVisibility(View.VISIBLE);
            selectchapterscroll.setVisibility(View.GONE);
            state = 1;
            return;
        }
        super.onBackPressed();
    }

    public void previous(View view)
    {
        if(currentchapter > 1)
        {
            loadtext(String.valueOf(currentbook),String.valueOf(currentchapter-1), currentbookname,"0");
            selectbook.setText("⬇ "+currentbookname+" "+String.valueOf(currentchapter-1)+" ⬇");
            currentchapter = currentchapter-1;
        }
        else if (currentchapter == 1)
        {

        }
    }

    public void loadspecific(String book, String chapter, String verses)
    {
        loadtext(book,chapter, booksnames[Integer.parseInt(book)-1],verses);
        selectbook.setText("⬇ "+booksnames[Integer.parseInt(book)-1]+" "+chapter+" ⬇");
        currentbook = Integer.parseInt(book);
        currentchapter = Integer.parseInt(chapter);
        currentbookname = booksnames[Integer.parseInt(book)-1];
        loadmax(Integer.parseInt(book));
        try {
            JSONObject obj = new JSONObject(loadbook(book));
            JSONArray obj2 = obj.getJSONArray(booksnames[Integer.parseInt(book)-1]);
            currentmax = obj2.getJSONObject(0).length()+1;
        } catch (JSONException e) {}
    }

    public void loadmax(int book)
    {
    }

    public void after(View view)
    {
        if(currentchapter < currentmax-1)
        {
            loadtext(String.valueOf(currentbook),String.valueOf(currentchapter+1), currentbookname,"0");
            selectbook.setText("⬇ "+currentbookname+" "+String.valueOf(currentchapter+1)+" ⬇");
            currentchapter = currentchapter+1;
        }
    }

    public void bookschange(View view)
    {
        state = 1;
        booksholder.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        bookindholder.setVisibility(View.GONE);
        selectbookscroll.setVisibility(View.VISIBLE);
    }

    public String loadbook(String number)
    {
        String json = null;
        try
        {
            InputStream is = getAssets().open(number+".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            return null;
        }
        return json;
    }

    public boolean isElementPresent(String[] arr, int key)
    {
        for (String element : arr)
        {
            if (Integer.parseInt(element) == key)
            {
                return true;
            }
        }
        return false;
    }

    public void loadtext(String number, String number2, String numbername, String verses)
    {
        try
        {
            JSONObject obj = new JSONObject(loadbook(number));
            JSONArray obj2 = obj.getJSONArray(numbername);
            JSONArray obj3 = obj2.getJSONObject(0).getJSONArray(number2);
            StringBuilder obj4 = new StringBuilder();

            for(int i = 1; i< obj3.length()+1; i++)
            {
                if(verses.equals("0"))
                {
                    obj4.append("<br>"+String.valueOf(i)+".");
                    obj4.append(new JSONObject(obj3.getString(i-1)).getString(String.valueOf(i)));
                }
                else
                {
                    if(isElementPresent(verses.split(","),i))
                    {
                        obj4.append("<br>"+String.valueOf(i)+".");
                        obj4.append("<font color=\"#FF0000\">"+new JSONObject(obj3.getString(i-1)).getString(String.valueOf(i))+"</font>");
                    }
                    else
                    {
                        obj4.append("<br>"+String.valueOf(i)+".");
                        obj4.append(new JSONObject(obj3.getString(i-1)).getString(String.valueOf(i)));
                    }
                }
            }
            bibletext.setText(HtmlCompat.fromHtml(obj4.toString(),HtmlCompat.FROM_HTML_MODE_LEGACY));
        }
        catch (Exception e){}
    }

    public void createchapters(String number, String numbername)
    {
        try {
            JSONObject obj = new JSONObject(loadbook(number));
            JSONArray obj2 = obj.getJSONArray(numbername);
            for(int i = 1; i < obj2.getJSONObject(0).length()+1;i++)
            {
                Button button = new Button(bibleactivity.this);
                button.setText(String.valueOf(i));
                int k = i;
                button.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        state = 0;
                        loadtext(number,String.valueOf(k), numbername,"0");
                        bookindholder.setVisibility(View.VISIBLE);
                        selectbookscroll.setVisibility(View.GONE);
                        selectchapterscroll.setVisibility(View.GONE);
                        booksholder.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(50)));
                        selectbook.setText("⬇ "+numbername+" "+String.valueOf(k)+" ⬇");
                        currentbook = Integer.parseInt(number);
                        currentchapter = k;
                        currentbookname = numbername;
                        try
                        {
                            currentmax = obj2.getJSONObject(0).length()+1;
                        }
                        catch (Exception e){}
                    }
                });
                selectchapter.addView(button);
            }
        } catch (JSONException e) {}

    }

    public int dpToPx(int dp)
    {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public void selectchapterfunction(View view)
    {

        state = 2;
        selectbookscroll.setVisibility(View.GONE);
        selectchapterscroll.setVisibility(View.VISIBLE);
        String buttontag = view.getTag().toString();
        if(buttontag.equals("b1"))
        {
            selectchapter.removeAllViews();
            createchapters("1", "ΓΕΝΕΣΙΣ");
        }
        else if (buttontag.equals("b2"))
        {
            selectchapter.removeAllViews();
            createchapters("2", "ΕΞΟΔΟΣ");
        }
        else if (buttontag.equals("b3"))
        {
            selectchapter.removeAllViews();
            createchapters("3", "ΛΕΥΙΤΙΚΟΝ");
        }
        else if (buttontag.equals("b4"))
        {
            selectchapter.removeAllViews();
            createchapters("4", "ΑΡΙΘΜΟΙ");
        }
        else if (buttontag.equals("b5"))
        {
            selectchapter.removeAllViews();
            createchapters("5", "ΔΕΥΤΕΡΟΝΟΜΙΟΝ");
        }
        else if (buttontag.equals("b6"))
        {
            selectchapter.removeAllViews();
            createchapters("6", "ΙΗΣΟΥΣ ΤΟΥ ΝΑΥΗ");
        }
        else if (buttontag.equals("b7"))
        {
            selectchapter.removeAllViews();
            createchapters("7", "ΚΡΙΤΑΙ");
        }
        else if (buttontag.equals("b8"))
        {
            selectchapter.removeAllViews();
            createchapters("8", "ΡΟΥΘ");
        }
        else if (buttontag.equals("b9"))
        {
            selectchapter.removeAllViews();
            createchapters("9", "Α΄ ΣΑΜΟΥΗΛ");
        }
        else if (buttontag.equals("b10"))
        {
            selectchapter.removeAllViews();
            createchapters("10", "Β΄ ΣΑΜΟΥΗΛ");
        }
        else if (buttontag.equals("b11"))
        {
            selectchapter.removeAllViews();
            createchapters("11", "Α΄ ΒΑΣΙΛΕΩΝ");
        }
        else if (buttontag.equals("b12"))
        {
            selectchapter.removeAllViews();
            createchapters("12", "Β΄ ΒΑΣΙΛΕΩΝ");
        }
        else if (buttontag.equals("b13"))
        {
            selectchapter.removeAllViews();
            createchapters("13", "Α΄ ΧΡΟΝΙΚΩΝ");
        }
        else if (buttontag.equals("b14"))
        {
            selectchapter.removeAllViews();
            createchapters("14", "Β΄ ΧΡΟΝΙΚΩΝ");
        }
        else if (buttontag.equals("b15"))
        {
            selectchapter.removeAllViews();
            createchapters("15", "ΕΣΔΡΑΣ");
        }
        else if (buttontag.equals("b16"))
        {
            selectchapter.removeAllViews();
            createchapters("16", "ΝΕΕΜΙΑΣ");
        }
        else if (buttontag.equals("b17"))
        {
            selectchapter.removeAllViews();
            createchapters("17", "ΕΣΘΗΡ");
        }
        else if (buttontag.equals("b18"))
        {
            selectchapter.removeAllViews();
            createchapters("18", "ΙΩΒ");
        }
        else if (buttontag.equals("b19"))
        {
            selectchapter.removeAllViews();
            createchapters("19", "ΨΑΛΜΟΙ");
        }
        else if (buttontag.equals("b20"))
        {
            selectchapter.removeAllViews();
            createchapters("20", "ΠΑΡΟΙΜΙΑΙ");
        }
        else if (buttontag.equals("b21"))
        {
            selectchapter.removeAllViews();
            createchapters("21", "ΕΚΚΛΗΣΙΑΣΤΗΣ");
        }
        else if (buttontag.equals("b22"))
        {
            selectchapter.removeAllViews();
            createchapters("22", "ΑΣΜΑ ΑΣΜΑΤΩΝ");
        }
        else if (buttontag.equals("b23"))
        {
            selectchapter.removeAllViews();
            createchapters("23", "ΗΣΑΪΑΣ");
        }
        else if (buttontag.equals("b24"))
        {
            selectchapter.removeAllViews();
            createchapters("24", "ΙΕΡΕΜΙΑΣ");
        }
        else if (buttontag.equals("b25"))
        {
            selectchapter.removeAllViews();
            createchapters("25", "ΘΡΗΝΟΙ");
        }
        else if (buttontag.equals("b26"))
        {
            selectchapter.removeAllViews();
            createchapters("26", "ΙΕΖΕΚΙΗΛ");
        }
        else if (buttontag.equals("b27"))
        {
            selectchapter.removeAllViews();
            createchapters("27", "ΔΑΝΙΗΛ");
        }
        else if (buttontag.equals("b28"))
        {
            selectchapter.removeAllViews();
            createchapters("28", "ΩΣΗΕ");
        }
        else if (buttontag.equals("b29"))
        {
            selectchapter.removeAllViews();
            createchapters("29", "ΙΩΗΛ");
        }
        else if (buttontag.equals("b30"))
        {
            selectchapter.removeAllViews();
            createchapters("30", "ΑΜΩΣ");
        }
        else if (buttontag.equals("b31"))
        {
            selectchapter.removeAllViews();
            createchapters("31", "ΟΒΔΙΟΥ");
        }
        else if (buttontag.equals("b32"))
        {
            selectchapter.removeAllViews();
            createchapters("32", "ΙΩΝΑΣ");
        }
        else if (buttontag.equals("b33"))
        {
            selectchapter.removeAllViews();
            createchapters("33", "ΜΙΧΑΙΑΣ");
        }
        else if (buttontag.equals("b34"))
        {
            selectchapter.removeAllViews();
            createchapters("34", "ΝΑΟΥΜ");
        }
        else if (buttontag.equals("b35"))
        {
            selectchapter.removeAllViews();
            createchapters("35", "ΑΒΒΑΚΟΥΜ");
        }
        else if (buttontag.equals("b36"))
        {
            selectchapter.removeAllViews();
            createchapters("36", "ΣΟΦΟΝΙΑΣ");
        }
        else if (buttontag.equals("b37"))
        {
            selectchapter.removeAllViews();
            createchapters("37", "ΑΓΓΑΙΟΣ");
        }
        else if (buttontag.equals("b38"))
        {
            selectchapter.removeAllViews();
            createchapters("38", "ΖΑΧΑΡΙΑΣ");
        }
        else if (buttontag.equals("b39"))
        {
            selectchapter.removeAllViews();
            createchapters("39", "ΜΑΛΑΧΙΑΣ");
        }
        else if (buttontag.equals("b40"))
        {
            selectchapter.removeAllViews();
            createchapters("40", "ΚΑΤΑ ΜΑΤΘΑΙΟΝ");
        }
        else if (buttontag.equals("b41"))
        {
            selectchapter.removeAllViews();
            createchapters("41", "ΚΑΤΑ ΜΑΡΚΟΝ");
        }
        else if (buttontag.equals("b42"))
        {
            selectchapter.removeAllViews();
            createchapters("42", "ΚΑΤΑ ΛΟΥΚΑΝ");
        }
        else if (buttontag.equals("b43"))
        {
            selectchapter.removeAllViews();
            createchapters("43", "ΚΑΤΑ ΙΩΑΝΝΗΝ");
        }
        else if (buttontag.equals("b44"))
        {
            selectchapter.removeAllViews();
            createchapters("44", "ΠΡΑΞΕΙΣ ΑΠΟΣΤΟΛΩΝ");
        }
        else if (buttontag.equals("b45"))
        {
            selectchapter.removeAllViews();
            createchapters("45", "ΠΡΟΣ ΡΩΜΑΙΟΥΣ");
        }
        else if (buttontag.equals("b46"))
        {
            selectchapter.removeAllViews();
            createchapters("46", "ΠΡΟΣ ΚΟΡΙΝΘΙΟΥΣ Α");
        }
        else if (buttontag.equals("b47"))
        {
            selectchapter.removeAllViews();
            createchapters("47", "ΠΡΟΣ ΚΟΡΙΝΘΙΟΥΣ Β");
        }
        else if (buttontag.equals("b48"))
        {
            selectchapter.removeAllViews();
            createchapters("48", "ΠΡΟΣ ΓΑΛΑΤΑΣ");
        }
        else if (buttontag.equals("b49"))
        {
            selectchapter.removeAllViews();
            createchapters("49", "ΠΡΟΣ ΕΦΕΣΙΟΥΣ");
        }
        else if (buttontag.equals("b50"))
        {
            selectchapter.removeAllViews();
            createchapters("50", "ΠΡΟΣ ΦΙΛΙΠΠΗΣΙΟΥΣ");
        }
        else if (buttontag.equals("b51"))
        {
            selectchapter.removeAllViews();
            createchapters("51", "ΠΡΟΣ ΚΟΛΟΣΣΑΕΙΣ");
        }
        else if (buttontag.equals("b52"))
        {
            selectchapter.removeAllViews();
            createchapters("52", "ΠΡΟΣ ΘΕΣΣΑΛΟΝΙΚΕΙΣ Α");
        }
        else if (buttontag.equals("b53"))
        {
            selectchapter.removeAllViews();
            createchapters("53", "ΠΡΟΣ ΘΕΣΣΑΛΟΝΙΚΕΙΣ Β");
        }
        else if (buttontag.equals("b54"))
        {
            selectchapter.removeAllViews();
            createchapters("54", "Α ΠΡΟΣ ΤΙΜΟΘΕΟΝ");
        }
        else if (buttontag.equals("b55"))
        {
            selectchapter.removeAllViews();
            createchapters("55", "Β ΠΡΟΣ ΤΙΜΟΘΕΟΝ");
        }
        else if (buttontag.equals("b56"))
        {
            selectchapter.removeAllViews();
            createchapters("56", "ΠΡΟΣ ΤΙΤΟΝ");
        }
        else if (buttontag.equals("b57"))
        {
            selectchapter.removeAllViews();
            createchapters("57", "ΠΡΟΣ ΦΙΛΗΜΟΝΑ");
        }
        else if (buttontag.equals("b58"))
        {
            selectchapter.removeAllViews();
            createchapters("58", "ΠΡΟΣ ΕΒΡΑΙΟΥΣ");
        }
        else if (buttontag.equals("b59"))
        {
            selectchapter.removeAllViews();
            createchapters("59", "ΙΑΚΩΒΟΥ");
        }
        else if (buttontag.equals("b60"))
        {
            selectchapter.removeAllViews();
            createchapters("60", "Α΄ ΠΕΤΡΟΥ");
        }
        else if (buttontag.equals("b61"))
        {
            selectchapter.removeAllViews();
            createchapters("61", "Β΄ ΠΕΤΡΟΥ");
        }
        else if (buttontag.equals("b62"))
        {
            selectchapter.removeAllViews();
            createchapters("62", "Α΄ ΙΩΑΝΝΟΥ");
        }
        else if (buttontag.equals("b63"))
        {
            selectchapter.removeAllViews();
            createchapters("63", "Β΄ ΙΩΑΝΝΟΥ");
        }
        else if (buttontag.equals("b64"))
        {
            selectchapter.removeAllViews();
            createchapters("64", "Γ΄ ΙΩΑΝΝΟΥ");
        }
        else if (buttontag.equals("b65"))
        {
            selectchapter.removeAllViews();
            createchapters("65", "ΙΟΥΔΑ");
        }
        else if (buttontag.equals("b66"))
        {
            selectchapter.removeAllViews();
            createchapters("66", "ΑΠΟΚΑΛΥΨΗ ΙΩΑΝΝΟΥ");
        }
    }
}
