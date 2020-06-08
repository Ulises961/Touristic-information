package com.OpenDataHub;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import com.OpenDataHub.requests.Loader;
import com.OpenDataHub.requests.Retriever;


public class RetrieverTest {

	Retriever o = new Retriever();

    @Test
	public void instanceOfRetrieverTest() {

		assertEquals(Retriever.class, o.getClass());

	}
	
	@Test
	public void equalitybyreferenceTest() {

		Retriever tester = o;

		assertEquals(o, tester, "Both references should point to the same object");
	}
	
}