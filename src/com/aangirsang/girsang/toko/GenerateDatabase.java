/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author GIRSANG PC
 */
public class GenerateDatabase {

    public static void main(String[] args) throws SQLException {
        AbstractApplicationContext ctx
                = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        DataSource dataSource = (DataSource) ctx.getBean("dataSource");

        Configuration cfg = new AnnotationConfiguration()
                .configure("hibernate.cfg.xml")
                .setProperty("hibernate.dialect",
                        "org.hibernate.dialect.H2Dialect");

        try (Connection conn = dataSource.getConnection()) {
            new SchemaExport(cfg, conn).create(true, true);
            
            cfg.generateSchemaCreationScript(
                    Dialect.getDialect(cfg.getProperties()));
            SchemaExport export = new SchemaExport(cfg, conn);
            export.create(true, true);
        }
        ctx.registerShutdownHook();

    }
}
