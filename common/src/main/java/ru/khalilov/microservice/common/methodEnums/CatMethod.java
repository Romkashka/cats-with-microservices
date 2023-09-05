package ru.khalilov.microservice.common.methodEnums;

public enum CatMethod {
    GET_ALL_CATS,
    GET_CAT,
    CREATE,
    UPDATE,
    DELETE,
    SET_OWNER,
    MAKE_FRIENDS,
    UNMAKE_FRIENDS,
    FILTER_BY_COLOR,
    FILTER_BY_COLOR_AND_OWNER,
    FILTER_BY_BREED,
    FILTER_BY_BREED_AND_OWNER,
    FILTER_BY_NAME,
    FILTER_BY_NAME_AND_OWNER,
    FILTER_BY_BIRTHDAY,
    FILTER_BY_BIRTHDAY_AND_OWNER,
    FILTER_BY_OWNER;

    public static class CatCommandsComponent {
        public static CatMethod GET_ALL_CATS = CatMethod.GET_ALL_CATS;
        public static CatMethod GET_CAT = CatMethod.GET_CAT;
        public static CatMethod CREATE = CatMethod.CREATE;
        public static CatMethod UPDATE = CatMethod.UPDATE;
        public static CatMethod DELETE = CatMethod.DELETE;
        public static CatMethod MAKE_FRIENDS = CatMethod.MAKE_FRIENDS;
        public static CatMethod UNMAKE_FRIENDS = CatMethod.UNMAKE_FRIENDS;
        public static CatMethod FILTER_BY_COLOR = CatMethod.FILTER_BY_COLOR;
        public static CatMethod FILTER_BY_COLOR_AND_OWNER = CatMethod.FILTER_BY_COLOR_AND_OWNER;
        public static CatMethod FILTER_BY_BREED = CatMethod.FILTER_BY_BREED;
        public static CatMethod FILTER_BY_BREED_AND_OWNER = CatMethod.FILTER_BY_BREED_AND_OWNER;
        public static CatMethod FILTER_BY_NAME = CatMethod.FILTER_BY_NAME;
        public static CatMethod FILTER_BY_NAME_AND_OWNER = CatMethod.FILTER_BY_NAME_AND_OWNER;
        public static CatMethod FILTER_BY_BIRTHDAY = CatMethod.FILTER_BY_BIRTHDAY;
        public static CatMethod FILTER_BY_BIRTHDAY_AND_OWNER = CatMethod.FILTER_BY_BIRTHDAY_AND_OWNER;
        public static CatMethod FILTER_BY_OWNER = CatMethod.FILTER_BY_OWNER;
    }
}
