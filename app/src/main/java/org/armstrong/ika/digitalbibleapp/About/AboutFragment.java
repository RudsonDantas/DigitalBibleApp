package org.armstrong.ika.digitalbibleapp.About;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.armstrong.ika.digitalbibleapp.Common.Utils;
import org.armstrong.ika.digitalbibleapp.PreferenceProvider;
import org.armstrong.ika.digitalbibleapp.R;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class AboutFragment extends Fragment {

    protected PreferenceProvider preferenceProvider;

    WebView webView;

    private String html;
    private int textSize;
    private String hexColor;

    public static AboutFragment newInstance() {

        AboutFragment aboutFragment = new AboutFragment();

        return aboutFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceProvider = new PreferenceProvider(getContext());
        textSize = preferenceProvider.gettextSizeVar();

        hexColor = String.format("#%06X", (0xFFFFFF & preferenceProvider.getColorVar()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.about_fragment, parent, false);
        webView = view.findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //webSettings.setCacheMode(webSettings.LOAD_NO_CACHE);

        //webView.clearCache(true);
        //webView.clearHistory();

        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String LINEHEIGHT = "110%";

        switch (textSize) {
            case 18:
                LINEHEIGHT = "120%";
                break;
            case 20:
                LINEHEIGHT = "130%";
                break;
            case 22:
                LINEHEIGHT = "140%";
                break;
        }

        html = Utils.loadAsset(getActivity(), "about_en.html");

        html = html.replaceFirst("SIZE", textSize + "px");
        html = html.replaceFirst("LINEHEIGHT", LINEHEIGHT);
        html = html.replaceFirst("TEXTCOLOR", hexColor);

        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);

        webView.addJavascriptInterface(new WebAppInterface(getActivity()), "Android");

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public class WebAppInterface {
        Context context;

        WebAppInterface(Context c) {
            context = c;
        }

        @JavascriptInterface
        public void getDataReference(String vars) { // called from html file

            String[] values = vars.split(";");

            String[] projection = {};
            String selectionClause = preferenceProvider.getlanguageCodeVar();
            String sortOrder = "";

            String[] selectionArgs = new String[3];
            selectionArgs[0] = values[1]; // book
            selectionArgs[1] = values[2]; // chapter
            selectionArgs[2] = values[3]; // verse

            Uri uri = Uri.parse("content://org.armstrong.ika.digitalbibleapp.DataProvider");

            Cursor cursor = getActivity().getContentResolver().query(
                    uri,
                    projection,
                    selectionClause,
                    selectionArgs,
                    sortOrder);

            String[] result = new String[cursor.getCount()];

            if (null == cursor) {

                values[0] = "Error!";
                result[0] = "You need to install a content provider.\nSee: Settings->Help.";

            } else if (cursor.getCount() < 1) {

                values[0] = "Error!";
                result[0] = "Search Unsuccessful!";

            } else {

                while (cursor.moveToNext()) {
                    result[cursor.getPosition()] = cursor.getString(cursor.getColumnIndex("txt"));
                }

            }

            StringBuilder txt = new StringBuilder();
            for (int i = 0; i < result.length; i++) {
                txt.append((i == result.length - 1) ? result[i] : result[i] + "  ");
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(values[0]);
            builder.setMessage(txt.toString());

            builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // do nothing
                }
            });
            builder.create();
            builder.show();
        }
    }


}
