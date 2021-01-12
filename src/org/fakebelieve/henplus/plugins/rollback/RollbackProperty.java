package org.fakebelieve.henplus.plugins.rollback;

import henplus.property.EnumeratedPropertyHolder;

class RollbackProperty extends EnumeratedPropertyHolder {

    public static final String ROLLBACK_OFF = "off";
    public static final String ROLLBACK_FAILURE = "failure";
    private static final String[] ROLLBACK_VALUES = {ROLLBACK_OFF, ROLLBACK_FAILURE};


    RollbackProperty() {
        super(ROLLBACK_VALUES);
        propertyValue = "off";
    }


    @Override
    public String getDefaultValue() {
        return "off";
    }

    @Override
    public String getShortDescription() {
        return "Set rollback conditions.";
    }

    @Override
    protected void enumeratedPropertyChanged(int i, String s) throws Exception {
        propertyValue = s;
    }
}
