package com.OpenDataHub.fileio;

import com.OpenDataHub.parser.support_classes.Activity;
import com.OpenDataHub.parser.support_classes.ActivityDescription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonFile {


    private FileWritable jsonObject;

    private String fullFileName;


    public JsonFile(FileWritable jsonObject) {

        this.jsonObject = jsonObject;
    }



    public void Save(String directory) throws IOException {

        setFullFileName(directory);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fullFileName), jsonObject);
    }


    public static <E extends FileWritable> JsonFile Open(String path, Class<E> type) throws IOException {

        ObjectMapper omapper = new ObjectMapper();
        byte[] data = Files.readAllBytes(Paths.get(path));

        E jObject = omapper.readValue(data, type);
        return new JsonFile(jObject);
    }


    public String getFileName()
    {
        return jsonObject.getFileId();
    }


    private void setFullFileName(String direc)
    {
        if(!direc.endsWith("/"))
        {
            direc += "/";
        }
        direc += jsonObject.getFileId() + ".json";
        this.fullFileName = direc;
    }

    public FileWritable getRoot() {
        return jsonObject;
    }

    public void setRoot(FileWritable jsonObject) {
        this.jsonObject = jsonObject;
    }


}
