package com.jiajia.mypractisedemos.module.jetpack;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jiajia.mypractisedemos.R;
import com.jiajia.mypractisedemos.databinding.ActivityJetpackBinding;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JetpackActivity extends AppCompatActivity {

    ActivityJetpackBinding binding;
    User user;
    People people;
    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_jetpack);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_jetpack);
        user = new User("家佳",25);
        binding.setUser(user);
        people = new People("jiajia", 23, null);
        binding.setProple(people);
        presenter = new Presenter();
        binding.setPresenter(presenter);

    }

    public class Presenter {
        public void onClick(View view) {
            people.name.set("啊啊啊啊啊");
            Toast.makeText(JetpackActivity.this, "点击了Button",Toast.LENGTH_SHORT).show();
        }
    }
}
