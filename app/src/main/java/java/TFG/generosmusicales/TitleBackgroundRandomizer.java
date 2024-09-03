package java.TFG.generosmusicales;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.TextView;


import java.util.Random;

public class TitleBackgroundRandomizer {

    private static final int[] titleBackgrounds = {
            R.drawable.background_1,
            R.drawable.background_2,
            R.drawable.background_3,
            R.drawable.background_4,
            R.drawable.background_5,
            R.drawable.background_6,
            R.drawable.backclasica

    };

    private static int getRandomTitleBackground() {
        Random random = new Random();
        return titleBackgrounds[random.nextInt(titleBackgrounds.length)];
    }

    public static void applyRandomTitleBackground(Context context, TextView textView) {
        int randomBackground = getRandomTitleBackground();
        Drawable drawable = context.getResources().getDrawable(randomBackground);
        textView.setBackground(drawable);
    }
}