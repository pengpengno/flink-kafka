import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class UnionCase {




    @Test
    public void union (){

    }

    /**
     * 自定义输出文本格式
     */
    static class MyFunction extends RichMapFunction<List<String>,String>{
        @Override
        public String map(List<String> key) throws Exception {

            return null;
        }
    }
}
