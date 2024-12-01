package ru.kpfu.itis;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.kpfu.itis.dao.TravelDao;
import ru.kpfu.itis.service.TravelService;
import ru.kpfu.itis.service.UserService;
import ru.kpfu.itis.util.ConnectionProvider;
import ru.kpfu.itis.util.DbException;

@WebListener()
public class InitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ConnectionProvider connectionProvider = ConnectionProvider.getInstance();
            sce.getServletContext().setAttribute("travelService",new TravelService());
            sce.getServletContext().setAttribute("userService",new UserService());
        } catch (DbException e) {
            throw new RuntimeException(e);
        }
    }
}
