package org.armstrong.ika.digitalbibleapp.Common;

import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

// https://github.com/eugenp/tutorials/tree/master/core-java-security

public final class Utils {

    public final static String encodedPassword = "D693C4871A99D7ACF43C4B1112DA0C6E";

    public static void makeToast(Context context, String text) {

        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static String makeMd5Hash(String text) {

        //String md5Hex = DigestUtils.md5Hex(text).toUpperCase();

        String md5Hex = new String(Hex.encodeHex(DigestUtils.md5(text))).toUpperCase();

        return md5Hex;
    }

    public static boolean checkMd5Hash(String input) {

        boolean check;

        check = (input.equals(encodedPassword)) ? true : false;

        return check;

    }

    public static String loadAsset(Context context, String inputFile) {

        String fContents = null;

        try {
            InputStream stream = context.getAssets().open(inputFile);

            int size = stream.available();

            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();

            fContents = new String(buffer);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fContents;

    }

    public static String escapeString(String st) {

        if (st.contains("'")) {
            st = st.replace("'", "`");
        }

        return st;

    }

    public static int calculateOffset() {

        int days = 30;

        int date = (int) (new Date().getTime() / 1000); // date as integer

        int limit = 60 * 60 * 24 * days; // 0 days clears cache

        int offset = (date - limit);

        return offset;
    }

    /**
     * Lightens a color by a given factor.
     *
     * @param color  The color to lighten
     * @param factor The factor to lighten the color. 0 will make the color unchanged. 1 will make the
     *               color white.
     * @return lighter version of the specified color.
     */
    public static int lighter(int color, float factor) {
        int red = (int) ((Color.red(color) * (1 - factor) / 255 + factor) * 255);
        int green = (int) ((Color.green(color) * (1 - factor) / 255 + factor) * 255);
        int blue = (int) ((Color.blue(color) * (1 - factor) / 255 + factor) * 255);
        return Color.argb(Color.alpha(color), red, green, blue);
    }



}
