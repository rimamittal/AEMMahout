package com.aem.mahout.core.servlets;

import com.aem.mahout.core.services.RecommenderService;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONObject;

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
        if(req.getRequestParameterList().size() != 0) {
            String userId = req.getParameter("userId");
            String recommendationsCount = req.getParameter("recommendationsCount");
            if(!"".equals(userId) && !"".equals(recommendationsCount) && recommendationsCount.matches("[0-9]+")) {
                //Setting the content type for response
                resp.setContentType("application/json");
                //Fetching the recommendations using recommender service
                JSONArray result =  recommenderService.getUserBasedRecommendations(req.getResourceResolver(), userId,
                        Integer.parseInt(recommendationsCount));
                resp.getWriter().write(result.toString());
                //Setting an error in response to handle in AJAX requests
                resp.setStatus(SlingHttpServletResponse.SC_OK);
            } else {
                resp.getWriter().write("Please check the query parameters !!!");
                //Setting an error in response to handle in AJAX requests
                resp.setStatus(SlingHttpServletResponse.SC_FORBIDDEN);
            }
        } else {
            resp.getWriter().write("Please pass the required query parameters !!!");
            //Setting an error in response to handle in AJAX requests
            resp.setStatus(SlingHttpServletResponse.SC_FORBIDDEN);
        }
    }
}
