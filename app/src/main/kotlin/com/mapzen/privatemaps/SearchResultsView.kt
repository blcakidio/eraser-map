package com.mapzen.privatemaps

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView

public class SearchResultsView(context: Context, attrs: AttributeSet)
        : LinearLayout(context, attrs), ViewPager.OnPageChangeListener {

    public var onSearchResultsSelectedListener: OnSearchResultSelectedListener? = null

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_search_results, this, true)
        setOrientation(LinearLayout.VERTICAL)
    }

    public fun setAdapter(adapter: PagerAdapter) {
        val pager = findViewById(R.id.pager) as ViewPager
        val indicator = findViewById(R.id.indicator) as TextView
        pager.setAdapter(adapter)
        pager.setOnPageChangeListener(this)
        indicator.setText(getResources().getString(R.string.search_results_indicator,
                pager.getCurrentItem() + 1, pager.getAdapter().getCount()))
    }

    public fun setCurrentItem(position: Int) {
        val pager = findViewById(R.id.pager) as ViewPager
        pager.setCurrentItem(position)
    }

    public fun getCurrentItem(): Int {
        val pager = findViewById(R.id.pager) as ViewPager
        return pager.getCurrentItem()
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        val pager = findViewById(R.id.pager) as ViewPager
        val indicator = findViewById(R.id.indicator) as TextView
        indicator.setText(getResources().getString(R.string.search_results_indicator,
                position + 1, pager.getAdapter().getCount()))

        onSearchResultsSelectedListener?.onSearchResultSelected(position)
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    public trait OnSearchResultSelectedListener {
        public fun onSearchResultSelected(position: Int)
    }
}
