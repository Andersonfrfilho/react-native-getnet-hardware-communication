package com.reactnative.andersonfrfilho.getnethardwarecommunication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Base64;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.getnet.posdigital.PosDigital;
import com.getnet.posdigital.camera.ICameraCallback;
import com.getnet.posdigital.card.CardResponse;
import com.getnet.posdigital.card.ICardCallback;
import com.getnet.posdigital.card.SearchType;
import com.getnet.posdigital.info.IInfoCallback;
import com.getnet.posdigital.info.InfoResponse;
import com.getnet.posdigital.printer.AlignMode;
import com.getnet.posdigital.printer.FontFormat;
import com.getnet.posdigital.printer.IPrinterCallback;
import com.getnet.posdigital.printer.PrinterStatus;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetnetHardwareCommunicationModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public GetnetHardwareCommunicationModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "GetnetHardwareCommunication";
    }

    @ReactMethod
    public void startingServices(final Promise promise) {
        PosDigital.register(this.reactContext, new PosDigital.BindCallback() {
            WritableNativeMap promiseResponseServiceStatus = new WritableNativeMap();
            @Override
            public void onError(Exception e) {
                promise.reject(e);
                return;
            }

            @Override
            public void onConnected() {
                promiseResponseServiceStatus.putBoolean("services",true);
                promise.resolve(promiseResponseServiceStatus);
                return;
            }

            @Override
            public void onDisconnected() {
                promiseResponseServiceStatus.putBoolean("services",false);
                promise.resolve(promiseResponseServiceStatus);
                return;
            }
        });
    }
    @ReactMethod
    public void checkConnections(final Promise promise){
        WritableNativeMap promiseCheckConnection= new WritableNativeMap();
        promiseCheckConnection.putBoolean("connection",PosDigital.getInstance().isInitiated());
        promise.resolve(promiseCheckConnection);
        return;
    }
    @ReactMethod
    public void devInformation(final Promise promise){
        try{
            PosDigital.getInstance().getInfo().info(new IInfoCallback.Stub() {
                WritableNativeMap devInfosInformation = new WritableNativeMap();
                @Override
                public void onInfo(InfoResponse infoResponse){
                    devInfosInformation.putString("bCVersion",infoResponse.getBcVersion());
                    devInfosInformation.putString("os",infoResponse.getOsVersion());
                    devInfosInformation.putString("sdk",infoResponse.getSdkVersion());
                    devInfosInformation.putString("serialNumber",infoResponse.getSerialNumber());
                    promise.resolve(devInfosInformation);
                    return;
                }

                @Override
                public void onError(String s){
                    devInfosInformation.putBoolean("error",true);
                    devInfosInformation.putString("message",s);
                    promise.resolve(devInfosInformation);
                    return;

                }
            });
        }catch (Exception error){
            promise.reject("error:",error.getMessage());
        }
    }
    @ReactMethod
    public void cardStartConnectAntenna(ReadableMap config,final Promise promise){
        if(config.getString("typeCard").toLowerCase().equals("magnetic")){
            try {
                String[] searchType = {SearchType.MAG};
                PosDigital.getInstance().getCard().search(config.getInt("timeout"),searchType, new ICardCallback.Stub() {
                    WritableNativeMap cardConnectResponse= new WritableNativeMap();
                    @Override
                    public void onCard(CardResponse cardResponse) {
                        cardConnectResponse.putString("typeCard","magnetic");
                        cardConnectResponse.putInt("numberCard",cardResponse.describeContents());
                        cardConnectResponse.putString("dataExpired",cardResponse.getExpireDate());
                        cardConnectResponse.putString("pan",cardResponse.getPan());
                        cardConnectResponse.putString("track1",cardResponse.getTrack1());
                        cardConnectResponse.putString("track2",cardResponse.getTrack2());
                        cardConnectResponse.putString("track3",cardResponse.getTrack3());
                        cardConnectResponse.putString("type",cardResponse.getType());
                        promise.resolve(cardConnectResponse);
                        return;
                    }

                    @Override
                    public void onMessage(String s) {
                        cardConnectResponse.putBoolean("error",false);
                        cardConnectResponse.putString("message",s);
                        promise.resolve(cardConnectResponse);
                        return;
                    }

                    @Override
                    public void onError(String s) {
                        cardConnectResponse.putBoolean("error",true);
                        cardConnectResponse.putString("message",s);
                        promise.resolve(cardConnectResponse);
                        return;
                    }
                });
            }catch (Exception error){
                promise.reject("error:",error.getMessage());
                return;
            }
        }else if(config.getString("typeCard").toLowerCase().equals("chip")){
            try {
                String[] searchType = {SearchType.CHIP};
                PosDigital.getInstance().getCard().search(config.getInt("timeout"),searchType, new ICardCallback.Stub() {
                    WritableNativeMap cardConnectResponse= new WritableNativeMap();
                    @Override
                    public void onCard(CardResponse cardResponse) {
                        cardConnectResponse.putString("typeCard","chip");
                        cardConnectResponse.putInt("numberCard",cardResponse.describeContents());
                        cardConnectResponse.putString("dataExpired",cardResponse.getExpireDate());
                        cardConnectResponse.putString("pan",cardResponse.getPan());
                        cardConnectResponse.putString("track1",cardResponse.getTrack1());
                        cardConnectResponse.putString("track2",cardResponse.getTrack2());
                        cardConnectResponse.putString("track3",cardResponse.getTrack3());
                        cardConnectResponse.putString("type",cardResponse.getType());
                        promise.resolve(cardConnectResponse);
                        return;
                    }

                    @Override
                    public void onMessage(String s) {
                        cardConnectResponse.putBoolean("error",false);
                        cardConnectResponse.putString("message",s);
                        promise.resolve(cardConnectResponse);
                        return;
                    }

                    @Override
                    public void onError(String s) {
                        cardConnectResponse.putBoolean("error",true);
                        cardConnectResponse.putString("error",s);
                        promise.resolve(cardConnectResponse);
                        return;
                    }
                });
            }catch (Exception error){
                promise.reject("error:",error.getMessage());
                return;
            }
        }else if(config.getString("typeCard").toLowerCase().equals("nfc")){
            try {
                String[] searchType = {SearchType.NFC};
                PosDigital.getInstance().getCard().search(config.getInt("timeout"),searchType, new ICardCallback.Stub() {
                    WritableNativeMap cardConnectResponse= new WritableNativeMap();
                    @Override
                    public void onCard(CardResponse cardResponse) {
                        cardConnectResponse.putString("typeCard","nfc");
                        cardConnectResponse.putInt("numberCard",cardResponse.describeContents());
                        cardConnectResponse.putString("dataExpired",cardResponse.getExpireDate());
                        cardConnectResponse.putString("pan",cardResponse.getPan());
                        cardConnectResponse.putString("track1",cardResponse.getTrack1());
                        cardConnectResponse.putString("track2",cardResponse.getTrack2());
                        cardConnectResponse.putString("track3",cardResponse.getTrack3());
                        cardConnectResponse.putString("type",cardResponse.getType());
                        promise.resolve(cardConnectResponse);
                        return;
                    }

                    @Override
                    public void onMessage(String s) {
                        cardConnectResponse.putBoolean("error",false);
                        cardConnectResponse.putString("message",s);
                        promise.resolve(cardConnectResponse);
                        return;
                    }

                    @Override
                    public void onError(String s) {
                        cardConnectResponse.putBoolean("error",true);
                        cardConnectResponse.putString("message",s);
                        promise.resolve(cardConnectResponse);
                        return;
                    }
                });
            }catch (Exception error){
                promise.reject("error:",error.getMessage());
                return;
            }
        }else{
            try {
                String[] searchType = {SearchType.MAG, SearchType.CHIP, SearchType.NFC};
                PosDigital.getInstance().getCard().search(config.getInt("timeout"),searchType, new ICardCallback.Stub() {
                    WritableNativeMap cardConnectResponse= new WritableNativeMap();
                    @Override
                    public void onCard(CardResponse cardResponse) {
                        cardConnectResponse.putInt("numberCard",cardResponse.describeContents());
                        cardConnectResponse.putString("dataExpired",cardResponse.getExpireDate());
                        cardConnectResponse.putString("pan",cardResponse.getPan());
                        cardConnectResponse.putString("track1",cardResponse.getTrack1());
                        cardConnectResponse.putString("track2",cardResponse.getTrack2());
                        cardConnectResponse.putString("track3",cardResponse.getTrack3());
                        cardConnectResponse.putString("type",cardResponse.getType());
                        promise.resolve(cardConnectResponse);
                        return;
                    }

                    @Override
                    public void onMessage(String s) {
                        cardConnectResponse.putBoolean("error",false);
                        cardConnectResponse.putString("message",s);
                        promise.resolve(cardConnectResponse);
                        return;
                    }

                    @Override
                    public void onError(String s) {
                        cardConnectResponse.putBoolean("error",true);
                        cardConnectResponse.putString("message",s);
                        promise.resolve(cardConnectResponse);
                        return;
                    }
                });
            }catch (Exception error){
                promise.reject("error:",error.getMessage());
                return;
            }
        }
    }
    @ReactMethod
    public void cardStopConnectAntenna(final Promise promise){
        WritableNativeMap cardStopConnectResponse= new WritableNativeMap();
        try{
            PosDigital.getInstance().getCard().stopAllReaders();
            cardStopConnectResponse.putBoolean("stop",true);
            promise.resolve(cardStopConnectResponse);
            return;

        }catch (Exception error){
            promise.reject("error:",error.getMessage());
            return;
        }
    }

    @ReactMethod
    public void printMethod(ReadableArray options, final Promise promise) {
        try {
            PosDigital.getInstance().getPrinter().init();
            for(int i=0;i<options.size();i++){
                if(options.getMap(i).getString("type").toLowerCase().equals("image")){
                    PosDigital.getInstance().getPrinter().setGray(options.getMap(i).getInt("weight"));
                    int position = options.getMap(i).getString("value").indexOf(',');
                    if(position==-1){
                        byte[] byteImage = Base64.decode(options.getMap(i).getString("value"),Base64.DEFAULT);
                        Bitmap decodeImage = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);

                        if(options.getMap(i).getString("align").toLowerCase().equals("left")){
                            PosDigital.getInstance().getPrinter().addImageBitmap(AlignMode.LEFT,decodeImage);
                        }else if(options.getMap(i).getString("align").toLowerCase().equals("right")){
                            PosDigital.getInstance().getPrinter().addImageBitmap(AlignMode.RIGHT,decodeImage);
                        }else{
                            PosDigital.getInstance().getPrinter().addImageBitmap(AlignMode.CENTER,decodeImage);
                        }
                    }else{
                        String newImageBase64 = options.getMap(i).getString("value").substring(position+1,options.getMap(i).getString("value").length());
                        byte[] byteImage = Base64.decode(newImageBase64,Base64.DEFAULT);
                        Bitmap decodeImage = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
                        if(options.getMap(i).getString("align").toLowerCase().equals("left")){
                            PosDigital.getInstance().getPrinter().addImageBitmap(AlignMode.LEFT,decodeImage);
                        }else if(options.getMap(i).getString("align").toLowerCase().equals("right")){
                            PosDigital.getInstance().getPrinter().addImageBitmap(AlignMode.RIGHT,decodeImage);
                        }else{
                            PosDigital.getInstance().getPrinter().addImageBitmap(AlignMode.CENTER,decodeImage);
                        }
                    }
                }else if(options.getMap(i).getString("type").toLowerCase().equals("barcode")){
                    PosDigital.getInstance().getPrinter().setGray(options.getMap(i).getInt("weight"));
                    if(options.getMap(i).getString("align").toLowerCase().equals("left")){
                        PosDigital.getInstance().getPrinter().addBarCode(AlignMode.LEFT,options.getMap(i).getString("value"));
                    }else if (options.getMap(i).getString("align").toLowerCase().equals("right")){
                        PosDigital.getInstance().getPrinter().addBarCode(AlignMode.RIGHT,options.getMap(i).getString("value"));
                    }else{
                        PosDigital.getInstance().getPrinter().addBarCode(AlignMode.CENTER,options.getMap(i).getString("value"));
                    }
                }
                else if(options.getMap(i).getString("type").toLowerCase().equals("qrcode")){
                    PosDigital.getInstance().getPrinter().setGray(options.getMap(i).getInt("weight"));
                    if(options.getMap(i).getString("align").toLowerCase().equals("left")){
                        PosDigital.getInstance().getPrinter().addQrCode(AlignMode.LEFT,240,options.getMap(i).getString("value"));
                    }else if (options.getMap(i).getString("align").toLowerCase().equals("right")){
                        PosDigital.getInstance().getPrinter().addQrCode(AlignMode.RIGHT,240,options.getMap(i).getString("value"));
                    }else{
                        PosDigital.getInstance().getPrinter().addQrCode(AlignMode.CENTER,240,options.getMap(i).getString("value"));
                    }
                }else{
                    PosDigital.getInstance().getPrinter().setGray(options.getMap(i).getInt("weight"));
                    if(options.getMap(i).getString("fontSize").toLowerCase().equals("small")){
                        PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.SMALL);
                    }else if(options.getMap(i).getString("fontSize").toLowerCase().equals("large")){
                        PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.LARGE);
                    }else{
                        PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.MEDIUM);
                    }
                    if(options.getMap(i).getString("align").toLowerCase().equals("left")){
                        PosDigital.getInstance().getPrinter().addText(AlignMode.LEFT,options.getMap(i).getString("value"));

                    }else if(options.getMap(i).getString("align").toLowerCase().equals("right")){
                        PosDigital.getInstance().getPrinter().addText(AlignMode.RIGHT,options.getMap(i).getString("value"));
                    }else{
                        PosDigital.getInstance().getPrinter().addText(AlignMode.CENTER,options.getMap(i).getString("value"));
                    }
                }
            }
            PosDigital.getInstance().getPrinter().addText(AlignMode.LEFT, "\n \n \n \n");
            PosDigital.getInstance().getPrinter().print(new IPrinterCallback.Stub() {
                @Override
                public void onSuccess() throws RemoteException {
                    WritableNativeMap printResponse= new WritableNativeMap();
                    printResponse.putBoolean("printer",true);
                    promise.resolve(printResponse);
                    return;
                }

                @Override
                public void onError(int i) throws RemoteException {
                    WritableNativeMap printResponse= new WritableNativeMap();
                    switch (i) {
                        case 2:
                            printResponse.putBoolean("printer",false);
                            printResponse.putString("message","Impressora não iniciada");
                            promise.resolve(printResponse);
                            return;
                        case 3:
                            printResponse.putBoolean("printer",false);
                            printResponse.putString("message","Impressora superaquecida");
                            promise.resolve(printResponse);
                            return;
                        case 4:
                            printResponse.putBoolean("printer",false);
                            printResponse.putString("message","Fila de impressão muito grande");
                            promise.resolve(printResponse);
                            return;
                        case 5:
                            printResponse.putBoolean("printer",false);
                            printResponse.putString("message","Parametros incorretos");
                            promise.resolve(printResponse);
                            return;
                        case 10:
                            printResponse.putBoolean("printer",false);
                            printResponse.putString("message","Porta da impressora aberta");
                            promise.resolve(printResponse);
                            return;
                        case 11:
                            printResponse.putBoolean("printer",false);
                            printResponse.putString("message","Temperatura baixa de mais");
                            promise.resolve(printResponse);
                            return;
                        case 12:
                            printResponse.putBoolean("printer",false);
                            printResponse.putString("message","Sem bateria suficiente para impressão");
                            promise.resolve(printResponse);
                            return;
                        case 13:
                            printResponse.putBoolean("printer",false);
                            printResponse.putString("message","Motor de passo com problemas");
                            promise.resolve(printResponse);
                            return;
                        case 15:
                            printResponse.putBoolean("printer",false);
                            printResponse.putString("message","Sem bobina");
                            promise.resolve(printResponse);
                            return;
                        case 16:
                            printResponse.putBoolean("printer",false);
                            printResponse.putString("message","bobina acabando");
                            promise.resolve(printResponse);
                            return;
                        case 17:
                            printResponse.putBoolean("printer",false);
                            printResponse.putString("message","Bobina travada");
                            promise.resolve(printResponse);
                            return;
                        default:
                            printResponse.putBoolean("printer",false);
                            printResponse.putString("message","Erro não identificado");
                            promise.resolve(printResponse);

                            return;
                    }

                }
            });
        } catch (Exception error) {
            promise.reject("error",error.getMessage());
            return;
        }
    }

    @ReactMethod
    public void ledMethod(ReadableMap data, final Promise promise) {
        try {
            WritableNativeMap ledResponse= new WritableNativeMap();
            if (data.getBoolean("turn")) {
                switch (data.getString("color").toLowerCase()) {
                    case "blue":
                        PosDigital.getInstance().getLed().turnOnBlue();
                        break;
                    case "green":
                        PosDigital.getInstance().getLed().turnOnGreen();
                        break;
                    case "red":
                        PosDigital.getInstance().getLed().turnOnRed();
                        break;
                    case "yellow":
                        PosDigital.getInstance().getLed().turnOnYellow();
                        break;
                    default:
                        PosDigital.getInstance().getLed().turnOnAll();
                        break;
                }
                ledResponse.putBoolean("turn",true);
                ledResponse.putString("color",data.getString("color").toLowerCase());
                promise.resolve(ledResponse);
                return;
            } else {
                switch (data.getString("color").toLowerCase()) {
                    case "blue":
                        PosDigital.getInstance().getLed().turnOffBlue();
                        break;
                    case "green":
                        PosDigital.getInstance().getLed().turnOffGreen();
                        break;
                    case "red":
                        PosDigital.getInstance().getLed().turnOffRed();
                        break;
                    case "yellow":
                        PosDigital.getInstance().getLed().turnOffYellow();
                        break;
                    default:
                        PosDigital.getInstance().getLed().turnOffAll();
                        break;
                }

                ledResponse.putBoolean("turn",false);
                ledResponse.putString("color",data.getString("color").toLowerCase());
                promise.resolve(ledResponse);
                return;
            }

        } catch (Exception e) {
            promise.reject("error no módulo de led:" + e + " ," + false, "error informações da maquina");

            return;
        }
    }

    @ReactMethod
    public void beeperMethod(ReadableMap data, final Promise promise) {
        try {
            WritableNativeMap beeperResponse= new WritableNativeMap();
            switch (data.getString("beeperMode").toLowerCase()) {
                case "error":
                    PosDigital.getInstance().getBeeper().error();
                    break;
                case "digit":
                    PosDigital.getInstance().getBeeper().digit();
                    break;
                case "nfc":
                    PosDigital.getInstance().getBeeper().nfc();
                    break;
                default:
                    PosDigital.getInstance().getBeeper().success();
                    break;
            }
            beeperResponse.putBoolean("beeper",true);
            beeperResponse.putString("type",data.getString("beeperMode").toLowerCase());
            promise.resolve(beeperResponse);
            return;
        } catch (Exception e) {
            promise.reject("error : " + data.getString("beeperMode").toLowerCase(),e.getMessage());
            return;
        }
    }
    @ReactMethod
    public void cameraMethod(ReadableMap data,final Promise promise) {
        try {
            if(data.getString("camera").toLowerCase().equals("front")){
                PosDigital.getInstance().getCamera().readFront(data.getInt("timeout"), new ICameraCallback.Stub() {
                    WritableNativeMap cameraResponse= new WritableNativeMap();
                    @Override
                    public void onSuccess(String s) throws RemoteException {
                        cameraResponse.putString("code",s);
                        promise.resolve(s);
                        return;
                    }

                    @Override
                    public void onTimeout() throws RemoteException {
                        cameraResponse.putBoolean("error",true);
                        cameraResponse.putString("message","time exceeded");
                        promise.resolve(cameraResponse);
                        return;
                    }

                    @Override
                    public void onCancel() throws RemoteException {
                        cameraResponse.putBoolean("error",true);
                        cameraResponse.putString("message","option canceled");
                        promise.resolve(cameraResponse);
                        return;
                    }

                    @Override
                    public void onError(String s) throws RemoteException {
                        cameraResponse.putBoolean("error",true);
                        cameraResponse.putString("message",s);
                        promise.resolve(cameraResponse);
                        return;
                    }
                });
            }else{
                PosDigital.getInstance().getCamera().readBack(data.getInt("timeout"), new ICameraCallback.Stub() {
                    WritableNativeMap cameraResponse= new WritableNativeMap();
                    @Override
                    public void onSuccess(String s) throws RemoteException {
                        cameraResponse.putString("code",s);
                        promise.resolve(s);
                        return;
                    }

                    @Override
                    public void onTimeout() throws RemoteException {
                        cameraResponse.putBoolean("error",true);
                        cameraResponse.putString("message","time exceeded");
                        promise.resolve(cameraResponse);
                        return;
                    }

                    @Override
                    public void onCancel() throws RemoteException {
                        cameraResponse.putBoolean("error",true);
                        cameraResponse.putString("message","option canceled");
                        promise.resolve(cameraResponse);
                        return;
                    }

                    @Override
                    public void onError(String s) throws RemoteException {
                        cameraResponse.putBoolean("error",true);
                        cameraResponse.putString("message",s);
                        promise.resolve(cameraResponse);
                        return;
                    }
                });
            }

        } catch (Exception e) {
            promise.reject("error", e.getMessage());
            return;
        }
    }
    @ReactMethod
    public void printView(ReadableMap data, final Promise promise) {
        List<String> printVector = new ArrayList<String>();
        for (int i = 0; i < data.getArray("textPrint").size(); i++) {
            if(data.getArray("textPrint").getMap(i).getString("position").equals("bitmap")){
                if(data.getArray("textPrint").getMap(i).getString("fontSize").toLowerCase().equals("small")){
                    printVector.add("|______Image show in next update module__________|");
                }else{
                    printVector.add("|Image show in next update module|");
                }
            }else if(data.getArray("textPrint").getMap(i).getString("position").toLowerCase().equals("left")){
                if(data.getArray("textPrint").getMap(i).getString("fontSize").toLowerCase().equals("small")){
                    int sizePhrase=48;
                    int sizePaper=50;
                    if(data.getArray("textPrint").getMap(i).getString("text").length()>=sizePhrase){
                        printVector.add("|"+data.getArray("textPrint").getMap(i).getString("text").substring(0,sizePhrase)+"|");
                    }else{
                        StringBuilder newPhrase = new StringBuilder();
                        newPhrase.append("|");
                        newPhrase.append(data.getArray("textPrint").getMap(i).getString("text"));
                        newPhrase.append("________________________________________________|".substring(newPhrase.length(),sizePaper-1));
                        if(newPhrase.length()<sizePaper){
                            newPhrase.insert(sizePhrase-1,"_");
                        }
                        printVector.add(newPhrase.toString());
                    }
                }else if(data.getArray("textPrint").getMap(i).getString("fontSize").toLowerCase().equals("medium")){
                    int sizePhrase=32;
                    int sizePaper=34;
                    if(data.getArray("textPrint").getMap(i).getString("text").length()>=sizePhrase){
                        printVector.add("|"+data.getArray("textPrint").getMap(i).getString("text").substring(0,sizePhrase)+"|");
                    }else{
                        StringBuilder newPhrase = new StringBuilder();
                        newPhrase.append("|");
                        newPhrase.append(data.getArray("textPrint").getMap(i).getString("text"));
                        newPhrase.append("_________________________________|".substring(newPhrase.length(),sizePaper));
                        if(newPhrase.length()<sizePaper){
                            newPhrase.insert(sizePhrase-1,"_");
                        }
                        printVector.add(newPhrase.toString());
                    }

                }else{
                    int sizePhrase=32;
                    int sizePaper=34;
                    if(data.getArray("textPrint").getMap(i).getString("text").length()>=sizePhrase){
                        printVector.add("|"+data.getArray("textPrint").getMap(i).getString("text").substring(0,sizePhrase)+"|");
                    }else{
                        StringBuilder newPhrase = new StringBuilder();
                        newPhrase.append("|");
                        newPhrase.append(data.getArray("textPrint").getMap(i).getString("text"));
                        newPhrase.append("_________________________________|".substring(newPhrase.length(),sizePaper));
                        if(newPhrase.length()<sizePaper){
                            newPhrase.insert(sizePhrase-1,"_");
                        }
                        printVector.add(newPhrase.toString());
                    }

                }
            }
            else if(data.getArray("textPrint").getMap(i).getString("position").toLowerCase().equals("right")){
                if(data.getArray("textPrint").getMap(i).getString("fontSize").toLowerCase().equals("small")){
                    int sizePhrase=48;
                    int sizePaper=50;
                    if(data.getArray("textPrint").getMap(i).getString("text").length()>=sizePhrase){
                        printVector.add("|"+data.getArray("textPrint").getMap(i).getString("text").substring(0,sizePhrase)+"|");
                    }else{
                        StringBuilder newPhrase = new StringBuilder();
                        int numberSpace = (sizePhrase - data.getArray("textPrint").getMap(i).getString("text").length());

                        newPhrase.append("|________________________________________________".substring(0,numberSpace));
                        newPhrase.insert(numberSpace,data.getArray("textPrint").getMap(i).getString("text"));
                        newPhrase.append("|");
                        if(newPhrase.length()<sizePaper){
                            newPhrase.insert(1,"_");
                        }
                        printVector.add(newPhrase.toString());
                    }
                }else if(data.getArray("textPrint").getMap(i).getString("fontSize").toLowerCase().equals("medium")){
                    int sizePhrase=32;
                    int sizePaper=34;
                    if(data.getArray("textPrint").getMap(i).getString("text").length()>=sizePhrase){
                        printVector.add("|"+data.getArray("textPrint").getMap(i).getString("text").substring(0,sizePhrase)+"|");
                    }else{
                        StringBuilder newPhrase = new StringBuilder();
                        int numberSpace = (sizePhrase - data.getArray("textPrint").getMap(i).getString("text").length());

                        newPhrase.append("|________________________________________________".substring(0,numberSpace));
                        newPhrase.insert(numberSpace,data.getArray("textPrint").getMap(i).getString("text"));
                        newPhrase.append("|");
                        if(newPhrase.length()<sizePaper){
                            newPhrase.insert(1,"_");
                        }
                        printVector.add(newPhrase.toString());
                    }
                }else{
                    int sizePhrase=32;
                    int sizePaper=34;
                    if(data.getArray("textPrint").getMap(i).getString("text").length()>=sizePhrase){
                        printVector.add("|"+data.getArray("textPrint").getMap(i).getString("text").substring(0,sizePhrase)+"|");
                    }else{
                        StringBuilder newPhrase = new StringBuilder();
                        int numberSpace = (sizePhrase - data.getArray("textPrint").getMap(i).getString("text").length());

                        newPhrase.append("|________________________________________________".substring(0,numberSpace));
                        newPhrase.insert(numberSpace,data.getArray("textPrint").getMap(i).getString("text"));
                        newPhrase.append("|");
                        if(newPhrase.length()<sizePaper){
                            newPhrase.insert(1,"_");
                        }
                        printVector.add(newPhrase.toString());
                    }
                }
            }else{
                if(data.getArray("textPrint").getMap(i).getString("fontSize").toLowerCase().equals("small")){
                    int sizePhrase=48;
                    int sizePaper=50;
                    if(data.getArray("textPrint").getMap(i).getString("text").length()>=sizePhrase){
                        printVector.add("|"+data.getArray("textPrint").getMap(i).getString("text").substring(0,sizePhrase)+"|");
                    }else{

                        StringBuilder newPhrase = new StringBuilder();
                        int numberSpace = (sizePhrase - data.getArray("textPrint").getMap(i).getString("text").length())/2;
                        newPhrase.append("|________________________________________________".substring(0,numberSpace));
                        newPhrase.append(data.getArray("textPrint").getMap(i).getString("text"));
                        newPhrase.append("________________________________________________|".substring(newPhrase.length(),sizePaper-1));
                        if(newPhrase.length()<sizePaper){
                            newPhrase.insert(sizePhrase,"_");
                        }
                        printVector.add(newPhrase.toString());
                    }
                }else if(data.getArray("textPrint").getMap(i).getString("fontSize").toLowerCase().equals("medium")){
                    int sizePhrase=32;
                    int sizePaper=34;
                    if(data.getArray("textPrint").getMap(i).getString("text").length()>=sizePhrase){
                        printVector.add("|"+data.getArray("textPrint").getMap(i).getString("text").substring(0,sizePhrase)+"|");
                    }else{

                        StringBuilder newPhrase = new StringBuilder();
                        int numberSpace = (sizePhrase - data.getArray("textPrint").getMap(i).getString("text").length())/2;
                        newPhrase.append("|________________________________".substring(0,numberSpace));
                        newPhrase.append(data.getArray("textPrint").getMap(i).getString("text"));
                        newPhrase.append("_________________________________|".substring(newPhrase.length(),sizePaper));
                        if(newPhrase.length()<sizePaper){
                            newPhrase.insert(sizePhrase,"_");
                        }
                        printVector.add(newPhrase.toString());
                    }
                }else{
                    int sizePhrase=32;
                    int sizePaper=34;
                    if(data.getArray("textPrint").getMap(i).getString("text").length()>=sizePhrase){
                        printVector.add("|"+data.getArray("textPrint").getMap(i).getString("text").substring(0,sizePhrase)+"|");
                    }else{

                        StringBuilder newPhrase = new StringBuilder();
                        int numberSpace = (sizePhrase - data.getArray("textPrint").getMap(i).getString("text").length())/2;
                        newPhrase.append("|________________________________".substring(0,numberSpace));
                        newPhrase.append(data.getArray("textPrint").getMap(i).getString("text"));
                        newPhrase.append("_________________________________|".substring(newPhrase.length(),sizePaper));

                        if(newPhrase.length()<sizePaper){
                            newPhrase.insert(sizePhrase,"_");
                        }
                        printVector.add(newPhrase.toString());
                    }
                }
            }
        }
        WritableArray promisePrintView= Arguments.createArray();

        for(int i=0;i<printVector.size();i++){
            if(i==0){
                promisePrintView.pushString("|\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/".substring(0,printVector.get(i).length()-1)+"|");
            }
            promisePrintView.pushString(printVector.get(i));
            if(i==printVector.size()-1){
                promisePrintView.pushString("|\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/".substring(0,printVector.get(i).length()-1)+"|");
            }

        }
        promise.resolve(promisePrintView);
        return;


    }
}
