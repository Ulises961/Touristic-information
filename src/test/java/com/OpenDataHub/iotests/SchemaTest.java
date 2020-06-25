package com.OpenDataHub.iotests;


import com.OpenDataHub.fileio.schema.JsonSchemaGenerator;
import com.OpenDataHub.helper.FileLoader;
import com.OpenDataHub.parser.support_classes.ActivityDescription;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Schema Test class")
public class SchemaTest {




    @DisplayName("Creating schema and checking if same with pregenerated")
    @Test
    void SchemaCreationAndValidation() throws IOException {
        JsonSchemaGenerator.GenerateSchemaFromClass(FileLoader.LoadableFiles.GENERATED_TEST_SCHEMA.toString(),ActivityDescription.class);

        byte[] generatedschema = FileLoader.LoadFile(FileLoader.LoadableFiles.GENERATED_TEST_SCHEMA).getBytes();
        byte[] sampleschema = FileLoader.LoadFile(FileLoader.LoadableFiles.SAMPLE_SCHEMA).getBytes();

        boolean areEquals = true;
        for(int i = 0; i < generatedschema.length; i++) {
            if(generatedschema[i] != sampleschema[i])
                areEquals = false;
        }

        assertEquals(areEquals, true);

    }
}
