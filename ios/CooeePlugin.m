//
//  ReactPlugin.m
//  cooee-react-native
//
//  Created by Ashish Gaikwad on 06/09/22.
//

#import "CooeePlugin.h"
#import "Constants.h"
#import "CooeeSDK/CooeeSDK-Swift.h"

static BOOL isAfterForeground = false;

@implementation CooeePlugin

/**
 * Initialize Native SDK to perform mandatory operations required to run.
 */
+ (void)appLaunched {
    [NewSessionExecutor updateWrapperInformationWithWrapperType:WrapperTypeREACT_NATIVE versionNumber:VERSION_NAME versionCode:VERSION_CODE];
    [[NSNotificationCenter defaultCenter] addObserver:CooeePlugin.self selector:@selector(appCameForeground) name:UIApplicationWillEnterForegroundNotification object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:CooeePlugin.self selector:@selector(appBecomeActive) name:UIApplicationDidBecomeActiveNotification object:nil];
    [AppController configure];
}

/**
 * Sends device to server
 * @param deviceToken deviceToken return by application(application:didRegisterForRemoteNotificationsWithDeviceToken:) method.
 */
+ (void)setDeviceToken:deviceToken {
    [[CooeeSDK getInstance] setDeviceTokenWithToken:deviceToken];
}

/**
 *Checks for Cooee Notification for foreground status otherwise returns default UNNotificationPresentationOptions
 * @param notification UNNotification provided by userNotificationCenter(_:willPresent:withCompletionHandler:) method.
 * @return UNNotificationPresentationOptions
 */
+ (UNNotificationPresentationOptions)presentNotification:(UNNotification *)notification {
    // Cooee can show only In-App while app is in foreground. Here Cooee will check for there notification and
    // decide what action should be taken on foreground notification. If Push notification do not belong to
    // Cooee then default [.badge,.alert,.sound] is return
    return [[CooeeSDK getInstance] presentNotification:notification];
}

/**
 *Accepts data received from user ``userNotificationCenter(:didReceive:withCompletionHandler:)`` and perform related
 * actions by Cooee
 * @param response UNNotificationResponse provided by userNotificationCenter(:didReceive:withCompletionHandler:) method.
 */
+ (void)notificationAction:(UNNotificationResponse * _Nonnull)response {
    [[CooeeSDK getInstance] notificationAction:response];
}

/**
 * Called when app came foreground.
 */
+ (void)appCameForeground {
    isAfterForeground = true;
}

/**
 * Called when app became active.
 * This method checks for flag set by appCameForeground to perform organic launch.
 */
+ (void)appBecomeActive {
    if (isAfterForeground) {
        return;
    }

    [[NSNotificationCenter defaultCenter] postNotificationName:UIApplicationWillEnterForegroundNotification object:nil];
}
@end
