package com.example.spotifyfestival.UIPackage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {
    private static final String SETTINGS_FILE = "settings.properties";

    private Properties properties;

    public Settings() {
        properties = new Properties();
        loadSettings();
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        saveSettings();
    }

    private void loadSettings() {
        try (FileInputStream input = new FileInputStream(SETTINGS_FILE)) {
            properties.load(input);
        } catch (IOException e) {
            // Handle the exception appropriately, e.g., log it
            e.printStackTrace();
        }
    }

    private void saveSettings() {
        try (FileOutputStream output = new FileOutputStream(SETTINGS_FILE)) {
            properties.store(output, null);
        } catch (IOException e) {
            // Handle the exception appropriately, e.g., log it
            e.printStackTrace();
        }
    }
}
