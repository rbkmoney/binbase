package com.rbkmoney.binbase.endpoint;

import com.rbkmoney.damsel.binbase.BinbaseSrv;
import com.rbkmoney.woody.thrift.impl.http.THServiceBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/v1/binbase")
public class BinbaseServlet extends GenericServlet {

    private Servlet thriftServlet;

    @Autowired
    private BinbaseSrv.Iface requestHandler;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        thriftServlet = new THServiceBuilder()
                .build(BinbaseSrv.Iface.class, requestHandler);
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse)
            throws ServletException, IOException {
        thriftServlet.service(servletRequest, servletResponse);
    }
}
