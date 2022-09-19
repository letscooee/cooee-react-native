# Change Log

## 1.4.0 (2022-09-19)

### Breaking

1. Removed `CooeeNotificationService.updateContent` method and
   exposed `CooeeNotificationService.updateContentFromRequest`.
    1. To Related changes open `NotificationService.m` from `ios/CooeeNotificationService` folder
    2. Add Update below code.
```diff
// Old code
- bestAttemptContent = [CooeeNotificationService updateContent: [request.content mutableCopy], with: [[request content] userInfo]];

// New code
+ bestAttemptContent = [CooeeNotificationService updateContentFromRequest:request];
```

### Improvement

1. Using Android SDK v1.4.0.
2. Using iOS SDK v1.4.1.

### Fixes
1. InApp rendering with custom event.

## 1.3.4 (2022-08-04)

### Improvement

1. Using Android SDK v1.3.13.
2. Using iOS SDK v1.3.16.

## 1.3.3 (2022-07-25)

### Improvement

1. Add script to support Static libraries in iOS.

### Fixes

1. Add missing import in `CooeePlugin.swift`

## 1.3.2 (2022-07-15)

### Improvement

1. Using Android SDK v1.3.12.
2. Add support to work with other firebase messaging plugin.

## 1.3.1 (2022-07-12)

### Improvement

1. Using Android SDK v1.3.11.
2. Using iOS SDK v1.3.15.

## 1.3.0 (2022-07-12)

### Improvement

1. Using Android SDK v1.3.10.
2. Using iOS SDK v1.3.14.
3. Update android gradle to latest.
4. Add Call to action.

## 0.0.2

1. App Lifecycle management changed.
2. Using 0.2.7 Cooee android sdk.

## 0.0.1

1. New Release.

