# snowplow-web-mobile-demo

Web and mobile tracker demo with Snowplow Mini

## Usage

There are three parts to this demo:

1. Web app
2. Android app
3. Kibana dashboard

The Web and Android apps send events to a Snowplow Mini hosted on AWS that also provides the Kibana dashboard.
Both apps are very simple and almost identical.

### Web App

The Web app can be [accessed here](https://snowplow-incubator.github.io/snowplow-web-mobile-demo).

All page events are automatically tracked.

### Android App

The Android app can be run in a simulator, on Browserstack or on an Android device.
The apk package to upload in Browserstack can be [downloaded from here](https://github.com/snowplow-incubator/snowplow-web-mobile-demo/releases/download/v0.0.1/snowplow-sales-demo.apk).

Page events are automatically tracked.

### Kibana Dashboard

The Kibana dashboard can be [accessed here](http://3.143.116.199/kibana/app/kibana#/dashboard/d92791c0-331d-11ec-ad0f-fb42d02128c7).
It shows statistics about the mobile and web events and a table with the events.
