package ru.kpfu.itis;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.kpfu.itis.service.*;
import ru.kpfu.itis.exception.DbException;

@WebListener()
public class InitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            sce.getServletContext().setAttribute("travelService",new TravelService());
            sce.getServletContext().setAttribute("userService",new UserService());
            sce.getServletContext().setAttribute("imageService",new ImagesService());
            sce.getServletContext().setAttribute("locationService",new LocationService());
            sce.getServletContext().setAttribute("cloudinaryService",new CloudinaryService());
        } catch (DbException e) {
            new RuntimeException(e);
        }
    }
}
