package com.OpenDataHub;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.OpenDataHub.requests.Loader;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


/**
 * Unit test for simple App.
 */
public class LoaderTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Nested
    public class LoaderClassTest {
        
        @Test
        public void ioExceptionTest() {

            assertDoesNotThrow(()-> Loader.retrieveInput(), "Test fails when the file does not exist or the path to file is incorrect. It DOES throw exception when the provided path is incorrect");
        }
        
        @Test
        
        public void inputIsString() {

            try {
                assertEquals(String.class, Loader.retrieveInput().getClass(), "is string");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        @Test
        public void NumberFormatExceptionString() {

            assertThrows(NumberFormatException.class, () -> Loader.validateInput("Not a number"),"throws exception if the input is a string");
        }

        @Test
        public void NumberFormatException_IntWithSpace() {
            assertThrows(NumberFormatException.class, () -> Loader.validateInput("1 1"),
                    "throws exception if the input are numbers separated by space");
        }
        
        @Test
        public void NumberFormatException_Space() {
            assertThrows(NumberFormatException.class, () -> Loader.validateInput("1 1"),"throws exception if the input is a string");
        }
        
        @Test
        public void NumberFormatException_negativeInput() {

            assertThrows(NumberFormatException.class, () -> Loader.validateInput("-1"),
                    "throws exception if the input is a negative value");
        }
        
        @Test
        public void returnsInt() {
            assertEquals(1, Loader.validateInput("1"), "Parses correctly an integer number from the txt file and returns it");
        }
        

    }


}
