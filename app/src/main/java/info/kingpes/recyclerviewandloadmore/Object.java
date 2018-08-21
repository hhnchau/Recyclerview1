package info.kingpes.recyclerviewandloadmore;

/**
 * Created by Chau Huynh on 03/12/02016.
 */

public class Object{
    public int SpecID;
    public String Name;
    public int IsSelect;

    public Object(int specID, String name, int isSelect) {
        SpecID = specID;
        Name = name;
        IsSelect = isSelect;
    }

    public int getSpecID() {
        return SpecID;
    }

    public void setSpecID(int specID) {
        SpecID = specID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getIsSelect() {
        return IsSelect;
    }

    public void setIsSelect(int isSelect) {
        IsSelect = isSelect;
    }
}
