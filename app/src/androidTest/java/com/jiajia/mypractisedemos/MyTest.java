
package com.jiajia.mypractisedemos;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.filters.MediumTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class MyTest {

    @Test
    public void test1() {
        Context context = InstrumentationRegistry.getTargetContext();
        assertEquals("com.jiajia.mypractisedemos",context.getPackageName());
        Log.i("tag", "$$$$$$$$$$$$");
        assertEquals("result:", 123, 100 + 23);
    }

    @Test
    public void test2() {

        boolean result = "18210741899".matches("\\d{11}");
        Log.i("tag", "#####:" + result);
        assertEquals("result:", result,true);
    }

}
