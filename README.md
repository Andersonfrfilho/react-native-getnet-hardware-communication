# `react-native-getnet-hardware-communication`

[![npm](https://img.shields.io/npm/v/react-native-getnet-hardware-communication)](https://www.npmjs.com/package/react-native-getnet-hardware-communication)
![Supports Android](https://img.shields.io/badge/platforms-android-lightgrey)
![MIT License](https://img.shields.io/npm/l/react-native-getnet-hardware-communication)

[![npm total downloads](https://img.shields.io/npm/dt/react-native-getnet-hardware-communication.svg)](https://img.shields.io/npm/dt/react-native-getnet-hardware-communication)
[![npm monthly downloads](https://img.shields.io/npm/dm/react-native-getnet-hardware-communication.svg)](https://img.shields.io/npm/dm/react-native-getnet-hardware-communication)
[![npm weekly downloads](https://img.shields.io/npm/dw/react-native-getnet-hardware-communication.svg)](https://img.shields.io/npm/dw/react-native-getnet-hardware-communication)<br>

Device Information for [React Native](https://github.com/facebook/react-native).<br>

## Getting started

`$ npm install react-native-getnet-hardware-communication --save`<br>

or

`$ yarn add react-native-getnet-hardware-communication`

#### Only Android

<img src="./android.png" width="48" height="48"/>

#### Includes and modify archives

1. in android/build.gradle change minSdkVersion for 22

```javascript
		buildscript {
    ext {
        ...,
        minSdkVersion = 22
        ...
        ...
		},...
```

3. finish

#### 0.60 < React-native version

### Manual installation

`$ react-native link react-native-communication-hardware-getnet`

#### alter archive.

1. Open up `android/app/src/main/java/[...]/MainApplication.java`

- Add `import com.reactlibrary.GetnetHardwareCommunicationPackage;` to the imports at the top of the file
- Add `new GetnetHardwareCommunicationPackage()` to the list returned by the `getPackages()` method

2. Append the following lines to `android/settings.gradle`:
   ```
   include ':react-native-communication-hardware-getnet'
   project(':react-native-communication-hardware-getnet').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-communication-hardware-getnet/android')
   ```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
   ```
     compile project(':react-native-communication-hardware-getnet')
   ```

## Usage

```javascript
import React, { useState } from "react";
import { StyleSheet, Text, View, TouchableOpacity } from "react-native";
import {
  startingServices,
  checkConnections,
  devInformation,
  cardStartConnectAntenna,
  cardStopConnectAntenna,
  printMethod,
  ledMethod,
  beeperMethod,
  cameraMethod
} from "react-native-getnet-hardware-communication";
import configPrint from "./configPrint";
export default function App() {
  const [service, setService] = useState(false);
  const [connection, setConnection] = useState(false);
  const [info, setInfo] = useState("");
  async function verifyService() {
    try {
      //Method for initialized services
      let services = await startingServices();
      console.log(services.services);
      //Method for initialized connections
      let connection = await checkConnections();
      console.log(connection.connection);
      setService(services.services.toString());
      setConnection(connection.connection.toString());
    } catch (error) {
      console.log(error);
    }
  }
  async function deviceInfo() {
    let infos = await devInformation();
    console.log(infos);
    setInfo(
      `bc:${infos.BcVersion}\nOS:${infos.os}\nsdk:${infos.sdk}\nserial-number:${infos.serialNumber}`
    );
  }
  async function connectCard() {
    try {
      // Method for initialized service card
      // configs obj
      // configs = {
      //   string - typeCard:'magnetic'||'chip'||'nfc',//type of card in find
      //   int - timeout:30,//value for timout wait.
      // }
      const configs = {
        typeCard: "nfc",
        timeout: 30
      };
      let cardConnect = await cardStartConnectAntenna(configs);
      console.log(cardConnect);
    } catch (error) {
      console.log(error);
    }
  }
  async function stopConnectCard() {
    try {
      await cardStopConnectAntenna();
    } catch (error) {
      console.log(error);
    }
  }
  async function printer() {
    const data = await printMethod(configPrint);
  }
  async function ledOn() {
    const data = await ledMethod({
      //turn: true | false
      turn: true,
      //color blue | green | red | yellow | all
      color: "all"
    });
  }
  async function ledOff() {
    const data = await ledMethod({
      //turn: true | false
      turn: false,
      //color blue | green | red | yellow | all
      color: "all"
    });
  }
  async function beeper() {
    //beeperMode: error|digit|nfc|success
    //default success
    const data = await beeperMethod({
      beeperMode: "success"
    });
  }
  async function camera() {
    //timeout parameter for cont
    const data = await cameraMethod({ camera: "back", timeout: 30 });
    console.log(data);
  }

  return (
    <View style={styles.container}>
      <View>
        <Text>Service: {service ? "work" : "not work"}</Text>
      </View>
      <View>
        <Text>Connection: {connection ? "connect" : "not connect"}</Text>
      </View>
      {info ? (
        <View>
          <Text>Infos: {"information"}</Text>
        </View>
      ) : null}
      <TouchableOpacity style={styles.button} onPress={() => verifyService()}>
        <Text style={styles.textButton}>Service / Connection</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.button}
        disabled={!(service && connection)}
        onPress={() => deviceInfo()}
      >
        <Text style={styles.textButton}>Device Information</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.button}
        disabled={!(service && connection)}
        onPress={() => connectCard()}
      >
        <Text style={styles.textButton}>Card Connection Start</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.button}
        disabled={!(service && connection)}
        onPress={() => stopConnectCard()}
      >
        <Text style={styles.textButton}>Card Connection Stop</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.button}
        disabled={!(service && connection)}
        onPress={() => printer()}
      >
        <Text style={styles.textButton}>Printer</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.button}
        disabled={!(service && connection)}
        onPress={() => ledOn()}
      >
        <Text style={styles.textButton}>On Led</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.button}
        disabled={!(service && connection)}
        onPress={() => ledOff()}
      >
        <Text style={styles.textButton}>Off Led</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.button}
        disabled={!(service && connection)}
        onPress={() => beeper()}
      >
        <Text style={styles.textButton}>Bepper</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.button}
        disabled={!(service && connection)}
        onPress={() => camera()}
      >
        <Text style={styles.textButton}>Camera</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#F5FCFF"
  },
  welcome: {
    fontSize: 20,
    textAlign: "center",
    margin: 10
  },
  instructions: {
    textAlign: "center",
    color: "#333333",
    marginBottom: 5
  },
  button: {
    backgroundColor: "#451234",
    width: "70%",
    height: 40,
    marginBottom: 10,
    borderRadius: 10,
    justifyContent: "center",
    alignItems: "center"
  },
  textButton: {
    fontSize: 14,
    color: "#f7f7f7"
  }
});
```

# Methods

### Summary

- [`startingServices`](#startingServices)
- [`checkConnections`](#checkConnections)
- [`devInformation`](#devInformation)
- [`cardStartConnectAntenna`](#cardStartConnectAntenna)
- [`cardStartConnectAntenna`](#cardStartConnectAntenna)
- [`cardStopConnectAntenna`](#cardStopConnectAntenna)
- [`printMethod`](#printMethod)
- [`ledMethod`](#ledMethod)
- [`beeperMethod`](#beeperMethod)
- [`cameraMethod`](#cameraMethod)

---

### Details

#### `startingServices()`

```jsx
import { startingServices } from "react-native-getnet-hardware-communication";
async function startedServices() {
  const response = await startingServices();
  console.log(response);
}
```

##### `Info`

this method must be called before starting any service with APOS to start hardware services.

#### `response`

```json
{
  "service": "boolean"
}
```

---

#### `checkConnections()`

```jsx
import { checkConnections } from "react-native-getnet-hardware-communication";
async function checkedTheConnection() {
  const response = await checkConnections();
  console.log(response);
}
```

##### `Info`

this method must be called when you want to check the connection to the services.

#### `response`

```json
{
  "connection": "boolean"
}
```

---

#### `devInformation()`

```jsx
import { devInformation } from "react-native-getnet-hardware-communication";
async function getInformation() {
  const response = await devInformation();
  console.log(response);
}
```

##### `Info`

this method must be called to get information from the device.

#### `response`

```json
{
  "bCVersion": "string",
  "os": "string",
  "sdk": "string",
  "serialNumber": "string"
}
```

<br>or case error</br>

```json
{
  "error": "boolean",
  "message": "string"
}
```

---

#### `cardStartConnectAntenna()`

```jsx
import { cardStartConnectAntenna } from "react-native-getnet-hardware-communication";
async function connectAntenaOn() {
  const response = await cardStartConnectAntenna({
    typeCard: "magnetic",
    timeout: 30
  });
  console.log(response);
}
```

##### `Info`

this method should be called active the card antenna can be by magnetic cards, Chip's and NFC.

**Parameters:**

| Name     | Type    | Values                         | Required | Description                                                                     |
| -------- | ------- | ------------------------------ | -------- | ------------------------------------------------------------------------------- |
| typeCard | string  | `magnetic`, `chip`,`nfc`,`all` | Yes      | card type.                                                                      |
| timeout  | integer | 0 > <b>&#8734;</b>             | Yes      | number of seconds to wait the <br> antenna will be on waiting for the card</br> |

#### `response`

```json
{
  "typeCard": "string",
  "numberCard": "int",
  "dataExpired": "string",
  "pan": "string",
  "track1": "string",
  "track2": "string",
  "track3": "string",
  "type": "string"
}
```

<br>or case error or message</br>

```json
{
  "error": "boolean",
  "message": "string"
}
```

---

#### `cardStopConnectAntenna()`

```jsx
import { cardStopConnectAntenna } from "react-native-getnet-hardware-communication";
async function connectAntenaOff() {
  const response = await cardStopConnectAntenna();
  console.log(response);
}
```

##### `Info`

this method turns off the card search antenna

#### `response`

```json
{
  "stop": "boolean"
}
```

<br>or case error or message</br>

```json
{
  "error": "string"
}
```

---

#### `printMethod()`

```jsx
import { printMethod } from "react-native-getnet-hardware-communication";
async function print() {
  const response = await printMethod([
  {
    type: 'image',
    value:'base64Image with data:image/jpeg;base64, or not',
    align: 'left',
    weight: 3,
    fontSize: null,
  },
  {
    type: 'image',
    value:'base64Image with data:image/jpeg;base64, or not',
    align: 'right',
    weight: 3,
    fontSize: null,
  },
  {
    type: 'image',
    value:'base64Image with data:image/jpeg;base64, or not',
    align: 'center',
    weight: 3,
    fontSize: null,
  },
  {
    type: 'barcode',
    value: '12345678901234567890',
    align: 'left',
    weight: 3,
    fontSize: null,
  },
  {
    type: 'barcode',
    value: '123456789012345678901',
    align: 'right',
    weight: 3,
    fontSize: null,
  },
  {
    type: 'barcode',
    value: '1234567890123456789012',
    align: 'center',
    weight: 3,
    fontSize: null,
  },
  {
    type: 'barcode',
    value: '12345678901234567890123',
    align: 'left',
    weight: 3,
    fontSize: null,
  },
  {
    type: 'barcode',
    value: '123456789012345678901234',
    align: 'right',
    weight: 3,
    fontSize: null,
  },
  {
    type: 'qrcode',
    value: 'www.google.com',
    align: 'left',
    weight: 3,
    fontSize: null,
  },
  {
    type: 'qrcode',
    value: 'www.google.com',
    align: 'right',
    weight: 3,
    fontSize: null,
  },

  {
    type: 'qrcode',
    value: 'www.google.com',
    align: 'left',
    weight: 3,
    fontSize: null,
  },
  {
    type: 'text',
    value: 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789',
    align: 'left',
    weight: 1,
    fontSize: 'small',
  },
  {
    type: 'text',
    value: 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789',
    align: 'right',
    weight: 1,
    fontSize: 'medium',
  },
  {
    type: 'text',
    value: 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789',
    align: 'center',
    weight: 1,
    fontSize: 'large',
  },
];);
  console.log(response);
}
```

##### `Info`

this method print whatever is in an array with the right parameters.

**Parameters:**
<br>**_type: Array of Objects_**</br>
| Name | Type | Values | Required | default | Description |
| -------- | ------- | ------------------------------ | -------- | ---- |------------------------------------------------------------------------------- |
| type | string | `image`, `barcode`,`qrcode`,`text` | Yes | `text` | type of element to print. |
| value | string | `Image in base64`,<br>`string`,`string`,`string` | Yes | not value default | values ​​that will be printed |
| align | string | `center`,`left`,`right` | Yes | `center` | print alignment |
| weight | integer | 1 <= 5 | Yes | not value default |weight of ink used for printing |
| fontSize | string | `small`,`medium`,`large` | Yes | `medium` |font size used: <br>chacarectes limits: small (48), medium (32) and large (32) |

#### `response`

```json
{
  "printer": "bollean"
}
```

<br>or case error or message</br>

```json
{
  "printer": "boolean",
  "message": "string"
}
```

---

#### `ledMethod()`

```jsx
import { ledMethod } from "react-native-getnet-hardware-communication";
async function print() {
  const response = await ledMethod({
    turn: true,
    color: "blue"
  });
  console.log(response);
}
```

##### `Info`

this method turn the leds on or off.

**Parameters:**
<br>**type:Object**</br>
| Name | Type | Values | Required | default | Description |
| -------- | ------- | ------------------------------ | -------- | ---- |------------------------------------------------------------------------------- |
| turn | boolean | boolean | Yes | `false` | turn on or off a specified led. |
| value | string | `blue`,`green`,`red`,`yellow`,`all` | Yes | `all` | specify the color |

#### `response`

```json
{
  "turn": "bollean",
  "color": "string"
}
```

<br>or case error or message</br>

```
"message string"
```

---

#### `beeperMethod()`

```jsx
import { beeperMethod } from "react-native-getnet-hardware-communication";
async function print() {
  const response = await beeperMethod({
    beeperMode: "success"
  });
  console.log(response);
}
```

##### `Info`

this method to turn on device sound.

**Parameters:**
<br>**type:Object**</br>
| Name | Type | Values | Required | default | Description |
| -------- | ------- | ------------------------------ | -------- | ---- |------------------------------------------------------------------------------- |
| beeperMode | string | `error`,`digit`,`nfc`,`success` | Yes | `success` | type of element to print. |

#### `response`

```json
{
  "beeper": "bollean",
  "type": "string"
}
```

<br>or case error or message</br>

```
"message string"
```

---

#### `cameraMethod()`

```jsx
import { cameraMethod } from "react-native-getnet-hardware-communication";
async function print() {
  const response = await cameraMethod({
    camera: "back",
    timeout: "success"
  });
  console.log(response);
}
```

##### `Info`

this method to turn on device sound.

**Parameters:**
<br>**type:Object**</br>
| Name | Type | Values | Required | default | Description |
| -------- | ------- | ------------------------------ | -------- | ---- |------------------------------------------------------------------------------- |
| camera | string | `back`,`front` | Yes | `back` | side of the camera. |
| timeout | integer | 0 > <b>&#8734;</b> | Yes | not value default | side of the camera. |

#### `response`

```json
{
  "code": "string"
}
```

<br>or case error or message</br>

```json
{
  "error": "boolean",
  "message": "string"
}
```

---

#### `printView()`

coming soon...

aiming to help developers who do not have the machines the method to view the print is under development.

This module is designed for reac-native developers to have no difficulty developing applications for new APOS-A8 card machines and the like ^^. Thanks.

getnet payment application call answer module

`react-native-deep-link-with-response`
https://www.npmjs.com/package/react-native-deep-link-with-response
