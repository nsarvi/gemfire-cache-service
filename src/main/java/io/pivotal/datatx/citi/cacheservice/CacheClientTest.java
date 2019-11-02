package io.pivotal.datatx.citi.cacheservice;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.client.ClientRegionShortcut;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CacheClientTest {

    private String locatorHost = "ec2-34-209-143-245.us-west-2.compute.amazonaws.com";
    private int locatorPort =  10334;
    private ClientCache clientCache;
    private Region<String, String> ssoRegion;
    static String keystoreLocation = "/tmp/gemfire-keystore.jks";
    static String truststoreLocation = "/tmp/gemfire-truststore.jks";
    static String keystoreURL = "https://github.com/nsarvi/gemfire-cache-service/raw/master/src/main/resources/gemfire-keystore.jks";
    static String truststoreURL = "https://github.com/nsarvi/gemfire-cache-service/raw/master/src/main/resources/gemfire-truststore.jks";

    private boolean ssl=true;

    public static void main(String[] args) throws Exception {
        CacheClientTest test=new CacheClientTest();
        test.download(keystoreURL, keystoreLocation);
        test.download(truststoreURL, truststoreLocation);
        test.connectoGemfire();
    }

    public void connectoGemfire(){
        Properties props = new Properties();
        System.out.println("Started to connect .....");
        try {
            ClientCacheFactory ccf = new ClientCacheFactory(props);
            // SSL
            if (ssl) {
                ccf.set("server-ssl-enabled", "true");
                ccf.set("server-ssl-keystore-type", "jks");
                ccf.set("server-ssl-keystore", keystoreLocation);
                ccf.set("server-ssl-keystore-password", "changeit");
                ccf.set("server-ssl-truststore", truststoreLocation);
                ccf.set("server-ssl-truststore-password", "changeit");
            }
            ccf.set("name", "CacheClientTest");
            ccf.set("log-level", "trace");
            ccf.setPoolSubscriptionEnabled(true);
            clientCache = ccf.addPoolLocator(locatorHost, locatorPort).create();
            clientCache = ccf.create();
            ssoRegion = clientCache.<String, String>createClientRegionFactory(ClientRegionShortcut.PROXY).create("SSO");
            ssoRegion.put("4", "ssl val4");
            System.out.println("---- > ");
        } catch (Exception e) {
            System.out.println("Exception  .....");
                e.printStackTrace();
                clientCache.close(true);
            }

    }

    public void download(String remoteUrl, String localFilePath) throws Exception {

        URL url  = new URL( remoteUrl );
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        Map< String, List< String >> header = http.getHeaderFields();
        while( isRedirected( header )) {
            remoteUrl = header.get( "Location" ).get( 0 );
            url    = new URL( remoteUrl );
            http   = (HttpURLConnection)url.openConnection();
            header = http.getHeaderFields();
        }
        InputStream input  = http.getInputStream();
        byte[]       buffer = new byte[4096];
        int          n      = -1;
        OutputStream output = new FileOutputStream( new File(localFilePath));
        while ((n = input.read(buffer)) != -1) {
            output.write( buffer, 0, n );
        }
        output.close();
    }

    private static boolean isRedirected( Map<String, List<String>> header ) {
        for( String hv : header.get( null )) {
            if(   hv.contains( " 301 " )
                    || hv.contains( " 302 " )) return true;
        }
        return false;
    }
}
