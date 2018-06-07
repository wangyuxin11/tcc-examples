package org.tcc.examples.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:/tcc-transaction-unit-test.xml","classpath:/tcc-transaction.xml"})
public abstract class AbstractTestCase {

}