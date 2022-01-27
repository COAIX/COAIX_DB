package com.coaix.databaseprototype.root;

import com.coaix.databaseprototype.bean.statement.CreateSm;
import com.coaix.databaseprototype.bean.statement.UseSm;
import com.coaix.databaseprototype.service.impl.CreateService;
import com.coaix.databaseprototype.tools.SmTools;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RootApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    void testService(){
        String str = "create database books";
        CreateSm typeSm = (CreateSm) SmTools.getTypeSm(str);
        String str2 = "use employee2";
        UseSm typeSm2 = (UseSm)SmTools.getTypeSm(str2);
        CreateService service = new CreateService(typeSm, typeSm2);
        service.runService();
    }
}
