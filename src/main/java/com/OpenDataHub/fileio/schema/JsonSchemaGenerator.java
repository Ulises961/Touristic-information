package com.OpenDataHub.fileio.schema;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.victools.jsonschema.generator.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



public class JsonSchemaGenerator {

    /**
     * Generates a JsonSchema Draft 2019/09 and saves it to file
     * @param path filepath where the .json gets saved
     * @param jsonClass the class which the schema is generated from
     */
    public static void GenerateSchemaFromClassSaveToFile(String path, Class jsonClass)
    {
        Logger l = LogManager.getRootLogger();
        String schema = GenerateSchemaFromClass(jsonClass);
        try {
            FileWriter fwr = new FileWriter(path);
            fwr.write(schema);
            fwr.close();
            l.info("Successfully created schema for class: " + jsonClass.toString());

        }catch (IOException e)
        {
            l.error("Failed to create file with error: " + e.toString());
        }
    }

    /**
     * Generates a JsonSchema Draft 2019/09 as String from a Class
     * @param jsonClass
     * @return
     */
    public static String GenerateSchemaFromClass(Class jsonClass)
    {
        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2019_09, OptionPreset.PLAIN_JSON);
        SchemaGeneratorConfig config = configBuilder.build();
        SchemaGenerator generator = new SchemaGenerator(config);
        JsonNode jsonSchema = generator.generateSchema(jsonClass);
        return jsonSchema.toString();
    }


}
