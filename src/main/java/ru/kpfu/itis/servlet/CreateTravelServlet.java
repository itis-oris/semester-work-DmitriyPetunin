package ru.kpfu.itis.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ru.kpfu.itis.dto.ImageDto;
import ru.kpfu.itis.dto.LocationDto;
import ru.kpfu.itis.dto.TravelDto;
import ru.kpfu.itis.service.ImagesService;
import ru.kpfu.itis.service.LocationService;
import ru.kpfu.itis.service.TravelService;
import ru.kpfu.itis.service.UserService;
import ru.kpfu.itis.util.CloudinaryUtil;

import java.io.*;
import java.util.*;

@WebServlet("/createtravel")
@MultipartConfig(
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024
)
public class CreateTravelServlet extends HttpServlet {
    private TravelService travelService;
    private UserService userService;
    private ImagesService imageService;
    private LocationService locationService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        travelService = (TravelService) getServletContext().getAttribute("travelService");
        userService = (UserService) getServletContext().getAttribute("userService");
        imageService = (ImagesService) getServletContext().getAttribute("imageService");
        locationService = (LocationService) getServletContext().getAttribute("locationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/travels/newtravel.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("tripName");
        String description = req.getParameter("description");
        String duration = req.getParameter("duration");

        String[] info = req.getParameterValues("country[]");
        List<Integer> locationIds = new ArrayList<>();

        Collection<Part> parts = req.getParts();
        try {
            //List<String> urls = CloudinaryUtil.upload(parts);
            for (String s : info){
                String[] strings = s.split(":");
                LocationDto locationDto = LocationDto.builder()
                        .name(strings[0])
                        .country(strings[1])
                        .build();
                Integer locationId = locationService.save(locationDto);
                locationIds.add(locationId);
            }
            TravelDto travelDto = TravelDto.builder()
                    .name(name)
                    .description(description)
                    .duration(duration)
                    .author(userService.getCurrentUserTravelDto(req))
                    .build();
            Integer travelId = travelService.save(travelDto);
//            imageService.saveAllImages(urls.stream().map(url -> ImageDto.builder()
//                            .travel_id(travelId)
//                            .image_url(url)
//                            .build())
//                    .toList());
            for (Integer locationId : locationIds) {
                locationService.addLocationToTravel(travelId, locationId);
            }
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            resp.sendRedirect("/createtravel");
            return;
        }
        resp.sendRedirect("/profile");
    }
}
