package com.example.findnerds.finderagain.NewsData.NewsUI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findnerds.finderagain.R
import com.example.findnerds.finderagain.NewsData.NewsAdapter.RecyclerAdapter


class FragTru : Fragment() {


    companion object {
        @JvmStatic
        fun newInstance() = NewsFragment()
        @JvmStatic
        private var viewModel: NewsViewModel? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view= inflater.inflate(R.layout.news_fragment, container, false)
        viewModel = ViewModelProviders.of(activity!!).get(NewsViewModel::class.java)
        val rView = view.findViewById<RecyclerView>(R.id.fragment_recycler)
        val manager = LinearLayoutManager(this.requireActivity())
        rView.layoutManager= manager
        viewModel!!.newsSearchedObservable.observe(this, Observer { articles->
            rView.adapter = RecyclerAdapter(this.activity!!, articles)
        })
        //rView.adapter = RecyclerAdapter(this.activity!!,dataArt!!)

        /*   rView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
               override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                   super.onScrollStateChanged(recyclerView, newState)
                   if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                       isScrolling=true
                   }
               }

               override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                   super.onScrolled(recyclerView, dx, dy)
                   currentItem = manager.childCount
                   totalItem=manager.itemCount
                   scrolledOutItem = manager.findFirstVisibleItemPosition()
                   if (isScrolling&&(currentItem+scrolledOutItem==totalItem)){
                       isScrolling=false

                           Handler().postDelayed({
                               viewModel!!.newsSearchedObservable.observe(this@NewsFragment, Observer { articles ->
                                   rView.adapter = RecyclerAdapter(context!!, articles)

                               })
                           },5000)

                   }
               }
           })*/
        //observer(viewModel!!)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //observer(viewModel!!)
        // TODO: Use the ViewModel
    }

    /* private fun observer(viewModel: NewsViewModel){
         viewModel = ViewModelProviders.of(activity!!).get(NewsViewModel::class.java)
         viewModel.newsArticleObservable.observe(this, Observer { articles->
             updateUi(articles)
         })
     }
     private fun updateUi(responses: NewsResponse?) {
     }
 */
}
