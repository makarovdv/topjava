package ru.javawebinar.topjava.web;
import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealDAOTest;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
public class MealServlet extends HttpServlet{
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("request to meals.jsp");

        MealDAO mealDAO = new MealDAOTest();
        String s = req.getParameter("remove");
        log.debug(s);
        if (s!=null) mealDAO.remove(Integer.parseInt(s));
        List<MealWithExceed> mealsWithExceeded = MealsUtil.getFilteredWithExceeded(
                mealDAO.getAll(), LocalTime.MIN,LocalTime.MAX,2000
        );

//        log.debug("setting Attribute" + mealsWithExceeded);
        req.setAttribute("meals",mealsWithExceeded);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
//        resp.sendRedirect("meals.jsp");
    }
}
