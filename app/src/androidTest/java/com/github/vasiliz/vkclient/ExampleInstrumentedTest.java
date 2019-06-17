package com.github.vasiliz.vkclient;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.github.vasiliz.vkclient.base.services.ExecutorDataServiceImpl;
import com.github.vasiliz.vkclient.news.NewsModel;
import com.github.vasiliz.vkclient.news.entity.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private NewsModel mNewsModel;
    private ExecutorDataServiceImpl mExecutorDataService;

    @Before
    public void initTest(){
        final VkApplication vkApplication = new VkApplication();
        vkApplication.onCreate();
        mExecutorDataService = ExecutorDataServiceImpl.getInstance();
       // mNewsModel = new NewsModel(mExecutorDataService);
    }

    @Test
    public void getNetworkData() {

        final Response response = mNewsModel.executeNetwork(false);
        assertNotNull(response);
        assertEquals(response.getResponseNews().getItemList().size(), 50);
    }

    @Test
    public void GetLocalData(){

        final Response response = mNewsModel.executeLocal();
        assertNotNull(response);
        assertEquals(response.getResponseNews().getItemList().size(), 49);
    }
}
