package com.jiajia.mypractisedemos.module.decoration;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jiajia.mypractisedemos.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DecorationActivity extends AppCompatActivity {

    @BindView(R.id.recyc_view_decoration)
    RecyclerView recyc_view_decoration;

    DecorationAdapter adapter;
    List<CityBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoration);
        ButterKnife.bind(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyc_view_decoration.setLayoutManager(manager);
        initDatas(getResources().getStringArray(R.array.provinces));
        adapter = new DecorationAdapter(this, mDatas);
        recyc_view_decoration.setAdapter(adapter);
        recyc_view_decoration.addItemDecoration(new MyItemDecoration(this));
        recyc_view_decoration.addItemDecoration(new TitleItemDecoration(this, mDatas));

    }

    private void initDatas(String[] data) {
        mDatas = new ArrayList<>();
        char tag = ' ';
        for (int i = 0; i < data.length; i++) {
            if (i % 4 == 0)
                tag = (char)(64 + i/4);
            CityBean cityBean = new CityBean();
            cityBean.setTag(String.valueOf(tag));
            cityBean.setCity(data[i]);//设置城市名称
            mDatas.add(cityBean);
        }
    }
}
