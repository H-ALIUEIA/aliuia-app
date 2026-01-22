package io.github.h_aliueia.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class notificationreceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        try
        {
            Bundle b = getResultExtras(true);
            String texttocopy = intent.getExtras().getString("texttocopy");
            if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB)
            {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(texttocopy);
            }
            else
            {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", texttocopy);
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(context, "Αντιγράφτηκε", Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {

        }
    }
}
