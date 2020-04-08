package sjsu.android.alarmclockplusplus;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SoundSelectorActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 100;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.DarkTheme);
        setContentView(R.layout.activity_sound_selector);


        requestPermission();
        if (checkPermission()){
            Toast.makeText(SoundSelectorActivity.this, "has permission",
                    Toast.LENGTH_LONG).show();
            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
            String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
            Cursor cur = getApplicationContext().getContentResolver().query(uri, null, selection, null, sortOrder);
            int count = 0;

            if(cur != null) {
                count = cur.getCount();
                String titles = "";
                if(count > 0) {
                    while(cur.moveToNext()) {
                        titles += cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.TITLE)) + "\n";
                        // Add code to get more column here

                        // Save to your list here
                    }
                }
                cur.close();
            }
        }
        else {
            Toast.makeText(SoundSelectorActivity.this, "does not have permission",
                    Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(
                SoundSelectorActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }
}
