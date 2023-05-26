package top.codingmore.generator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/knife4j")
@Api(tags = "kni4j测试")
public class Knife4jController {

    @ApiOperation("测试方法")
    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public String test(String author){
        return "knife4j is very beautiful ..., write by " + author;
    }
}
