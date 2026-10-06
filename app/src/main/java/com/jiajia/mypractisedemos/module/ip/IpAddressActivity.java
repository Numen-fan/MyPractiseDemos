package com.jiajia.mypractisedemos.module.ip;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jiajia.mypractisedemos.R;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IpAddressActivity extends AppCompatActivity {

    private TextView mTvIpInfo;
    private TextView mTvCallCount;

    private int mAddressMethodCallCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_address);

        mTvIpInfo = findViewById(R.id.tv_ip_info);
        mTvCallCount = findViewById(R.id.tv_call_count);
        Button btnGetIp = findViewById(R.id.btn_get_ip);
        btnGetIp.setOnClickListener(v -> showLocalIp());

        showLocalIp();
    }

    private void showLocalIp() {
        String ips = getLocalIpv4Addresses();
        mTvIpInfo.setText(TextUtils.isEmpty(ips) ? "未获取到局域网 IP" : ips);
        mTvCallCount.setText("getHostAddress 调用总次数: " + mAddressMethodCallCount);
    }

    private String getLocalIpv4Addresses() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            if (en == null) {
                return ip;
            }
            outer:
            while (en.hasMoreElements()) {
                NetworkInterface intf = en.nextElement();
                if (intf == null || intf.isLoopback() || !intf.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                        ip = getHostAddress(inetAddress);
                        break outer;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ip;
    }

    private String getHostAddress(InetAddress address) {
        mAddressMethodCallCount++;
        return address.getHostAddress();
    }
}
