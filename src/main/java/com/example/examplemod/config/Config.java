package com.example.examplemod.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.HashMap;

public class Config {
    private boolean enabled = true;
    private File configFile;
    private HashMap<String, String> settings;
    private String color;

    public Config(File configFile) {
        System.out.println("USING FILE " + configFile.getName());
        this.configFile = configFile;
        settings = new HashMap<String, String>();
        reloadConfig(false);
    }

    public void reloadConfig(boolean save) {
        if (save) {
            saveConfig();
            settings.clear();
        }
        try {
            if (!configFile.exists())
                packConfig();
            FileReader fr = new FileReader(configFile);
            BufferedReader br = new BufferedReader(fr);
            StringBuilder builder = new StringBuilder();
            String l;
            while ((l = br.readLine()) != null) {
                builder.append(l);
            }
            JsonObject config = new JsonParser().parse(builder.toString()).getAsJsonObject();
            System.out.println("Config: " + config);
            this.enabled = config.has("enabled") && config.get("enabled").getAsBoolean();
            JsonObject settings = config.has("settings") ? config.getAsJsonObject("settings").getAsJsonObject() : new JsonObject();
            this.settings = new Gson().fromJson(settings.toString(), HashMap.class);
            this.color = config.has("color") ? config.get("color").getAsString() : "GRAY";
        } catch (IOException e) {

        }
    }

    private void packConfig() {
        JsonObject master = new JsonObject();
        master.addProperty("enabled", true);
        JsonObject settings = new JsonObject();
        settings.addProperty("prefix", "[");
        settings.addProperty("separator", ":");
        settings.addProperty("suffix", "] ");
        master.add("settings", settings);
        master.addProperty("color", "GRAY");
        saveFile(master);
    }

    private void saveFile(JsonObject cont) {
        try {
            FileWriter fw = new FileWriter(configFile);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(cont.toString());
            bw.close();
            fw.close();
        } catch (IOException e) {

        }
    }

    public void saveConfig() {
        Gson gson = new Gson();
        JsonObject master = new JsonObject();
        master.addProperty("enabled", true);
        JsonObject settings = gson.toJsonTree(this.settings).getAsJsonObject();
        master.add("settings", settings);
        master.addProperty("color", color);
        saveFile(master);
    }

    public HashMap<String, String> getSettings() {
        return settings;
    }

    public String getColor() { return color; }

    public void setColor(String val) { color = val; }

    public boolean getEnabled() { return enabled; }

    public void setEnabled(boolean val) { enabled = val; }
}
