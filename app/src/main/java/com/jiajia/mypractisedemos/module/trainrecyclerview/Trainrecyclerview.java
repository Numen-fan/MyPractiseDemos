package com.jiajia.mypractisedemos.module.trainrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.jiajia.mypractisedemos.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Trainrecyclerview extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.cb_train_check)
    CheckBox cb_train_check;
    @BindView(R.id.btn_train_clear)
    Button btn_train_clear;
    @BindView(R.id.btn_train_sure)
    Button btn_train_sure;

    List<ItemBean> list;
    TrainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainrecyclerview);
        ButterKnife.bind(this);
        initView();
        initData();
        initRecyclerView();

    }

    private void initView() {
        btn_train_clear.setOnClickListener(this);
        btn_train_sure.setOnClickListener(this);
    }

    private void initRecyclerView() {
        adapter = new TrainAdapter(this, list);
        adapter.setSelectListener(new ItemSelectedListener(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
    }

    private void initData() {
        list = new ArrayList<ItemBean>();
        for (int i = 1; i < 24; i++) {
            list.add(new ItemBean(i + ""));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_train_clear:
                for (ItemBean bean: list) {
                    bean.setSelected(false);
                    bean.setMiddle(false);
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.btn_train_sure:
                for (ItemBean bean: list) {
                    if (bean.isSelected() || bean.isMiddle()){
                        bean.setHandled(true);
                    }
                }
                adapter.notifyDataSetChanged();
            default:
                break;
        }
    }

    class ItemSelectedListener implements TrainAdapter.onItemSelectListener {

        WeakReference<Trainrecyclerview> ref;
        public ItemSelectedListener(Trainrecyclerview activity) {
            ref = new WeakReference<Trainrecyclerview>(activity);
        }

        @Override
        public void onSelect(int pos, int latestSelectedIndex, int selectedNums) {
            Toast.makeText(ref.get(), list.get(pos).getContent(), Toast.LENGTH_SHORT).show();
            if (cb_train_check.isChecked()) {
                if (selectedNums == 0) { // 执行清空
                    for (ItemBean bean: list) {
                        bean.setSelected(false);
                        bean.setMiddle(false);
                    }
                    list.get(pos).setSelected(true);
                    adapter.setSelectedNums(1);
                }else if (selectedNums == 1){
                    list.get(pos).setSelected(true);
                }else {
                    // 说明选了两个
                    if (pos == latestSelectedIndex) {
                        // 两次选中了同一个
                        list.get(pos).setSelected(false);
                        adapter.setSelectedNums(0);
                    }else {
                        for (int i = 0; i < list.size(); i++) {
                            if (i == pos || i == latestSelectedIndex) {
                                list.get(i).setSelected(true);
                            }
                            if ((i < pos && i > latestSelectedIndex) || (i > pos && i < latestSelectedIndex)) {
                                list.get(i).setMiddle(list.get(i).isHandled() ? false : true);
                            }
                        }
                    }
                }
            }// cb_train_check.isChecked()
            else {
                list.get(pos).setSelected(!list.get(pos).isSelected());
            }
            adapter.notifyDataSetChanged();
        }
    }
}
