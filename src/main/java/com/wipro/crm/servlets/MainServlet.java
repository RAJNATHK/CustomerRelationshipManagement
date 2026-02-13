package com.wipro.crm.servlets;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import com.wipro.crm.bean.CrmBean;
import com.wipro.crm.dao.CrmDAO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String operation = request.getParameter("operation");
        CrmDAO dao = new CrmDAO();

        try {

   
            if ("newRecord".equals(operation)) {

                String name = request.getParameter("customerName");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String status = request.getParameter("status");
                String remarks = request.getParameter("remarks");
                String dateStr = request.getParameter("joinDate");

           
                Date sqlDate = Date.valueOf(dateStr);

                CrmBean bean = new CrmBean();
                bean.setCustomerName(name);
                bean.setEmail(email);
                bean.setPhone(phone);
                bean.setJoinDate(sqlDate);
                bean.setStatus(status);
                bean.setRemarks(remarks);

            
                String recordId = dao.generateRecordID(name, sqlDate);
                bean.setRecordId(recordId);

                String result = dao.createRecord(bean);

                if (!"FAIL".equals(result)) {
                    response.sendRedirect("success.html");
                } else {
                    response.sendRedirect("error.html");
                }
            }

            else if ("viewRecord".equals(operation)) {

                String name = request.getParameter("customerName");
                String dateStr = request.getParameter("joinDate");

                System.out.println("DATE RECEIVED: " + dateStr);

                // ✅ Direct conversion
                Date sqlDate = Date.valueOf(dateStr);

                CrmBean bean = dao.fetchRecord(name, sqlDate);

                if (bean != null) {

                    request.setAttribute("record", bean);
                    RequestDispatcher rd =
                            request.getRequestDispatcher("displayCustomer.jsp");
                    rd.forward(request, response);

                } else {
                    response.getWriter().println("No matching records exists! Please try again!");
                }
            }

           
            else if ("viewAllRecords".equals(operation)) {

                List<CrmBean> list = dao.fetchAllRecords();

                if (!list.isEmpty()) {

                    request.setAttribute("list", list);

                    RequestDispatcher rd =
                            request.getRequestDispatcher("displayAllCustomers.jsp");
                    rd.forward(request, response);

                } else {
                    response.sendRedirect("error.html");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Something went wrong!");
        }
    }
}
