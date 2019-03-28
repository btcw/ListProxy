package top.iwill.listproxy

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import top.iwill.listmanager.RecyclerViewProxy
import top.iwill.listproxy.adapter.MyRcyAdapter
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val list by lazy {
        mutableListOf<String>().apply {
            for (i in 0 until 10000)
                add("数据填充")
        }
    }

    private val mErrorView by lazy { LayoutInflater.from(this).inflate(R.layout.test_error_layout, null) }

//    "第一行数据", "第二行数据", "第三行数据", "第四行数据",
//    "第五行数据", "第六行数据", "第七行数据", "第八行数据"

    private val r = Random(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv.adapter = MyRcyAdapter(list)
        val rvd = RecyclerViewProxy(rv, null, mErrorView)
        rvd.notifyDataSetChanged()

        mErrorView.findViewById<TextView>(R.id.refresh).setOnClickListener {
            rvd.notifyDataSetChanged()
        }
        button.setOnClickListener {
            val key = r.nextInt(3)
            when (key) {
                0 -> {
                    list.clear()
                    rvd.notifyDataSetChanged()
                }
                1 -> {
                    list.add("测试数据一")
                    list.add("测试数据二")
                    rvd.notifyDataSetChanged()
                }
                2 -> {
                    rvd.showErrorView()
                }
            }
        }
    }
}
