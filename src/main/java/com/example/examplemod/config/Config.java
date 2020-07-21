package com.example.examplemod.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.HashMap;

public class Config {
    @Getter @Setter private boolean enabled = true;
    @Getter @Setter private boolean seconds = true;
    private File configFile;
    private HashMap<String, String> settings;
    @Getter @Setter private String color;

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
            this.seconds = config.has("enabled") && config.get("enabled").getAsBoolean();
            JsonObject settings = config.has("settings") ? config.getAsJsonObject("settings").getAsJsonObject() : new JsonObject();
            this.settings = new Gson().fromJson(settings.toString(), HashMap.class);
            this.color = config.has("color") ? config.get("color").getAsString() : "GRAY";
        } catch (IOException ignored) {

        }
    }

    private void packConfig() {
        JsonObject master = new JsonObject();
        master.addProperty("enabled", true);
        master.addProperty("seconds", true);
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
        master.addProperty("seconds", seconds);
        JsonObject settings = gson.toJsonTree(this.settings).getAsJsonObject();
        master.add("settings", settings);
        master.addProperty("color", color);
        saveFile(master);
    }

    public HashMap<String, String> getSettings() {
        return settings;
    }
}
