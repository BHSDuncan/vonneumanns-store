package com.bhsconsultants.vonneumanns.dataimport;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GameDbLocalStorage {

    private String storagePath;
    protected ObjectMapper mapper;

    public GameDbLocalStorage(String storagePath) {
        this.storagePath = storagePath;
        ensureStorageDirectoryExists();
        mapper = new ObjectMapper();
    }

    private void ensureStorageDirectoryExists() {
        File storageDirectory = new File(storagePath);
        if (!storageDirectory.isDirectory()) {
            if (!storageDirectory.mkdirs()) {
                throw new RuntimeException("Failed to create storage dir");
            }
        }
    }

    public boolean hasGame(String GameId) {
        return fileForGame(GameId).exists();
    }

    private File fileForGame(String GameId) {
        return new File(storagePath, String.format("Game_%s.json", GameId));
    }

    public Map loadGame(String GameId) {
        File storageFile = fileForGame(GameId);
        return loadJsonValue(storageFile);
    }

    private Map loadJsonValue(File storageFile) {
        try {
            final Object value = mapper.readValue(storageFile, Object.class);
            if (value instanceof List) {
                List list = (List) value;
                if (list.isEmpty() || list.get(0).equals("Nothing found.")) return Collections.singletonMap("not_found", System.currentTimeMillis());
                return asMap(list.get(0));
            }
            return asMap(value);
        } catch (Exception e) {
            throw new GameDbException("Failed to load JSON from storage for file " + storageFile.getPath(), e);
        }
    }

    private Map asMap(Object value) {
        if (value instanceof Map) {
            return (Map) value;
        }
        final String typeInformation = value == null ? "null" : value.getClass().getSimpleName();
        throw new GameDbException("Wrong Game data format, expected Map/JSON-Object but was "+ typeInformation);
    }

    public void storeGame(String GameId, Object GameData) {
        File storageFile = fileForGame(GameId);
        storeJsonValue(GameData, storageFile);
    }

    private void storeJsonValue(Object jsonData, File storageFile) {
        try {
            mapper.writeValue(storageFile,jsonData);
        } catch (Exception e) {
            throw new GameDbException("Failed to store JSON to storage for file " + storageFile.getPath(), e);
        }
    }

    // Company
    public boolean hasCompany(String companyId) {
        return fileForCompany(companyId).exists();
    }

    private File fileForCompany(String companyId) {
        return new File(storagePath, String.format("company_%s.json", companyId));
    }

    public Map loadCompany(String companyId) {
        File storageFile = fileForCompany(companyId);
        return loadJsonValue(storageFile);
    }

    public void storeCompany(String companyId, Object companyJson) {
        File storageFile = fileForCompany(companyId);
        storeJsonValue(companyJson, storageFile);
    }

    // Genre
    public boolean hasGenre(String genreId) {
        return fileForGenre(genreId).exists();
    }

    private File fileForGenre(String genreId) {
        return new File(storagePath, String.format("genre_%s.json", genreId));
    }

    public Map loadGenre(String genreId) {
        File storageFile = fileForGenre(genreId);
        return loadJsonValue(storageFile);
    }

    public void storeGenre(String genreId, Object genreJson) {
        File storageFile = fileForGenre(genreId);
        storeJsonValue(genreJson, storageFile);
    }

    // Platform
    public boolean hasPlatform(String platformId) {
        return fileForPlatform(platformId).exists();
    }

    private File fileForPlatform(String platformId) {
        return new File(storagePath, String.format("platform_%s.json", platformId));
    }

    public Map loadPlatform(String platformId) {
        File storageFile = fileForPlatform(platformId);
        return loadJsonValue(storageFile);
    }

    public void storePlatform(String platformId, Object platformJson) {
        File storageFile = fileForPlatform(platformId);
        storeJsonValue(platformJson, storageFile);
    }

    // Review
    public boolean hasReview(String reviewId) {
        return fileForReview(reviewId).exists();
    }

    private File fileForReview(String reviewId) {
        return new File(storagePath, String.format("review_%s.json", reviewId));
    }

    public Map loadReview(String reviewId) {
        File storageFile = fileForReview(reviewId);
        return loadJsonValue(storageFile);
    }

    public void storeReview(String reviewId, Object reviewJson) {
        File storageFile = fileForReview(reviewId);
        storeJsonValue(reviewJson, storageFile);
    }

    // Character
    public boolean hasCharacter(String characterId) {
        return fileForCharacter(characterId).exists();
    }

    private File fileForCharacter(String characterId) {
        return new File(storagePath, String.format("character_%s.json", characterId));
    }

    public Map loadCharacter(String characterId) {
        File storageFile = fileForCharacter(characterId);
        return loadJsonValue(storageFile);
    }

    public void storeCharacter(String characterId, Object characterJson) {
        File storageFile = fileForCharacter(characterId);
        storeJsonValue(characterJson, storageFile);
    }

}
