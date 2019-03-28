package top.iwill.listproxy.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import top.iwill.listproxy.R

/**
 * @description:
 * @author: btcw
 * @date: 2019/3/28
 */
class MyRcyAdapter(val mList: MutableList<String>) : RecyclerView.Adapter<MyRcyAdapter.ViewHolder>() {

    private var mContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        mContext = parent?.context
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_my_rcy_layout, parent,false))
    }

    override fun getItemCount(): Int = mList.size


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
       holder?.view?.findViewById<TextView>(R.id.tv)?.text =  mList[position]
    }

    data class ViewHolder(val view: View?) : RecyclerView.ViewHolder(view)
}