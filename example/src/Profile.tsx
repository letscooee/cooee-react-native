import * as React from 'react';

import {ImageBackground, ScrollView, StyleSheet, Text, TextInput, TouchableOpacity, View} from 'react-native';
import CooeeReactNative from '@letscooee/react-native';
import {useNavigation} from "@react-navigation/native";


export default function Profile() {
    React.useEffect(() => {
        CooeeReactNative.updateScreenName("ProfileScreen");
        CooeeReactNative.sendEvent("Add To Cart", {})
            .then((resp: any) => console.log("Success: ", resp))
            .catch((err: any) => console.error("Error:", err))
    })

    let name = "";
    let email = "";
    let mobile = "";
    const saveProfile = () => {
        CooeeReactNative.updateUserProfile({
            "name": name,
            "email": email,
            "mobile": mobile,
            "isLogin": true
        }).then((resp: any) => console.log("Success: ", resp))
            .catch((err: any) => console.error("Error:", err))
    }

    const backAction = () => {
        navigation.goBack();
    }
    const navigation = useNavigation()
    return (
        <View style={styles.container}>
            <ImageBackground source={require('./banner.png')} resizeMode="stretch" style={styles.image}>
                <ScrollView>
                    <View style={styles.image}>
                        <TextInput placeholder={'Name'} onChangeText={(text) => name = text}
                                   style={styles.text_border}></TextInput>
                        <TextInput placeholder={'Email Address'} onChangeText={(text) => email = text}
                                   keyboardType={"email-address"}
                                   style={styles.text_border}></TextInput>
                        <TextInput placeholder={'Mobile Number'} onChangeText={(text) => mobile = text}
                                   keyboardType={"numeric"}
                                   style={styles.text_border}></TextInput>
                        <TouchableOpacity onPress={saveProfile} style={styles.margin_common}>
                            <Text style={styles.text_common}>Save</Text>
                        </TouchableOpacity>
                        <TouchableOpacity onPress={backAction} style={styles.margin_common}>
                            <Text style={styles.text_common}>Exit</Text>
                        </TouchableOpacity>
                    </View>
                </ScrollView>
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
    text_border: {
        width: 250,
        borderRadius: 5,
        borderColor: "#000",
        borderWidth: 1,
        margin: 5
    }
});
