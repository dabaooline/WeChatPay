package net.sourceforge.simcpux;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by admin on 2015/12/15.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initData();
        initListener();


    }


    public void initListener(){};

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData();



}
