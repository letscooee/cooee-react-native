import * as React from 'react';

import {StyleSheet, View, Text, Alert, Button} from 'react-native';
import CooeeReactNative from 'cooee-react-native';
import {NativeEventEmitter, NativeModules} from 'react-native';

export default function App() {
    const [result, setResult] = React.useState<number | undefined>();

    const onPress = () => {
        console.log("UserId Pressed")
    };

    const showInfo = () => {
        CooeeReactNative.showDebugInfo();
    };


    React.useEffect(() => {
        CooeeReactNative.updateScreenName("HomeActivity");
        CooeeReactNative.getUserID().then(setResult);
        //onSubmit()
        const eventEmitter = new NativeEventEmitter(CooeeReactNative);
        const eventListener = eventEmitter.addListener('onCooeeCTAListener', (event) => {
            console.log("CTA CTA", event)
        });

        CooeeReactNative.assLis

        CooeeReactNative.updateUserProfile({
            "name": "Ashish React",
            "email": "Ashish@react.com",
            "mobile": "1234567890",
            "isLogin": true
        }).then(resp => console.log("Success: ", resp.toString())).catch(err => console.error("Error:", err.toString()))


        CooeeReactNative.sendEvent("Add To Cart", {}).then(resp => console.log("Success: ", resp.toString())).catch(err => console.error("Error:", err.toString()))


    }, []);

    const onSubmit = async () => {

        try {
            const resp = await CooeeReactNative.sendEvent("Add To Cart", {"CE abcd": true})
            Alert.alert("done", resp.toString())
        } catch (e) {
            Alert.alert("error", e.toString())
        }
    };

    return (
        <View style={styles.container}>
            <Text onPress={onPress}>Result: {result}</Text>
            <Button title={'Debug Info'} onPress={showInfo}/>
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
