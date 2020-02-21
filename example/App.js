import React, {useState} from 'react';
import {StyleSheet, Text, View, TouchableOpacity} from 'react-native';
import {
  startingServices,
  checkConnections,
  devInformation,
  cardStartConnectAntenna,
  cardStopConnectAntenna,
  printMethod,
  ledMethod,
  beeperMethod,
  cameraBackMethod,
} from 'react-native-getnet-hardware-communication';
import configPrint from './configPrint';
export default function App() {
  const [service, setService] = useState(false);
  const [connection, setConnection] = useState(false);
  const [info, setInfo] = useState('');
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
      color: 'all',
    });
  }
  async function ledOff() {
    const data = await ledMethod({
      //turn: true | false
      turn: false,
      //color blue | green | red | yellow | all
      color: 'all',
    });
  }
  async function beeper() {
    //beeperMode: error|digit|nfc|success
    //default success
    const data = await beeperMethod({
      beeperMode: 'success',
    });
  }
  async function camera() {
    //timeout parameter for cont
    const data = await cameraBackMethod(30);
    console.log(data);
  }

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
      <TouchableOpacity
        style={styles.button}
        disabled={!(service && connection)}
        onPress={() => ledOn()}>
        <Text style={styles.textButton}>On Led</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.button}
        disabled={!(service && connection)}
        onPress={() => ledOff()}>
        <Text style={styles.textButton}>Off Led</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.button}
        disabled={!(service && connection)}
        onPress={() => beeper()}>
        <Text style={styles.textButton}>Bepper</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={styles.button}
        disabled={!(service && connection)}
        onPress={() => camera()}>
        <Text style={styles.textButton}>Camera</Text>
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
