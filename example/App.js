import React, {useState, useEffect} from 'react';
import {
  StyleSheet,
  Text,
  View,
  Button,
  Linking,
  TextInput,
  TouchableOpacity,
} from 'react-native';
import GetnetHardwareCommunication from 'react-native-getnet-hardware-communication';

export default function App() {
  const [service, setService] = useState(false);
  const [connection, setConnection] = useState(false);
  const [info, setInfo] = useState('');
  async function verifyService() {
    try {
      //Method for initialized services
      let services = await GetnetHardwareCommunication.startingServices();
      console.log(services.services);
      //Method for initialized connections
      let connection = await GetnetHardwareCommunication.checkConnections();
      console.log(connection.connection);
      setService(services.services.toString());
      setConnection(connection.connection.toString());
    } catch (error) {
      console.log(error);
    }
  }
  async function deviceInfo() {
    let infos = await GetnetHardwareCommunication.devInformation();
    console.log(infos);
    setInfo(
      `bc:${infos.BcVersion}\nOS:${infos.os}\nsdk:${infos.sdk}\nserial-number:${infos.serialNumber}`,
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
        typeCard: 'nfc',
        timeout: 30,
      };
      let cardConnect = await GetnetHardwareCommunication.cardStartConnectAntenna(
        configs,
      );
      console.log(cardConnect);
    } catch (error) {
      console.log(error);
    }
  }
  async function stopConnectCard() {
    try {
      await GetnetHardwareCommunication.cardStopConnectAntenna();
    } catch (error) {
      console.log(error);
    }
  }
  async function printer() {
    await GetnetHardwareCommunication.printMethod({weight: 3});
  }
  // async function print() {
  //   const responsePrint = await GetnetHardwareCommunication.printMethod({
  //     // 1 - 5;
  //     setFontGray: 5,
  //     //choice a size
  //     fontSize: 'small',
  //     //using text(small<48 || medium && large << 36) and position
  //     //Bitmap width: 378 pixels
  //     textPrint: [
  //       {
  //         text: 'text write or path_bitmap',
  //         position: 'bitmap-1',
  //       },
  //       {
  //         text: 'text write or path_bitmap',
  //         position: 'left',
  //       },
  //       {
  //         text: 'text write or path_bitmap',
  //         position: 'center',
  //       },
  //       //Example BITMAP -> bitmap-0 (left), bitmap-1 (central), bitmap-2 (rigth);
  //       //Example Text
  //       {text: 'text implementation', position: 'center'},
  //       ,
  //     ],
  //   });
  // }
  // async function printView() {
  //   const data = await GetnetHardwareCommunication.printView({
  //     textPrint: [
  //       {
  //         text: 'Anderson',
  //         position: 'bitmap',
  //         fontSize: 'small',
  //         setFontGray: 5,
  //       },
  //       {
  //         text: 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789ABCE56789',
  //         position: 'center',
  //         setFontGray: 5,
  //         fontSize: 'small',
  //       },
  //       {
  //         text:
  //           'ABCDEFGHIJKLMNOPQRSTUVWXYZ012349ABCEGHIJKLMNOPQRSTUVWXYZ123456789',
  //         position: 'center',
  //         setFontGray: 5,
  //         fontSize: 'small',
  //       },
  //       {
  //         text: 'ABCDEFGHIJKLMNOPYZ123456789',
  //         position: 'center',
  //         setFontGray: 5,
  //         fontSize: 'small',
  //       },
  //       {
  //         text: 'ABCDEFGHIJKLMNOPQRSTUABCEGHIJKLMNOPQRSTUVWXYZ123456789',
  //         position: 'center',
  //         setFontGray: 5,
  //         fontSize: 'small',
  //       },
  //     ],
  //   });
  //   for (let i = 0; i < data.length; i++) {
  //     console.log(data[i]);
  //   }
  // }
  return (
    <View style={styles.container}>
      <View>
        <Text>Service: {service ? 'work' : 'not work'}</Text>
      </View>
      <View>
        <Text>Connection: {connection ? 'connect' : 'not connect'}</Text>
      </View>
      {info ? (
        <View>
          <Text>Infos: {'information'}</Text>
        </View>
      ) : null}
      <TouchableOpacity style={styles.button} onPress={() => verifyService()}>
        <Text style={styles.textButton}>Service / Connection</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.button}
        disabled={!(service && connection)}
        onPress={() => deviceInfo()}>
        <Text style={styles.textButton}>Device Information</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.button}
        disabled={!(service && connection)}
        onPress={() => connectCard()}>
        <Text style={styles.textButton}>Card Connection Start</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.button}
        disabled={!(service && connection)}
        onPress={() => stopConnectCard()}>
        <Text style={styles.textButton}>Card Connection Stop</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.button}
        disabled={!(service && connection)}
        onPress={() => printer()}>
        <Text style={styles.textButton}>Printer</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
  button: {
    backgroundColor: '#451234',
    width: '70%',
    height: 40,
    marginBottom: 10,
    borderRadius: 10,
    justifyContent: 'center',
    alignItems: 'center',
  },
  textButton: {
    fontSize: 14,
    color: '#f7f7f7',
  },
});
