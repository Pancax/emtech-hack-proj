package pancax.emtechproj;

public interface OnTaskDoneListener {
    void onTaskDone(String responseData, String getWay);

    void onError();
}