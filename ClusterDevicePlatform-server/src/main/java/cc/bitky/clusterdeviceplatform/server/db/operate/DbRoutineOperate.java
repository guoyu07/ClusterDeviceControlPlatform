package cc.bitky.clusterdeviceplatform.server.db.operate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import cc.bitky.clusterdeviceplatform.messageutils.msg.MsgReplyChargeStatus;
import cc.bitky.clusterdeviceplatform.server.db.bean.routineinfo.HistoryInfo;
import cc.bitky.clusterdeviceplatform.server.db.bean.routineinfo.LampStatusHistory;
import cc.bitky.clusterdeviceplatform.server.db.repository.RoutineTableRepository;

@Repository
public class DbRoutineOperate {
    private final RoutineTableRepository repository;

    @Autowired
    public DbRoutineOperate(RoutineTableRepository repository) {
        this.repository = repository;
    }

    /**
     * 更新员工的考勤表
     *
     * @param employeeObjectId 员工的 ObjectId，该值不能为 Null
     * @param chargeStatus     设备状态包
     */
    public void updateRoutineById(String employeeObjectId, MsgReplyChargeStatus chargeStatus) {
        Optional<LampStatusHistory> optional = repository.findById(employeeObjectId);
        LampStatusHistory document = optional.orElseGet(() -> {
            LampStatusHistory temp = new LampStatusHistory();
            temp.setId(employeeObjectId);
            return temp;
        });
        document.getStatusList().add(new HistoryInfo(chargeStatus.getTime(), chargeStatus.getStatus()));
        repository.save(document);
    }
}
