package com.silent.android.mxboxs.event;

import com.silent.android.mxboxs.bean.Config;
import com.silent.android.mxboxs.bean.Device;
import com.silent.android.mxboxs.bean.History;

import org.greenrobot.eventbus.EventBus;

public record CastEvent(Config config, Device device, History history) {

    public static void post(Config config, Device device, History history) {
        EventBus.getDefault().post(new CastEvent(config, device, history));
    }
}
