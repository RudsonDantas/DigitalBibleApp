package org.armstrong.ika.digitalbibleapp.Common;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Copier {

    private Context context;
    private String db_name;

    public boolean installDatabaseFromAssets(Context c, String d) {

        this.context = c;
        this.db_name = d;

        final InputStream inputStream;
        final OutputStream outputStream;

        final File outputFile = context.getDatabasePath(db_name);

        try {

            inputStream = context.getAssets().open(db_name);

            outputStream = new FileOutputStream(outputFile);

            byte[] buffer = new byte[8192];
            int length;

            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();

            outputStream.close();
            inputStream.close();

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }


}
