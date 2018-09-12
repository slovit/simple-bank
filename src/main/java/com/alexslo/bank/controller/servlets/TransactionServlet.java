package com.alexslo.bank.controller.servlets;


import com.alexslo.bank.model.entity.Transaction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class TransactionServlet extends HttpServlet {

    private Map<Integer, Transaction> transactions;
    private AtomicInteger id;

    @Override
    public void init() throws ServletException {

        final Object transactions = getServletContext().getAttribute("transactions");

        if (transactions == null || !(transactions instanceof ConcurrentHashMap)) {

            throw new IllegalStateException("You're repo does not initialize!");
        } else {

            this.transactions = (ConcurrentHashMap<Integer, Transaction>) transactions;
        }

        id = new AtomicInteger(3);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        final HttpSession session = req.getSession();
        final String fromAccount = req.getParameter("fromAccount");
        final String toAccount = req.getParameter("amount");
        final double amount = Double.parseDouble(req.getParameter("fromAccount"));
        final LocalDateTime time = LocalDateTime.now().withNano(0);
        Transaction transaction = new Transaction(fromAccount, toAccount, amount, time);
        final int id = this.id.getAndIncrement();
        transactions.put(id, transaction);

        resp.sendRedirect(req.getContextPath() + "/");

    }
}
