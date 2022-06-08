import com.linyonghao.influxdb2.InfluxdbGlobal;
import com.linyonghao.influxdb2.exception.NotFoundMeasurementException;
import com.linyonghao.influxdb2.mapper.UserMapper;
import org.junit.Test;

public class Application {
    @Test
    public void testAnnotation() throws NotFoundMeasurementException {
        UserMapper userMapper = new UserMapper();
        userMapper.insert(null,0);
    }
}
