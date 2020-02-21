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
    cameraBackMethod
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
  cameraBackMethod
};
