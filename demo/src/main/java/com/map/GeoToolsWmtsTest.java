package com.map;

import java.net.*;
import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

import org.geotools.http.HTTPClient;
import org.geotools.http.HTTPClientFinder;
import org.geotools.ows.wmts.*;
import org.geotools.ows.wmts.model.*;
import org.geotools.ows.wmts.request.*;

public class GeoToolsWmtsTest
{

  public static void main(String[] args) throws Exception
  {

    String apiKey = "184ebfa1-17ab-4d4d-b9ff-7808ca122246";

    /* 
    HTTPClient client = HTTPClientFinder.createClient();
    Map<String, String> headers = new HashMap<>();
    headers.put("username", apiKey);
    headers.put("password", "");
    headers.put("User-Agent", "Mozilla/5.0");
    */

    URL url = new URI("https://avoin-karttakuva.maanmittauslaitos.fi/avoin/wmts/1.0.0/WMTSCapabilities.xml?api-key=184ebfa1-17ab-4d4d-b9ff-7808ca122246").toURL();
    
    //KAATUU TÄHÄN
    WebMapTileServer wmts = new WebMapTileServer(url);  // (url, client, headers)
    
    WMTSCapabilities capabilities = wmts.getCapabilities();
    WMTSLayer layer = capabilities.getLayer("Ortokuva");
    TileMatrixSet matrixSet = capabilities.getMatrixSet("WGS84_Pseudo-Mercator");

    /*String serverName = capabilities.getService().getName();
    String serverTitle = capabilities.getService().getTitle();
    System.out.println("Capabilities retrieved from server: " + serverName + " (" + serverTitle + ")"); */
    
    getTileRequest(wmts, layer, matrixSet, true);
    getTileRequest(wmts, layer, matrixSet, false);
  }

  private static void getTileRequest(WebMapTileServer wmts, WMTSLayer layer, TileMatrixSet matrixSet, boolean multi)
  {
    GetTileRequest request = wmts.createGetTileRequest(multi);
  
    request.setLayer(layer);
    request.setFormat(layer.getFormats().get(0));
    request.setTileMatrixSet(matrixSet.getIdentifier());
    request.setTileMatrix("13");
    request.setTileRow(10);
    request.setTileCol(10);
    System.out.println(request.getFinalURL());
    System.out.println("Expected (from QGis): https://sgx.geodatenzentrum.de/wmts_basemapde_schummerung/tile/1.0.0/de_basemapde_web_raster_hillshade/default/GLOBAL_WEBMERCATOR/11/693/1084.png");
    
  }


}
