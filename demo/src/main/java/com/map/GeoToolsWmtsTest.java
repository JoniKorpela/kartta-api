package com.map;

import java.net.*;
import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

import org.geotools.ows.wmts.*;
import org.geotools.ows.wmts.model.*;
import org.geotools.ows.wmts.request.*;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class GeoToolsWmtsTest
{

  public static void main(String[] args) throws Exception
  {
    HttpClient client = HttpClient.newHttpClient();
    Map<String, String> headers = new HashMap<>();
    headers.put("apikey", "184ebfa1-17ab-4d4d-b9ff-7808ca122246");

    URL url = new URI("https://avoin-karttakuva.maanmittauslaitos.fi/avoin/wmts/1.0.0/WMTSCapabilities.xml").toURL();

    WebMapTileServer wmts = new WebMapTileServer(url, client, headers);

    WMTSCapabilities capabilities = wmts.getCapabilities();
    WMTSLayer layer = capabilities.getLayer("de_basemapde_web_raster_hillshade");
    TileMatrixSet matrixSet = capabilities.getMatrixSet("GLOBAL_WEBMERCATOR");
    
    getTileRequest(wmts, layer, matrixSet, true);
    getTileRequest(wmts, layer, matrixSet, false);
  }

  private static void getTileRequest(WebMapTileServer wmts, WMTSLayer layer, TileMatrixSet matrixSet, boolean multi)
  {
    GetTileRequest request = wmts.createGetTileRequest(multi);
  
    request.setLayer(layer);
    request.setFormat(layer.getFormats().get(0));
    request.setTileMatrixSet(matrixSet.getIdentifier());
    request.setTileMatrix("11");
    request.setTileRow(693);
    request.setTileCol(1094);
    System.out.println(request.getFinalURL());
    //System.out.println("Expected (from QGis): https://sgx.geodatenzentrum.de/wmts_basemapde_schummerung/tile/1.0.0/de_basemapde_web_raster_hillshade/default/GLOBAL_WEBMERCATOR/11/693/1084.png");
    
  }


}
