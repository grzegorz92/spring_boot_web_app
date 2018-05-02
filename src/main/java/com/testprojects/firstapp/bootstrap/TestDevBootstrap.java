package com.testprojects.firstapp.bootstrap;

import com.testprojects.firstapp.service.v1.PropertyConfig;
import com.testprojects.firstapp.model.v1.TestEmployee;
import com.testprojects.firstapp.repositories.v1.TestEmployeeRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class TestDevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private TestEmployeeRepository testEmployeeRepository;
    private PropertyConfig propConfig;

    public TestDevBootstrap(TestEmployeeRepository testEmployeeRepository, PropertyConfig propConfig) {
        this.testEmployeeRepository = testEmployeeRepository;
        this.propConfig = propConfig;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        init();

    }


    private void init(){


        for(int i=0; i<propConfig.getName().size();i++) {
            TestEmployee te = new TestEmployee(propConfig.getName().get(i), propConfig.getLastName().get(i), propConfig.getHireDate().get(i), propConfig.getSalary().get(i));
            testEmployeeRepository.save(te);
        }
    }
}
