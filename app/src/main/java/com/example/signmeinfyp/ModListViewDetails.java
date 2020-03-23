package com.example.signmeinfyp;

public class ModListViewDetails
{
    private int moduleID;
    private String lecturerID;
    private String moduleName;
    private String classList;


    public ModListViewDetails(int moduleID, String lecturerID, String modName, String cList)
    {
        this.setModuleName(modName);
        this.setClassList(cList);
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getClassList() {
        return classList;
    }

    public void setClassList(String classList) {
        this.classList = classList;
    }

    public int getModuleID() {
        return moduleID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

    public String getLecturerID() {
        return lecturerID;
    }

    public void setLecturerID(String lecturerID) {
        this.lecturerID = lecturerID;
    }
}
