import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import CooeeReactNative from 'cooee-react-native';

export default function App() {
  const [result, setResult] = React.useState<number | undefined>();

  const onPress = () => {

    CooeeReactNative.updateScreenName("HomrActivity");
    // CooeeReactNative.getUUID().then(setResult);
    CooeeReactNative.updateUserData({ "name": "Ashish React Test App", "email": "Ashish@react.test", "mobile": "1234567890" });
    CooeeReactNative.sendEvent("Add To Cart", {});

  };

  React.useEffect(() => {
    CooeeReactNative.updateScreenName("HomrActivity");
    CooeeReactNative.getUUID().then(setResult);
    CooeeReactNative.updateUserData({ "name": "Ashish React", "email": "Ashish@react.com", "mobile": "1234567890" });
    CooeeReactNative.sendEvent("Add To Cart", {});

  }, []);

  return (
    <View style={styles.container}>
      <Text onPress={onPress}>Result: {result}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
