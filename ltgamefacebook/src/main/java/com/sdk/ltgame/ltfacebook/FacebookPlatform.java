package com.sdk.ltgame.ltfacebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.sdk.ltgame.ltfacebook.uikit.FacebookActionActivity;
import com.gentop.ltgame.ltgamesdkcore.common.LTGameOptions;
import com.gentop.ltgame.ltgamesdkcore.common.LTGameSdk;
import com.gentop.ltgame.ltgamesdkcore.common.Target;
import com.gentop.ltgame.ltgamesdkcore.impl.OnLoginStateListener;
import com.gentop.ltgame.ltgamesdkcore.impl.OnRechargeListener;
import com.gentop.ltgame.ltgamesdkcore.model.LoginObject;
import com.gentop.ltgame.ltgamesdkcore.model.RechargeObject;
import com.gentop.ltgame.ltgamesdkcore.platform.AbsPlatform;
import com.gentop.ltgame.ltgamesdkcore.platform.IPlatform;
import com.gentop.ltgame.ltgamesdkcore.platform.PlatformFactory;
import com.gentop.ltgame.ltgamesdkcore.uikit.BaseActionActivity;
import com.gentop.ltgame.ltgamesdkcore.util.LTGameUtil;

public class FacebookPlatform extends AbsPlatform {

    private static final String TAG = FacebookPlatform.class.getSimpleName();
    private FacebookLoginHelper mLoginHelper;


    private FacebookPlatform(Context context, boolean isServerTest, String appId, String appKey,
                             String adID, String packageID, int target) {
        super(context, isServerTest, appId, appKey, adID, packageID, target);
    }


    public static class Factory implements PlatformFactory {

        @Override
        public IPlatform create(Context context, int target) {
            IPlatform platform = null;
            LTGameOptions options = LTGameSdk.options();
            if (!LTGameUtil.isAnyEmpty(options.getLtAppId(),
                    options.getLtAppKey(), options.getAdID(), options.getPackageID())) {
                platform = new FacebookPlatform(context, options.getISServerTest(), options.getLtAppId(),
                        options.getLtAppKey(), options.getAdID(), options.getPackageID(),
                        target);
            }
            return platform;
        }

        @Override
        public int getPlatformTarget() {
            return Target.PLATFORM_FACEBOOK;
        }

        @Override
        public boolean checkLoginPlatformTarget(int target) {
            return target == Target.LOGIN_FACEBOOK;
        }

        @Override
        public boolean checkRechargePlatformTarget(int target) {
            return false;
        }
    }

    @Override
    public Class getUIKitClazz() {
        return FacebookActionActivity.class;
    }

    @Override
    public void onActivityResult(BaseActionActivity activity, int requestCode, int resultCode, Intent data) {
        super.onActivityResult(activity, requestCode, resultCode, data);
        mLoginHelper.setOnActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void login(Activity activity, int target, LoginObject object, OnLoginStateListener listener) {
        mLoginHelper = new FacebookLoginHelper(activity, listener, target);
        mLoginHelper.login(object.getFacebookAppID(), activity, object.isLoginOut());

    }

    @Override
    public void recharge(Activity activity, int target, RechargeObject object, OnRechargeListener listener) {

    }
}
