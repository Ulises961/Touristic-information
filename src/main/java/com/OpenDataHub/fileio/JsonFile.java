package com.OpenDataHub.fileio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonFile extends File {

    
    public String json;

    public JsonNode root;


    public JsonFile(String pathname, String jsonData) throws JsonProcessingException {
        super(pathname);
        json = jsonData;
        CreateRootFromString();
    }

    public JsonFile(String pathname, JsonNode node) {
        super(pathname);
        root = node;
    }



    public void Save() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(this, root);
    }


    public static JsonFile Open(String path) throws IOException {
        ObjectMapper omapper = new ObjectMapper();
        byte[] data = Files.readAllBytes(Paths.get(path));
        JsonNode jnode = omapper.readTree(data);
        return new JsonFile(path,jnode);
    }


    private void CreateRootFromString() throws JsonProcessingException {
        if(json != null) {
            root = new ObjectMapper().readTree(json);
        }
    }


    public JsonNode getRoot() {
        return root;
    }

    public void setRoot(JsonNode root) {
        this.root = root;
    }
    
    // @JsonGetter("json")
    // public String getJson() {
    //     return this.json;
    // }

}