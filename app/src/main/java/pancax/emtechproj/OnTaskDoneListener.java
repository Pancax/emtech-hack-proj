package pancax.emtechproj;

public interface OnTaskDoneListener {
    void onTaskDone(String responseData);

    void onError();
}