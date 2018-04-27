
/**
 * 查询条件基类
*/
public class SearchBox {
    /**
     * 页数
     */
    private Integer pNum;
    /**
     * 每页大小
     */
    private Integer pSize;
    /**
     * 起始位置
    */
    private Integer startPosition;

    public Integer getpNum() {
        return pNum;
    }

    public void setpNum(Integer pNum) {
        this.pNum = pNum;
    }

    public Integer getpSize() {
        return pSize;
    }

    public void setpSize(Integer pSize) {
        this.pSize = pSize;
    }

    public Integer getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Integer startPosition) {
        this.startPosition = startPosition;
    }
}
