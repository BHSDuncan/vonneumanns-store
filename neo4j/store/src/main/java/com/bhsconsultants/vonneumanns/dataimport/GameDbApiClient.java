package com.bhsconsultants.vonneumanns.dataimport;

import org.codehaus.jackson.map.ObjectMapper;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GameDbApiClient {

    private final String baseUrl = "http://www.giantbomb.com/api";
    private final String apiKey;
    private final String format;
    protected final ObjectMapper mapper;

    public GameDbApiClient(String apiKey, String format) {
        this.apiKey = apiKey;
        this.format = format;
        mapper = new ObjectMapper();
    }

    public Map getGame(String id) {
        return loadJsonData(id, buildGameUrl(id));
    }

    private Map loadJsonData(String id, String url) {
        try {
            //List value = mapper.readValue(new URL(url), List.class);
            Map<String, Object> value = mapper.readValue(new URL(url), Map.class);
            if (value.isEmpty() || !value.get("error").equals("OK")) return Collections.singletonMap("not_found",System.currentTimeMillis());
            return (Map) value.get("results");
        } catch (Exception e) {
            throw new RuntimeException("Failed to get data from " + url, e);
        }
    }

    private String buildGameUrl(String gameId) {
        return String.format("%s/game/%s/?api_key=%s&format=%s", baseUrl, gameId, apiKey, format);
    }

    public Map getPlatform(String id) {
        return loadJsonData(id, buildPlatformUrl(id));
    }

    private String buildPlatformUrl(String platformId) {
    	return String.format("%s/platform/%s/?api_key=%s&format=%s", baseUrl, platformId, apiKey, format);
    }
}
