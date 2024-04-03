/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.company.resume.controller;

import com.company.dao.inter.UserDaoInter;
import com.company.entity.User;
import com.company.main.Context;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author asgar
 */
@WebServlet(name = "UserController", urlPatterns = {"/users"})
public class UserController extends HttpServlet {
    private UserDaoInter userDao = Context.instanceUserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");

            String nationalityStr = request.getParameter("nid");
            Integer nationalityId = null;
            if (nationalityStr != null && !nationalityStr.trim().isEmpty()) {
                nationalityId = Integer.parseInt(nationalityStr);
            }

            List<User> list = userDao.getAll(name, surname, nationalityId);
            request.setAttribute("list", list);

            request.getRequestDispatcher("users.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("-- error --");
            response.sendRedirect("error?msg=" + e.getMessage());
        }
    }

}



