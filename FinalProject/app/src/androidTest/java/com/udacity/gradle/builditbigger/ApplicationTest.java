package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.concurrent.CountDownLatch;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@SuppressWarnings("deprecation")
public class ApplicationTest extends ApplicationTestCase<Application> {
    private CountDownLatch mCountDownLatch = null;

    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        mCountDownLatch = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        mCountDownLatch.countDown();
    }

    public void testJokeGetTask() throws InterruptedException {
        final String[] testResult = new String[1];

        GetJokeEndpointsAsyncTask getJokeEndpointsAsyncTask = new GetJokeEndpointsAsyncTask(new GetJokeEndpointsAsyncTask.OnTaskFinishedListener() {
            @Override
            public void handle(String result) {
                testResult[0] = result;
                mCountDownLatch.countDown();
            }
        });
        getJokeEndpointsAsyncTask.execute(getContext());
        mCountDownLatch.await();

        assertTrue(!testResult[0].isEmpty());
    }
}