package com.OpenDataHub.requests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.OpenDataHub.requests.Retriever;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


	public class RetrieverTest {
		static String query = "http://tourism.opendatahub.bz.it/api/Activity?pagenumber=1&pagesize=10&activitytype=1023";
	static 	Retriever o;

		@BeforeAll
		static void setUp(){
			o = new Retriever(query);

		}
		@Test
		public void instanceOfRetrieverTest() {

			assertEquals(Retriever.class, o.getClass());

		}

		@Test
		public void equalitybyreferenceTest() {

			Retriever tester = o;

			assertEquals(o, tester, "Both references should point to the same object");
		}

		

		@Test
		void makeRequestTest() {
			try {
				o.makeRequest();
			} catch (ExecutionException e1) {
				assertThrows(ExecutionException.class, () -> {
					throw e1;
				});
			} catch (InterruptedException e2) {
				assertThrows(InterruptedException.class, () -> {
					throw e2;
				});
			} catch (IOException e3) {
				assertThrows(IOException.class, () -> {
					throw e3;
				});

			}
		}

		@Test 
		void callTest() {

			try {
				o.call();
			} catch (Exception e) {
				assertThrows(Exception.class, () -> {
					throw e;
				});
			}

			
		}
	
}