package com.funny.wanandroid.widget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.funny.wanandroid.R;


/**
 * ClassName: CustomLoadMoreView
 * Function:
 * Date:     2017/2/18 15:49
 *
 * @author pengl
 * @see
 */
public class CustomLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.custom_view_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
