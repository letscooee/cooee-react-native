import CooeeSDK
import React

@objc(CooeeReactNative)
public class CooeeReactNative: RCTEventEmitter {
    // MARK: Lifecycle

    override init() {
        super.init()
        cooeeSDK.setOnCTADelegate(self)
    }

    // MARK: Public

    @objc override public static func requiresMainQueueSetup() -> Bool {
        return true
    }

    @objc
    public static func appLaunched() {
        AppController.configure()
    }

    @objc
    public func onCTAResponse(payload: [String: Any]) {
        sendEvent(withName: "onCooeeCTAListener", body: payload)
    }

    override public func startObserving() {}

    override public func stopObserving() {}

    override public func supportedEvents() -> [String]! {
        ["onCooeeCTAListener"]
    }

    // MARK: Internal

    var cooeeSDK = CooeeSDK.getInstance()

    @objc(getUserID:withRejecter:)
    func getUserID(resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        let UDID = cooeeSDK.getUserID() ?? ""
        resolve(UDID)
    }

    @objc(sendEvent:withData:withResolver:withRejecter:)
    func sendEvent(eventName: String, eventProperties: [String: Any]?, resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        do {
            if eventProperties == nil || eventProperties!.isEmpty {
                try cooeeSDK.sendEvent(eventName: eventName)
                resolve("Event sent successfully")
            } else {
                try cooeeSDK.sendEvent(eventName: eventName, eventProperties: eventProperties)
            }
        } catch {
            reject("Event", "Fail to send event", error)
        }
    }

    @objc(updateUserProfile:withResolver:withRejecter:)
    func updateUserProfile(userProfile: [String: Any], resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        do {
            try cooeeSDK.updateUserProfile(userProfile)
            resolve("User profile updated successfully")
        } catch {
            reject("UserProfile", "Fail to update Profile", error)
        }
    }

    @objc(updateScreenName:)
    func updateScreenName(screenName: String) {
        cooeeSDK.setCurrentScreen(screenName: screenName)
    }

    @objc(showDebugInfo:withRejecter:)
    func showDebugInfo(resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) {
        reject("DebugInfo", "iOS do not have support yet", NSError(domain: "iOS do not have support yet", code: 0, userInfo: nil))
    }
}

extension CooeeReactNative: CooeeCTADelegate {}
