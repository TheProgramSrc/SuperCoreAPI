package xyz.theprogramsrc.supercoreapi.global.updater;

public interface IUpdateChecker {

    void checkUpdates();

    void onFailCheck();

    void onSuccessCheck(String lastVersion);
}
