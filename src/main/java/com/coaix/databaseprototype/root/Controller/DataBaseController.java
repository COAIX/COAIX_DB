package com.coaix.databaseprototype.root.Controller;

import com.coaix.databaseprototype.bean.statement.UseSm;
import com.coaix.databaseprototype.root.Tools.GetDataTools;
import com.coaix.databaseprototype.root.Tools.StatementTools;
import com.coaix.databaseprototype.root.bean.Databaseinformation;
import com.coaix.databaseprototype.root.bean.TableData;
import com.coaix.databaseprototype.tools.FileTools;
import com.coaix.databaseprototype.webbean.testName;
import org.junit.rules.TestName;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author LiaoWei
 * @date 2021-11-04 22:37
 */
@RestController
public class DataBaseController {
    
    @GetMapping("/test")
    public HashMap test(){



        HashMap<String, String> map = new HashMap<>();

        map.put("name","123");
        map.put("no","1324");
//        testName testName = new testName("123","1324");


        return map;
    }

    @GetMapping("/sendStatement")
    public String sendStatement(@RequestParam String selectStatement){
        UseSm useSm = FileTools.fileReader(new UseSm(), FileTools.USESMPATH);
        String check = StatementTools.sendStatement(useSm, selectStatement);
        return check;
    }

    /*获取侧边栏数据库信息*/
    @GetMapping("/getDatabaseinformation")
    public ArrayList<Databaseinformation> getDatabaseinformation(){
        return FileTools.getDatabaseinfromation();
    }

    @PostMapping("/getTableData")
    public TableData getTableData(
           @RequestParam String databaseName,
           @RequestParam String tableName
    ){
        System.out.println(databaseName+"_"+tableName);
        UseSm useSm = new UseSm();
        useSm.setComplete(true);
        useSm.setName(databaseName);
        FileTools.fileWriter(useSm,FileTools.USESMPATH);
        return GetDataTools.getTableData(databaseName,tableName);
    }
}
