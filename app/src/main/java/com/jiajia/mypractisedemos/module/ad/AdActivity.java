package com.jiajia.mypractisedemos.module.ad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.AdapterStatus;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.jiajia.mypractisedemos.R;
import com.jiajia.mypractisedemos.module.kotlin.util.ToastUtils;

import java.util.Map;

public class AdActivity extends AppCompatActivity {

    private static final String TAG = "AdActivity";

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        new Thread(
                this::initAdSDK)
                .start();
    }

    private void initAdSDK() {
        MobileAds.initialize(this, initializationStatus -> {
            for (Map.Entry<String, AdapterStatus> entry : initializationStatus.getAdapterStatusMap().entrySet()) {
                if (entry.getValue().getInitializationState() == AdapterStatus.State.READY) {
                    ToastUtils.showToast("广告SDK初始化完成");
                    loadInterstitialAd();
                    break;
                }
            }
        });
    }

    private void loadInterstitialAd() {
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d(TAG, loadAdError.toString());
                mInterstitialAd = null;
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                mInterstitialAd = interstitialAd;
                Log.i(TAG, "onAdLoaded");
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdClicked() {
                        // Called when a click is recorded for an ad.
                        Log.d(TAG, "Ad was clicked.");
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        // Set the ad reference to null so you don't show the ad a second time.
                        Log.d(TAG, "Ad dismissed fullscreen content.");
                        mInterstitialAd = null;
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Called when ad fails to show.
                        Log.e(TAG, "Ad failed to show fullscreen content.");
                        mInterstitialAd = null;
                    }

                    @Override
                    public void onAdImpression() {
                        // Called when an impression is recorded for an ad.
                        Log.d(TAG, "Ad recorded an impression.");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when ad is shown.
                        Log.d(TAG, "Ad showed fullscreen content.");
                    }
                });
                mInterstitialAd.show(AdActivity.this);
            }
        });
    }
}