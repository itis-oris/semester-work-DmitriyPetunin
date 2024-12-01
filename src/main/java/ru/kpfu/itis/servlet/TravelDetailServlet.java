package ru.kpfu.itis.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dao.TravelDao;
import ru.kpfu.itis.dto.TravelDto;
import ru.kpfu.itis.entity.Travel;
import ru.kpfu.itis.service.TravelService;
import ru.kpfu.itis.util.DbException;

import java.io.IOException;
@WebServlet(urlPatterns = "/travel/detail")
public class TravelDetailServlet extends HttpServlet {

    private TravelService travelService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        travelService = (TravelService) getServletContext().getAttribute("travelService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String id = req.getParameter("id");
            if (id == null){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Bad request. No id has been provided ");
            }
            TravelDto travel = travelService.getDetail(id);
            System.out.println(travel.getId());
            if (travel == null){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                getServletContext().getRequestDispatcher("/WEB-INF/view/errors/notfound.jsp")
                        .forward(req,resp);
            }
            req.setAttribute("travel",travel);
            getServletContext().getRequestDispatcher("/WEB-INF/view/travels/detail.jsp")
                    .forward(req,resp);
        } catch (DbException e) {
            throw new ServletException(e);
        }
    }
}
