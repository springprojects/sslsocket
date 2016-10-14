package com.exampledata.sslsocket.server;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * Created by eva on 2016/10/14.
 */
public class Application {

    private static String kpath = "D:\\eva\\keytool\\kserver.keystore";
    private static String tpath = "D:\\eva\\keytool\\tserver.keystore";
    private static char[] password = "123456".toCharArray();

    public static void main(String[] args) {
        boolean flag = true;
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
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        SSLServerSocketFactory ssf = context.getServerSocketFactory();
        try {
            SSLServerSocket ss = (SSLServerSocket)ssf.createServerSocket(8000);
            //ss.setNeedClientAuth(true);
            //ss.setUseClientMode(true);
            //ss.setWantClientAuth(true);
            System.out.println("等待客户端连接...");
            while (flag) {
                Socket s = ss.accept();
                System.out.println("接收到客户端连接...");
                ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
                os.writeObject("echo:Hello");
                os.flush();
                os.close();
                System.out.println("发送响应到客户端");
                s.close();
            }
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
