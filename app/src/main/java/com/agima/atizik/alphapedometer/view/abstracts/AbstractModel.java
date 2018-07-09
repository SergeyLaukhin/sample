package com.agima.atizik.alphapedometer.view.abstracts;

import com.agima.atizik.alphapedometer.data.managers.DataManager;
import com.agima.atizik.alphapedometer.di.DaggerService;
import com.agima.atizik.alphapedometer.di.model.DaggerModelComponent;
import com.agima.atizik.alphapedometer.di.model.ModelComponent;
import com.agima.atizik.alphapedometer.di.model.ModelModule;

import javax.inject.Inject;

/**
 * Created by DiNo on 08.03.2018.
 */

public abstract class AbstractModel {

    @Inject
    public
    DataManager dataManager;


    public AbstractModel() {
        ModelComponent component = DaggerService.getComponent(ModelComponent.class);
        if (component == null) {
            component = createDaggerComponent();
            DaggerService.registerComponent(ModelComponent.class, component);
        }
        component.inject(this);
    }


    private ModelComponent createDaggerComponent() {
        return DaggerModelComponent.builder()
                .modelModule(new ModelModule())
                .build();
    }

}
