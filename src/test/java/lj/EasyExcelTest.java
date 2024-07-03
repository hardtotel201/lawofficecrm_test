package lj;

import com.alibaba.excel.EasyExcel;
import lj.model.client.ClientInfo;
import lj.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.util.Date;
import java.util.List;

@Slf4j
public class EasyExcelTest {
    @Test
    public void TestFile(){
        File file = new File("/Users/chengwenhao/Documents/研究生/律所项目/1.客户信息导入格式(1).xlsx");
        List<Object> list = EasyExcel.read(file)
                .head(ClientInfo.class)
                .headRowNumber(1 )
                .sheet()
                .doReadSync();
        for (Object item: list) {
            System.out.println("clientInfoController.insertClientInfo() request:" + JsonUtils.objectToJson(item));
//            String msg = clientInfoService.insertClientInfo(clientInfo);
        }

    }
}
