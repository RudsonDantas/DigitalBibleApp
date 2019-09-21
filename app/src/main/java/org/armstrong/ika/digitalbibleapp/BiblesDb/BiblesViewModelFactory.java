package org.armstrong.ika.digitalbibleapp.BiblesDb;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BiblesViewModelFactory implements ViewModelProvider.Factory {

    protected Application application;
    private int version;

    public BiblesViewModelFactory(Application application, int version) {
        this.application = application;
        this.version = version;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(Class<T> modelClass) {
        final T t = (T) new BiblesViewModel(application, version);
        return t;
    }


}
