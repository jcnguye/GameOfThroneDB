
create table characters
(
    Name        	varchar(65) not null
        primary key,
    `Date Of Birth` varchar(65) null,
    `Date of death` varchar(65) null,
    Weapon      	varchar(65) null,
    Age         	int     	null
);



create table casltecitiestowns
(
    Name varchar(65) not null
        primary key
);


create table knights
(
    Name            varchar(65) not null
        primary key,
    `Date of birth` varchar(65) null,
    `Date of death` varchar(65) null,
    Weapon          varchar(65) null,
    Age             int         null,
    constraint `Fk.Kn`
        foreign key (Name) references characters (Name)
            on update cascade on delete cascade
);


create table commoner
(
    Name        	varchar(65) not null
        primary key,
    `Date of Birth` varchar(65) null,
    `Date of Death` varchar(65) not null,
    Weapon      	varchar(65) not null,
    Age         	int     	null,
    constraint `FK.Name`
        foreign key (Name) references characters (Name)
);
create table belongto
(
    CharacterName varchar(65) not null,
    HouseName 	varchar(65) not null,
    constraint CharName
        foreign key (CharacterName) references characters (Name)
            on update cascade on delete cascade,
    constraint HouNames
        foreign key (HouseName) references houses (Name)
            on update cascade on delete cascade
);

create table wards
(
    LordName     varchar(65) not null,
    ProvinceName varchar(65) not null,
    constraint WardLord
        foreign key (LordName) references lords (Name)
            on update cascade on delete cascade,
    constraint WardProvince
        foreign key (ProvinceName) references province (Name)
            on update cascade on delete cascade
);




create table contains
(
    CCT_Name 	varchar(65) not null,
    ProvinceName varchar(65) not null,
    constraint `Con.Name`
        foreign key (CCT_Name) references casltecitiestowns (Name)
            on update cascade on delete cascade,
    constraint `Con.Prov`
        foreign key (ProvinceName) references province (Name)
            on update cascade on delete cascade
);

create table `from`
(
    `P.Name` varchar(65) not null,
    `C.Name` varchar(65) null,
    constraint CNAMEFK
        foreign key (`C.Name`) references characters (Name)
            on update cascade on delete cascade,
    constraint FROMP
        foreign key (`P.Name`) references province (Name)
            on update cascade on delete cascade
);



create table houses
(
    Name  varchar(65) not null
        primary key,
    Sigil varchar(65) null,
    Words varchar(65) null
);

create table kingsguard_to
(
    KnightName  varchar(65) not null,
    MonarchName varchar(65) not null,
    constraint `FK.Monarch`
        foreign key (MonarchName) references monarchs (Name)
            on update cascade on delete cascade,
    constraint KnightGuard
        foreign key (KnightName) references knights (Name)
            on update cascade on delete cascade
);





create table locatedin
(
    HouseName    varchar(65) not null,
    ProvinceName varchar(65) not null,
    constraint `FK.Ho`
        foreign key (HouseName) references houses (Name)
            on update cascade on delete cascade,
    constraint `FK.PN`
        foreign key (ProvinceName) references province (Name)
            on update cascade on delete cascade
);
create table lords
(
    Name            varchar(65) not null
        primary key,
    `Date of birth` varchar(65) null,
    `Date of death` varchar(65) null,
    Weapon          varchar(65) null,
    Age             int         null,
    constraint `Fk.L`
        foreign key (Name) references characters (Name)
            on update cascade on delete cascade
);

create table lordshipover
(
    LordName varchar(65) not null,
    LCCTName varchar(65) not null,
    constraint `FK.LCCT`
        foreign key (LCCTName) references casltecitiestowns (Name)
            on update cascade on delete cascade,
    constraint `FK.LordName`
        foreign key (LordName) references lords (Name)
            on update cascade on delete cascade
);
create table monarchs
(
    Name            varchar(65) not null
        primary key,
    `Date of Birth` varchar(65) null,
    `Date of death` varchar(65) null,
    Weapon          varchar(65) null,
    Age             int         null,
    constraint `Fk.Mon`
        foreign key (Name) references characters (Name)
            on update cascade on delete cascade
);

create table pledgeto
(
    KnightName varchar(65) null,
    LordsName  varchar(65) null,
    constraint `KnightNam.FK`
        foreign key (KnightName) references knights (Name)
            on update cascade on delete cascade,
    constraint `PlLordsName.fk`
        foreign key (LordsName) references lords (Name)
            on update cascade on delete cascade
);
create table province
(
    Name varchar(65) not null
        primary key
);
create table seatofpower
(
    HouseName varchar(65) not null,
    CCTName   varchar(65) not null,
    constraint `FK.CCT`
        foreign key (CCTName) references casltecitiestowns (Name)
            on update cascade on delete cascade,
    constraint `FK.House`
        foreign key (HouseName) references houses (Name)
            on update cascade on delete cascade
);

create table swears_loyalty
(
    LordName    varchar(65) not null,
    MonarchName varchar(65) not null,
    constraint `Lo.Name`
        foreign key (LordName) references lords (Name)
            on update cascade on delete cascade,
    constraint `Mon.Nam`
        foreign key (MonarchName) references monarchs (Name)
            on update cascade on delete cascade
);

