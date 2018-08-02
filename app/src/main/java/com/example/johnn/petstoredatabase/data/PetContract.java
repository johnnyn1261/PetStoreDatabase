package com.example.johnn.petstoredatabase.data;

import android.provider.BaseColumns;

public final class PetContract {

    // Prevent from accidentally instantiating the contract class
    private PetContract() {}

    public static final class PetEntry implements BaseColumns {
        public final static String TABLE_NAME = "pets";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PET_NAME ="name";
        public final static String COLUMN_PET_BREED = "breed";
        public final static String COLUMN_PET_GENDER = "gender";
        public final static String COLUMN_PET_WEIGHT = "weight";

        // Values for gender
        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;
    }

}

