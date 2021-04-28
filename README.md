# cooee-react-native

Cooee's React Native Plugin for hyper-personalised Mobile App Re-Engagement via Machine Learning

## Installation

```sh
npm install cooee-react-native
```

## Environment Setup
### Android
Add following updates to you `Manifest.xml`

```xml
<!-- add following line to application tag -->
<application 
tools:replace="android:name"
...
/>

<!-- APP_ID and APP_SECRET should be replaced with you credentials -->
<meta-data
    android:name="COOEE_APP_ID"
    android:value="<APP_ID>" />

<meta-data
    android:name="COOEE_APP_SECRET"
    android:value="<APP_SECRET>" />
```
Open your `MainApplication` class present at `android/app/src/main/java/<yourpackage_name>/` and extend it with `Controller` class

```java
import com.letscooee.CooeePlugin;
...

public class MainApplication extends CooeePlugin implements ReactApplication {
...
}
```

### iOS
Add the following lines to your `info.plist` (Note: APP_ID, APP_SECRET will be replace by credentials)
```xml
<key>NSBluetoothPeripheralUsageDescription</key>
<string>App uses Bluetooth to find out nearby devices</string> 

<key>NSLocationWhenInUseUsageDescription</key>
<string>App uses location to search retailer location</string>

<key>CooeeAppID</key>
<string><APP_ID></string>
<key>CooeeSecretKey</key>
<string><APP_SECRET></string>
```

Also setup your Apps `deployment-target` to `13.0`

## Usage

And then in you `js` or `tsx` import `CooeeReactNative` Module and start accessing methods.
```js
import CooeeReactNative from "cooee-react-native";

// ...
// get User ID
const result = await CooeeReactNative.getUUID();

// Update Screen Name
CooeeReactNative.updateScreenName("HomeActivity");

//Update User Data
CooeeReactNative.updateUserData({ "name": "USER_NAME", "email": "USER_EMAIL", "mobile": "USER_MOBILE_NO" });

//Submit events
CooeeReactNative.sendEvent("EVENT_NAME", {"foo":"bar"});
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
