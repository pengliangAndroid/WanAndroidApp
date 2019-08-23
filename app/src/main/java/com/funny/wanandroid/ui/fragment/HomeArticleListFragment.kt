package ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseViewHolder
import com.funny.appframework.base.BaseLazyFragment
import com.funny.appframework.data.model.ArticleInfo
import com.funny.appframework.mvp.HomeArticleListPresenter
import com.funny.appframework.mvp.HomeArticleListView
import com.funny.wanandroid.AppConstants
import com.funny.wanandroid.R
import com.funny.wanandroid.ui.adapter.CommonAdapter
import com.funny.wanandroid.widget.CustomLoadMoreView
import com.funny.wanandroid.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_home_article_list.*
import kotlin.properties.Delegates




/**
 * @author pengl
 */
class HomeArticleListFragment : BaseLazyFragment(),HomeArticleListView{


    val presenter by lazy { HomeArticleListPresenter()}

    var adapter : CommonAdapter<ArticleInfo> by Delegates.notNull()

    override fun getLayoutId(): Int {
        return R.layout.fragment_home_article_list
    }

    override fun initViews(rootView: View, savedState: Bundle?) {
        val arrayOfInt = IntArray(4)
        arrayOfInt[0] = R.color.red
        arrayOfInt[1] = R.color.yellow
        arrayOfInt[2] = R.color.green
        arrayOfInt[3] = R.color.blue
        swipeRefreshLayout.setColorSchemeResources(*arrayOfInt)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(DividerItemDecoration(activity,DividerItemDecoration.VERTICAL_LIST,R.drawable.white_divider_10_bg))

        adapter = object : CommonAdapter<ArticleInfo>(R.layout.item_home_article, mutableListOf()){
            override fun convertViewItem(holder: BaseViewHolder, item: ArticleInfo) {
                holder.setText(R.id.tv_date,item.niceDate)
                holder.setText(R.id.tv_title,item.title)
                holder.setText(R.id.tv_category,item.superChapterName + "/" + item.chapterName)
                holder.setText(R.id.tv_author,item.author)

                holder.setVisible(R.id.tv_tag_new,item.fresh)

                holder.setVisible(R.id.tv_tag_top,item.isTop)

                if(!item.tags.isNullOrEmpty()){
                    holder.setVisible(R.id.tv_tag_green,true)
                    holder.setText(R.id.tv_tag_green,item.tags[0].name)
                }else{
                    holder.setVisible(R.id.tv_tag_green,false)
                }

//                holder.setVisible(R.id.tv_tag_top,item.i)
            }
        }
        recyclerView.adapter = adapter

        adapter.setLoadMoreView(CustomLoadMoreView())
        adapter.setOnLoadMoreListener{
            recyclerView.post {
                if (!presenter.hasMoreData) {
                    val isHideEnd = adapter.itemCount <= AppConstants.PAGE_SIZE
                    adapter.loadMoreEnd(isHideEnd)
                } else {
                    loadNextPageData()
                }
            }
        }
        swipeRefreshLayout.setOnRefreshListener {
            presenter.setCurPage(0)
            loadNextPageData()
        }
    }

    override fun initData() {

    }

    override fun loadData() {
        presenter.attachView(this)

        swipeRefreshLayout.isRefreshing = true

        loadNextPageData()
    }

    private fun loadNextPageData(){
        presenter.getHomeArticleList()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }


    override fun onGetDataSuccess(data: List<ArticleInfo>) {
        swipeRefreshLayout.isRefreshing = false

//        if (CommonUtils.isEmptyArray(data)) {
//            if (adapter.itemCount === 0) {
//                multipleStatusView.showEmpty()
//            }
//        }

        if (swipeRefreshLayout.isRefreshing) {
            adapter.setNewData(data)
        } else {
                adapter.addData(data)
            adapter.loadMoreComplete()
        }

        if (!presenter.hasMoreData) {
            val isHideEnd = adapter.itemCount <= AppConstants.PAGE_SIZE
            adapter.loadMoreEnd(isHideEnd)
        }

    }

    override fun onGetDataFail(error: String) {
        swipeRefreshLayout.isRefreshing = false

        if (swipeRefreshLayout.isRefreshing || adapter.itemCount == 0) {
//            multipleStatusView.showError()
//            ptrLayout.refreshComplete()
        } else {
            adapter.loadMoreFail()
        }
    }

    override fun onGetTopDataSuccess(data: List<ArticleInfo>) {

    }

    override fun onGetTopDataFail(error: String) {
    }

}