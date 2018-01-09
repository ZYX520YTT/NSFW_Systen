package zhongfucheng.complain.entity;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by ozc on 2017/6/12.
 */
public class Complain {
private String compId;
    private String compCompany;
    private String compName;
    private String compMobile;
    private Byte isNm;
    private Timestamp compTime;
    private String compTitle;
    private String toCompName;
    private String toCompDept;
    private String compContent;
    private String state;

    //状态
    public static String COMPLAIN_STATE_UNDONE = "0";
    public static String COMPLAIN_STATE_DONE = "1";
    public static String COMPLAIN_STATE_INVALID = "2";
    public static Map<String, String> COMPLAIN_STATE_MAP;
    static {
        COMPLAIN_STATE_MAP = new HashMap<>();
        COMPLAIN_STATE_MAP.put(COMPLAIN_STATE_UNDONE, "待受理");
        COMPLAIN_STATE_MAP.put(COMPLAIN_STATE_DONE, "已受理");
        COMPLAIN_STATE_MAP.put(COMPLAIN_STATE_INVALID, "已失效");
    }

    //添加set集合
    private Set complainReplies = new HashSet(0);
    public Set getComplainReplies() {
        return complainReplies;
    }
    public void setComplainReplies(Set complainReplies) {
        this.complainReplies = complainReplies;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getCompCompany() {
        return compCompany;
    }

    public void setCompCompany(String compCompany) {
        this.compCompany = compCompany;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getCompMobile() {
        return compMobile;
    }

    public void setCompMobile(String compMobile) {
        this.compMobile = compMobile;
    }

    public Byte getIsNm() {
        return isNm;
    }

    public void setIsNm(Byte isNm) {
        this.isNm = isNm;
    }

    public Timestamp getCompTime() {
        return compTime;
    }

    public void setCompTime(Timestamp compTime) {
        this.compTime = compTime;
    }

    public String getCompTitle() {
        return compTitle;
    }

    public void setCompTitle(String compTitle) {
        this.compTitle = compTitle;
    }

    public String getToCompName() {
        return toCompName;
    }

    public void setToCompName(String toCompName) {
        this.toCompName = toCompName;
    }

    public String getToCompDept() {
        return toCompDept;
    }

    public void setToCompDept(String toCompDept) {
        this.toCompDept = toCompDept;
    }

    public String getCompContent() {
        return compContent;
    }

    public void setCompContent(String compContent) {
        this.compContent = compContent;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Complain complain = (Complain) o;

        if (compId != null ? !compId.equals(complain.compId) : complain.compId != null) return false;
        if (compCompany != null ? !compCompany.equals(complain.compCompany) : complain.compCompany != null)
            return false;
        if (compName != null ? !compName.equals(complain.compName) : complain.compName != null) return false;
        if (compMobile != null ? !compMobile.equals(complain.compMobile) : complain.compMobile != null) return false;
        if (isNm != null ? !isNm.equals(complain.isNm) : complain.isNm != null) return false;
        if (compTime != null ? !compTime.equals(complain.compTime) : complain.compTime != null) return false;
        if (compTitle != null ? !compTitle.equals(complain.compTitle) : complain.compTitle != null) return false;
        if (toCompName != null ? !toCompName.equals(complain.toCompName) : complain.toCompName != null) return false;
        if (toCompDept != null ? !toCompDept.equals(complain.toCompDept) : complain.toCompDept != null) return false;
        if (compContent != null ? !compContent.equals(complain.compContent) : complain.compContent != null)
            return false;
        if (state != null ? !state.equals(complain.state) : complain.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = compId != null ? compId.hashCode() : 0;
        result = 31 * result + (compCompany != null ? compCompany.hashCode() : 0);
        result = 31 * result + (compName != null ? compName.hashCode() : 0);
        result = 31 * result + (compMobile != null ? compMobile.hashCode() : 0);
        result = 31 * result + (isNm != null ? isNm.hashCode() : 0);
        result = 31 * result + (compTime != null ? compTime.hashCode() : 0);
        result = 31 * result + (compTitle != null ? compTitle.hashCode() : 0);
        result = 31 * result + (toCompName != null ? toCompName.hashCode() : 0);
        result = 31 * result + (toCompDept != null ? toCompDept.hashCode() : 0);
        result = 31 * result + (compContent != null ? compContent.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }
}
