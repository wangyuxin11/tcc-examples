package org.tcc.examples.test.entity;

public enum AccountStatus {

    NORMAL(1),

    TRANSFERING(2);

    private int id;

    AccountStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
