import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* ${classModel.desc!''}Controller
*
*/
@Controller
@RequestMapping(value = "需要修改")
public class ${classModel.uname!''}Controller {
    private static final Logger logger = LoggerFactory.getLogger(${classModel.uname!''}Controller.class);
    @Resource
    private ${classModel.uname!''}Service ${classModel.lname!''}Service;
    /**
    * 查询${classModel.desc!''}
    */
    @ResponseBody
    @RequestMapping("query")
    public Map${r'<'}String, Object${r'>'} query${classModel.uname!''}(${classModel.uname!''}ModelSearchBox searchBox) {
        JsonResponse jsonResult = JsonResponse.create();

        try {
            Pagination${r'<'}${classModel.uname!''}Model${r'>'} pagination = Pagination.instance(${classModel.lname!''}Service.getCountForPage(searchBox), searchBox.getpNum(), searchBox.getpSize());
            searchBox.setStartPosition(pagination.getStartPosition());
            List${r'<'}${classModel.uname!''}Model${r'>'} ${classModel.lname!''}ModelList = ${classModel.lname!''}Service.get${classModel.uname!''}ModelForPage(searchBox);
            jsonResult.ret(true).data(pagination.bindData(${classModel.lname!''}ModelList));
        } catch (Exception e) {
            jsonResult.ret(false).errMsg("查询${classModel.desc!''}失败");
            logger.error("查询${classModel.desc!''}失败",e);
        }

        return jsonResult.build();
    }

    /**
    * 新建${classModel.desc!''}
    */
    @ResponseBody
    @RequestMapping("save")
    public Map${r'<'}String, Object${r'>'} save${classModel.uname!''}(${classModel.uname!''}Model model) {
        JsonResponse jsonResult = JsonResponse.create();

        try {
            ${classModel.lname!''}Service.save${classModel.uname!''}Model(model);
        } catch (Exception e) {
            jsonResult.ret(false).errMsg("新建${classModel.desc!''}失败");
            logger.error("新建${classModel.desc!''}失败",e);
        }

        return jsonResult.build();
    }
    /**
    * 更新${classModel.desc!''}
    */
    @ResponseBody
    @RequestMapping("update")
    public Map${r'<'}String, Object${r'>'} update${classModel.uname!''}(${classModel.uname!''}Model model) {
        JsonResponse jsonResult = JsonResponse.create();

        try {
            ${classModel.lname!''}Service.update${classModel.uname!''}Model(model);
        } catch (Exception e) {
            jsonResult.ret(false).errMsg("更新${classModel.desc!''}失败");
            logger.error("更新${classModel.desc!''}失败",e);
        }

        return jsonResult.build();
    }
    /**
    * 删除${classModel.desc!''}
    */
    @ResponseBody
    @RequestMapping("delete")
    public Map${r'<'}String, Object${r'>'} delete${classModel.uname!''}(Integer id) {
        JsonResponse jsonResult = JsonResponse.create();

        try {
            ${classModel.lname!''}Service.delete${classModel.uname!''}Model(id);
        } catch (Exception e) {
            jsonResult.ret(false).errMsg("删除${classModel.desc!''}失败");
            logger.error("删除${classModel.desc!''}失败",e);
        }

        return jsonResult.build();
    }
}
