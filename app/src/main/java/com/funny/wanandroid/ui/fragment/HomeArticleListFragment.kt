package ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseViewHolder
import com.funny.appframework.base.BaseLazyFragment
import com.funny.appframework.data.model.ArticleInfo
import com.funny.appframework.mvp.HomeArticleListPresenter
import com.funny.appframework.mvp.HomeArticleListView
import com.funny.wanandroid.R
import com.funny.wanandroid.ui.adapter.CommonAdapter
import kotlinx.android.synthetic.main.fragment_home_article_list.*



/**
 * @author pengl
 */
class HomeArticleListFragment : BaseLazyFragment(),HomeArticleListView{

    val presenter by lazy { HomeArticleListPresenter()}

    var adapter : CommonAdapter<ArticleInfo>?= null

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
//        recyclerView.addItemDecoration()

        adapter = object : CommonAdapter<ArticleInfo>(R.layout.fragment_system, emptyList()){
            override fun convertViewItem(baseViewHolder: BaseViewHolder, item: ArticleInfo) {

            }
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

    }

    override fun onGetDataFail(error: String) {

    }

}