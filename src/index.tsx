import { NativeModules } from 'react-native';

// type CooeeReactNativeType = {
//   multiply(a: number, b: number): Promise<number>;
// };

const { CooeeReactNative } = NativeModules;

export default CooeeReactNative //as CooeeReactNativeType;
