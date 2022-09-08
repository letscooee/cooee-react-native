//
//  AppDelegate+CooeePlugin.h
//  Pods
//
//  Created by Ashish Gaikwad on 06/09/22.
//

#import <UIKit/UIKit.h>
#import <UserNotifications/UserNotifications.h>

/**
 * CooeePlugin class provides override method from CooeeSDK to make everything plugin specific.
 *
 * @author Ashish Gaikwad on 7/9/22
 * @since 1.4.0
 */
@interface CooeePlugin : NSObject
+ (void)appLaunched;

+ (void)setDeviceTokenWithToken:deviceToken;

+ (UNNotificationPresentationOptions)presentNotification:(UNNotification *_Nonnull)notification;

+ (void)notificationAction:(UNNotificationResponse *_Nonnull)response;
@end

