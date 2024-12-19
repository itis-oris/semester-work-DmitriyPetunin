package ru.kpfu.itis.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.ImageDto;
import ru.kpfu.itis.dto.LocationDto;
import ru.kpfu.itis.dto.TravelDto;
import ru.kpfu.itis.service.LocationService;
import ru.kpfu.itis.service.TravelService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@WebServlet("/travel/edit")
public class UpdateTravelServlet extends HttpServlet {
    private TravelService travelService;
    private LocationService locationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        travelService = (TravelService) getServletContext().getAttribute("travelService");
        locationService = (LocationService) getServletContext().getAttribute("locationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        TravelDto travelDto = travelService.getById(id);
        Set<LocationDto> locations = locationService.getLocationsByTravelId(id);

        req.setAttribute("travel",travelDto);
        req.setAttribute("locations",locations);
        req.getRequestDispatcher("/WEB-INF/view/travels/edittravel.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int travelId = Integer.parseInt(req.getParameter("id"));
        TravelDto travelDto = travelService.getById(travelId);
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String duration = req.getParameter("duration");

        List<String> info = List.of(req.getParameterValues("country[]"));
        List<Integer> ids = new ArrayList<>();
        if (!info.isEmpty()){
            for (String s : info){
                String[] strings = s.split(":");
                LocationDto locationDto = LocationDto.builder()
                        .name(strings[0])
                        .country(strings[1])
                        .build();
                ids.add(locationService.updateInfo(locationDto,travelDto.getId()));
            }
        }
        locationService.updateTravelLocation(travelDto.getId(), ids);
        travelDto.setName(name);
        travelDto.setDescription(description);
        travelDto.setDuration(duration);
        boolean isUpdated = travelService.updateInfo(travelDto);
        resp.sendRedirect("/travel/list");
    }
}
