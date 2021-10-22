package com.snowplowanalytics.snowplow.salesdemo;

import static com.snowplowanalytics.snowplow.internal.utils.Util.addToMap;

import android.content.Context;

import com.snowplowanalytics.snowplow.Snowplow;
import com.snowplowanalytics.snowplow.configuration.EmitterConfiguration;
import com.snowplowanalytics.snowplow.configuration.GdprConfiguration;
import com.snowplowanalytics.snowplow.configuration.GlobalContextsConfiguration;
import com.snowplowanalytics.snowplow.configuration.NetworkConfiguration;
import com.snowplowanalytics.snowplow.configuration.SessionConfiguration;
import com.snowplowanalytics.snowplow.configuration.TrackerConfiguration;
import com.snowplowanalytics.snowplow.globalcontexts.GlobalContext;
import com.snowplowanalytics.snowplow.internal.constants.Parameters;
import com.snowplowanalytics.snowplow.internal.constants.TrackerConstants;
import com.snowplowanalytics.snowplow.network.HttpMethod;
import com.snowplowanalytics.snowplow.network.RequestCallback;
import com.snowplowanalytics.snowplow.payload.SelfDescribingJson;
import com.snowplowanalytics.snowplow.util.Basis;
import com.snowplowanalytics.snowplow.util.TimeMeasure;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Context context = getApplicationContext();
        TrackerConfiguration trackerConfiguration = new TrackerConfiguration("com.snowplowanalytics.snowplow.salesdemo")
                .sessionContext(true)
                .platformContext(true)
                .applicationContext(true)
                .screenContext(true)
                .lifecycleAutotracking(true)
                .screenViewAutotracking(true)
                .exceptionAutotracking(true)
                .installAutotracking(true);
        EmitterConfiguration emitterConfiguration = new EmitterConfiguration()
                .requestCallback(getRequestCallback())
                .threadPoolSize(20)
                .emitRange(500)
                .byteLimitPost(52000);
        NetworkConfiguration networkConfiguration = new NetworkConfiguration("http://3.143.116.199", HttpMethod.GET);
        SessionConfiguration sessionConfiguration = new SessionConfiguration(
                new TimeMeasure(30, TimeUnit.SECONDS),
                new TimeMeasure(30, TimeUnit.SECONDS)
        );
        GdprConfiguration gdprConfiguration = new GdprConfiguration(
                Basis.CONSENT,
                "someId",
                "0.1.0",
                "this is a demo document description"
        );
        GlobalContextsConfiguration gcConfiguration = new GlobalContextsConfiguration(null);
        Map<String, Object> pairs = new HashMap<>();
        addToMap(Parameters.APP_VERSION, "0.3.0", pairs);
        addToMap(Parameters.APP_BUILD, "3", pairs);
        gcConfiguration.add("ruleSetExampleTag", new GlobalContext(Collections.singletonList(new SelfDescribingJson(TrackerConstants.SCHEMA_APPLICATION, pairs))));

        Snowplow.createTracker(context,
                "appTracker",
                networkConfiguration,
                trackerConfiguration,
                emitterConfiguration,
                sessionConfiguration,
                gdprConfiguration,
                gcConfiguration

        );
    }

    private RequestCallback getRequestCallback() {
        return new RequestCallback() {
            @Override
            public void onSuccess(int successCount) {
                System.out.println("Emitter Send Success:\n " +
                        "- Events sent: " + successCount + "\n");
                System.out.println(successCount);
            }
            @Override
            public void onFailure(int successCount, int failureCount) {
                System.out.println("Emitter Send Failure:\n " +
                        "- Events sent: " + successCount + "\n " +
                        "- Events failed: " + failureCount + "\n");
                System.out.println(successCount);
            }
        };
    }
}
