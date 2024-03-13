package com.map;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

import org.geotools.http.HTTPClient;
import org.geotools.http.HTTPClientFinder;
import org.geotools.ows.ServiceException;
import org.geotools.ows.wmts.*;
import org.geotools.ows.wmts.model.*;
import org.geotools.ows.wmts.request.*;
import org.xml.sax.SAXException;

public class GeoToolsWmtsTest
{

  public static void main(String[] args) throws Exception
  {

    String urlString = "https://avoin-karttakuva.maanmittauslaitos.fi/avoin/wmts/1.0.0/WMTSCapabilities.xml?api-key=184ebfa1-17ab-4d4d-b9ff-7808ca122246";
    String apiKey = "184ebfa1-17ab-4d4d-b9ff-7808ca122246";

    /* 
    HTTPClient client = HTTPClientFinder.createClient();
    Map<String, String> headers = new HashMap<>();
    headers.put("username", apiKey);
    headers.put("password", "");
    headers.put("User-Agent", "Mozilla/5.0");
    */

    URL url = new URI("iws.erdas.com/ImageX/ecw_wmts.dll?service=wmts&request=getcapabilities").toURL();
    
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
    System.out.println("");
  }
}
