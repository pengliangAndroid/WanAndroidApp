package com.funny.appframework.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.TypedValue;

public class ColorUtil
{

  public static int getAttrColor(Context paramContext, int paramInt)
  {
    TypedValue localTypedValue = new TypedValue();
    paramContext.getTheme().resolveAttribute(paramInt, localTypedValue, true);
    return localTypedValue.data;
  }

  public static ColorStateList newColorStateList(int paramInt)
  {
    return ColorStateList.valueOf(paramInt);
  }

  public static ColorStateList newColorStateList(int paramInt1, int paramInt2)
  {
    int[][] arrayOfInt = { { -16842913 }, { 16842912 }, { 16842919 }, { 16842910 }, { 16842913 } };
    int[] arrayOfInt1 = new int[9];
    arrayOfInt1[0] = paramInt1;
    arrayOfInt1[1] = paramInt2;
    arrayOfInt1[2] = paramInt2;
    arrayOfInt1[3] = paramInt2;
    arrayOfInt1[4] = paramInt2;
    return new ColorStateList(arrayOfInt, arrayOfInt1);
  }



}