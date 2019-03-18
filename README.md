


[![Release](https://jitpack.io/v/panos-stavrianos/LFRFID.svg)](https://jitpack.io/#panos-stavrianos/LFRFID)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![paypal](http://orbitsystems.gr/images/Donation-Buy%20me%20beer-blue.svg)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=J73XPNMNBGX6C)
# LFRFID
This small library allow you to read Low-frequency 125khz RFID.
Written in Kotlin

# Setup
1. Add the JitPack repository to your build.gradle file
```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

2. Add the dependency
```gradle
dependencies {
        implementation 'com.github.panos-stavrianos:LFRFID:1.0.2'
}
```

# Usage
###### All examples are in kotlin
Extend your class with the listener interface Reader.RFIDListener. Then implement the onNewRFID method to get the results   
```kotlin
override fun onNewRFID(rfid: String) {  }
 ```
Then register your class to the listener
```kotlin
val rfidReader = Reader()
rfidReader.read(this)
```
Don't forget to close the reader after you done. You can override onDestroy just to be sure that the reader will definetly close when the app is terminated
```kotlin
rfidReader.close()
```
# Note
All tests have done with Alps KT45Q. It is posible that it won't work with other devices. Feel free to contribute, especialy if you have some other device in your possession.


# License
      Copyright (c) 2019 panos-stavrianos

      Licensed under the Apache License, Version 2.0 (the "License");
      you may not use this file except in compliance with the License.
      You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

      Unless required by applicable law or agreed to in writing, software
      distributed under the License is distributed on an "AS IS" BASIS,
      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
      See the License for the specific language governing permissions and
      limitations under the License.
