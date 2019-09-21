//package org.armstrong.ika.digitalbibleapp.Text;
//
//import android.content.Intent;
//import android.os.Bundle;
//
//import org.armstrong.ika.digitalbibleapp.Common.Utils;
//import org.armstrong.ika.digitalbibleapp.R;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class ProcessTextActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.process_text_main);
//
//        CharSequence text = getIntent()
//                .getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
//        // process the text
//        Utils.makeToast(getApplicationContext(),text.toString());
//    }
//}
