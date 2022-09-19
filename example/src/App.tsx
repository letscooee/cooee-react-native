import * as React from 'react';

import {ImageBackground, NativeEventEmitter, StyleSheet, Text, TouchableOpacity} from 'react-native';
import CooeeReactNative from '@letscooee/react-native';
import {createNativeStackNavigator} from "@react-navigation/native-stack";
import {NavigationContainer} from "@react-navigation/native";
import Profile from "./Profile";

const Stack = createNativeStackNavigator();

const HomeScreen = ({navigation}) => {
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
        eventEmitter.addListener('onCooeeCTAListener', (event) => {
            console.log("CTA CTA", event)
        });

        CooeeReactNative.sendEvent("Add To Cart", {})
            .then((resp: any) => console.log("Success: ", resp))
            .catch((err: any) => console.error("Error:", err))
        CooeeReactNative.sendEvent("Add To Wishlist", {
            item: {
                name: "hi"
            }
        })
            .then((resp: any) => console.log("Success: ", resp))
            .catch((err: any) => console.error("Error:", err))


    }, []);

    const profile = () => {
        navigation.navigate('Profile', {})
    }
    return (
        <ImageBackground source={require('./banner.png')} resizeMode="stretch" style={styles.image}>
            <Text onPress={onPress}>User ID: {result}</Text>
            <TouchableOpacity onPress={showInfo} style={styles.margin_common}>
                <Text style={styles.text_common}>Debug Info</Text>
            </TouchableOpacity>
            <TouchableOpacity onPress={profile} style={styles.margin_common}>
                <Text style={styles.text_common}>Profile</Text>
            </TouchableOpacity>
        </ImageBackground>
    )
}

export default function App() {

    return (
        <NavigationContainer>
            <Stack.Navigator>
                <Stack.Screen
                    name="Home"
                    component={HomeScreen}
                />
                <Stack.Screen name="Profile" component={Profile}/>
            </Stack.Navigator>
        </NavigationContainer>
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
