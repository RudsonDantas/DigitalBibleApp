package org.armstrong.ika.digitalbibleapp.Main;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

public class CustomLinearLayoutManager extends LinearLayoutManager {

    private static final float MILLISECONDS_PER_INCH = 50f;

    private Context context;

    public CustomLinearLayoutManager(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView,
                                       RecyclerView.State state, int position) {

        //Create your RecyclerView.SmoothScroller instance? Check.
        LinearSmoothScroller smoothScroller = new LinearSmoothScroller(context) {

                    @Override
                    protected int getVerticalSnapPreference() {
                        return LinearSmoothScroller.SNAP_TO_START;
                    }

                    //This controls the direction in which smoothScroll looks for your
                    //list item
                    @Override
                    public PointF computeScrollVectorForPosition(int targetPosition) {
                        //What is PointF? A class that just holds two float coordinates.
                        //Accepts a (x , y)
                        //for y: use -1 for up direction, 1 for down direction.
                        //for x (did not test): use -1 for left direction, 1 for right
                        //direction.
                        //We let our custom LinearLayoutManager calculate PointF for us
                        return CustomLinearLayoutManager.this.computeScrollVectorForPosition
                                (targetPosition);
                    }

                    //The holy grail of smooth scrolling
                    //returns the milliseconds it takes to scroll one pixel.
                    @Override
                    protected float calculateSpeedPerPixel
                    (DisplayMetrics displayMetrics) {
                        return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
                    }
                };

        //Docs do not tell us anything about this,
        //but we need to set the position we want to scroll to.
        smoothScroller.setTargetPosition(position);

        //Call startSmoothScroll(SmoothScroller)
        startSmoothScroll(smoothScroller);
    }
}
