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

    private boolean ssl=true;

    public static void main(String[] args){
        CacheClientTest test=new CacheClientTest();
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
                ccf.set("server-ssl-keystore", "/Users/niranjansarvi/Documents/workspace-gemfire/citi-cache-service-gemfire/src/main/resources/gemfire-keystore.jks");
                ccf.set("server-ssl-keystore-password", "changeit");
                ccf.set("server-ssl-truststore", "/Users/niranjansarvi/Documents/workspace-gemfire/citi-cache-service-gemfire/src/main/resources/gemfire-truststore.jks");
                ccf.set("server-ssl-truststore-password", "changeit");
            }
            ccf.set("name", "CacheClientTest");
            ccf.setPoolSubscriptionEnabled(true);
            clientCache = ccf.addPoolLocator(locatorHost, locatorPort).create();
            clientCache = ccf.create();
            ssoRegion = clientCache.<String, String>createClientRegionFactory(ClientRegionShortcut.PROXY).create("SSO");
            ssoRegion.put("3", "ssl val3");
            System.out.println("---- > ");
        } catch (Exception e) {
            System.out.println("Exception  .....");
                e.printStackTrace();
                clientCache.close(true);
            }

    }

    public void download( String[] args ) throws Throwable
    {
        String link =
                "http://github.com/downloads/TheHolyWaffle/ChampionHelper/" +
                        "ChampionHelper-4.jar";
        String            fileName = "ChampionHelper-4.jar";
        URL url  = new URL( link );
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        Map< String, List< String >> header = http.getHeaderFields();
        while( isRedirected( header )) {
            link = header.get( "Location" ).get( 0 );
            url    = new URL( link );
            http   = (HttpURLConnection)url.openConnection();
            header = http.getHeaderFields();
        }
        InputStream input  = http.getInputStream();
        byte[]       buffer = new byte[4096];
        int          n      = -1;
        OutputStream output = new FileOutputStream( new File( fileName ));
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
