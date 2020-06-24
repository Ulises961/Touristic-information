package com.OpenDataHub.requests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.FutureTask;

// import com.OpenDataHub.requests.RequestSetter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RequestSetterTest {

    String query = "http://tourism.opendatahub.bz.it/api/Activity?pagenumber=1&pagesize=10&activitytype=1023&seed=null";

    private static RequestMaker requestMaker;
   
    @BeforeAll
    static void setUp() {
        
        int requestedActivities = 102;

        RequestUtil.loadMissingParameters();
        RequestUtil.setActivitiesToRetrieve(requestedActivities);
    }

    @Test
    
    void setQueryTest() {

        assertEquals(query, RequestUtil.setDefaultQuery(),
                "Should return a String that reflects the query as is to be requested to the server");

    }
    
    @Test
    void calculatePagesTest() {
        // RequestUtil.setActivitiesToRetrieve(102);
        assertEquals(11, RequestUtil.TOTAL_PAGES, " Returns how many pages will be requested");
    }

    @Test
    void elementsOfLastPageTest() {
        
        assertEquals(2, RequestUtil.ELEMENT_IN_LAST_PAGE, "Returns how many elements will be fitted into the last page");
    }

    @Test
    void startThreadsTest() {
        List<FutureTask<StringBuilder>> list = RequestMaker.startThreadsMakingRequests();
        assertEquals(LinkedList.class, list.getClass(), "Returns a LinkedList");
    }

    

    
}