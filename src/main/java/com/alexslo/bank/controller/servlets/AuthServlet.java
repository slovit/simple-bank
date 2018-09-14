package com.alexslo.bank.controller.servlets;

import com.alexslo.bank.mem.UserDaoImpl;
import com.alexslo.bank.model.entity.Transaction;
import com.alexslo.bank.model.entity.UserRole;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.nonNull;

public class AuthServlet extends HttpServlet {
    private Map<Integer, Transaction> transactions;

    @Override
    public void init() throws ServletException {

        final Object transactions = getServletContext().getAttribute("transactions");

        this.transactions = (ConcurrentHashMap<Integer, Transaction>) transactions;

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String login = req.getParameter("login");
        final String password = req.getParameter("password");

        @SuppressWarnings("unchecked") final AtomicReference<UserDaoImpl> dao = (AtomicReference<UserDaoImpl>) req.getServletContext().getAttribute("com/alexslo/bank/dao");
        req.setAttribute("transactions", transactions.values());
        final HttpSession session = req.getSession();

        if (nonNull(session) &&
                nonNull(session.getAttribute("login")) &&
                nonNull(session.getAttribute("password"))) {

            final UserRole role = (UserRole) session.getAttribute("role");

            moveToMenu(req, resp, role);


        } else if (dao.get().userExists(login, password)) {

            final UserRole role = dao.get().getUserRoleByLoginPassword(login, password);

            req.getSession().setAttribute("password", password);
            req.getSession().setAttribute("login", login);
            req.getSession().setAttribute("role", role);

            moveToMenu(req, resp, role);

        } else {

            moveToMenu(req, resp, UserRole.GUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private void moveToMenu(final HttpServletRequest req,
                            final HttpServletResponse res,
                            final UserRole role)
            throws ServletException, IOException {

        switch (role) {
            case USER:
                req.getRequestDispatcher("/WEB-INF/view/user_menu.jsp").forward(req, res);
                break;
            case ADMIN:
                req.getRequestDispatcher("/WEB-INF/view/admin_menu.jsp").forward(req, res);
                break;
            case GUEST:
                req.getRequestDispatcher("/WEB-INF/view/test.jsp").forward(req, res);
                break;
        }
    }
}
