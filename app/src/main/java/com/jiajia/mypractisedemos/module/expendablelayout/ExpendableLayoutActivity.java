package com.jiajia.mypractisedemos.module.expendablelayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaychan.library.ExpandableLinearLayout;
import com.jiajia.mypractisedemos.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpendableLayoutActivity extends AppCompatActivity {

    @BindView(R.id.ell_product)
    ExpandableLinearLayout ell_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expendable_layout);

        ButterKnife.bind(this);

        ell_product.removeAllViews();

        for (int i = 0; i < 7; i+=2) {
            View view = View.inflate(this, R.layout.item_expendable_layout, null);
            MyViewHolder holder = new MyViewHolder(view, "5-门0" + i, (i+1) < 7? "5-门0" + (i+1): "");
            holder.refreshUI();
            ell_product.addItem(view);
        }
    }
}

class MyViewHolder {

    @BindView(R.id.linear_item_expendable_layout1)
    LinearLayout linear_item_expendable_layout1;
    @BindView(R.id.ckb_expendable1)
    CheckBox ckb_expendable1;
//    @BindView(R.id.tv_expendable_content1)
//    TextView tv_expendable_content1;

    @BindView(R.id.linear_item_expendable_layout2)
    LinearLayout linear_item_expendable_layout2;
    @BindView(R.id.ckb_expendable2)
    CheckBox ckb_expendable2;
//    @BindView(R.id.tv_expendable_content2)
//    TextView tv_expendable_content2;

    String content1,content2;

    public MyViewHolder(View view, String content1, String content2) {
        ButterKnife.bind(this, view);
        this.content1 = content1;
        this.content2 = content2;
    }

    protected void refreshUI() {
        if (content1 == null || content1.equals("")) {
            linear_item_expendable_layout1.setVisibility(View.GONE);
        }else {
            ckb_expendable1.setText(content1);
        }
        if (content2 == null || content2.equals("")) {
            linear_item_expendable_layout2.setVisibility(View.GONE);
        }else {
            ckb_expendable2.setText(content2);
        }
    }
}
