/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.trino.conversionfunctions;

import io.trino.metadata.InternalFunctionBundle;
import io.trino.sql.query.QueryAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class TestConversion
{
    private QueryAssertions assertions;

    @BeforeAll
    public void init()
    {
        assertions = new QueryAssertions();
        assertions.addFunctions(InternalFunctionBundle.builder()
                .scalars(MGRSUDFPlugin.class)
                .build());
    }

    @AfterAll
    public void teardown()
    {
        assertions.close();
        assertions = null;
    }

    @Test
    public void testConversion()
    {
        assertThat(assertions.function("mgrs_to_latlong", "'33XVG7459043590'"))
                .isEqualTo("77.8654629, 13.9171014");
        assertThat(assertions.function("mgrs_to_latlong", "'33PUR'"))
                .isEqualTo("13.5616097, 13.1517277");
        assertThat(assertions.function("latlong_to_mgrs", "77.8654629", "13.9171014"))
                .isEqualTo("33XVG7459043590");
        assertThat(assertions.function("latlong_to_mgrs", "14", "14"))
                .isEqualTo("33PUR9200447954");
    }
}
