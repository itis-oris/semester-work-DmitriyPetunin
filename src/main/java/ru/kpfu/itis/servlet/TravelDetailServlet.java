package ru.kpfu.itis.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dao.TravelDao;
import ru.kpfu.itis.dto.ImageDto;
import ru.kpfu.itis.dto.LocationDto;
import ru.kpfu.itis.dto.TravelDto;
import ru.kpfu.itis.entity.Travel;
import ru.kpfu.itis.service.ImagesService;
import ru.kpfu.itis.service.LocationService;
import ru.kpfu.itis.service.TravelService;
import ru.kpfu.itis.util.DbException;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@WebServlet(urlPatterns = "/travel/detail")
public class TravelDetailServlet extends HttpServlet {
    private TravelService travelService;
    private ImagesService imagesService;
    private LocationService locationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        travelService = (TravelService) getServletContext().getAttribute("travelService");
        imagesService = (ImagesService) getServletContext().getAttribute("imageService");
        locationService = (LocationService) getServletContext().getAttribute("locationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(req.getParameter("id"));
            if (id == null){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Bad request. No id has been provided ");
            }
            TravelDto travel = travelService.getById(id);
            List<ImageDto> images = imagesService.getImagesByTravelId(id);
            Set<LocationDto> locations = locationService.getLocationsByTravelId(id);

            req.setAttribute("travel",travel);
            req.setAttribute("images",images);
            req.setAttribute("locations",locations);

            getServletContext().getRequestDispatcher("/WEB-INF/view/travels/detail.jsp")
                    .forward(req,resp);
        } catch (DbException e) {
            throw new ServletException(e);
        }
    }
}
