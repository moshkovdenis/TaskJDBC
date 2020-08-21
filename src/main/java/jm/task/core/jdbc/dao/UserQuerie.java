package jm.task.core.jdbc.dao;

public class UserQuerie {
    public static final String SAVE_USER = "INSERT INTO `jm`.`user` (`name`, `lastName`, `age`) VALUES (?, ?, ?)";
    public static final String CREATE_TABLE = "create table if not exists jm.user" +
            "(\n" +
            "\tid int auto_increment primary key,\n" +
            "\tname varchar(35) not null,\n" +
            "\tlastName varchar(45) not null,\n" +
            "\tage int not null,\n" +
            "\tconstraint id_UNIQUE\n" +
            "\t\tunique (id)\n" +
            ");";
    public static final String DROP_TABLE = "DROP TABLE if exists jm.user;";
    public static final String DELETE_USER = "DELETE FROM jm.user WHERE id = ?";
    public static final String CLEAR = "DELETE from jm.user;";
    public static final String SELECT_ALL = "SELECT * FROM jm.user;";
}
