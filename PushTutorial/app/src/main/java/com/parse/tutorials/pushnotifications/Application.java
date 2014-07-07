package com.parse.tutorials.pushnotifications;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class Application extends android.app.Application {

  public Application() {
  }

  @Override
  public void onCreate() {
    super.onCreate();
      //Parse.initialize(this, "Z60CRrXGDeJkHSgsSHnQ1HGWWKvgVPYCh5tGpIuW", "Q0ULoJsSkxdME4RCGNY07Piw7dEd0aD6dT8rLt9A");
      Parse.initialize(this, "Z60CRrXGDeJkHSgsSHnQ1HGWWKvgVPYCh5tGpIuW", "Q0ULoJsSkxdME4RCGNY07Piw7dEd0aD6dT8rLt9A");
      PushService.setDefaultPushCallback(this, MainActivity.class);
  }
}