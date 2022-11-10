import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.List;

@Slf4j
public class MongoParalelSource implements SourceFunction<List<String>> {


    @Override
    public void run(SourceContext<List<String>> ctx) throws Exception {

    }

    @Override
    public void cancel() {

    }
}
