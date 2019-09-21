package org.armstrong.ika.digitalbibleapp.Splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import org.armstrong.ika.digitalbibleapp.MainActivity;
import org.armstrong.ika.digitalbibleapp.VerKeyDb.VersionDatabase;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("Registered")
public class SplashActivity extends AppCompatActivity {

    private VersionDatabase versionDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        versionDatabase = VersionDatabase.getInstance(this);
//
//        versionDatabase.versionDoa().deleteAll();
//
//        String[] version_key = new String[]{
//                "1|1|1|1|t_kjv|KJV|eng|King James Version",
//                "2|1|2|1|t_clem|CLEM|lat|Vulgata Clementina",
//                "3|1|4|1|t_cpdv|CPDV|eng|Catholic Public Domain Version",
//                "4|1|3|1|t_nova|NOVA|lat|Nova Vulgata",
//                "5|1|6|0|t_afr|AFR53|afr|Afrikaans 1933/53",
//                "6|1|7|1|t_dan|DN1933|dan|Bibelen på Dansk"
//        };
//
//        for (int i = 0; i < version_key.length; i++) {
//
//            String line = version_key[i];
//
//            String[] parts = line.split("[|]+");
//
//            VersionEntities versionModel = new VersionEntities();
//
//            versionModel.setNumber(Integer.parseInt(parts[0]));
//            versionModel.setActive(Integer.parseInt(parts[1]));
//            versionModel.setOrderBy(Integer.parseInt(parts[2]));
//            versionModel.setCopyRight(Integer.parseInt(parts[3]));
//            versionModel.setTableName(parts[4]);
//            versionModel.setVerAbbr(parts[5]);
//            versionModel.setTransLang(parts[6]);
//            versionModel.setVerName(parts[7]);
//
//            versionDatabase.versionDoa().insertVerKeys(versionModel);

//        }


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 1500);
    }

}
