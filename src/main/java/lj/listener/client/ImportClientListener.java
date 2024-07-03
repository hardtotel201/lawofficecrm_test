//package lj.listener.client;
//
//import com.alibaba.excel.context.AnalysisContext;
//import com.alibaba.excel.read.listener.ReadListener;
//import com.alibaba.excel.util.ListUtils;
//import com.alibaba.fastjson.JSON;
//import lj.model.client.ClientInfo;
//import lj.service.client.ClientInfoService;
//import lj.util.JsonUtils;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.List;
//
//@Slf4j
//public class ImportClientListener implements ReadListener<ClientInfo> {
//
//    /**
//     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
//     */
//    private static final int BATCH_COUNT = 5;
//    /**
//     * 缓存的数据
//     */
//    private List<ClientInfo> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
//
//    private ClientInfoService clientInfoService;
//
//    /**
//     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
//     *
//     * @param clientInfoService
//     */
//    public ImportClientListener(ClientInfoService clientInfoService) {
//        this.clientInfoService = clientInfoService;
//    }
//
//    @Override
//    public void invoke(ClientInfo clientInfo, AnalysisContext analysisContext) {
//        log.info("解析到一条数据:{}", JsonUtils.objectToJson(clientInfo));
//        cachedDataList.add(clientInfo);
//        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
//        if (cachedDataList.size() >= BATCH_COUNT) {
//            saveData();
//            // 存储完成清理 list
//            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
//        }
//    }
//
//    @Override
//    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
//        saveData();
//        log.info("所有数据解析完成！");
//    }
//
//    /**
//     * 加上存储数据库
//     */
//    private void saveData() {
//        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
//        clientInfoService.save(cachedDataList);
//        log.info("存储数据库成功！");
//    }
//}