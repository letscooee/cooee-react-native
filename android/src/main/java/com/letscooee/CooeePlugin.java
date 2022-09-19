package com.letscooee;

import android.app.Application;
import com.letscooee.enums.WrapperType;
import com.letscooee.init.CooeeBootstrap;
import com.letscooee.user.NewSessionExecutor;
import com.letscooee.utils.Constants;

/**
 * Will handle application state.
 *
 * @author Ashish Gaikwad
 */
public class CooeePlugin extends Application {

    public CooeePlugin() {
    }

    public void onCreate() {
        super.onCreate();
        NewSessionExecutor.updateWrapperInformation(WrapperType.REACT_NATIVE, Constants.VERSION_CODE, Constants.VERSION_NAME);
        new CooeeBootstrap(this).init();
    }
}
