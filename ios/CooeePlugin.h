//
//  AppDelegate+CooeePlugin.h
//  Pods
//
//  Created by Ashish Gaikwad on 06/09/22.
//

#import <UIKit/UIKit.h>
#import <UserNotifications/UserNotifications.h>

@interface CooeePlugin : NSObject
+(void)appLaunched;
+(void) setDeviceTokenWithToken:deviceToken;
+(UNNotificationPresentationOptions)presentNotification:(UNNotification * _Nonnull)notification;
+(void)notificationAction:(UNNotificationResponse * _Nonnull)response;
@end

