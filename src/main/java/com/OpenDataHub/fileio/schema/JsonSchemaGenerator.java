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

    public static void GenerateSchemaFromClass(String path, Class jsonClass)
    {
        Logger l = LogManager.getRootLogger();
        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2019_09, OptionPreset.PLAIN_JSON);
        SchemaGeneratorConfig config = configBuilder.build();
        SchemaGenerator generator = new SchemaGenerator(config);
        JsonNode jsonSchema = generator.generateSchema(jsonClass);
        try {
            FileWriter fwr = new FileWriter(path);
            fwr.write(jsonSchema.toString());
            fwr.close();
            l.info("Successfully created schema for class: " + jsonClass.toString());

        }catch (IOException e)
        {
            l.error("Failed to create file with error: " + e.toString());
        }
    }
}
