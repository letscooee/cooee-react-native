# cooee-react-native

Cooee's React Native Plugin for hyper-personalised Mobile App Re-Engagement via Machine Learning

## Installation

```sh
npm install cooee-react-native
```

## Environment Setup
### Android
Add following lines to you `Manifest.xml`

```xml
<!-- APP_ID and APP_SECRET shoud be replaced with you credencials-->
<meta-data
    android:name="COOEE_APP_ID"
    android:value="<APP_ID>" />

<meta-data
    android:name="COOEE_APP_SECRET"
    android:value="<APP_SECRET>" />
```

## Usage

```js
import CooeeReactNative from "cooee-react-native";

// ...
// get User ID
const result = await CooeeReactNative.getUUID();

// Update Screen Name
CooeeReactNative.updateScreenName("HomrActivity");

//Update User Data
CooeeReactNative.updateUserData({ "name": "USER_NAME", "email": "USER_EMAIL", "mobile": "USER_MOBILE_NO" });

//Submit events
CooeeReactNative.sendEvent("EVENT_NAME", {"foo":"bar"});
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
