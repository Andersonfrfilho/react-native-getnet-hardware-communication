import { NativeModules } from "react-native";

const {
  GetnetHardwareCommunication: {
    startingServices,
    checkConnections,
    devInformation,
    cardStartConnectAntenna,
    cardStopConnectAntenna,
    printMethod,
    ledMethod,
    beeperMethod,
    cameraMethod
  }
} = NativeModules;

export {
  startingServices,
  checkConnections,
  devInformation,
  cardStartConnectAntenna,
  cardStopConnectAntenna,
  printMethod,
  ledMethod,
  beeperMethod,
  cameraMethod
};
