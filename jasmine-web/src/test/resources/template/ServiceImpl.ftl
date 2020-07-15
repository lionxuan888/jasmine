
import com.google.common.base.Preconditions;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import javax.annotation.Resource;
import java.util.List;
import org.springframework.stereotype.Service;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

/**
 * ${classModel.desc!''} 操作接口实现类
 */
@Service
public class ${classModel.uname!''}ServiceImpl implements ${classModel.uname!''}Service {

    private Logger logger = LoggerFactory.getLogger(${classModel.uname!''}ServiceImpl.class);

    @Resource
    private ${classModel.uname!''}Dao ${classModel.lname!''}Dao;

    /**
     * 保存${classModel.desc!''}数据
     * @param model the save model
     * @return return id
     */
    @Override
    public Long save(${classModel.uname!''}DO model) {
        if (model == null) {
            logger.warn("保存${classModel.desc!''}失败，id={}", -1);
            throw new RuntimeException("保存失败");
        }
        ${classModel.lname!''}Dao.save(model);
        logger.info("保存${classModel.desc!''}成功，id={}", model.getId());
        return model.getId();
    }

    /**
     * 批量保存${classModel.desc!''}数据
     * @param list the save list
     */
    @Transactional
    @Override
    public void saveBatch(List${r'<'}${classModel.uname!''}DO${r'>'} list) {
       if (CollectionUtils.isEmpty(list)) {
            logger.info("批量保存${classModel.desc!''}失败，size={}", 0);
            return;
        }
        List${r'<'}List${r'<'}${classModel.uname!''}DO${r'>>'} partitionList = Lists.partition(list, 1000);
        for (List${r'<'}${classModel.uname!''}DO${r'>'} partition : partitionList) {
            ${classModel.lname!''}Dao.saveBatch(partition);
            logger.info("批量保存${classModel.desc!''}成功，size={}", partition.size());
        }
    }

    /**
     * 删除${classModel.desc!''}数据
     * @param id the id
     * @return return true if delete success
     */
    @Override
    public void delete(Long id) {
        if (id <= 0) {
            logger.info("删除${classModel.desc!''}失败，id={},count={}", 0, 0);
            return;
        }
        int count = ${classModel.lname!''}Dao.delete(id);
        logger.info("删除${classModel.desc!''}成功，id={},count={}", id, count);
    }

    /**
     * 批量删除${classModel.desc!''}数据
     * @param list the id list
     * @return return true if delete success
     */
    @Transactional
    @Override
    public void deleteByIds(List${r'<Long>'} list){
        if (CollectionUtils.isEmpty(list)) {
            logger.info("删除${classModel.desc!''}失败,count={}",0);
            return;
        }
        List${r'<'}List${r'<Long>'}${r'>'} partitionList = Lists.partition(list, 1000);
        int count = 0;
        for (List${r'<Long>'} partition : partitionList) {
            count += ${classModel.lname!''}Dao.deleteByIds(partition);
        }
        logger.info("删除${classModel.desc!''}成功,count={}", count);
    }

    /**
     * 更新${classModel.desc!''}数据
     * @param model the update model
     * @return return true if update success
     */
    @Override
    public int update(${classModel.uname!''}DO model) {
        if (model == null || model.getId() <= 0) {
            logger.info("更新${classModel.desc!''}失败，参数不合法");
            return 0;
        }
        int count = ${classModel.lname!''}Dao.update(model);
        if (count != 1) {
           logger.error("更新${classModel.desc!''}失败，id={},count={}", model.getId(), count);
           throw new RuntimeException("更新失败" + model.getId());
        }
        logger.info("更新${classModel.desc!''}成功，id={},count={}", model.getId(), count);
        return count;
    }

    /**
     * 根据id获取${classModel.desc!''}数据
     * @param id the id
     * @return the finding model
     */
    @Override
    public ${classModel.uname!''}DO findById(Long id){
        if (id <= 0) {
            logger.info("根据id获取${classModel.desc!''}失败，id={}", id);
            return null;
        }
        ${classModel.uname!''}DO model = ${classModel.lname!''}Dao.findById(id);
        logger.info("根据id获取${classModel.desc!''}成功，id={}", id);
        return model;
    }
}