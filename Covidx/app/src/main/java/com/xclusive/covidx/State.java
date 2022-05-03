package com.xclusive.covidx;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class State {
    @SerializedName("statewise")
    private List<StateData> stateDataList;

    public List<StateData> getStateDataList() {
        return stateDataList;
    }

    public void setStateDataList(List<StateData> stateDataList) {
        this.stateDataList = stateDataList;
    }
}
