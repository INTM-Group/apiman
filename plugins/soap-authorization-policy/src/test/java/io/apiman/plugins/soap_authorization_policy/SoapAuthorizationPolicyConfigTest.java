/*
 * Copyright 2016 JBoss Inc
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
package io.apiman.plugins.soap_authorization_policy;

import io.apiman.gateway.engine.policies.config.MultipleMatchType;
import io.apiman.gateway.engine.policies.config.UnmatchedRequestType;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test
 *
 * @author rubenrm1@gmail.com
 * @author rachel.yordan@redhat.com
 * Test the {@link SoapAuthorizationConfig}.
 *
 */

@SuppressWarnings({ "nls" })

public class SoapAuthorizationPolicyConfigTest {

    @Test
    public void testParseConfiguration() {
        SoapAuthorizationPolicy policy = new SoapAuthorizationPolicy();

        String config = "{}";
        Object parsed = policy.parseConfiguration(config);
        SoapAuthorizationConfig parsedConfig = (SoapAuthorizationConfig) parsed;
        
        Assert.assertNotNull(parsed);
        Assert.assertEquals(SoapAuthorizationConfig.class, parsed.getClass());
       
        Assert.assertNotNull(parsedConfig.getRules());
        Assert.assertTrue(parsedConfig.getRules().isEmpty());
    }
    
    
    @Test
    public void testBooleanFlags() {
        SoapAuthorizationPolicy policy = new SoapAuthorizationPolicy();

        String config = "{}";
        Object parsed = policy.parseConfiguration(config);
        SoapAuthorizationConfig parsedConfig = (SoapAuthorizationConfig) parsed;
        
        config = "{\r\n" +
                "    \"requestUnmatched\" : \"pass\",\r\n" +
                "    \"multiMatch\" : \"any\"\r\n" +
                "}\r\n" +
                "";
        parsed = policy.parseConfiguration(config);
        parsedConfig = (SoapAuthorizationConfig) parsed;
        Assert.assertEquals(Collections.emptyList(), parsedConfig.getRules());
        Assert.assertEquals(MultipleMatchType.any, parsedConfig.getMultiMatch());
        Assert.assertEquals(UnmatchedRequestType.pass, parsedConfig.getRequestUnmatched());
    }

}
