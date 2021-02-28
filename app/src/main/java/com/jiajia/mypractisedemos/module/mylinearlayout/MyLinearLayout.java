
package com.jiajia.mypractisedemos.module.mylinearlayout;

import android.content.Context;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiajia.mypractisedemos.R;

/**
 * Created by fanjiajia02 on 2020-07-26
 */
public class MyLinearLayout extends LinearLayout {

    private TextView content;
    private TextView select;
    private View divider_view;

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // 这句话中root为this才能使得item布局的根节点的width和height有效
        LayoutInflater.from(context).inflate(R.layout.mylinearlayout_item, this);

        content = findViewById(R.id.content);
        select = findViewById(R.id.select);
        divider_view = findViewById(R.id.divider_view);
    }

    public void setContent(String content) {
        if (!TextUtils.isEmpty(content)) {
            this.content.setText(content);
        }
    }

    public void setData (String food) {
        if (!TextUtils.isEmpty(food)) {
            this.select.setText(food);
        }
    }

    public void showDividerView(boolean showDividerView) {
        if(divider_view != null) {
            divider_view.setVisibility(showDividerView ? View.VISIBLE : View.GONE);
        }
    }
}
