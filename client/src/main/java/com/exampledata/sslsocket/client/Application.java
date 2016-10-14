package com.exampledata.sslsocket.client;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.UnknownHostException;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * Created by eva on 2016/10/14.
 */
public class Application {

    private static String kpath = "D:\\eva\\keytool\\kclient.keystore";
    private static String tpath = "D:\\eva\\keytool\\tclient.keystore";
    private static char[] password = "123456".toCharArray();

    public static void main(String[] args) {
        SSLContext context = null;
        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(kpath), password);
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, password);
            KeyManager[] km = kmf.getKeyManagers();
            KeyStore ts = KeyStore.getInstance("JKS");
            ts.load(new FileInputStream(tpath), password);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ts);
            TrustManager[] tm = tmf.getTrustManagers();
            context = SSLContext.getInstance("SSL");
            context.init(km, tm, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        SSLSocketFactory ssf = context.getSocketFactory();
        try {
            SSLSocket ss = (SSLSocket) ssf.createSocket("localhost", 8000);
            //ss.setNeedClientAuth(true);
            //ss.setUseClientMode(true);
            //ss.setWantClientAuth(true);
            System.out.println("客户端就绪...");
            ObjectInputStream br = new ObjectInputStream(ss.getInputStream());
            try {
                System.out.println(br.readObject());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            br.close();
            ss.close();
            System.out.println("客户端测试成功");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
