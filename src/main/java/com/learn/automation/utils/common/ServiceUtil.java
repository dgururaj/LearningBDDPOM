package com.learn.automation.utils.common;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.json.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


public class ServiceUtil {

    List<String> value = new ArrayList<>();

    public String getResponse(String soapRequest, String endpoint) {
        HttpResponse response = null;
        String result = "";
        try {
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30 * 1000).setSocketTimeout(30 * 1000).build();
            HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
            HttpPost request = new HttpPost(endpoint);
            StringEntity params = new StringEntity(soapRequest);
            request.setEntity(params);
            response = httpClient.execute(request);
            result = EntityUtils.toString(response.getEntity());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public String getResponse(String soapRequest, String endpoint, String SOAPAction) {
        HttpResponse response = null;
        String result = "";
        try {
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30 * 1000).setSocketTimeout(30 * 1000).build();
            HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
            HttpPost request = new HttpPost(endpoint);
            request.addHeader("SOAPAction", SOAPAction);
            StringEntity params = new StringEntity(soapRequest);
            request.setEntity(params);
            response = httpClient.execute(request);
            result = EntityUtils.toString(response.getEntity());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public List<String> getJSonValue(String jSOnResponse, String keyName) {
        JsonReader jsonReader = Json.createReader(new StringReader(jSOnResponse));
        JsonObject jsonObject = jsonReader.readObject();
        value.clear();
        return navigateTree(jsonObject, "", keyName);
    }

    public List<String> navigateTree(JsonValue tree, String key, String keyName) {
        if (key != null)
            switch (tree.getValueType()) {
                case OBJECT:
                    JsonObject object = (JsonObject) tree;
                    for (String name : object.keySet()) {
                        navigateTree(object.get(name), name, keyName);
                    }
                    break;
                case ARRAY:
                    JsonArray array = (JsonArray) tree;
                    if (key.contains(keyName)) {
                        value.add(array.toString());
                    }
                    for (JsonValue val : array)
                        navigateTree(val, null, keyName);
                    break;
                case STRING:
                    JsonString st = (JsonString) tree;
                    if (key.contains(keyName)) {
                        value.add(st.getString());
                    }
                    break;
                case NUMBER:
                    JsonNumber num = (JsonNumber) tree;
                    if (key.contains(keyName)) {
                        value.add(num.toString());
                    }
                    break;
                case TRUE:
                case FALSE:
                case NULL:
                    break;
            }
        return value;
    }


    public List<String> getNodeValues(String xml, String node) {
        NodeList nodes = null;
        ArrayList<String> values = new ArrayList<>();
        Document doc = null;
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource src = new InputSource();
            src.setCharacterStream(new StringReader(xml));
            doc = builder.parse(src);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        String[] nodesMsg = node.split(",");
        for (String nodeVal : nodesMsg) {
            nodes = doc.getElementsByTagName(nodeVal);
            for (int i = 0; i < nodes.getLength(); i++) {
                values.add(nodes.item(i).getTextContent());
            }
        }
        return values;
    }


}