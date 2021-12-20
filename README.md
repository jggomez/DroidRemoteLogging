## Remote Logging Library Documentation

[![Kotlin](https://img.shields.io/badge/Kotlin-1.6.0-blueviolet.svg)](https://kotlinlang.org)
![Minimum SDK Version](https://img.shields.io/badge/minSdkVersion-23-brightgreen.svg)
![GitHub release](https://img.shields.io/github/v/release/jggomez/DroidRemoteLogging)

We need to log our app because we can debug errors or behaviors abnormal, thus improving our
application. This library is a tree of Timber. We can register this tree so that the logs are
recorded in Firestore. Thus, you should add the firebase credentials in your project.

## Installation

You can [download](https://github.com/jggomez/DroidRemoteLogging) and install `Droid Remote Logging`
with `Maven` and `Gradle`:

```gradle
dependencies {
    implementation("co.devhack:remotelogging:{current_version}")
}
```

Make sure to include `MavenCentral` in your repositories

```gradle
repositories {
  mavenCentral()
}
```

## Quick Start

How to use this library:

First, you need to add the "enable" variable as metadata in the manifest file (for default the value
is false) :

```xml

<meta-data android:name="co.devhack.remotelogging.enable" android:value="true" />
```

And the types of logs. You can put various types of logs for default is INFO

```xml

<meta-data android:name="co.devhack.remotelogging.logtypes"
    android:value="DEBUG, INFO, DEBUG, VERBOSE, ERROR" />
```

Afterward...

Add the tree to Timber in the application file

```kotlin
val deviceDetails = DeviceDetails(
    Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID),
    Build.VERSION.RELEASE,
    Build.MANUFACTURER,
    Build.BRAND,
    Build.DEVICE,
    Build.MODEL,
    VERSION_NAME,
    VERSION_CODE
)
Timber.plant(TimberRemoteLogging(applicationContext, deviceDetails))
```

Use Timber normally.

```kotlin
Timber.i("log info")
Timber.e(Exception("error"))
Timber.log(Log.VERBOSE, "log verbose")
```

You can see the data in firestore like this:

<img width="809" alt="Screen Shot 2021-12-20 at 12 29 06" src="https://user-images.githubusercontent.com/661231/146807919-754e6521-e630-4924-be98-a27edf3eb46e.png">



Made with ❤ by  [jggomez](https://devhack.co).

[![Twitter Badge](https://img.shields.io/badge/-@jggomezt-1ca0f1?style=flat-square&labelColor=1ca0f1&logo=twitter&logoColor=white&link=https://twitter.com/jggomezt)](https://twitter.com/jggomezt)
[![Linkedin Badge](https://img.shields.io/badge/-jggomezt-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/jggomezt/)](https://www.linkedin.com/in/jggomezt/)
[![Medium Badge](https://img.shields.io/badge/-@jggomezt-03a57a?style=flat-square&labelColor=000000&logo=Medium&link=https://medium.com/@jggomezt)](https://medium.com/@jggomezt)

## License

    Copyright 2021 Juan Guillermo Gómez

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
