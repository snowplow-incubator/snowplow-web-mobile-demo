package com.snowplowanalytics.snowplow.salesdemo;

import android.app.Activity;
import android.os.Bundle;

import com.snowplowanalytics.snowplow.Snowplow;
import com.snowplowanalytics.snowplow.controller.TrackerController;
import com.snowplowanalytics.snowplow.event.SelfDescribing;
import com.snowplowanalytics.snowplow.payload.SelfDescribingJson;

import java.util.Map;
import java.util.TreeMap;

public class UnlockedActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlocked);

        TrackerController tracker = Snowplow.getDefaultTracker();

        Map<String, String> properties = new TreeMap<>();
        properties.put("greeting", "Hello from Android!");
        properties.put("message", "Snowplow data is always in the same format, you won't need different data models for web and mobile");
        SelfDescribingJson sdj = new SelfDescribingJson("iglu:com.snowplowanalytics.snowplow/web_mobile_demo/jsonschema/1-0-0", properties);

        SelfDescribing event = new SelfDescribing(sdj);

        tracker.track(event);
    }
}