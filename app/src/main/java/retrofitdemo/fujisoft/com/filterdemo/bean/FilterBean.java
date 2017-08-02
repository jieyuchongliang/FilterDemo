package retrofitdemo.fujisoft.com.filterdemo.bean;

/**
 * Created by 860617010 on 2017/8/1.
 */

public class FilterBean {
    public FilterBean(String name, boolean isSelect) {
        this.name = name;
        this.isSelect = isSelect;
    }
    private boolean isSelect;
    private String name;

    public boolean getSelect() {
        return isSelect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
