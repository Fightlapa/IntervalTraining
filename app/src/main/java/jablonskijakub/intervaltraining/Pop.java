package jablonskijakub.intervaltraining;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Japko on 2017-04-16.
 */
public class Pop extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow);
        DisplayMetrics dm=new DisplayMetrics();
        Display display=getWindowManager().getDefaultDisplay();
        display.getMetrics(dm);
        int width=dm.widthPixels;
        int height;
        String text="";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final TextView displayTextView=(TextView)findViewById(R.id.aboutText);
            text=extras.getString("text", text);
            displayTextView.setText(Html.fromHtml(text));
        }
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        height=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 17*MainActivity.countLines(text)+50, metrics);
        getWindow().setLayout((width),(height));

    }
}
