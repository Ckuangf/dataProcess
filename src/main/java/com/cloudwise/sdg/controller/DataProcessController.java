package com.cloudwise.sdg.controller;

import com.cloudwise.sdg.dic.DicInitializer;
import com.cloudwise.sdg.template.TemplateAnalyzer;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@RestController
public class DataProcessController {

    @PostMapping("/getData")
    public JSONObject getData(@RequestBody @NotNull String dataTemplate,@RequestParam(required = false,defaultValue = "1") int itemCount) {
        JSONObject result = new JSONObject();
        //初始化词典
        try {
            DicInitializer.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(System.getProperty("user.dir"));
        //System.out.println("======tplName: " + tplName + ", begin===================");
        TemplateAnalyzer testTplAnalyzer = new TemplateAnalyzer(null, dataTemplate);
        //System.out.println("======tplName: " + tplName + ", end==================");
        //System.out.println();
        ArrayList<JSONObject> dataResult = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            String abc = testTplAnalyzer.analyse();
            JSONObject abc1 = JSONObject.fromObject(abc);
            dataResult.add(abc1);
            System.out.println(abc1);
        }
        System.out.println("fininsh");
        result.put("result",dataResult);
        return result;
    }

}
