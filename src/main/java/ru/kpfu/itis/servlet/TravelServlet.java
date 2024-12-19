package ru.kpfu.itis.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.ImageDto;
import ru.kpfu.itis.dto.TravelDto;
import ru.kpfu.itis.dto.UserDto;
import ru.kpfu.itis.entity.Image;
import ru.kpfu.itis.service.ImagesService;
import ru.kpfu.itis.service.LocationService;
import ru.kpfu.itis.service.TravelService;
import ru.kpfu.itis.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "travelServlet",urlPatterns = "/travel/list")
public class TravelServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(TravelServlet.class.getName());
    private TravelService travelService;
    private LocationService locationService;
    private ImagesService imagesService;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        travelService = (TravelService) getServletContext().getAttribute("travelService");
        locationService = (LocationService) getServletContext().getAttribute("locationService");
        imagesService = (ImagesService) getServletContext().getAttribute("imageService");
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("doGet method called");
        String locationId = req.getParameter("locationId");
        String userId = req.getParameter("userId");
        Integer idLocation = parseInt(locationId);
        Integer idUser = parseInt(userId);

        if (locationId != null){
            req.setAttribute("location",locationService.getById(idLocation));
            if (userId != null){
                List<TravelDto> travelDtos = travelService.getTravelsByLocationIdAndUserId(idLocation,idUser);
                req.setAttribute("travels",travelDtos);
                req.setAttribute("images",imagesService.getFirstImageUrlList(travelDtos));
            } else {
                List<TravelDto> travelDtos = travelService.getTravelsByLocationId(idLocation);
                req.setAttribute("travels",travelDtos);
                req.setAttribute("images",imagesService.getFirstImageUrlList(travelDtos));
            }
        } else if (userId != null) {
            List<TravelDto> travelDtos = travelService.getTravelsByUserId(idUser);
            req.setAttribute("travels", travelDtos);
            req.setAttribute("images",imagesService.getFirstImageUrlList(travelDtos));
        } else {
            List<TravelDto> travelDtos = travelService.getAllTravels();
            req.setAttribute("travels", travelDtos);
            req.setAttribute("images",imagesService.getFirstImageUrlList(travelDtos));
        }
        UserDto userDto = userService.getCurrentUserDto(req);

        req.setAttribute("user", userDto);
        getServletContext().getRequestDispatcher("/WEB-INF/view/travels/list.jsp")
                .forward(req,resp);
    }
    private Integer parseInt(String param) {
        return (param != null) ? Integer.parseInt(param) : null;
    }
}
