package com.dabanin.moneytransfer.di;

import com.dabanin.moneytransfer.component.AccountNumberIncrementComponent;
import com.dabanin.moneytransfer.repository.AccountRepository;
import com.google.inject.AbstractModule;

public class DependencyContext extends AbstractModule {

    @Override
    protected void configure() {
        bind(AccountRepository.class).toInstance(new AccountRepository());
        bind(AccountNumberIncrementComponent.class).toInstance(new AccountNumberIncrementComponent());
    }
}
