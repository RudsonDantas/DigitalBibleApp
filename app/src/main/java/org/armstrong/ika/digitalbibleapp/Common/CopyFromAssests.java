package org.armstrong.ika.digitalbibleapp.Common;

import android.content.Context;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyFromAssests {

    String TAG = "logg";

    public static Boolean status;

    Context context;

    String dbFile;

    Long assetDBSize;

    public CopyFromAssests(Context c, String f) {

        this.context = c;
        this.dbFile = f;

        PreExecute();

        Execute();

        PostExecute();

    }

    protected void PreExecute() {

        try {

            assetDBSize = getAssetDBSizeBytes();

        } catch (IOException e) {
            e.printStackTrace();
        }

        deleteDatabase();
    }

    protected void Execute() {

        InputStream inputStream;
        OutputStream outputStream;

        try {
            // open db from assets as input stream
            inputStream = context.getAssets().open(dbFile);

            // open db in application context
            File outputPath = context.getDatabasePath(dbFile);

            // make sure the database directory exist
            outputPath.getParentFile().mkdir();

            outputStream = new FileOutputStream(outputPath);

            // transfer contents
            byte[] buffer = new byte[1024];
            int length;

            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();

            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void PostExecute() {

        if (assetDBSize != getActualDBSizeBytes()) {
            deleteDatabase();
            Log.e(TAG, "onPostExecute: '" + dbFile + "' size mismatch");
            status = false;
        } else {
            Log.e(TAG, "onPostExecute: '" + dbFile + "' installed successfully");
            status = true; //testReadWrite();
        }

    }

    private long getActualDBSizeBytes() {
        return context.getDatabasePath(dbFile).length();
    }

    private void deleteDatabase() {
        File db = context.getDatabasePath(dbFile);
        if (db.exists()) {
            db.delete();
        }
    }

    private long getAssetDBSizeBytes() throws IOException {

        InputStream inputStream = context.getAssets().open(dbFile);

        byte[] buffer = new byte[1024];
        int length;
        long totalLength = 0;

        while ((length = inputStream.read(buffer)) > 0) {
            totalLength += length;
        }

        inputStream.close();

        return totalLength;
    }

//    private boolean testReadWrite() {
//        String databasePath = context.getDatabasePath(dbFile).toString();
//        SQLiteDatabase db = SQLiteDatabase.openDatabase(databasePath,
//                null, SQLiteDatabase.OPEN_READWRITE);
//        if (db != null) {
//            Log.e(TAG, "testReadWrite: " + dbFile + " open for read/write");
//            db.close();
//            return true;
//        } else {
//            Log.e(TAG, "testReadWrite: " + dbFile + " failure");
//            return false;
//        }
//    }

}
