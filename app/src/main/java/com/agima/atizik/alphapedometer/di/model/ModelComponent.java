package com.agima.atizik.alphapedometer.di.model;

import com.agima.atizik.alphapedometer.view.abstracts.AbstractModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by DiNo on 08.03.2018.
 */

@Component(modules = ModelModule.class)
@Singleton
public interface ModelComponent {
    void inject(AbstractModel abstractModel);
}
