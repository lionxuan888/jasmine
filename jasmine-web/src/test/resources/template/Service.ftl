import java.util.List;

/**
 * ${classModel.desc!''} 操作接口
 */
public interface ${classModel.uname!''}Service {

    /**
     * 保存${classModel.desc!''}数据
     * @param model the save model
     * @return return id
     */
    int save(${classModel.uname!''}Model model);

    /**
    * 批量保存${classModel.desc!''}数据
    * @param list the save list
    */
    void saveBatch(List${r'<'}${classModel.uname!''}Model${r'>'} list);

    /**
    * 删除${classModel.desc!''}数据
    * @param id the id
    * @return return true if delete success
    */
    boolean delete(int id);

    /**
    * 批量删除${classModel.desc!''}数据
    * @param list the id list
    * @return return true if delete success
    */
    boolean deleteByIds(List${r'<Integer>'} list);

    /**
    * 更新${classModel.desc!''}数据
    * @param model the update model
    * @return return true if update success
    */
    boolean update(${classModel.uname!''}Model model);

    /**
    * 根据id获取${classModel.desc!''}数据
    * @param id the id
    * @return the finding model
    */
    ${classModel.uname!''}Model findById(int id);

    /**
    * 分页获取${classModel.desc!''}数据
    * @param searchBox the search box
    * @return the query list
    */
    List${r'<'}${classModel.uname!''}Model${r'>'} queryForPage(${classModel.uname!''}ModelSearchBox searchBox);

    /**
    * 获取${classModel.desc!''}数据总条数
    * @param searchBox the search box
    * @return the count
    */
    int count(${classModel.uname!''}ModelSearchBox searchBox);
}