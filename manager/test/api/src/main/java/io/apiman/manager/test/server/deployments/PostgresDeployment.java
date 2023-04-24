/*
 * Copyright 2023 Black Parrot Labs Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.apiman.manager.test.server.deployments;

import com.zaxxer.hikari.HikariDataSource;
import io.apiman.test.common.util.TestUtil;
import org.jdbi.v3.core.Jdbi;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Optional;

/**
 * @author Marc Savy {@literal <marc@blackparrotlabs.io>}
 */
public class PostgresDeployment implements ITestDatabaseDeployment {

    static final String DEFAULT_IMAGE = "postgres:15";
    PostgreSQLContainer<?> postgresServer;
    HikariDataSource ds;
    InitialContext ctx;

    @Override
    public void start(String containerImageName) {
        String image = Optional.ofNullable(containerImageName).orElse(DEFAULT_IMAGE);
        postgresServer = new PostgreSQLContainer<>(image);
        postgresServer.start();
        createEmpty();
        bindDs();
        setConnectionProps();
    }


    @Override
    public void stop() {
        postgresServer.stop();
        ds.close();
    }

    void createEmpty() {
        this.ds = new HikariDataSource();
        ds.setJdbcUrl(postgresServer.getJdbcUrl());
        ds.setUsername(postgresServer.getUsername());
        ds.setPassword(postgresServer.getPassword());
        Jdbi.create(ds).withHandle(h -> h.execute("CREATE DATABASE apiman_manager"));
    }

    void bindDs() {
        try {
            // If already had ctx, then must unbind it to prevent weird behaviour and/or binding exceptions.
            if (ctx != null) {
                ctx.unbind("java:/apiman/datasources/apiman-manager");
            }
            ctx = TestUtil.initialContext();
            TestUtil.ensureCtx(ctx, "java:/apiman");
            TestUtil.ensureCtx(ctx, "java:/apiman/datasources");
            ctx.bind("java:/apiman/datasources/apiman-manager", ds);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    void setConnectionProps() {
        System.setProperty("hibernate.show_sql", "false");
        System.setProperty("apiman.hibernate.dialect", "io.apiman.manager.api.jpa.ApimanPostgreSQLDialect");
        System.setProperty("apiman.hibernate.hbm2ddl.auto", "validate");
        System.setProperty("hibernate.auto_quote_keyword", "false");
    }
}
