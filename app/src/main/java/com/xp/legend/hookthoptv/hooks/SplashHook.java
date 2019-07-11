package com.xp.legend.hookthoptv.hooks;

import android.content.Context;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class SplashHook implements IXposedHookLoadPackage {

    private ClassLoader classLoader;


    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {


        if (lpparam.packageName.equals("livetvstream.thoptv.com.thoptv")){

            XposedHelpers.findAndHookMethod("com.stub.StubApp", lpparam.classLoader, "attachBaseContext", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);

                    Context context= (Context) param.args[0];

                    classLoader=context.getClassLoader();

//                    XposedBridge.log("class---->>>获取成功！");

                    change(classLoader);
                }
            });


        }





    }

    private void change(ClassLoader classLoader){


        XposedHelpers.findAndHookMethod("livetvstream.thoptv.com.thoptv.Splash",
                classLoader, "b", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);

//                        XposedBridge.log("result--->>>"+param.getResult());

                        param.setResult("200");

                    }
                });

    }


}
