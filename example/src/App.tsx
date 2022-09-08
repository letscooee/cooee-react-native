import * as React from 'react';

import {StyleSheet, View, Text, ImageBackground, TouchableOpacity} from 'react-native';
import CooeeReactNative from '@letscooee/react-native';
import {NativeEventEmitter} from 'react-native';


export default function App() {
    const [result, setResult] = React.useState<number | undefined>();
    //const banner = {uri: "banner.png"}
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
        eventEmitter.addListener('onCooeeCTAListener', (event) => {
            console.log("CTA CTA", event)
        });

        CooeeReactNative.assLis

        CooeeReactNative.updateUserProfile({
            "name": "Ashish React",
            "email": "Ashish@react.com",
            "mobile": "1234567890",
            "isLogin": true
        }).then((resp: any) => console.log("Success: ", resp))
            .catch((err: any) => console.error("Error:", err))


        CooeeReactNative.sendEvent("Add To Cart", {})
            .then((resp: any) => console.log("Success: ", resp))
            .catch((err: any) => console.error("Error:", err))


    }, []);

    /*const onSubmit = async () => {

        try {
            const resp = await CooeeReactNative.sendEvent("Add To Cart", {"CE abcd": true})
            Alert.alert("done", resp.toString())
        } catch (e) {
            Alert.alert("error", e.toString())
        }
    };*/

    return (
        <View style={styles.container}>
            <ImageBackground source={require('./banner.png')} resizeMode="stretch" style={styles.image}>
                <Text onPress={onPress}>User ID: {result}</Text>
                <TouchableOpacity onPress={showInfo} style={styles.margin_common}>
                    <Text style={styles.text_common}>Debug Info</Text>
                </TouchableOpacity>
                <TouchableOpacity onPress={showInfo} style={styles.margin_common}>
                    <Text style={styles.text_common}>Copy UserID</Text>
                </TouchableOpacity>
            </ImageBackground>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'stretch',
        justifyContent: 'center',

    },
    image: {
        flex: 1,
        justifyContent: "center",
        alignItems: "center"
    },
    margin_common: {
        marginTop: 10,
        backgroundColor: "#fff",
        width: 150,
        height: 30,
        shadowRadius: 5,
        borderRadius: 10,
        alignItems: "center",
        justifyContent: 'center',
    },
    text_common: {
        fontSize: 15,
        fontWeight: "bold",
    },
    box: {
        width: 60,
        height: 60,
        marginVertical: 20,
    },
});
