/*
 * Copyright 2014-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.session.mongodb.examples;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.session.mongodb.examples.pages.HomePage;
import org.springframework.session.mongodb.examples.pages.HomePage.Attribute;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Eddú Meléndez
 * @author Rob Winch
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AttributeTests {

	@LocalServerPort
	int port;

	private WebDriver driver;

	@BeforeEach
	public void setup() {
		this.driver = new HtmlUnitDriver();
	}

	@AfterEach
	public void tearDown() {
		this.driver.quit();
	}

	@Test
	public void home() {

		HomePage home = HomePage.go(this.driver, this.port);
		home.assertAt();
	}

	@Test
	public void noAttributes() {

		HomePage home = HomePage.go(this.driver, this.port);
		assertThat(home.attributes()).isEmpty();
	}

	@Test
	public void createAttribute() {

		HomePage home = HomePage.go(this.driver, this.port);
		home = home.form().attributeName("a").attributeValue("b").submit(HomePage.class);

		List<Attribute> attributes = home.attributes();
		assertThat(attributes).hasSize(1);

		Attribute row = attributes.get(0);
		assertThat(row.getAttributeName()).isEqualTo("a");
		assertThat(row.getAttributeValue()).isEqualTo("b");
	}

}
