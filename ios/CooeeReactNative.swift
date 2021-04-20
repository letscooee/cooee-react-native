import CooeeSDK
@objc(CooeeReactNative)
class CooeeReactNative: NSObject {
var sdk=Cooee.shared
    @objc(multiply:withB:withResolver:withRejecter:)
    func multiply(a: Float, b: Float, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) -> Void {
        resolve(a*b)
    }
    @objc(getUUID:withRejecter:)
    func getUUID(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) -> Void {
        let UDID = Cooee.shared.fetchUDID() ?? ""
        resolve(UDID)
    }

    @objc(sendEvent:withData:)
    func sendEvent(eventName: String, data: Any) -> Void {
        if let eventParams = data as? [String: Any]{
            if  let eventProperties = eventParams as? [String: Any]{
                sdk.sendEvent(withName: eventName, properties: eventProperties)
            }
        }
    }

    @objc(sendUserProperty:)
    func sendUserProperty(data: Any) -> Void {
        if let userProperties = data as? [String: Any]{
            sdk.updateProfile(withProperties: userProperties, andData: nil)
        }
    }

    @objc(updateUserData:)
    func updateUserData(data: Any) -> Void {
        if let userData = data as? [String: Any]{
            sdk.updateProfile(withProperties: nil, andData: userData)
        }
    }

    @objc(updateScreenName:)
    func updateScreenName(screenName: String) -> Void {
        sdk.screenName = screenName
    }
    
}
