package top.iwill.listmanager

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * @description: 列表view的代理类
 * @author: btcw
 * @date: 2019/3/28
 */
class RecyclerViewDelegate(
    private val mRecyclerView: RecyclerView,
    var mEmptyView: View? = null,
    var mErrorView: View? = null
) {

    companion object {
        const val NORMAL = 0
        const val EMPTY = 1
        const val ERROR = 2
    }

    private val mContext = mRecyclerView.context

    private val mParent by lazy { mRecyclerView.parent as ViewGroup }

    private var mState = NORMAL

    private var timeTag = 0L

    init {
        mEmptyView?.let {
            it.layoutParams = mRecyclerView.layoutParams
        } ?: let {
            mEmptyView = TextView(mContext).apply {
                text = "暂无数据"
                gravity = Gravity.CENTER
                layoutParams = mRecyclerView.layoutParams
            }
        }

        mErrorView?.let {
            it.layoutParams = mRecyclerView.layoutParams
        } ?: let {
            mErrorView = TextView(mContext).apply {
                text = "遇到问题了呢..."
                setTextColor(Color.RED)
                gravity = Gravity.CENTER
                layoutParams = mRecyclerView.layoutParams
            }
        }

    }

    public fun notifyDataSetChanged() {
        if (isEmpty())
            showEmptyView()
        else
            showRecyclerView()
        mRecyclerView.adapter?.notifyDataSetChanged()
        timeTag = System.currentTimeMillis()
    }


    private fun showEmptyView() {
        if (mState == ERROR) {
            hideErrorView()
        } else if (mState == NORMAL) {
            mParent.removeView(mRecyclerView)
            mParent.addView(mEmptyView)
            mState = EMPTY
        }
    }

    private fun showRecyclerView() {
        if (mState == ERROR) {
            hideErrorView()
        } else if (mState == EMPTY) {
            mParent.removeView(mEmptyView)
            mParent.addView(mRecyclerView)
            mState = NORMAL
        }
    }

    private fun hideErrorView() {
        if (mState == ERROR) {
            mParent.removeView(mErrorView)
            mParent.addView(mRecyclerView)
            mState = NORMAL
            notifyDataSetChanged()
        }
    }

    public fun showErrorView() {
        if (mState != ERROR) {
            if (mState == EMPTY) mParent.removeView(mEmptyView) else mParent.removeView(mRecyclerView)
            mParent.addView(mErrorView)
            mState = ERROR
        }
    }

    private fun isEmpty() = mRecyclerView.adapter?.itemCount ?: 0 == 0

    public fun notifyItemChanged(position: Int) = mRecyclerView.adapter?.notifyItemChanged(position)

    public fun notifyItemInserted(position: Int) = mRecyclerView.adapter?.notifyItemInserted(position)


    public fun notifyItemMoved(fromPosition: Int, toPosition: Int) =
        mRecyclerView.adapter?.notifyItemMoved(fromPosition, toPosition)

    public fun notifyItemRangeChanged(startPosition: Int, itemCount: Int) =
        mRecyclerView.adapter?.notifyItemRangeChanged(startPosition, itemCount)
}