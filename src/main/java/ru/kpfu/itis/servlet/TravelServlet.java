package ru.kpfu.itis.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dao.TravelDao;
import ru.kpfu.itis.service.LocationService;
import ru.kpfu.itis.service.TravelService;
import ru.kpfu.itis.util.DbException;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "travelServlet",urlPatterns = "/travel/list")
public class TravelServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(TravelServlet.class.getName());
    private TravelService travelService;
    private LocationService locationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        travelService = (TravelService) getServletContext().getAttribute("travelService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("doGet method called");
        try {
            req.setAttribute("travelCount",travelService.getCount());
            req.setAttribute("travels",travelService.getAllTravels());
        } catch (DbException e) {
            throw new ServletException(e);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/view/travels/list.jsp")
                .forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
