package com.funny.wanandroid.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.funny.wanandroid.R;

/**
 * @author pengl
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int defaultDivider = R.drawable.divider_item_shape;

    public static final int HORIZONTAL_LIST = LinearLayout.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayout.VERTICAL;

    public Drawable mDivider;

    public int mOrientation;

    public boolean bottomDivider = false;


    public DividerItemDecoration(Context context, int orientation) {
        this.setDefaultDivider(context);
        this.setOrientation(orientation);
    }


    public DividerItemDecoration(Activity activity, int orientation, int drawableId) {
        this.setDivider(activity, drawableId);
        this.setOrientation(orientation);
    }


    /**
     * set divider color
     *
     * @param activity activity
     * @param drawableId drawableId
     */
    private void setDivider(Activity activity, int drawableId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                this.mDivider = activity.getTheme().getDrawable(drawableId);
            } else {
                this.mDivider = activity.getResources().getDrawable(drawableId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * set default divider color
         */
        if (this.mDivider == null) this.setDefaultDivider(activity);
    }


    /**
     * set default divider color
     *
     * @param context context
     */
    private void setDefaultDivider(Context context) {
        //final TypedArray typedArray = context.obtainStyledAttributes(ATTRS);
        this.mDivider = context.getResources().getDrawable(defaultDivider);
        //typedArray.recycle();
    }


    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        this.mOrientation = orientation;
    }


    /**
     * Draw any appropriate decorations into the Canvas supplied to the RecyclerView.
     * Any content drawn by this method will be drawn before the item views are drawn,
     * and will thus appear underneath the views.
     *
     * @param c Canvas to draw into
     * @param parent RecyclerView this ItemDecoration is drawing into
     * @param state The current state of RecyclerView
     */
    @Override public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (this.mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }


    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        int drawCount = childCount;
        if (!bottomDivider) drawCount--;
        for (int i = 0; i <= drawCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params
                    = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin +
                    Math.round(ViewCompat.getTranslationY(child));
            final int bottom = top + this.mDivider.getIntrinsicHeight();
            this.mDivider.setBounds(left, top, right, bottom);
            this.mDivider.draw(c);
        }
    }


    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        int drawCount = childCount;
        if (!bottomDivider) drawCount--;
        for (int i = 0; i <= drawCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params
                    = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin +
                    Math.round(ViewCompat.getTranslationX(child));
            final int right = left + this.mDivider.getIntrinsicHeight();
            this.mDivider.setBounds(left, top, right, bottom);
            this.mDivider.draw(c);
        }
    }


    /**
     * Retrieve any offsets for the given item. Each field of <code>outRect</code> specifies
     * the number of pixels that the item view should be inset by, similar to padding or margin.
     * The default implementation sets the bounds of outRect to 0 and returns.
     * If this ItemDecoration does not affect the positioning of item views, it should set
     * all four fields of <code>outRect</code> (left, top, right, bottom) to zero
     * before returning.
     * If you need to access Adapter for additional data, you can call
     * RecyclerView#getChildAdapterPosition(View)} to get the adapter position of the
     * View.
     *
     * @param outRect Rect to receive the output.
     * @param view The child view to decorate
     * @param parent RecyclerView this ItemDecoration is decorating
     * @param state The current state of RecyclerView.
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (this.mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, this.mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, this.mDivider.getIntrinsicWidth(), 0);
        }
    }
}
