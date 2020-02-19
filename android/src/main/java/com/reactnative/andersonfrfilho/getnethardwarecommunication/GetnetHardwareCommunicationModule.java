package com.reactnative.andersonfrfilho.getnethardwarecommunication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.RemoteException;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
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
                    devInfosInformation.putString("BcVersion",infoResponse.getBcVersion());
                    devInfosInformation.putString("os",infoResponse.getOsVersion());
                    devInfosInformation.putString("sdk",infoResponse.getSdkVersion());
                    devInfosInformation.putString("serialNumber",infoResponse.getSerialNumber());
                    promise.resolve(devInfosInformation);
                    return;
                }

                @Override
                public void onError(String s){
                    devInfosInformation.putString("error",s);
                    promise.resolve(devInfosInformation);
                    return;

                }
            });
        }catch (Exception error){
            promise.reject(error);
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
                        cardConnectResponse.putString("message",s);
                        promise.resolve(cardConnectResponse);
                        return;
                    }

                    @Override
                    public void onError(String s) {
                        cardConnectResponse.putString("error",s);
                        promise.resolve(cardConnectResponse);
                        return;
                    }
                });
            }catch (Exception error){
                promise.reject(error);
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
                        cardConnectResponse.putString("message",s);
                        promise.resolve(cardConnectResponse);
                        return;
                    }

                    @Override
                    public void onError(String s) {
                        cardConnectResponse.putString("error",s);
                        promise.resolve(cardConnectResponse);
                        return;
                    }
                });
            }catch (Exception error){
                promise.reject(error);
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
                        cardConnectResponse.putString("message",s);
                        promise.resolve(cardConnectResponse);
                        return;
                    }

                    @Override
                    public void onError(String s) {
                        cardConnectResponse.putString("error",s);
                        promise.resolve(cardConnectResponse);
                        return;
                    }
                });
            }catch (Exception error){
                promise.reject(error);
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
                        cardConnectResponse.putString("message",s);
                        promise.resolve(cardConnectResponse);
                        return;
                    }

                    @Override
                    public void onError(String s) {
                        cardConnectResponse.putString("error",s);
                        promise.resolve(cardConnectResponse);
                        return;
                    }
                });
            }catch (Exception error){
                promise.reject(error);
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
            promise.reject(error);
            return;
        }
    }



    @ReactMethod
    public void printMethod(ReadableMap options, final Promise promise) {
        try {
            PosDigital.getInstance().getPrinter().init();
            PosDigital.getInstance().getPrinter().setGray(options.getInt("weight"));
            PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.MEDIUM);
            PosDigital.getInstance().getPrinter().addQrCode(AlignMode.LEFT,240,"Anderson Fernandes");
            PosDigital.getInstance().getPrinter().addQrCode(AlignMode.RIGHT,240,"Anderson Fernandes");
            PosDigital.getInstance().getPrinter().addImageBitmap(AlignMode.CENTER,Bitmap.createBitmap(200,200,Bitmap.Config.ARGB_8888));
            PosDigital.getInstance().getPrinter().print(new IPrinterCallback.Stub() {
                @Override
                public void onSuccess() throws RemoteException {
                    promise.resolve("true");
                    return;
                }

                @Override
                public void onError(int i) throws RemoteException {
                    promise.resolve("true");
                    return;
                }
            });
        } catch (Exception error) {
            promise.resolve(error);
            return;
        }
    }
            /*
        }
            PosDigital.getInstance().getPrinter().init();
            for(int i=0;i<options.getArray("textPrint").size();i++){
                if(options.getArray("print").getMap(i).getString("type").equals("bitmap")){

                }else if(options.getArray("print").getMap(i).getString("type").equals("qrcode")){
                    if(options.getArray("print").getMap(i).getString("fontSize").toLowerCase().equals("small")){
                        if(options.getArray("print").getMap(i).getString("aling").toLowerCase().equals("left")){
                            PosDigital.getInstance().getPrinter().setGray(options.getInt("weight"));
                            PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.SMALL);
                            PosDigital.getInstance().getPrinter().addText(AlignMode.LEFT,options.getArray("print").getMap(i).getString("content"));
                        }else if(options.getArray("print").getMap(i).getString("aling").toLowerCase().equals("right")){
                            PosDigital.getInstance().getPrinter().setGray(options.getInt("weight"));
                            PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.SMALL);
                            PosDigital.getInstance().getPrinter().addText(AlignMode.RIGHT,options.getArray("print").getMap(i).getString("content"));
                        }else{
                            PosDigital.getInstance().getPrinter().setGray(options.getInt("weight"));
                            PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.SMALL);
                            PosDigital.getInstance().getPrinter().addText(AlignMode.CENTER,options.getArray("print").getMap(i).getString("content"));
                        }
                    }
                }
            }
            PosDigital.getInstance().getPrinter().setGray(options.getInt("weight"));
            PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.MEDIUM);
            PosDigital.getInstance().getPrinter().addQrCode(AlignMode.LEFT,240,"Anderson Fernandes");
            PosDigital.getInstance().getPrinter().addQrCode(AlignMode.RIGHT,240,"Anderson Fernandes");
            //BitmapFactory bitmapFactory = new BitmapFactory(com.facebook.react.R.id.);
            //PosDigital.getInstance().getPrinter().addImageBitmap(AlignMode.CENTER,bitmap);

            PosDigital.getInstance().getPrinter().print(new IPrinterCallback.Stub() {
                WritableNativeMap printResponse= new WritableNativeMap();
                @Override
                public void onSuccess() {
                    printResponse.putBoolean("printer",true);
                    promise.resolve(printResponse);
                    return;
                }
                @Override
                public void onError(int i) {
                    printResponse.putBoolean("printer",false);
                    promise.resolve(printResponse);
                    return;
                }
            });
            return;
            /*for (int i = 0; i < data.getArray("textPrint").size(); i++){
                if(data.getArray("textPrint").getMap(i).getString("position").equals("bitmap")){

                }else if(data.getArray("textPrint").getMap(i).getString("position").toLowerCase().equals("left")){
                    if(data.getArray("textPrint").getMap(i).getString("fontSize").toLowerCase().equals("small")){
                        int sizePhrase=48;
                        if(data.getArray("textPrint").getMap(i).getString("text").length()>=sizePhrase){
                            PosDigital.getInstance().getPrinter().init();
                            PosDigital.getInstance().getPrinter().setGray(data.getArray("textPrint").getMap(i).getInt("setFontGray"));
                            PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.SMALL);
                            PosDigital.getInstance().getPrinter().addText(AlignMode.LEFT,data.getArray("textPrint").getMap(i).getString("text").substring(0,sizePhrase));
                        }else{
                            PosDigital.getInstance().getPrinter().init();
                            PosDigital.getInstance().getPrinter().setGray(data.getArray("textPrint").getMap(i).getInt("setFontGray"));
                            PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.SMALL);
                            PosDigital.getInstance().getPrinter().addText(AlignMode.LEFT,data.getArray("textPrint").getMap(i).getString("text").substring(0,data.getArray("textPrint").getMap(i).getString("text").length()));
                        }
                    }else if(data.getArray("textPrint").getMap(i).getString("fontSize").toLowerCase().equals("medium")){
                        int sizePhrase=32;
                        if(data.getArray("textPrint").getMap(i).getString("text").length()>=sizePhrase){
                            PosDigital.getInstance().getPrinter().init();
                            PosDigital.getInstance().getPrinter().setGray(data.getArray("textPrint").getMap(i).getInt("setFontGray"));
                            PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.MEDIUM);
                            PosDigital.getInstance().getPrinter().addText(AlignMode.LEFT,data.getArray("textPrint").getMap(i).getString("text").substring(0,sizePhrase));
                        }else{
                            PosDigital.getInstance().getPrinter().init();
                            PosDigital.getInstance().getPrinter().setGray(data.getArray("textPrint").getMap(i).getInt("setFontGray"));
                            PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.MEDIUM);
                            PosDigital.getInstance().getPrinter().addText(AlignMode.LEFT,data.getArray("textPrint").getMap(i).getString("text").substring(0,data.getArray("textPrint").getMap(i).getString("text").length()));
                        }
                    }else{
                        int sizePhrase=32;
                        if(data.getArray("textPrint").getMap(i).getString("text").length()>=sizePhrase){
                            PosDigital.getInstance().getPrinter().init();
                            PosDigital.getInstance().getPrinter().setGray(data.getArray("textPrint").getMap(i).getInt("setFontGray"));
                            PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.LARGE);
                            PosDigital.getInstance().getPrinter().addText(AlignMode.LEFT,data.getArray("textPrint").getMap(i).getString("text").substring(0,sizePhrase));
                        }else{
                            PosDigital.getInstance().getPrinter().init();
                            PosDigital.getInstance().getPrinter().setGray(data.getArray("textPrint").getMap(i).getInt("setFontGray"));
                            PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.LARGE);
                            PosDigital.getInstance().getPrinter().addText(AlignMode.LEFT,data.getArray("textPrint").getMap(i).getString("text").substring(0,data.getArray("textPrint").getMap(i).getString("text").length()));
                        }
                    }
                }
                else if(data.getArray("textPrint").getMap(i).getString("position").toLowerCase().equals("right")){
                    if(data.getArray("textPrint").getMap(i).getString("fontSize").toLowerCase().equals("small")){
                        int sizePhrase=48;
                        if(data.getArray("textPrint").getMap(i).getString("text").length()>=sizePhrase){
                            PosDigital.getInstance().getPrinter().init();
                            PosDigital.getInstance().getPrinter().setGray(data.getArray("textPrint").getMap(i).getInt("setFontGray"));
                            PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.SMALL);
                            PosDigital.getInstance().getPrinter().addText(AlignMode.RIGHT,data.getArray("textPrint").getMap(i).getString("text").substring(0,sizePhrase));
                        }else{
                            PosDigital.getInstance().getPrinter().init();
                            PosDigital.getInstance().getPrinter().setGray(data.getArray("textPrint").getMap(i).getInt("setFontGray"));
                            PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.SMALL);
                            PosDigital.getInstance().getPrinter().addText(AlignMode.RIGHT,data.getArray("textPrint").getMap(i).getString("text").substring(0,data.getArray("textPrint").getMap(i).getString("text").length()));
                        }
                    }else if(data.getArray("textPrint").getMap(i).getString("fontSize").toLowerCase().equals("medium")){
                        int sizePhrase=32;
                        if(data.getArray("textPrint").getMap(i).getString("text").length()>=sizePhrase){
                            PosDigital.getInstance().getPrinter().init();
                            PosDigital.getInstance().getPrinter().setGray(data.getArray("textPrint").getMap(i).getInt("setFontGray"));
                            PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.SMALL);
                            PosDigital.getInstance().getPrinter().addText(AlignMode.RIGHT,data.getArray("textPrint").getMap(i).getString("text").substring(0,sizePhrase));
                        }else{
                            PosDigital.getInstance().getPrinter().init();
                            PosDigital.getInstance().getPrinter().setGray(data.getArray("textPrint").getMap(i).getInt("setFontGray"));
                            PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.SMALL);
                            PosDigital.getInstance().getPrinter().addText(AlignMode.RIGHT,data.getArray("textPrint").getMap(i).getString("text").substring(0,data.getArray("textPrint").getMap(i).getString("text").length()));
                        }
                    }else{
                        int sizePhrase=32;

                        if(data.getArray("textPrint").getMap(i).getString("text").length()>=sizePhrase){
                            PosDigital.getInstance().getPrinter().init();
                            PosDigital.getInstance().getPrinter().setGray(data.getArray("textPrint").getMap(i).getInt("setFontGray"));
                            PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.LARGE);
                            PosDigital.getInstance().getPrinter().addText(AlignMode.RIGHT,data.getArray("textPrint").getMap(i).getString("text").substring(0,sizePhrase));
                        }else{
                            PosDigital.getInstance().getPrinter().init();
                            PosDigital.getInstance().getPrinter().setGray(data.getArray("textPrint").getMap(i).getInt("setFontGray"));
                            PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.LARGE);
                            PosDigital.getInstance().getPrinter().addText(AlignMode.RIGHT,data.getArray("textPrint").getMap(i).getString("text").substring(0,data.getArray("textPrint").getMap(i).getString("text").length()));
                        }
                    }
                }else{
                    if(data.getArray("textPrint").getMap(i).getString("fontSize").toLowerCase().equals("small")){
                        int sizePhrase=48;
                        if(data.getArray("textPrint").getMap(i).getString("text").length()>=sizePhrase){
                            PosDigital.getInstance().getPrinter().init();
                            PosDigital.getInstance().getPrinter().setGray(data.getArray("textPrint").getMap(i).getInt("setFontGray"));
                            PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.SMALL);
                            PosDigital.getInstance().getPrinter().addText(AlignMode.CENTER,data.getArray("textPrint").getMap(i).getString("text").substring(0,sizePhrase));
                        }else{
                            PosDigital.getInstance().getPrinter().init();
                            PosDigital.getInstance().getPrinter().setGray(data.getArray("textPrint").getMap(i).getInt("setFontGray"));
                            PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.SMALL);
                            PosDigital.getInstance().getPrinter().addText(AlignMode.CENTER,data.getArray("textPrint").getMap(i).getString("text").substring(0,data.getArray("textPrint").getMap(i).getString("text").length()));
                        }
                    }else if(data.getArray("textPrint").getMap(i).getString("fontSize").toLowerCase().equals("medium")){
                        int sizePhrase=32;
                        if(data.getArray("textPrint").getMap(i).getString("text").length()>=sizePhrase){
                            PosDigital.getInstance().getPrinter().init();
                            PosDigital.getInstance().getPrinter().setGray(data.getArray("textPrint").getMap(i).getInt("setFontGray"));
                            PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.MEDIUM);
                            PosDigital.getInstance().getPrinter().addText(AlignMode.CENTER,data.getArray("textPrint").getMap(i).getString("text").substring(0,sizePhrase));
                        }else{
                            PosDigital.getInstance().getPrinter().init();
                            PosDigital.getInstance().getPrinter().setGray(data.getArray("textPrint").getMap(i).getInt("setFontGray"));
                            PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.MEDIUM);
                            PosDigital.getInstance().getPrinter().addText(AlignMode.CENTER,data.getArray("textPrint").getMap(i).getString("text").substring(0,data.getArray("textPrint").getMap(i).getString("text").length()));
                        }
                    }else{
                        int sizePhrase=32;
                        if(data.getArray("textPrint").getMap(i).getString("text").length()>=sizePhrase){
                            PosDigital.getInstance().getPrinter().init();
                            PosDigital.getInstance().getPrinter().setGray(data.getArray("textPrint").getMap(i).getInt("setFontGray"));
                            PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.LARGE);
                            PosDigital.getInstance().getPrinter().addText(AlignMode.CENTER,data.getArray("textPrint").getMap(i).getString("text").substring(0,sizePhrase));
                        }else{
                            PosDigital.getInstance().getPrinter().init();
                            PosDigital.getInstance().getPrinter().setGray(data.getArray("textPrint").getMap(i).getInt("setFontGray"));
                            PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.LARGE);
                            PosDigital.getInstance().getPrinter().addText(AlignMode.CENTER,data.getArray("textPrint").getMap(i).getString("text").substring(0,data.getArray("textPrint").getMap(i).getString("text").length()));
                        }
                    }
                }
            }
            PosDigital.getInstance().getPrinter().print(new IPrinterCallback() {
                @Override
                public void onSuccess() throws RemoteException {
                    promise.resolve("" + PrinterStatus.OK);
                }

                @Override
                public void onError(int i) throws RemoteException {
                    switch (i) {
                        case 2:
                            promise.reject("erro no módulo de impressão:" + false, "Impressora não iniciada");
                            return;
                        case 3:
                            promise.reject("erro no módulo de impressão:" + false, "Impressora superaquecida");

                            return;
                        case 4:
                            promise.reject("erro no módulo de impressão:" + false, "Fila de impressão muito grande");

                            return;
                        case 5:
                            promise.reject("erro no módulo de impressão:" + false, "Parametros incorretos");

                            return;
                        case 10:
                            promise.reject("erro no módulo de impressão:" + false, "Porta da impressora aberta");

                            return;
                        case 11:
                            promise.reject("erro no módulo de impressão:" + false, "Temperatura baixa de mais");

                            return;
                        case 12:
                            promise.reject("erro no módulo de impressão:" + false, "Sem bateria suficiente para impressão");

                            return;
                        case 13:
                            promise.reject("erro no módulo de impressão:" + false, "Motor de passo com problemas");

                            return;
                        case 15:
                            promise.reject("erro no módulo de impressão:" + false, "Sem bobina");

                            return;
                        case 16:
                            promise.reject("erro no módulo de impressão:" + false, "bobina acabando");

                            return;
                        case 17:
                            promise.reject("erro no módulo de impressão:" + false, "Bobina travada");

                            return;
                        default:
                            promise.reject("erro no módulo de impressão:" + false, "Erro não identificado");

                            return;
                    }
                }

                @Override
                public IBinder asBinder() {
                    return null;
                }

            });
            promise.resolve("Modulo de impressão teve sucesso");
            return;
        } catch (Exception e) {
            promise.reject("erro no módulo de impressão:" + false, "reject");

            return;
        }

    }*/

    @ReactMethod
    public void ledMethod(ReadableMap data, final Promise promise) {
        try {
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
                promise.resolve("Modulo de led acendeu: " + data.getString("color").toLowerCase());
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
                promise.resolve("Modulo de led apagou: " + data.getString("color").toLowerCase());
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
            PosDigital.getInstance().getBeeper().nfc();
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
            promise.resolve("Modulo de led apagou: " + data.getString("color").toLowerCase());
            return;
        } catch (Exception e) {
            promise.resolve("error : " + data.getString("color").toLowerCase());
            return;
        }
    }

    @ReactMethod
    public void cameraBackMethod(final Promise promise) {

        try {
            int timeout = 30;
            PosDigital.getInstance().getCamera().readBack(timeout, new ICameraCallback.Stub() {
                @Override
                public void onSuccess(String s) throws RemoteException {
                    promise.resolve(s);
                    return;
                }

                @Override
                public void onTimeout() throws RemoteException {
                    promise.resolve("Tempo ultrapassado");
                    return;
                }

                @Override
                public void onCancel() throws RemoteException {
                    promise.resolve("Opção Cancelada");
                    return;
                }

                @Override
                public void onError(String s) throws RemoteException {
                    promise.resolve("Operacao com erro:" + s);
                    return;
                }
            });
            promise.resolve("Método da camera ativado");
            return;
        } catch (Exception e) {
            promise.reject("error", "error informações da maquina");
            return;
        }
    }

    @ReactMethod
    public void infoMethod(final Promise promise) {
        try {
            PosDigital.getInstance().getInfo().info(new IInfoCallback.Stub() {
                @Override
                public void onInfo(InfoResponse infoResponse) throws RemoteException {
                    StringBuilder info = new StringBuilder().append("SDK Version:[").append(infoResponse.getSdkVersion())
                            .append("]\n").append("BC Version:[").append(infoResponse.getBcVersion()).append("]\n")
                            .append("Os Version:[").append(infoResponse.getOsVersion()).append("]\n").append("Serial Number:[")
                            .append(infoResponse.getSerialNumber()).append("]");
                    promise.resolve(info);
                    return;
                }

                @Override
                public void onError(String s) throws RemoteException {
                    promise.reject(s, "error");
                    return;
                }
            });
            promise.resolve("modulo de informações da maquina ativado");
            return;
        } catch (RemoteException e) {
            promise.reject("error", "error informações da maquina");

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
