package com.OpenDataHub.requests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.FutureTask;

// import com.OpenDataHub.requests.RequestSetter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RequestSetterTest {

    String query = "http://tourism.opendatahub.bz.it/api/Activity?pagenumber=0&pagesize=10&activitytype=1023&seed=1";

    private static RequestMaker requestMaker;
   
    @BeforeAll
    static void setUp() {
        
        int requestedActivities = 102;

        RequestUtil.loadMissingParameters();
        RequestUtil.setActivitiesToRetrieve(requestedActivities);
    }

    @Test
    
    void setQueryTest() {

        assertEquals(query, RequestUtil.setDefaultQuesry(),
                "Should return a String that reflects the query as is to be requested to the server");

    }
    
    @Test
    void calculatePagesTest() {
        assertEquals(11, r.calculateTotalPages(), " Returns how many pages will be requested");
    }

    @Test
    void elementsOfLastPageTest() {
        
        assertEquals(2, r.calculateElementsOfLastPage(), "Returns how many elements will be fitted into the last page");
    }

    @Test
    void startThreadsTest() {
        List<FutureTask<StringBuilder>> list = r.startThreads();
        assertEquals(LinkedList.class, list.getClass(), "Returns a LinkedList");
    }

    

    
}