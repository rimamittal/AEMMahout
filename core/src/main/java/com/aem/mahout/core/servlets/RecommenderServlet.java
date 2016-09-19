package com.aem.mahout.core.servlets;

import com.aem.mahout.core.services.RecommenderService;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONArray;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;


@SlingServlet(paths = "/bin/recommend/mahout", label = "AEM-Mahout Recommender Servlet", extensions = {"json"})
public class RecommenderServlet extends SlingAllMethodsServlet {

    @Reference
    private RecommenderService recommenderService;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
                         final SlingHttpServletResponse resp) throws ServletException, IOException {
        //Setting the content type for response
        resp.setContentType("application/json");
        //Fetching the recommendations using recommender service
        JSONArray result =  recommenderService.showRecommendations(req.getResourceResolver(), "jason.werner@dodgit.com", 3);
        resp.getWriter().write(result.toString());

    }


}
