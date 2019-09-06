package com.funny.wanandroid.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.FrameLayout;

public final class FixInsetsFrameLayout extends FrameLayout
{
  private boolean insetEnable = false;
  private int[] mInsets = new int[4];

  public FixInsetsFrameLayout(Context paramContext)
  {
    super(paramContext);
  }

  public FixInsetsFrameLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public FixInsetsFrameLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  protected final boolean fitSystemWindows(Rect paramRect)
  {
    if ((!this.insetEnable) && (Build.VERSION.SDK_INT >= 21))
    {
      this.mInsets[0] = paramRect.left;
      this.mInsets[1] = paramRect.top;
      this.mInsets[2] = paramRect.right;
      paramRect.left = 0;
      paramRect.top = 0;
      paramRect.right = 0;
    }
    return super.fitSystemWindows(paramRect);
  }

  public final WindowInsets onApplyWindowInsets(WindowInsets paramWindowInsets)
  {
    if ((!this.insetEnable) && (Build.VERSION.SDK_INT >= 21))
    {
      this.mInsets[0] = paramWindowInsets.getSystemWindowInsetLeft();
      this.mInsets[1] = paramWindowInsets.getSystemWindowInsetTop();
      this.mInsets[2] = paramWindowInsets.getSystemWindowInsetRight();
      paramWindowInsets = super.onApplyWindowInsets(paramWindowInsets.replaceSystemWindowInsets(0, 0, 0, paramWindowInsets.getSystemWindowInsetBottom()));
    }
    return paramWindowInsets;
  }

  public void setInsetEnable(boolean paramBoolean)
  {
    this.insetEnable = paramBoolean;
  }
}