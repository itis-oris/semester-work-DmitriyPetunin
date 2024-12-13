package ru.kpfu.itis;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.kpfu.itis.service.ImagesService;
import ru.kpfu.itis.service.LocationService;
import ru.kpfu.itis.service.TravelService;
import ru.kpfu.itis.service.UserService;
import ru.kpfu.itis.util.ConnectionProvider;
import ru.kpfu.itis.util.DbException;

@WebListener()
public class InitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            sce.getServletContext().setAttribute("travelService",new TravelService());
            sce.getServletContext().setAttribute("userService",new UserService());
            sce.getServletContext().setAttribute("imageService",new ImagesService());
            sce.getServletContext().setAttribute("locationService",new LocationService());
        } catch (DbException e) {
            new RuntimeException(e);
        }
    }
}
