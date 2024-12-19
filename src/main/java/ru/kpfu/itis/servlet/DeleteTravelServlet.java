package ru.kpfu.itis.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.service.TravelService;

import java.io.IOException;

@WebServlet("/delete-travel")
public class DeleteTravelServlet extends HttpServlet {
    private TravelService travelService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        travelService = (TravelService) getServletContext().getAttribute("travelService");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String travelId = req.getParameter("id");
        travelService.delete(Integer.parseInt(travelId));
        resp.sendRedirect("/profile");
    }
}
