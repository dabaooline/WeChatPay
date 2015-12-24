package net.sourceforge.simcpux;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.sourceforge.simcpux.bean.ProductList;
import net.sourceforge.simcpux.dialog.DialogHelper;
import net.sourceforge.simcpux.util.HttpUtils;
import net.sourceforge.simcpux.util.WXCreatePayListUtils;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @Bind(R.id.et_product_name)
    EditText et_product_name;

    @Bind(R.id.et_product_des)
    EditText et_product_des;

    @Bind(R.id.et_product_price)
    EditText et_product_price;

    @Bind(R.id.bt_cteate_list)
    Button bt_create_list;

    @Bind(R.id.bt_onkey_pay)
    Button bt_onkey_pay;

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        bt_create_list.setOnClickListener(this);
        bt_onkey_pay.setOnClickListener(this);
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bt_cteate_list:
                CreateOrderListAsyncTask createOrderListAsyncTask = new CreateOrderListAsyncTask();
                createOrderListAsyncTask.execute();
                break;
            case R.id.bt_onkey_pay:
                String testUrl = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
                WXCreatePayListUtils wxCreatePayListUtils = new WXCreatePayListUtils(getApplicationContext(), Constants.APP_ID);
                wxCreatePayListUtils.createPrepayOrderList(new ProductList(),testUrl,true);
                break;
        }
    }


    class CreateOrderListAsyncTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog waitDialog;
        private byte[] bytes;

        @Override
        protected void onPreExecute() {
            //显示等待dialog
            waitDialog = DialogHelper.getWaitDialog(MainActivity.this, "正在生成订单...");
            waitDialog.setCanceledOnTouchOutside(false);
            waitDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                //将订单数据传输到后台
                String testUrl = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
                bytes = HttpUtils.httpGet(testUrl);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            waitDialog.dismiss();
            if (bytes != null) {
                Intent intent = new Intent(getApplicationContext(), OrderListDetailActivity.class);
                intent.putExtra("result", bytes);
                startActivity(intent);
            } else {
                DialogHelper.getConfirmDialog(MainActivity.this, "连接错误...", null, null).show();
            }
            super.onPostExecute(param);
        }
    }
}
