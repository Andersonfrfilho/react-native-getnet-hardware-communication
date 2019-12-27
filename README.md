# react-native-getnet-hardware-communication

## Getting started

`$ npm install react-native-getnet-hardware-communication --save`

### Mostly automatic installation

`$ react-native link react-native-getnet-hardware-communication`

#### Android

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

2. in android/app/src/main/androidManifest.xml add in

```javascript
<manifest xmlns:android="..." package="...">
  ...
  <uses-permission android:name="com.getnet.posdigital.service.POSDIGITAL" />
  ...
</manifest>
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
import GetnetHardwareCommunication from "react-native-getnet-hardware-communication";
//example
//service of Initialized hardware comunicaction
//return True or False
const myFunction = async () => {
  ///////////////////////////////////
  //responseMethod;
  const responseService = await checkServiceMethod();
  ///////////////////////////////////
  //printMethod;
  const responsePrint = await printMethod({
    // 1 - 5;
    setFontGray: 5,
    //choice a size
    fontSize: "small" || "medium" || "large",
    //using text(small<48 || medium && large << 36) and position
    //Bitmap width: 378 pixels
    textPrint: [
      {
        text: "text write or path_bitmap",
        position: "bitmap-1" || "left" || "center" || "rigth"
      },
      {
        text: "text write or path_bitmap",
        position: "bitmap-2" || "left" || "center" || "rigth"
      },
      {
        text: "text write or path_bitmap",
        position: "bitmap-3" || "left" || "center" || "rigth"
      },
      //Example BITMAP -> bitmap-0 (left), bitmap-1 (central), bitmap-2 (rigth);
      {
        text: "/../sourcefile.bitmap",
        position: "bitmap-0" || "bitmap-1" || "bitmap-2"
      },
      //Example Text
      { text: "text implementation", position: "center" }
    ]
  });
  ///////////////////////////////////
  //ledMethod;
  const responseLed = await ledMethod({
    turn: true || false,
    color: "blue" || "green" || "red" || "yellow" || "all"
  });
  ///////////////////////////////////
  //beeperMethod;
  const responseBeeper = await beeperMethod({
    beeperMode: "error" || "digit" || "nfc" || "success"
  });
  ///////////////////////////////////
  //cameraBackMethod;
  const responseCamera = await cameraBackMethod();
  //////////////////////////////////
  //information Method
  const responseInfo = await infoMethod();
};

//response method printer success
("ok");
//response method printer error
("Impressora não iniciada");

("Impressora superaquecida");

("Fila de impressão muito grande");

("Parametros incorretos");

("Porta da impressora aberta");

("Temperatura baixa de mais");

("Sem bateria suficiente para impressão");

("Motor de passo com problemas");

("Sem bobina");

("bobina acabando");

("Bobina travada");
////////////////////////////////
//Led Method

///////////////////////////////////
//beeperMethod

GetnetHardwareCommunication;
```

This module is designed for reac-native developers to have no difficulty developing applications for new APOS-A8 card machines and the like ^^. Thanks.

getnet payment application call answer module

`react-native-deep-link-with-response`
https://www.npmjs.com/package/react-native-deep-link-with-response
