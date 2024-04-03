package com.company.resume.controller;


import com.company.dao.inter.UserDaoInter;
import com.company.entity.User;
import com.company.main.Context;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author asgar
 */
@WebServlet(name = "MyUserDetailController", urlPatterns = {"/myuserdetail"})
public class MyUserDetailController extends HttpServlet {
    private UserDaoInter userDao = Context.instanceUserDao();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = getServletContext();
        int id = Integer.parseInt(request.getParameter("id"));
        String action = request.getParameter("action");
        context.log("action: " + action);
        if (action.equals("update")) {
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");

            context.log("Name :" + name + "  /  " + "Surname: " + surname);

            User user = userDao.getById(id);
            user.setName(name);
            user.setSurname(surname);

            userDao.updateUser(user);
        } else if (action.equals("delete")) {
            userDao.removeUser(id);
        } else if (action.equals("back")) {
            response.sendRedirect("users");
        }
        response.sendRedirect("users");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String userIdStr = request.getParameter("id");
            if (userIdStr == null || userIdStr.trim().isEmpty()) {
                throw new IllegalArgumentException("id is not valid !");
            }

            Integer userId = Integer.parseInt(userIdStr);

            UserDaoInter userDao = Context.instanceUserDao();
            User u = userDao.getById(userId);
            if (u == null) {
                throw new IllegalArgumentException("There is no user with this id");
            }

            request.setAttribute("user", u);
            request.getRequestDispatcher("myuserdetail.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error?msg=" + e.getMessage());
        }

    }


}
