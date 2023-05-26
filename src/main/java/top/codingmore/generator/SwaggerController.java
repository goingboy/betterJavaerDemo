package top.codingmore.generator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "测试 Swagger")
@RestController
@RequestMapping("/swagger")
public class SwaggerController {

    @ApiOperation("测试")
    @RequestMapping("/test")
    public String test(String name) {
        return "你的名字是：" + name;
    }
}
