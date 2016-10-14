# sslsocket
使用Java自带的keytool命令，在命令行生成
（1）、生成服务器端私钥kserver.keystore文件
keytool -genkey -alias serverkey -validity 1 -keystore kserver.keystore
（2）、根据私钥、导出服务器端安全证书
keytool -export -alias serverkey -keystore kserver.keystore -file server.crt
（3）、将服务器端证书、导入到客户端的TrustKeyStore中
keytool -import -alias serverkey -file server.crt -keystore tclient.keystore
（4）、生成客户端私钥kclient.keystore文件
keytool -genkey -alias clientkey -validity 1 -keystore kclient.keystore
（5）、根据私钥，导出客户端安全证书
keytool -export -alias clientkey -keystore kclient.keystore -file client.crt
（6）、将客户端证书，导入到服务器端的TrustKeyStore中
keytool -import -alias clientkey -file client.crt -keystore tserver.keystore

生成的文件分成两组，服务端保存：kserver.keystore tserver.keystore 客户端保存：kclient.keystore tclient.keystore