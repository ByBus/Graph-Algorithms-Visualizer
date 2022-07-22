package visualizer.domain.usecases;

public interface Dialog {
    void show();

    void addCallBack(Callback callback);

    interface Callback {
        void onSuccess(String index);
        void onFailed();
        void onCancel();
    }
}
