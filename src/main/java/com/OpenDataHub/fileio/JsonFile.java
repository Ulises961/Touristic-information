package com.OpenDataHub.fileio;

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

    /**
     * Saves this object as a file to the filesystem
     * @param directory the directory in which save the file
     * @throws IOException
     */

    public void Save(String directory) throws IOException {

        setFullFileName(directory);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fullFileName), jsonObject);
    }

    /**
     * Returns a new JsonFile object
     * @param path the path to the file
     * @param type the class which the json file gets mapped to
     * @param <E> {@link FileWritable} type
     * @return
     * @throws IOException if there is a problem reading the file
     */
    public static <E extends FileWritable> JsonFile Open(String path, Class<E> type) throws IOException {

        ObjectMapper omapper = new ObjectMapper();
        byte[] data = Files.readAllBytes(Paths.get(path));

        E jObject = omapper.readValue(data, type);
        return new JsonFile(jObject);
    }

    /**
     * filename without extension
     * @return
     */
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

    
    public FileWritable getFileWritable() {
        return jsonObject;
    }

    public void setFileWritable(FileWritable jsonObject) {
        this.jsonObject = jsonObject;
    }


}
