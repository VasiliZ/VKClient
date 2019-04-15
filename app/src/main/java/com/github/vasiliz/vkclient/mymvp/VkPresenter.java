package com.github.vasiliz.vkclient.mymvp;

import java.lang.ref.WeakReference;

public class VkPresenter<View extends VkBaseView> {

    private WeakReference<View> mReference;

    void attachView(final View pView){
        this.mReference = new WeakReference<View>(pView);
    }

    protected View getView(){
        return this.mReference.get();
    }

    void onPause(){

    }

    void onResume(){
        if (mReference!=null){
            mReference.get();
        }
    }

    void detachView(){
        if (mReference!=null){
            mReference=null;
        }
    }

}
