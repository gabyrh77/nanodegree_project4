package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.test.AndroidTestCase;

import java.util.concurrent.CountDownLatch;

/**
 * Created by gaby_ on 9/1/2016.
 */
public class EndpointTaskTest extends AndroidTestCase {
    CountDownLatch signal;
    String mResult;
    String mError;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        signal = new CountDownLatch(1);
    }

    public void testEndpointTask() throws Throwable {
        EndpointTask task = new EndpointTask(true, new EndpointTask.EndpointTaskListener() {
            @Override
            public void onComplete(String result, String error) {
                mResult = result;
                mError = error;
                signal.countDown();
            }
        });
        task.execute(getContext());
        signal.await();
        assertEquals(null, mError);
        assertNotNull(mResult);
        assertFalse(mResult.isEmpty());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        signal.countDown();
    }
}
