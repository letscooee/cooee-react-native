#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(CooeeReactNative, NSObject)

RCT_EXTERN_METHOD(multiply:(float)a withB:(float)b
                 withResolver:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getUUID:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(sendEvent:(NSString)eventName
                 withData:(NSDictionary)data)

RCT_EXTERN_METHOD(sendUserProperty:(NSDictionary)data)

RCT_EXTERN_METHOD(updateUserData:(NSDictionary)data)

RCT_EXTERN_METHOD(updateScreenName:(NSString)data)

@end
