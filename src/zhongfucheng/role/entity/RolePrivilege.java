package zhongfucheng.role.entity;

import java.io.Serializable;

/**
 * Created by ozc on 2017/5/26.
 */
public class RolePrivilege implements Serializable {

    public RolePrivilege(CompositeKey compositeKey) {
        this.compositeKey = compositeKey;
    }

    public RolePrivilege() {
    }

    private CompositeKey compositeKey;

    public CompositeKey getCompositeKey() {
        return compositeKey;
    }

    public void setCompositeKey(CompositeKey compositeKey) {
        this.compositeKey = compositeKey;
    }
}
