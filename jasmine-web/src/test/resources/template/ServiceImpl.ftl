
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
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
    public int save(${classModel.uname!''}Model model) {
        if (model == null) {
            logger.info("保存${classModel.desc!''}失败，id={}", -1);
            return -1;
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
    public void saveBatch(List${r'<'}${classModel.uname!''}Model${r'>'} list) {
       if (CollectionUtils.isEmpty(list)) {
            logger.info("批量保存${classModel.desc!''}失败，size={}", 0);
            return;
        }
        List${r'<'}List${r'<'}${classModel.uname!''}Model${r'>>'} partitionList = Lists.partition(list, 1000);
        for (List${r'<'}${classModel.uname!''}Model${r'>'} partition : partitionList) {
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
    public boolean delete(int id) {
        if (id <= 0) {
            logger.info("删除${classModel.desc!''}失败，id={},count={}", 0, 0);
            return false;
        }
        int count = ${classModel.lname!''}Dao.delete(id);
        logger.info("删除${classModel.desc!''}成功，id={},count={}", id, count);
        return count > 0;
    }

    /**
     * 批量删除${classModel.desc!''}数据
     * @param list the id list
     * @return return true if delete success
     */
    @Transactional
    @Override
    public boolean deleteByIds(List${r'<Integer>'} list){
        if (CollectionUtils.isEmpty(list)) {
            logger.info("删除${classModel.desc!''}失败,count={}",0);
            return false;
        }
        List${r'<'}List${r'<Integer>'}${r'>'} partitionList = Lists.partition(list, 1000);
        int count = 0;
        for (List${r'<Integer>'} partition : partitionList) {
            count += ${classModel.lname!''}Dao.deleteByIds(partition);
        }
        logger.info("删除${classModel.desc!''}成功,count={}", count);
        return count==list.size();
    }

    /**
     * 更新${classModel.desc!''}数据
     * @param model the update model
     * @return return true if update success
     */
    @Override
    public boolean update(${classModel.uname!''}Model model) {
        if (model == null || model.getId() <= 0) {
            logger.info("更新${classModel.desc!''}失败，参数不合法");
            return false;
        }
        int count = ${classModel.lname!''}Dao.update(model);
        logger.info("更新${classModel.desc!''}成功，id={},count={}", model.getId(), count);
        return count > 0;
    }

    /**
     * 根据id获取${classModel.desc!''}数据
     * @param id the id
     * @return the finding model
     */
    @Override
    public ${classModel.uname!''}Model findById(int id){
        if (id <= 0) {
            logger.info("根据id获取${classModel.desc!''}失败，id={}", id);
            return null;
        }
        ${classModel.uname!''}Model model = ${classModel.lname!''}Dao.findById(id);
        logger.info("根据id获取${classModel.desc!''}成功，id={}", id);
        return model;
    }

    /**
     * 分页获取${classModel.desc!''}数据
     * @param searchBox the search box
     * @return the query list
     */
    @Override
    public List${r'<'}${classModel.uname!''}Model${r'>'} queryForPage(${classModel.uname!''}ModelSearchBox searchBox){

        List${r'<'}${classModel.uname!''}Model${r'>'} modelList = ${classModel.lname!''}Dao.queryForPage(searchBox);

        if (CollectionUtils.isEmpty(modelList)) {
            modelList = Lists.newArrayList();
        }

        logger.info("分页获取${classModel.desc!''}数据，page={},pageSize={}", searchBox.getPageNo(), searchBox.getPageSize());
        return modelList;
    }

    /**
     * 获取${classModel.desc!''}数据总条数
     * @param searchBox the search box
     * @return the count
     */
    @Override
    public int count(${classModel.uname!''}ModelSearchBox searchBox){
        int count = ${classModel.lname!''}Dao.count(searchBox);
        logger.info("获取${classModel.desc!''}数据总条数，count={}", count);
        return count;
    }
}