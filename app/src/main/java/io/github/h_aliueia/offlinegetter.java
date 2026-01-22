package io.github.h_aliueia;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class offlinegetter
{

    public static boolean offlinechecker(Context context, int type)
    {
        if(type == 0)
        {
            File file = new File(context.getApplicationInfo().dataDir+"/files/offlinedb.json");
            if(file.exists())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else if(type == 1)
        {
            File file = new File(context.getApplicationInfo().dataDir+"/files/shorts/");
            File file2 = new File(context.getApplicationInfo().dataDir+"/files/shorts.zip");
            if(file.exists() || file2.exists())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else if(type == 2)
        {
            File file = new File(context.getApplicationInfo().dataDir+"/files/lessons/");
            File file2 = new File(context.getApplicationInfo().dataDir+"/files/lessons.zip");
            if(file.exists() || file2.exists())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else if(type == 3)
        {
            File file = new File(context.getApplicationInfo().dataDir+"/files/prophecy/");
            File file2 = new File(context.getApplicationInfo().dataDir+"/files/prophecy.zip");
            if(file.exists() || file2.exists())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else if(type == 4)
        {
            File file = new File(context.getApplicationInfo().dataDir+"/files/unlearn/");
            File file2 = new File(context.getApplicationInfo().dataDir+"/files/unlearn.zip");
            if(file.exists() || file2.exists())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public static String levellistgetter(Context context, int book)
    {
        try
        {
            FileInputStream fileInputStream = context.openFileInput("offlinedb.json");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader reader2 = new BufferedReader(inputStreamReader);
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader2.readLine()) != null)
            {
                buffer.append(line + "\n");
            }
            JSONObject jsonObject = new JSONObject(buffer.toString());
            JSONArray qwe = jsonObject.getJSONArray("LevelsList");
            JSONArray qwe2 = new JSONArray();
            for(int i = 0; i < qwe.length(); i++)
            {
                if(qwe.getJSONObject(i).getInt("book") == book)
                {
                    qwe2.put(qwe.getJSONObject(i));
                }
            }
            return qwe2.toString();
        }
        catch (Exception e){return "";}
    }

    public static String quotegetter(Context context, int category)
    {
        try
        {
            FileInputStream fileInputStream = context.openFileInput("offlinedb.json");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader reader2 = new BufferedReader(inputStreamReader);
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader2.readLine()) != null)
            {
                buffer.append(line+"\n");
            }
            JSONObject jsonObject = new JSONObject(buffer.toString());
            if(category == 0)
            {
                JSONArray qwe = jsonObject.getJSONArray("QuotesModel");
                return qwe.toString();
            }
            else if (category == 1)
            {
                JSONArray qwe = jsonObject.getJSONArray("QuotesModel");
                JSONArray qwe2 = new JSONArray();
                for(int i = 0;i < qwe.length(); i++)
                {
                    if(qwe.getJSONObject(i).getString("category").strip().equals("Bible"))
                    {
                        qwe2.put(qwe.getJSONObject(i));
                    }
                }
                return qwe2.toString();
            }
            else if (category == 2)
            {
                JSONArray qwe = jsonObject.getJSONArray("QuotesModel");
                JSONArray qwe2 = new JSONArray();
                for(int i = 0;i < qwe.length(); i++)
                {
                    if(qwe.getJSONObject(i).getString("category").strip().equals("Cons"))
                    {
                        qwe2.put(qwe.getJSONObject(i));
                    }
                }
                return qwe2.toString();
            }
            else if (category == 3)
            {
                JSONArray qwe = jsonObject.getJSONArray("QuotesModel");
                JSONArray qwe2 = new JSONArray();
                for(int i = 0;i < qwe.length(); i++)
                {
                    if(qwe.getJSONObject(i).getString("category").strip().equals("Mov-Ser"))
                    {
                        qwe2.put(qwe.getJSONObject(i));
                    }
                }
                return qwe2.toString();
            }
            else
            {
                JSONArray qwe = jsonObject.getJSONArray("QuotesModel");
                return qwe.toString();
            }
        }
        catch (Exception e)
        {
            return "";
        }
    }

    public static String videogetter(Context context)
    {
        try
        {
            FileInputStream fileInputStream = context.openFileInput("offlinedb.json");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader reader2 = new BufferedReader(inputStreamReader);
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader2.readLine()) != null)
            {
                buffer.append(line+"\n");
            }
            JSONObject jsonObject = new JSONObject(buffer.toString());
            JSONArray qwe = jsonObject.getJSONArray("VideosModel");
            return qwe.toString();
        }
        catch (Exception e)
        {
            return "";
        }
    }

    public static JSONArray notificationgetter(Context context)
    {
        try
        {
            FileInputStream fileInputStream = context.openFileInput("notifications.json");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader reader2 = new BufferedReader(inputStreamReader);
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader2.readLine()) != null)
            {
                buffer.append(line+"\n");
            }
            JSONArray jsonArray = new JSONArray(buffer.toString());
            return jsonArray;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static String levelgetter(Context context, int level)
    {
        try
        {
            FileInputStream fileInputStream = context.openFileInput("offlinedb.json");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader reader2 = new BufferedReader(inputStreamReader);
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader2.readLine()) != null)
            {
                buffer.append(line+"\n");
            }
            JSONObject jsonObject = new JSONObject(buffer.toString());
            JSONObject qwe = jsonObject.getJSONArray("LevelsModel").getJSONObject(level);
            return qwe.toString();
        }
        catch (Exception e)
        {
            return "";
        }
    }

    public static String shortsgetter(Context context, int category)
    {
        try
        {
            FileInputStream fileInputStream = context.openFileInput("offlinedb.json");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader reader2 = new BufferedReader(inputStreamReader);
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader2.readLine()) != null)
            {
                buffer.append(line+"\n");
            }
            JSONObject jsonObject = new JSONObject(buffer.toString());
            if(category == 0)
            {
                JSONArray qwe = jsonObject.getJSONArray("ShortsModel");
                return qwe.toString();
            }
            else if (category == 1)
            {
                JSONArray qwe = jsonObject.getJSONArray("ShortsModel");
                JSONArray qwe2 = new JSONArray();
                for(int i = 0;i < qwe.length(); i++)
                {
                    if(qwe.getJSONObject(i).getString("category").strip().equals("ΠΟΛΕΙΣ 15 ΛΕΠΤΩΝ"))
                    {
                        qwe2.put(qwe.getJSONObject(i));
                    }
                }
                return qwe2.toString();
            }
            else if (category == 2)
            {
                JSONArray qwe = jsonObject.getJSONArray("ShortsModel");
                JSONArray qwe2 = new JSONArray();
                for(int i = 0;i < qwe.length(); i++)
                {
                    if(qwe.getJSONObject(i).getString("category").strip().equals("ΣΥΝΑΓΩΓΗ ΤΟΥ ΣΑΤΑΝΑ ΚΑΙ Ο ΑΝΤΙΧΡΙΣΤΟΣ"))
                    {
                        qwe2.put(qwe.getJSONObject(i));
                    }
                }
                return qwe2.toString();
            }
            else if (category == 3)
            {
                JSONArray qwe = jsonObject.getJSONArray("ShortsModel");
                JSONArray qwe2 = new JSONArray();
                for(int i = 0;i < qwe.length(); i++)
                {
                    if(qwe.getJSONObject(i).getString("category").strip().equals("ΤΣΙΠΑΚΙΑ"))
                    {
                        qwe2.put(qwe.getJSONObject(i));
                    }
                }
                return qwe2.toString();
            }
            else if (category == 4)
            {
                JSONArray qwe = jsonObject.getJSONArray("ShortsModel");
                JSONArray qwe2 = new JSONArray();
                for(int i = 0;i < qwe.length(); i++)
                {
                    if(qwe.getJSONObject(i).getString("category").strip().equals("ΕΛΕΓΧΟΣ"))
                    {
                        qwe2.put(qwe.getJSONObject(i));
                    }
                }
                return qwe2.toString();
            }
            else if (category == 5)
            {
                JSONArray qwe = jsonObject.getJSONArray("ShortsModel");
                JSONArray qwe2 = new JSONArray();
                for(int i = 0;i < qwe.length(); i++)
                {
                    if(qwe.getJSONObject(i).getString("category").strip().equals("ΚΟΡΩΝΟΪΟΣ"))
                    {
                        qwe2.put(qwe.getJSONObject(i));
                    }
                }
                return qwe2.toString();
            }
            else if (category == 6)
            {
                JSONArray qwe = jsonObject.getJSONArray("ShortsModel");
                JSONArray qwe2 = new JSONArray();
                for(int i = 0;i < qwe.length(); i++)
                {
                    if(qwe.getJSONObject(i).getString("category").strip().equals("ΚΟΡΩΝΟΪΟΣ ΠΡΟΓΝΩΣΤΙΚΟΣ ΠΡΟΓΡΑΜΜΑΤΙΣΜΟΣ"))
                    {
                        qwe2.put(qwe.getJSONObject(i));
                    }
                }
                return qwe2.toString();
            }
            else if (category == 7)
            {
                JSONArray qwe = jsonObject.getJSONArray("ShortsModel");
                JSONArray qwe2 = new JSONArray();
                for(int i = 0;i < qwe.length(); i++)
                {
                    if(qwe.getJSONObject(i).getString("category").strip().equals("Η ΠΡΑΓΜΑΤΙΚΗ ΘΡΗΣΚΕΙΑ ΤΩΝ 'ΠΕΤΥΧΗΜΕΝΩΝ'"))
                    {
                        qwe2.put(qwe.getJSONObject(i));
                    }
                }
                return qwe2.toString();
            }
            else if (category == 8)
            {
                JSONArray qwe = jsonObject.getJSONArray("ShortsModel");
                JSONArray qwe2 = new JSONArray();
                for(int i = 0;i < qwe.length(); i++)
                {
                    if(qwe.getJSONObject(i).getString("category").strip().equals("ΨΕΥΤΙΚΗ ΕΞΩΓΗΙΝΗ ΕΙΣΒΟΛΗ"))
                    {
                        qwe2.put(qwe.getJSONObject(i));
                    }
                }
                return qwe2.toString();
            }
            else if (category == 9)
            {
                JSONArray qwe = jsonObject.getJSONArray("ShortsModel");
                JSONArray qwe2 = new JSONArray();
                for(int i = 0;i < qwe.length(); i++)
                {
                    if(qwe.getJSONObject(i).getString("category").strip().equals("ΜΕΙΩΣΗ ΤΟΥ ΠΛΗΘΥΣΜΟΥ"))
                    {
                        qwe2.put(qwe.getJSONObject(i));
                    }
                }
                return qwe2.toString();
            }
            else if (category == 10)
            {
                JSONArray qwe = jsonObject.getJSONArray("ShortsModel");
                JSONArray qwe2 = new JSONArray();
                for(int i = 0;i < qwe.length(); i++)
                {
                    if(qwe.getJSONObject(i).getString("category").strip().equals("ΕΠΙΠΛΕΟΝ"))
                    {
                        qwe2.put(qwe.getJSONObject(i));
                    }
                }
                return qwe2.toString();
            }
            else if (category == 11)
            {
                JSONArray qwe = jsonObject.getJSONArray("ShortsModel");
                JSONArray qwe2 = new JSONArray();
                for(int i = 0;i < qwe.length(); i++)
                {
                    if(qwe.getJSONObject(i).getString("category").strip().equals(""))
                    {
                        qwe2.put(qwe.getJSONObject(i));
                    }
                }
                return qwe2.toString();
            }
            else
            {
                return "";
            }
        }
        catch (Exception e)
        {
            return "";
        }
    }

}
