
import java.util.List;
import java.util.Map;

/**
 * ${classModel.desc!''} 操作接口
 */
public interface ${classModel.uname!''}Dao {

    /**
    * 保存${classModel.desc!''}数据
    * @param model the save model
    * @return return count
    */
    Long save(${classModel.uname!''}Model model);

    /**
    * 批量保存${classModel.desc!''}数据
    * @param list the save list
    */
    void saveBatch(List${r'<'}${classModel.uname!''}Model${r'>'} list);

    /**
    * 删除${classModel.desc!''}数据
    * @param id the id
    * @return return delete count
    */
    int delete(Long id);

    /**
    * 批量删除${classModel.desc!''}数据
    * @param list the id list
    * @return return delete count
    */
    int deleteByIds(List${r'<Long>'} list);

    /**
    * 更新${classModel.desc!''}数据
    * @param model the update model
    * @return return update count
    */
    int update(${classModel.uname!''}Model model);

    /**
    * 根据id获取${classModel.desc!''}数据
    * @param id the id
    * @return the finding model
    */
    ${classModel.uname!''}Model findById(Long id);

}