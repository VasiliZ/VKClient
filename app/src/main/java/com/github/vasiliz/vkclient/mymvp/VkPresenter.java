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

}
