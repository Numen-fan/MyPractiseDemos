package com.jiajia.mypractisedemos.module.manfunctionsui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiajia.mypractisedemos.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import q.rorbin.badgeview.QBadgeView;

public class ManyFunctionUIActivity extends AppCompatActivity {

    @BindView(R.id.rl_zflhp)
    RelativeLayout rl_zflhp;
    @BindView(R.id.tv_dsh)
    TextView tv_dsh;
    @BindView(R.id.img_chevron_tlzljssh)
    ImageView img_chevron_tlzljssh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_many_function_ui);

        ButterKnife.bind(this);
        final QBadgeView view = new QBadgeView(this);

        rl_zflhp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ManyFunctionUIActivity.this, "直放理货票", Toast.LENGTH_SHORT).show();
                view.hide(true);

            }
        });
        tv_dsh.setText("     ");
        view.bindTarget(tv_dsh);
        view.setBadgeGravity(Gravity.CENTER);
        view.setShowShadow(true);
        view.setBadgeNumber(5);
    }
}
