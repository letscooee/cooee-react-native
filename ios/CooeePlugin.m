//
//  ReactPlugin.m
//  cooee-react-native
//
//  Created by Ashish Gaikwad on 06/09/22.
//

#import "CooeePlugin.h"
#import "Constants.h"
#import "CooeeSDK/CooeeSDK-Swift.h"

@implementation CooeePlugin

+ (void)appLaunched
{
    [NewSessionExecutor updateWrapperInformationWithWrapperType: WrapperTypeREACT_NATIVE versionNumber:VERSION_NAME versionCode:VERSION_CODE];
    [AppController configure];
}

+(void)setDeviceTokenWithToken:deviceToken{
    [[CooeeSDK getInstance] setDeviceTokenWithToken:deviceToken];
}

+(UNNotificationPresentationOptions)presentNotification:(UNNotification *)notification
{
    // Cooee can show only In-App while app is in foreground. Here Cooee will check for there notification and
    // decide what action should be taken on foreground notification. If Push notification do not belong to
    // Cooee then default [.badge,.alert,.sound] is return
    return [[CooeeSDK getInstance] presentNotification:notification];
}

+(void)notificationAction:(UNNotificationResponse * _Nonnull)response{
    [[CooeeSDK getInstance] notificationAction:response];
}
@end
