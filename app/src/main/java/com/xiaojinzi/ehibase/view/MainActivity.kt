package com.xiaojinzi.ehibase.view

import android.content.Context
import android.view.View
import com.xiaojinzi.ehibase.R
import com.xiaojinzi.ehibase.base.view.IBaseActivity
import com.xiaojinzi.ehibase.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : IBaseActivity<MainPresenter>(), IMainView {

    override fun showData(s: String) {
        tv_data.setText(s)
    }

    override fun getView(context: Context?): View {
        return View.inflate(context, R.layout.activity_main, null)
    }

    init {
        presenter = MainPresenter(this)
    }

    fun clickView1(view: View?) {
        presenter.onLoadDataFailTest();
    }

    fun clickView2(view: View?) {
        presenter.onLoadDataSuccessTest();
    }

}
