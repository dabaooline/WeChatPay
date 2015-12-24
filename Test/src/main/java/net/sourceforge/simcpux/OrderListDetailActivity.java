package net.sourceforge.simcpux;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import net.sourceforge.simcpux.bean.PrePayListInfo;
import net.sourceforge.simcpux.util.PayUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2015/12/15.
 */
public class OrderListDetailActivity extends BaseActivity {

    @Bind(R.id.tv_detail)
    TextView tv_detail;

    @Bind(R.id.confirm_pay)
    Button bt_confirm_pay;

    private byte[] results;
    private IWXAPI wxApi;

    @Override
    public int getLayoutId() {

        return R.layout.activity_orderlistdetail;
    }

    @Override
    public void initView() {
        wxApi = WXAPIFactory.createWXAPI(getApplicationContext(), Constants.APP_ID);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        results = intent.getByteArrayExtra("result");
        tv_detail.setText(new String(results));
        if (results != null) {
            bt_confirm_pay.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initListener() {
        bt_confirm_pay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.confirm_pay:
                Toast.makeText(getApplicationContext(), "test   ", Toast.LENGTH_SHORT).show();
                requestPay();
                break;
        }
    }

    private void requestPay() {
        if (results != null && results.length > 0) {
            try {
                JSONObject json = new JSONObject(new String(results));
                if (null != json && !json.has("retcode")) {
                    Gson gson = new Gson();
                    PrePayListInfo payParamsBean = gson.fromJson(json.toString().replace("package", "packageName"), PrePayListInfo.class);
                    //PayUtils.pay(payParamsBean, wxApi);
                    PayUtils.onKeyPay(getApplicationContext(), Constants.APP_ID, payParamsBean);
                } else {
                    Log.d("PAY_GET", "返回错误" + json.getString("retmsg"));
                    Toast.makeText(OrderListDetailActivity.this, "返回错误" + json.getString("retmsg"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("PAY_GET", "服务器请求错误");
            Toast.makeText(OrderListDetailActivity.this, "服务器请求错误", Toast.LENGTH_SHORT).show();
        }
    }
}
