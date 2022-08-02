package com.jiajia.mypractisedemos.module.demo;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jiajia.mypractisedemos.R;

/**
 * Created by fanjiajia02 on 2022/6/28
 * Desc:
 **/
public class VoipTipsView extends FrameLayout {

    private SimpleDraweeView imgView;
    private TextView tvTips;

    public VoipTipsView(@NonNull Context context) {
        super(context);
    }

    public VoipTipsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        View layout = LayoutInflater.from(getContext()).inflate(R.layout.voip_tips_view_layout, this);
        imgView = layout.findViewById(R.id.img_tip_view);
        tvTips = layout.findViewById(R.id.tv_tips);
    }

    public void load(int imgResId, int tipsResId) {
        imgView.setImageResource(imgResId);
        tvTips.setText(tipsResId);
    }

    public void loadGif(int gifPath, int tipsResId) {
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(R.drawable.ic_cell))
                .build();

        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                // 设置uri,加载本地的gif资源
                .setUri(uri)
                .build();
        //设置Controller
        imgView.setController(draweeController);

        tvTips.setText(tipsResId);
    }

}
